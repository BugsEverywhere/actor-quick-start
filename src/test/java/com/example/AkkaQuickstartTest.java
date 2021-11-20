package com.example;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import com.example.msg.GreetMessage;
import com.example.msg.RespondMessage;
import org.junit.ClassRule;
import org.junit.Test;

//#definition
public class AkkaQuickstartTest {

    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();
//#definition

    //#test
    @Test
    public void testGreeterActorSendingOfGreeting() {
        TestProbe<RespondMessage> testProbe = testKit.createTestProbe();
        ActorRef<GreetMessage> underTest = testKit.spawn(GreetChild1Behavior.create(), "greeter");
        underTest.tell(new GreetMessage("Charles", testProbe.getRef()));
        testProbe.expectMessage(new RespondMessage("Charles", underTest));
    }
    //#test
}
