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

