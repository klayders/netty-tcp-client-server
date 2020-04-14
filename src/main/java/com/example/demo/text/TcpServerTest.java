package com.example.demo.text;

import java.time.Duration;
import reactor.netty.tcp.TcpServer;

public class TcpServerTest {
//  TODO: make
//   curl localhost:8080
  public static void main(String[] args) throws InterruptedException {
    TcpServer.create()
        .wiretap(true)
        .port(8080)
        .handle((inbound, outbound) ->
            outbound.sendString(
                inbound.receive()
                .asString()
                .map(text -> {
                  System.out.println(text);
                  return text;
                })
                    .repeatWhen(publisher -> publisher.delayElements(Duration.ofSeconds(1))))
            )
        .bind()
        .subscribe();

    Thread.sleep(100000);
  }
}