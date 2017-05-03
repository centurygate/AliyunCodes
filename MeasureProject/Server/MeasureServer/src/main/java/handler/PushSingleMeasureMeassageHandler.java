package handler;

import clientmanager.ClientManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import log.SystemLog;
import message.PushSingleMeasureMessage;

/**
 * Created by free on 2016/9/2.
 */
public class PushSingleMeasureMeassageHandler extends ChannelInboundHandlerAdapter
{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
		//super.channelRead(ctx, msg);
		if (msg instanceof PushSingleMeasureMessage)
		{
			SystemLog.log(msg);
			ClientManager.PushMessage(msg);
		}
		else
		{
			ctx.fireChannelRead(msg);
		}
	}
}
