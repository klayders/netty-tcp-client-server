package com.example.demo.file.send;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpServer;

public class TcpServerSendFile {


  public static void main(String[] args) throws InterruptedException {
    TcpServer.create()
        .wiretap(true)
        .port(8080)
        .handle((inbound, outbound) ->
                {
                  try {
                    Path file = Paths.get(TcpServerSendFile.class.getResource("/help.md").toURI());
                    return outbound.sendFile(file);
                  } catch (URISyntaxException e) {
                      e.printStackTrace();
                  }
                  return outbound.sendString(Mono.just("error"));
                }
        )
        .bind()
        .subscribe();

    Thread.sleep(100000);
  }
}