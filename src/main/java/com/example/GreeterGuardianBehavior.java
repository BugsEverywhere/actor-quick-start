package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import akka.japi.function.Function;
import com.example.msg.GreetMessage;
import com.example.msg.HelloMessage;
import com.example.msg.RespondMessage;

public class GreeterGuardianBehavior extends AbstractBehavior<HelloMessage> {

    //持有一个消息接受者的引用，同时这个消息接收者Actor也是自己的子Actor
    private final ActorRef<GreetMessage> child1;

    public static Behavior<HelloMessage> create() {
        //之所以使用Behaviors.setup来创建Actor是因为，可以延迟加载
        return Behaviors.setup(new Function<ActorContext<HelloMessage>, Behavior<HelloMessage>>() {
            @Override
            public Behavior<HelloMessage> apply(ActorContext<HelloMessage> context) {
                return new GreeterGuardianBehavior(context);
            }
        });
    }

    private GreeterGuardianBehavior(ActorContext<HelloMessage> context) {
        super(context);
        //作为父Actor创建一个子Actor
        //#create-actors
        child1 = context.spawn(GreetChild1Behavior.create(), "greeter");
        //#create-actors
    }

    @Override
    public Receive<HelloMessage> createReceive() {
        return newReceiveBuilder().onMessage(HelloMessage.class, this::onSayHello).build();
    }

    //收到一条来自Guardian Actor的SayHello消息之后触发的方法，在createReceive中调用
    private Behavior<HelloMessage> onSayHello(HelloMessage msg) {
        //#create-actors
        ActorRef<RespondMessage> child2 = getContext().spawn(GreetChild2Behavior.create(3), msg.name);
        child1.tell(new GreetMessage(msg.name, child2));
        //#create-actors
        return this;
    }
}
