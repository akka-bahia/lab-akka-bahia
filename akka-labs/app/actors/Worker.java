package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import play.libs.Akka;

import java.util.Date;

/**
 * Created by w6c on 25/07/2014.
 */

public class Worker extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {

        String MESSAGEM1 = message +  " - 111";
        String MESSAGEM2 = message +  " - 222";
        String MESSAGEM3 = message +  " - 333";

        if(message instanceof String){
            System.out.println(":::::::::::: " + MESSAGEM1 + " ::::::::::::");
            System.out.println("ID: " + System.identityHashCode(this) + " - " + new Date());
            System.out.println("");

            Thread.sleep(5000);

            System.out.println(":::::::::::: " + MESSAGEM2 + " ::::::::::::");
            System.out.println("ID: " + System.identityHashCode(this) + " - " + new Date());
            System.out.println("");

            Thread.sleep(3000);

            System.out.println(":::::::::::: " + MESSAGEM3 + " ::::::::::::");
            System.out.println("ID: " + System.identityHashCode(this) + " - " + new Date());
            System.out.println("");

        }
    }
}
