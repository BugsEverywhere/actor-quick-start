package com.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.example.msg.GreetMessage;
import com.example.msg.RespondMessage;

public class GreetChild2Behavior extends AbstractBehavior<RespondMessage> {

    public static Behavior<RespondMessage> create(int max) {
        return Behaviors.setup(context -> new GreetChild2Behavior(context, max));
    }

    private final int max;
    private int greetingCounter;

    private GreetChild2Behavior(ActorContext<RespondMessage> context, int max) {
        super(context);
        this.max = max;
    }

    //收到消息之后触发的方法，这里onMessage方法的第一个参数指定了消息的类型，如果是GreetedMessage类型的消息就执行后面的Function，除了onMessage方法还有onMessageEquals，也就是收到指定字符串的消息时
    @Override
    public Receive<RespondMessage> createReceive() {
        return newReceiveBuilder().onMessage(RespondMessage.class, this::onRespond).build();
    }

    private Behavior<RespondMessage> onRespond(RespondMessage message) {
        greetingCounter++;
        getContext().getLog().info("Greeting {} for {}", greetingCounter, message.whom);
        if (greetingCounter == max) {
            return Behaviors.stopped();
        } else {
            message.from.tell(new GreetMessage(message.whom, getContext().getSelf()));
            return this;
        }
    }
}
