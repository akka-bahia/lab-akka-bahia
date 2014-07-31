package actors.mail;

import actors.mail.EmailOperacionalMessage;
import akka.actor.UntypedActor;

import utils.ConstantUtil;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.EmailException;

/**
 * Created by w6c on 26/07/2014.
 */

public class WorkerMailOperacional extends UntypedActor{

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof EmailOperacionalMessage) {

            System.out.println("START WorkerMailOperacional: " + new java.util.Date());
            System.out.println("TO: " + ConstantUtil.SES_EMAIL_TO_OPERACIONAL);
            System.out.println("WorkerMailOperacional ID: " + System.identityHashCode(this));
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
                email.addTo(ConstantUtil.SES_EMAIL_TO_OPERACIONAL);

                email.setSubject(((EmailOperacionalMessage) message).subject);
                email.setTextMsg(((EmailOperacionalMessage) message).messageBodyText);
                email.setHtmlMsg(((EmailOperacionalMessage) message).templateHtmlMessage);

                email.attach(((EmailOperacionalMessage) message).attachmentFoto);
                email.attach(((EmailOperacionalMessage) message).attachmentDocumento);

                email.send();

            } catch (EmailException ex) {
                ex.printStackTrace();
            }

            System.out.println("END WorkerMailOperacional: " + new java.util.Date());
            System.out.println("");
        }

    }
}
