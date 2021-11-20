package com.example.msg;

import akka.actor.typed.ActorRef;

import java.util.Objects;

/**
 * @author chenzhuo(zhiyue)
 */
//收到对端消息之后，将自身作为GreetedMessage发送回去，消息中包含自己的ActorRef引用
public final class RespondMessage {
    public final String whom;
    public final ActorRef<GreetMessage> from;

    public RespondMessage(String whom, ActorRef<GreetMessage> from) {
        this.whom = whom;
        this.from = from;
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
        return Objects.equals(whom, greeted.whom) &&
                Objects.equals(from, greeted.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(whom, from);
    }

    @Override
    public String toString() {
        return "Greeted{" +
                "whom='" + whom + '\'' +
                ", from=" + from +
                '}';
    }
// #greeter
}
