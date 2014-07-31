package actors.mail;

import java.io.File;

/**
 * Created by w6c on 27/07/2014.
 */

public class EmailOperacionalMessage {

    public final String subject;
    public final String messageBodyText;
    public final String templateHtmlMessage;
    //public final String to;
    public final File attachmentFoto;
    public final File attachmentDocumento;


    public EmailOperacionalMessage(String subject, String messageBodyText, String templateHtmlMessage, File attachmentFoto, File attachmentDocumento) {
        this.subject = subject;
        this.messageBodyText = messageBodyText;
        this.templateHtmlMessage = templateHtmlMessage;
        this.attachmentFoto = attachmentFoto;
        this.attachmentDocumento = attachmentDocumento;
    }
}
