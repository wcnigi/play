package com.pzhang7.study.network.thrift.example.threadpool;

import com.pzhang7.study.network.thrift.service.HelloWorldService;
import com.pzhang7.study.network.thrift.service.impl.HelloWorldServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * Author: Zhang Peng
 * Date: 2017/1/23 0023
 * Description:
 */
public class HelloWorldServer {
    public static final int SERVER_PORT = 8090;

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloWorldServer server = new HelloWorldServer();
        server.startServer();
    }

    public void startServer() {
        try {
            System.out.println("HelloWorld TThreadPoolServer start ....");

            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(
                    new HelloWorldServiceImpl());

            TServerSocket tServerSocket = new TServerSocket(SERVER_PORT);
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(tServerSocket);
            args.processor(tprocessor);
            args.protocolFactory(new TBinaryProtocol.Factory());

            // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
            TServer server = new TThreadPoolServer(args);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }
}
