package com.example.demo.file;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.Duration;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;
import reactor.netty.tcp.TcpServer;

public class TcpClientSendFile {

  //  TODO: make
  //   curl localhost:8080
  public static void main(String[] args) throws InterruptedException {
    TcpClient.create()
        .wiretap(true)
        .port(8080)
        .handle((inbound, outbound) ->
                    inbound.receive()
                        .asString()
                        .map(text -> {
                          System.out.println(text);
                          return text;
                        })
                        .then()
        )
        .connect()
        .subscribe();

    Thread.sleep(100000);
  }
}