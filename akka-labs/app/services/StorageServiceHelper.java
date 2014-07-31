package services;

import actors.s3aws.S3AWSSupervisor;
import actors.s3aws.S3FileObject;
import akka.actor.ActorRef;
import akka.actor.Props;
import play.libs.Akka;

/**
 * Created by w6c on 30/07/2014.
 */
public class StorageServiceHelper {

    ActorRef actorRefSupervisor = Akka.system().actorOf(Props.create(S3AWSSupervisor.class));

    public void putObjectS3(S3FileObject s3FileObject){

        actorRefSupervisor.tell(s3FileObject, ActorRef.noSender());
    }


    public void getObjectS3(S3FileObject s3FileObject){
        //

    }
}
