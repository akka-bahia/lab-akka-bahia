package actors;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.Props;

import akka.actor.ActorContext;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;
import static akka.actor.SupervisorStrategy.escalate;


/**
 * Created by w6c on 26/07/2014.
 */


public class Supervisor extends UntypedActor {

    private SupervisorStrategy strategy = new OneForOneStrategy(
            10, Duration.create("1 minute"), new Function<Throwable, Directive>() {
        @Override
        public Directive apply(Throwable t) {
            if (t instanceof ArithmeticException) return resume();
            else if (t instanceof NullPointerException) return restart();
            else return escalate();
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    ActorRef worker = getContext().actorOf(Props.create(Worker.class));

    public void onReceive(Object message) throws Exception {

        if (message instanceof String) {
            worker.forward(message, getContext());
        }
    }
}
