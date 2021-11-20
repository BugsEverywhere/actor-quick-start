package com.example;

import akka.actor.typed.ActorSystem;
import com.example.msg.HelloMessage;

import java.io.IOException;

public class AkkaQuickstart {
    public static void main(String[] args) {
        //#actor-system
        final ActorSystem<HelloMessage> greeterMain = ActorSystem.create(GreeterGuardianBehavior.create(), "helloakka");
        //#actor-system

        //#main-send-messages
        greeterMain.tell(new HelloMessage("Charles"));
        //#main-send-messages

        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ignored) {
        } finally {
            greeterMain.terminate();
        }
    }
}
