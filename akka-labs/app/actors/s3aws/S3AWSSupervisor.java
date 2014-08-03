package actors.s3aws;

import akka.actor.*;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.stop;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.Directive;

/**
 * Created by emerson on 30/07/2014.
 */

public class S3AWSSupervisor extends UntypedActor{

    //Criando Estrantegia de Recuperacao de Falhas
    //NAO ESTA FUNCIONANDO POIS O FILHO(Woker) ESTA COM TRAMENTO DE EXCEÇOES
    SupervisorStrategy strategy = new OneForOneStrategy(5, Duration.create("30 seconds"), new Function<Throwable, SupervisorStrategy.Directive>(){

        @Override
        public Directive apply(Throwable ex) throws Exception {

            if(ex instanceof AmazonClientException || ex instanceof AmazonServiceException ){
                return restart();
            }else if(ex instanceof NullPointerException){
                return stop();
            }else{
                return escalate();
            }
        }
    });


    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    //CRIANDO Worker
    ActorRef s3Worker = context().actorOf(Props.create(S3AWSWorker.class));

    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof S3FileObject){
            //Enviando a mensagem
            s3Worker.forward(message, getContext());
        }else{
            System.out.println("***** S3AWSSupervisor - unhandled ****");
            unhandled(message);
        }
    }
}
