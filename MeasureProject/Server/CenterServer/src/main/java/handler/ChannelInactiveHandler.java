package handler;

import clientmanager.ClientManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import log.SystemLog;

/**
 * Created by free on 2016/9/12.
 */
public class ChannelInactiveHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SystemLog.log("+-----------------Channel Inactive Handler : Client Close the Connection------------------------+");
        ClientManager.UnregisterChannel(ctx.channel());
        super.channelInactive(ctx);
    }
}
