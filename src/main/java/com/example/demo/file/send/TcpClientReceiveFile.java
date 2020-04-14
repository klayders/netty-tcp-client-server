package com.example.demo.file.send;

import reactor.netty.tcp.TcpClient;

public class TcpClientReceiveFile {


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