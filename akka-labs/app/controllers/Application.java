package controllers;

import actors.Supervisor;
import actors.s3aws.S3AWSSupervisor;
import actors.s3aws.S3FileObject;
//import actors.s3aws.S3Helper;
import akka.actor.ActorRef;
import akka.actor.Props;
import play.Play;
import play.cache.Cache;
import play.libs.Akka;
import play.mvc.*;

import java.io.File;
import java.util.Date;
import java.util.Objects;

import actors.mail.EmailNotificacaoMessage;
import actors.mail.EmailOperacionalMessage;

import services.MailServiceHelper;
import services.StorageServiceHelper;
import utils.ConstantUtil;
import views.html.*;

public class Application extends Controller {

    public static Result index() {

        for(int i=1;  i<=20; i++){
            System.out.println("");
        }

        System.out.println("Application - index");
        System.out.println("");

        return ok(index.render("Your new application is ready."));
    }

    public static Result blank(){

        for(int i=1;  i<=20; i++){
            System.out.println("");
        }

        System.out.println("Application - blank");
        System.out.println("");

        return ok(play.libs.Json.toJson("blank-blank-blank-blank-blank-blank-blank-blank"));
    }

    public Result ses(String to){
        String subject = "Confirmacao - " + to + " - " + new Date();
        String MsgTextBody = "TExto Alternativo";
        String MsgHtmlBody = "Confirmacao - " + to + " - " + new Date();


        String subjectOperacional = "Operacional - " + to + " - " + new Date();
        String MsgTextBodyOperacional = "TExto Operacional";
        String MsgHtmlBodyOperacional = "Operacional - " + to + " - " + new Date();
        File attachementTest = new File("C:\\z800.jpg");

        String resultStr = "to: " + to+ " - " + new Date();

        System.out.println(resultStr);

        EmailNotificacaoMessage notificacaoMessage = new EmailNotificacaoMessage(subject, MsgTextBody, MsgHtmlBody, to);
        EmailOperacionalMessage operacionalMessage = new EmailOperacionalMessage(subjectOperacional, MsgTextBodyOperacional, MsgHtmlBodyOperacional, attachementTest, attachementTest);


        //supervisorRef.tell(new OperacionalMailPOJO(to), supervisorRef);
        //mailServiceHelper.sendMailOperacional(operacionalMailPOJO);
        MailServiceHelper.sendMailOperacional(operacionalMessage);

        //supervisorRef.tell(notificacaoMailPOJO, supervisorRef);
        //mailServiceHelper.sendMailNotificacao(notificacaoMailPOJO);
        MailServiceHelper.sendMailNotificacao(notificacaoMessage);

        return ok(resultStr);
    }

    //%%%%%%%%%%%%%%%%%%%%
    public static Result s3() {

        String paramNomeArquivoFoto = "111-Z800-2.jpg";
        File fileTemp = new File("C:\\Z800-2.jpg");

        S3FileObject s3FileObject = new S3FileObject(ConstantUtil.BUCKET_NAME, ConstantUtil.DIRETORIO_FOTOS, paramNomeArquivoFoto, fileTemp);

        //ActorRef actorRefSupervisor = Akka.system().actorOf(Props.create(S3AWSSupervisor.class));
        //actorRefSupervisor.tell(s3FileObject, ActorRef.noSender());
        //S3Helper.putS3(s3FileObject);
        StorageServiceHelper storageService = new StorageServiceHelper();
        storageService.putObjectS3(s3FileObject);

        return ok("Application - S3 - PUT: " + " - " + new Date());
    }


    public static Result setCache(String keyObject){

        String key = "101";
        String object = keyObject; //"A-1-A-1-A-1";
        int duration = 30;

        Cache.set(key, object, duration);

        return ok("setCache: " + new Date());
    }

    public static Result getCache(String key){

        String objRetrive = (String) Cache.get("101");

        return ok("objRetrive: " + objRetrive);
    }
}
