import com.baidu.bjf.remoting.protobuf.ProtobufIDLGenerator;
import handler.HttpCliUtilForWebLaser;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import log.SystemLog;
import measurebussiness.service.IMeasureService;
import message.LoginResponseMessage;
import message.PushBulkMeasureMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import session.Session;

import java.nio.ByteOrder;

/**
 * Hello world!
 *
 */
public class Main
{
    private  int port;
    
    public Main(int port)
    {
        this.port = port;
    }
    public void run() throws Exception {
        SystemLog.log("Connecting Web Server......");
        while (!HttpCliUtilForWebLaser.loginWebServer("http://www.ismart4d.com:8080/WebLaserProject","user","Abcd1234@@@"))
        {
            SystemLog.log("Check whether web server has started | Reconnect to the server after 3 seconds......");
            Thread.sleep(3000);
        }
        SystemLog.log(ProtobufIDLGenerator.getIDL(LoginResponseMessage.class));
        SystemLog.log(ProtobufIDLGenerator.getIDL(PushBulkMeasureMessage.class));
        SystemLog.log(ByteOrder.nativeOrder());
        ApplicationContext ctx = new ClassPathXmlApplicationContext("measurebussiness/mapping/spring-mybatis.xml");
        Session.measureService = (IMeasureService) ctx.getBean("measureservice");
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    //.handler(new LoggingHandler(LogLevel.ERROR))
                    .childHandler(new SimpleChatServerInitializer())  //(4)
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync(); // (7)
            SystemLog.log("Center Server Start......");
            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
            SystemLog.log("Center Server Start Stop......");

        } finally {
            SystemLog.log("Center Server Start Stop......");
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args ) throws Exception
    {
        SystemLog.log( "Hello World!" );
        int port;
        if (args.length > 0)
        {
            port = Integer.parseInt(args[0]);
        }
        else
        {
            port = 9090;
        }
        
        new Main(port).run();
    }
}
