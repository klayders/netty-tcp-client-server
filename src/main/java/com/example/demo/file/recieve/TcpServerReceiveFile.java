package com.example.demo.file.recieve;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpServer;

public class TcpServerReceiveFile {


  public static void main(String[] args) throws InterruptedException {
    TcpServer.create()
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
        .bind()
        .subscribe();

    Thread.sleep(100000);
  }
}