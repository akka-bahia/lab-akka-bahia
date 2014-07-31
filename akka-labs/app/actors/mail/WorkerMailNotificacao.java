package actors.mail;

import actors.mail.EmailNotificacaoMessage;
import akka.actor.UntypedActor;

import utils.ConstantUtil;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.EmailException;

/**
 * Created by w6c on 26/07/2014.
 */

public class WorkerMailNotificacao extends UntypedActor{

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof EmailNotificacaoMessage) {

            System.out.println("START WorkerMailNotificacao: " + new java.util.Date());
            System.out.println("TO: " + ((EmailNotificacaoMessage) message).to);
            System.out.println("WorkerMailNotificacao ID: " + System.identityHashCode(this));
            System.out.println("");

            try {
                HtmlEmail email = new HtmlEmail();
                email.setHostName(ConstantUtil.SES_HOST_SMTP);
                email.setSmtpPort(ConstantUtil.SES_PORT_SMTP);

                email.setAuthentication(ConstantUtil.SES_USERNAME_SMTP,ConstantUtil.SES_PASSWORD_SMTP);
                email.setStartTLSEnabled(true);
                email.setStartTLSRequired(true);
                //email.setDebug(true);
                email.setFrom(ConstantUtil.SES_EMAIL_FROM);
                email.setSubject(((EmailNotificacaoMessage) message).subject);
                //email.setSubject(ConstantUtil.SES_ASSUNTO_CONFIRMACAO);


                email.addTo(((EmailNotificacaoMessage) message).to);
                email.setTextMsg(((EmailNotificacaoMessage) message).messageBodyText);
                email.setHtmlMsg(((EmailNotificacaoMessage) message).templateHtmlMessage);

                email.send();

            } catch (EmailException ex) {
                ex.printStackTrace();
            }

            System.out.println("END WorkerMailNotificacao: " + new java.util.Date());
            System.out.println("");
        }else{
            System.out.println("***unhandled****");
            unhandled(message);
        }
    }
}
