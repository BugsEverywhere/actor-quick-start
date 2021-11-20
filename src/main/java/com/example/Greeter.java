package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

// #greeter
public class Greeter extends AbstractBehavior<Greeter.GreetMessage> {

  //监听对端发过来的消息就是GreetMessage类型，里面包含一个ActorRef引用，就是对端的引用
  public static final class GreetMessage {
    public final String whom;
    public final ActorRef<RespondMessage> replyTo;

    public GreetMessage(String whom, ActorRef<RespondMessage> replyTo) {
      this.whom = whom;
      this.replyTo = replyTo;
    }
  }

  //收到对端消息之后，将自身作为GreetedMessage发送回去，消息中包含自己的ActorRef引用
  public static final class RespondMessage {
    public final String whom;
    public final ActorRef<GreetMessage> from;

    public RespondMessage(String whom, ActorRef<GreetMessage> from) {
      this.whom = whom;
      this.from = from;
    }

//// #greeter
//    @Override
//    public boolean equals(Object o) {
//      if (this == o) {
//        return true;
//      }
//      if (o == null || getClass() != o.getClass()) {
//        return false;
//      }
//      Greeted greeted = (Greeted) o;
//      return Objects.equals(whom, greeted.whom) &&
//              Objects.equals(from, greeted.from);
//    }
//
//    @Override
//    public int hashCode() {
//      return Objects.hash(whom, from);
//    }
//
//    @Override
//    public String toString() {
//      return "Greeted{" +
//              "whom='" + whom + '\'' +
//              ", from=" + from +
//              '}';
//    }
// #greeter
  }

  public static Behavior<GreetMessage> create() {
    return Behaviors.setup(Greeter::new);
  }

  private Greeter(ActorContext<GreetMessage> context) {
    super(context);
  }

  @Override
  public Receive<GreetMessage> createReceive() {
    return newReceiveBuilder().onMessage(GreetMessage.class, this::onGreet).build();
  }

  private Behavior<GreetMessage> onGreet(GreetMessage command) {
    getContext().getLog().info("Hello {}!", command.whom);
    //#greeter-send-message
    command.replyTo.tell(new RespondMessage(command.whom, getContext().getSelf()));
    //#greeter-send-message
    return this;
  }
}
// #greeter

