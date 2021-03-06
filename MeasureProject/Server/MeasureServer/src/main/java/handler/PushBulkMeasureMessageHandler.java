package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import log.SystemLog;
import message.PushBulkMeasureMessage;

/**
 * Created by free on 2016/9/2.
 */
public class PushBulkMeasureMessageHandler extends ChannelInboundHandlerAdapter
{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
	{
//		super.channelRead(ctx, msg);
		if (msg instanceof PushBulkMeasureMessage)
		{
			SystemLog.log(msg);
		}
		else
		{
			ctx.fireChannelRead(msg);
		}
	}
}
