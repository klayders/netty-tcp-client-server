package com.example.demo.text;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Random;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

public class TcpClientTest {

  public static void main(String[] args) throws InterruptedException {
    TcpClient.create()
        .port(8080)
        .handle((inbound, outbound) ->
                    outbound
                        .sendString(
                            Mono.just("number = " + new Random().nextLong())
                                .repeatWhen(publisher -> publisher.delayElements(Duration.ofSeconds(1)))
                        )
                        .then()
                        .flatMap(ii ->
                                     inbound
                                         .receive()
                                         .map(byteBuf -> {
                                                var text = byteBuf.toString(Charset.defaultCharset());
                                                System.out.println(text);
                                                return text;
                                              }
                                         )
                                         .count()
                        ).map(counter -> {
                          System.out.println(counter);
                          return counter;
                        })
                        .then()
        )
                        .connect()
                        .subscribe();

    Thread.sleep(100000);
  }
}
