package services;

import actors.s3aws.S3AWSSupervisor;
import actors.s3aws.S3FileObject;
import akka.actor.ActorRef;
import akka.actor.Props;
import play.cache.Cache;
import play.libs.Akka;
import utils.ConstantUtil;

import java.io.File;

/**
 * Created by w6c on 30/07/2014.
 */
public class StorageServiceHelper {

    ActorRef actorRefSupervisor = Akka.system().actorOf(Props.create(S3AWSSupervisor.class));


    public void salvarFotoStorage(String nomeFileFotoCache){

        //PEGA FIle do ehCache
        File oldFile = (File) Cache.get(nomeFileFotoCache);

        //Cria NOVO nome no mesmo diretorio do arquivo do Cache(MultiParti recebido pelo Play no controller)
        String newPath = oldFile.getParent() + "\\" + nomeFileFotoCache;

        File newFileToS3 = new File(newPath);

        //CRIA arquivo com NOVO Nome.. O antigo é automativamente excluido pelo java.io.File
        if(oldFile.renameTo(newFileToS3)){
            S3FileObject s3FileObject = new S3FileObject(ConstantUtil.BUCKET_NAME, ConstantUtil.DIRETORIO_FOTOS, nomeFileFotoCache, newFileToS3);

            //Envia mensagem p/ Supervidor do Actor
            actorRefSupervisor.tell(s3FileObject, ActorRef.noSender());

            //#CLEAN-RESOURCE: Limpa arquivo do ehCache
            Cache.remove(nomeFileFotoCache);

        }else{
            System.out.println("StorageServiceHelper - salvarFotoStorage - fileToS3.renameTo: FALHOU");
        }
    }


    public void putObjectS3(S3FileObject s3FileObject){

        actorRefSupervisor.tell(s3FileObject, ActorRef.noSender());
    }


    public void getObjectS3(S3FileObject s3FileObject){
        //

    }
}
