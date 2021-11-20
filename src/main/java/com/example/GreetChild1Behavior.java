package com.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.example.msg.GreetMessage;
import com.example.msg.RespondMessage;

public class GreetChild1Behavior extends AbstractBehavior<GreetMessage> {

  public static Behavior<GreetMessage> create() {
    return Behaviors.setup(GreetChild1Behavior::new);
  }

  private GreetChild1Behavior(ActorContext<GreetMessage> context) {
    super(context);
  }

  @Override
  public Receive<GreetMessage> createReceive() {
    return newReceiveBuilder().onMessage(GreetMessage.class, this::behave).build();
  }

  private Behavior<GreetMessage> behave(GreetMessage msg) {
    getContext().getLog().info("Hello {}!", msg.fromWhom);
    //#greeter-send-message
    msg.fromWhomActor.tell(new RespondMessage("child1", getContext().getSelf()));
    //#greeter-send-message
    return this;
  }
}

