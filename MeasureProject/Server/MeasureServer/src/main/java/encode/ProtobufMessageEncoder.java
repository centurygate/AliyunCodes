package encode;

import enumeration.CommandEnum;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.zip.Adler32;
import java.util.zip.Checksum;

/**
 * Created by free on 2016/9/1.
 */
public class ProtobufMessageEncoder extends MessageToByteEncoder<Object>
{
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception
	{
		//SystemLog.log("In Encoder:"+ctx.channel());
		//SystemLog.log("+++++++++++++++++ Encoding "+msg.getClass().getName()+" +++++++++++++++++");
		int messageType = CommandEnum.getType(msg.getClass().getName());
		
		int packetLenth = 16;
		Codec messageTypeCodec = ProtobufProxy.create(CommandEnum.getClass(msg.getClass().getName()));
		
		byte[] messageByte = messageTypeCodec.encode(msg);
		//SystemLog.log("+++++++++++++++++ ClassType "+messageType+" +++++++++++++++++");
		Adler32 checksum = new Adler32();
		checksum.update(messageByte);
		packetLenth = packetLenth + messageByte.length;
		out.writeInt(packetLenth);
		out.writeInt(messageType);
		out.writeBytes(messageByte);
		out.writeLong(checksum.getValue());
		ctx.flush();
		
	}
}
