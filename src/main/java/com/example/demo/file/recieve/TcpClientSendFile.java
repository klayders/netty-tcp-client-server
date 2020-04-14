package com.example.demo.file.recieve;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

public class TcpClientSendFile {


  public static void main(String[] args) throws InterruptedException {
    TcpClient.create()
        .wiretap(true)
        .port(8080)
        .handle((inbound, outbound) ->
                {
                  try {
                    Path file = Paths.get(TcpServerReceiveFile.class.getResource("/help.md").toURI());
                    return outbound.sendFile(file);
                  } catch (URISyntaxException e) {
                    e.printStackTrace();
                  }
                  return outbound.sendString(Mono.just("error"));
                }
        )
        .connect()
        .subscribe();

    Thread.sleep(100000);
  }
}