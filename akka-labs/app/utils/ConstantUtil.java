package utils;

/**
 * Created by w6c on 27/07/2014.
 */
public class ConstantUtil {

    public static final String SES_HOST_SMTP = "email-smtp.us-west-2.amazonaws.com";
    public static final Integer SES_PORT_SMTP = 25;
    public static final String SES_USERNAME_SMTP = "AKIAJWBN5JT3TDCY3NSQ";
    public static final String SES_PASSWORD_SMTP = "AoqTzQVsC6Yk11YvNtsCYciCD6rFMockeyHpJ6mmvzjk";
    //
    public static final String SES_EMAIL_FROM = "carteira@dceunifacs.com";
    public static final String SES_EMAIL_TO_OPERACIONAL = "user2.integracao@gmail.com"; //"emerson@unifacs.br"; //"carteira@dceunifacs.com";
    //public static final String SES_ASSUNTO_OPERACIONAL = "";
    public static final String SES_ASSUNTO_CONFIRMACAO = "Solicitação de Carteirinha de Estudante DCE UNIFACS";
    //
    public static final String BUCKET_NAME = "elasticbeanstalk-sa-east-1-172718621343"; //"elasticbeanstalk-us-east-1-503252033224";
    //public static final String DIRETORIO_FOTOS = "deposito/appCarteirinha/unifacs/fotos/"; //"/deposito/appCarteirinha/unifacs/fotos"
    public static final String DIRETORIO_FOTOS = "deposito/";
    public static final String DIRETORIO_DOCUMENTOS = "deposito/appCarteirinha/unifacs/documentos/";
    //
    public static final String PREFIX_FOTO = "foto_";
    public static final String PREFIX_DOCUMENTO = "documento_";
    public static final String KEY_MULTIPARTI_FILE_UPLOAD_FOTO = "fotoFile";
    public static final String KEY_MULTIPARTI_FILE_UPLOAD_DOCUMENTO = "documentoFile";

}
