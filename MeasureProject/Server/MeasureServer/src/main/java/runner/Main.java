package runner;

import exceptionprocess.Exception2Msg;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import log.SystemLog;
import serverhandlerinitializer.MeasureServerHandlerInitializer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main
{
	public static Channel channel = null;
	private static final String centerServerhost = "localhost";
	private static final int centerServerport = 9090;

//	private static final String measureServerhost = "192.168.1.109";
	private static final int measureServerport = 8765;

	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(6);


	private void connect()
	{
		SystemLog.log("===========================Start Connect========================");
		EventLoopGroup group = new NioEventLoopGroup();
		try
		{
			Bootstrap bootstrap = new Bootstrap()
					.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
					.handler(new SimpleChatClientInitializer());
			this.channel  = bootstrap.connect(Main.centerServerhost, Main.centerServerport).sync().channel();
			SystemLog.log("S________________________________________________");
			SystemLog.log(channel);
			SystemLog.log("S________________________________________________");
			if(group.isShutdown())
			{
				SystemLog.log("############################################################");
				SystemLog.log("group.isShutdown()");
				SystemLog.log("############################################################");
				System.exit(1);
			}
			channel.closeFuture().sync();

			SystemLog.log("++++++++++++++++++++++++++++Execute channel.closeFuture().sync()+++++++++++++++++++++++++++++++++");
		}
		catch (Exception e)
		{
			SystemLog.log(Exception2Msg.convertException2Msg(e));
		}
		finally
		{
			//this.channel = null;
			group.shutdownGracefully();
			if (executorService.isShutdown())
			{
				SystemLog.log("############################################################");
				SystemLog.log("executorService is Shutdown");
				SystemLog.log("############################################################");
			}
			executorService.execute(new Runnable()
			{
				public void run()
				{
					try
					{
						TimeUnit.SECONDS.sleep(2);
						try
						{
							connect();
						}
						catch (Exception e)
						{
							SystemLog.log(Exception2Msg.convertException2Msg(e));
						}
					}
					catch (InterruptedException e)
					{
						SystemLog.log(Exception2Msg.convertException2Msg(e));
					}
				}
			});
		}
	}
	public void run() throws Exception
	{

//		ApplicationContext ctx = new ClassPathXmlApplicationContext("measurebussiness/mapping/spring-mybatis.xml");
//		Session.measureService = (IMeasureService) ctx.getBean("measureservice");
		EventLoopGroup bossGroup = null; // (1)
		EventLoopGroup workerGroup = null;
		try
		{
			// client connection for centerserver
			new Thread(new Runnable() {
				public void run() {
					connect();
				}
			}).start();

			while(channel == null)
			{
				Thread.sleep(500);
				SystemLog.log("Waiting for Connecting CenterServer......");
			}
			//device server for measurebussiness device
			bossGroup = new NioEventLoopGroup(); // (1)
			workerGroup = new NioEventLoopGroup();
			ServerBootstrap serverbootstrap = new ServerBootstrap();
			serverbootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new MeasureServerHandlerInitializer())
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			SystemLog.log("MeasureServer Started......");
			//bind the port, accept the connenction
			ChannelFuture serverchanel = serverbootstrap.bind(Main.measureServerport).sync();
			serverchanel.channel().closeFuture().sync();
			channel.closeFuture().sync();
		}
		catch (Exception e)
		{
			SystemLog.log(Exception2Msg.convertException2Msg(e));
		}
		finally
		{
			SystemLog.log("MeasureServer Stop......");
			if (null != bossGroup)
			{
				bossGroup.shutdownGracefully();
			}
			if(null != workerGroup)
			{
				workerGroup.shutdownGracefully();
			}
		}
		
	}
	
	public static void main(String[] args)
	{
		// TODO code application logic here
		try
		{
			new Main().run();
		}
		catch (Exception e)
		{
			SystemLog.log(Exception2Msg.convertException2Msg(e));
		}
	}
}
