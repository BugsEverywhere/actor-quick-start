package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import akka.japi.function.Function;

public class GreeterMain extends AbstractBehavior<GreeterMain.HelloMessage> {

    public static class HelloMessage {
        public final String name;

        public HelloMessage(String name) {
            this.name = name;
        }
    }

    //持有一个消息接受者的引用，同时这个消息接收者Actor也是自己的子Actor
    private final ActorRef<Greeter.GreetMessage> greeter;

    public static Behavior<HelloMessage> create() {
        return Behaviors.setup(new Function<ActorContext<HelloMessage>, Behavior<HelloMessage>>() {
            @Override
            public Behavior<HelloMessage> apply(ActorContext<HelloMessage> context) {
                return new GreeterMain(context);
            }
        });
    }

    private GreeterMain(ActorContext<HelloMessage> context) {
        super(context);
        //作为父Actor创建一个子Actor
        //#create-actors
        greeter = context.spawn(Greeter.create(), "greeter");
        //#create-actors
    }

    @Override
    public Receive<HelloMessage> createReceive() {
        return newReceiveBuilder().onMessage(HelloMessage.class, this::onSayHello).build();
    }

    //收到一条来自Guardian Actor的SayHello消息之后触发的方法，在createReceive中调用
    private Behavior<HelloMessage> onSayHello(HelloMessage command) {
        //#create-actors
        ActorRef<Greeter.RespondMessage> replyTo =
                getContext().spawn(GreeterBot.create(3), command.name);
        greeter.tell(new Greeter.GreetMessage(command.name, replyTo));
        //#create-actors
        return this;
    }
}
