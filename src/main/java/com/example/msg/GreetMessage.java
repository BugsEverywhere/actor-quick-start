package com.example.msg;

import akka.actor.typed.ActorRef;

/**
 * @author chenzhuo(zhiyue)
 */
//监听对端发过来的消息就是GreetMessage类型，里面包含一个ActorRef引用，就是对端的引用
public final class GreetMessage {
    public final String fromWhom;
    public final ActorRef<RespondMessage> fromWhomActor;

    public GreetMessage(String fromWhom, ActorRef<RespondMessage> fromWhomActor) {
        this.fromWhom = fromWhom;
        this.fromWhomActor = fromWhomActor;
    }
}
