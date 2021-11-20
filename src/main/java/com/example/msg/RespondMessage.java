package com.example.msg;

import akka.actor.typed.ActorRef;

import java.util.Objects;

/**
 * @author chenzhuo(zhiyue)
 */
//收到对端消息之后，将自身作为GreetedMessage发送回去，消息中包含自己的ActorRef引用
public final class RespondMessage {
    public final String fromWhom;
    public final ActorRef<GreetMessage> fromWhomActor;

    public RespondMessage(String fromWhom, ActorRef<GreetMessage> fromWhomActor) {
        this.fromWhom = fromWhom;
        this.fromWhomActor = fromWhomActor;
    }

    //// #greeter
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RespondMessage greeted = (RespondMessage) o;
        return Objects.equals(fromWhom, greeted.fromWhom) &&
                Objects.equals(fromWhomActor, greeted.fromWhomActor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromWhom, fromWhomActor);
    }

    @Override
    public String toString() {
        return "Respond{" +
                "fromWhom='" + fromWhom + '\'' +
                ", fromWhomActor=" + fromWhomActor +
                '}';
    }
// #greeter
}
