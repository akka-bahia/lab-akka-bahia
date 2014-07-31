package services;

import actors.mail.SupervisorMail;
import akka.actor.ActorRef;
import akka.actor.Props;
import play.libs.Akka;

import actors.mail.EmailNotificacaoMessage;
import actors.mail.EmailOperacionalMessage;

import java.util.Date;

/**
 * Created by w6c on 26/07/2014.
 */


public class MailServiceHelper {

    static ActorRef supervisorRef =  Akka.system().actorOf(Props.create(SupervisorMail.class), "supervisor-Ref");

    public MailServiceHelper() {
        System.out.println("MailServiceHelper ID: " + System.identityHashCode(this) + " - " + new Date());
    }

    public static void sendMailNotificacao(EmailNotificacaoMessage emailNotificacaoMessage){

        supervisorRef.tell(emailNotificacaoMessage, supervisorRef);
    }

    public static void sendMailOperacional(EmailOperacionalMessage emailOperacionalMessage){
        supervisorRef.tell(emailOperacionalMessage, supervisorRef);
    }

    public void terminate(){
        //TODO
    }
}
