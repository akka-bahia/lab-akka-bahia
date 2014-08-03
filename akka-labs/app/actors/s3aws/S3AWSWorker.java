package actors.s3aws;

import akka.actor.UntypedActor;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import com.amazonaws.services.s3.model.PutObjectRequest;

import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import utils.ToolsUtil;

import java.util.Date;

/**
 * Created by w6c on 27/07/2014.
 */

public class S3AWSWorker extends UntypedActor{

    TransferManager tm;

    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof S3FileObject){
            try{
                //Criar TransferManager recebendo cliente autenticado com AWS Credentials
                tm =  new TransferManager(ToolsUtil.awsCredentials);

                //Mensagem do ator na verdade é POJO com info do Objecto S3
                final S3FileObject s3FileObject = (S3FileObject) message;

                //NOME do ARQUIVO no bucket S3
                String nameObjectInBucket = s3FileObject.getBucketPath() + s3FileObject.getKeyFileName();

                //CRIA arquivo PutObjectRequest e INCIA transferencia
                Upload upload = tm.upload(new PutObjectRequest(s3FileObject.getBucketName(),
                        nameObjectInBucket, s3FileObject.getFile()));

                //Força aguardar a transfencia concluir
                upload.waitForCompletion();

                //Transferencia Executada com sucesso
                if(upload.isDone()){
                    System.out.println("$$$$$$$$$$ S3AWSWorker - isDone [" + new Date() + "] $$$$$$$$$$");

                    //#CLEAN-RESOURCE: Apaga arquivo da pasta temporaria do sistema de arquivo
                    s3FileObject.getFile().delete();
                }

            } catch (AmazonServiceException ase) {

                System.out.println("Caught an AmazonServiceException, which "
                        + "means your request made it "
                        + "to Amazon S3, but was rejected with an error response"
                        + " for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                System.out.println("Error Type:       " + ase.getErrorType());
                System.out.println("Request ID:       " + ase.getRequestId());


            } catch (AmazonClientException ace) {

                System.out.println("Caught an AmazonClientException, which "
                        + "means the client encountered "
                        + "an internal error while trying to "
                        + "communicate with S3, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ace.getMessage());
            }catch(Exception ex){

                ex.printStackTrace();
            }finally {

                //#CLEAN-RESOURCE: Desalocando o AWS TransferManager
                if(tm != null){
                    tm.shutdownNow();
                }
            }

        }else{
            System.out.println("**** S3AWSWorker - unhandled ****");
            unhandled(message);
        }
    }
}