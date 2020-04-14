package com.example.demo.text;

import java.time.Duration;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

public class TcpClientTest {

  public static void main(String[] args) throws InterruptedException {
    TcpClient.create()
        .port(8080)
        .handle((inbound, outbound) ->
                    outbound.sendString(
                        Mono.just("hello")
                            .repeatWhen(publisher -> publisher.delayElements(Duration.ofSeconds(1))))
        )
        .connect()
        .subscribe();

    Thread.sleep(100000);
  }
}
