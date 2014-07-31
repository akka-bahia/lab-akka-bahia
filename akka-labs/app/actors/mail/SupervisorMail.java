package actors.mail;

import akka.actor.*;

import akka.japi.Function;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.escalate;

import akka.actor.SupervisorStrategy.Directive;

import java.util.Date;

/**
 * Created by w6c on 26/07/2014.
 */

public class SupervisorMail extends UntypedActor{

    public SupervisorMail() {
        System.out.println("SupervisorMail ID: " + System.identityHashCode(this) + " - " + new Date());
    }

    private SupervisorStrategy strategy = new OneForOneStrategy(5, Duration.create("1 minute"),
            new Function<Throwable, Directive>() {

                @Override
                public Directive apply(Throwable exception) throws Exception {

                    if(exception == null  ){
                        System.out.println("");
                        System.out.println("SupervisorMail - SupervisorStrategy - exception - " + "null ;= resume");

                        return resume();

                    }else if(exception instanceof NullPointerException){

                        System.out.println("");
                        System.out.println("SupervisorMail - SupervisorStrategy - exception - " + "NullPointerException ;= restart");

                        return restart();

                    }else{
                        return escalate();
                    }
                }
            });


    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    ActorRef workerRefNotificacao = getContext().actorOf(Props.create(WorkerMailNotificacao.class));
    ActorRef workerRefOperacao = getContext().actorOf(Props.create(WorkerMailOperacional.class));

    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof EmailOperacionalMessage){
            workerRefOperacao.forward(message, getContext());

        }else if(message instanceof EmailNotificacaoMessage){
            workerRefNotificacao.forward(message, getContext());
        }else{
            System.out.println("SupervisorMail - onReceive - unhandled - " + message.toString());
            unhandled(message);
        }
    }

}
