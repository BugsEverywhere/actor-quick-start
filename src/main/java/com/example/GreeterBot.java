package com.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

public class GreeterBot extends AbstractBehavior<Greeter.GreetedMessage> {

    public static Behavior<Greeter.GreetedMessage> create(int max) {
        return Behaviors.setup(context -> new GreeterBot(context, max));
    }

    private final int max;
    private int greetingCounter;

    private GreeterBot(ActorContext<Greeter.GreetedMessage> context, int max) {
        super(context);
        this.max = max;
    }

    //收到消息之后触发的方法
    @Override
    public Receive<Greeter.GreetedMessage> createReceive() {
        return newReceiveBuilder().onMessage(Greeter.GreetedMessage.class, this::onGreeted).build();
    }

    private Behavior<Greeter.GreetedMessage> onGreeted(Greeter.GreetedMessage message) {
        greetingCounter++;
        getContext().getLog().info("Greeting {} for {}", greetingCounter, message.whom);
        if (greetingCounter == max) {
            return Behaviors.stopped();
        } else {
            message.from.tell(new Greeter.GreetMessage(message.whom, getContext().getSelf()));
            return this;
        }
    }
}
