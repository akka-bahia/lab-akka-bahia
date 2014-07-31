package actors.mail;

/**
 * Created by w6c on 27/07/2014.
 */
public class EmailNotificacaoMessage {

    public final String subject;
    public final String messageBodyText;
    public final String templateHtmlMessage;
    public final String to;


    public EmailNotificacaoMessage(String subject, String messageBodyText, String templateHtmlMessage, String to) {
        this.subject = subject;
        this.messageBodyText = messageBodyText;
        this.templateHtmlMessage = templateHtmlMessage;
        this.to = to;
    }
}
