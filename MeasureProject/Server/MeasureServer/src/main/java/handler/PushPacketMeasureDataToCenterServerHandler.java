package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import log.SystemLog;
import message.MeasureRecord;
import message.PushMeasureDataMessage;
import message.RequestCmdHeader;
import runner.Main;
import session.Session;

import java.util.regex.Pattern;

/**
 * Created by free on 2016/9/3.
 */
class Util
{
    public Util()
    {
    }

    /**
     * 将指定byte数组以16进制的形式打印到控制台
     *
     * @param hint String
     * @param b    byte[]
     * @return void
     */
    public static void printHexString(String hint, byte[] b)
    {
        System.out.print(hint);
        for (int i = 0; i < b.length; i++)
        {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
        }
        SystemLog.log("");
    }

    /**
     * @param b byte[]
     * @return String
     */
    public static String Bytes2HexString(byte[] b)
    {
        String ret = "";
        for (int i = 0; i < b.length; i++)
        {
            String hex = "0x" + Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    /**
     * 将两个ASCII字符合成一个字节；
     * 如："EF"--> 0xEF
     *
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1)
    {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式
     * 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
     *
     * @param src String
     * @return byte[]
     */
    public static byte[] HexString2Bytes(String src)
    {
        byte[] ret = new byte[8];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < 8; i++)
        {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }
}

public class PushPacketMeasureDataToCenterServerHandler extends ChannelInboundHandlerAdapter
{
    //	private Channel channel ;
//
//	public PushPacketMeasureDataToCenterServerHandler(Channel channel)
//	{
//		this.channel = channel;
//	}
    private String BytetoStr(byte[] databyte)
    {
        int len = databyte.length;
        char hexchar[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder strbuilder = new StringBuilder();
        for (int i = 0; i < len; i++)
        {
            char low = hexchar[((byte) databyte[i] & 0xf)];
            char high = hexchar[((byte) databyte[i] >> 4 & 0xf)];
            strbuilder.append(high).append(low);
        }
        return strbuilder.toString();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        String setaddrokstr = "FA048181";
        String setaddrerrstr = "FA848102FF";

        String modifydis_okstr = "FA048B77";
        String modifydis_errstr = "FA848B01F6";

        String setintervalok_okstr = "FA04857D";
        String setintervalerror_paramstr = "FA848501FC";
        String setintervalerrstr = "FA848501FA";


        String setstartpos_okstr = "FA04887A";
        String setstartpos_errstr = "FA848801F9";

        String setragnge_okstr = "FA048979";
        String setragnge_errstr = "FA848901F8";

        String setfreq_okstr = "FA048A78";
        String setfreq_errstr = "FA048A78";

        String setresolution_okstr = "FA048C76";
        String setresolution_errstr = "FA848C01F5";

        String setonpowerwork_okstr = "FA048D75";
        String setonpowerwork_errstr = "FA848D01F4";

        //take care of the device address
        String single_measure_low_precision_okstr = ".*0682(3[0-9]){3}2E(3[0-9]){3}.{2}$";
        String single_measure_low_precision_errstr = ".*0682ERR--(3[0-9]){2}.{2}$";

        String single_measure_high_precision_okstr = ".*0682(3[0-9]){3}2E(3[0-9]){4}.{2}$";
        String single_measure_high_precision_errstr = ".*0682ERR---(3[0-9]){2}.{2}$";

        String consecutive_measure_low_precision_okstr = ".*0683(3[0-9]){3}2E(3[0-9]){3}.{2}$";
        String consecutive_measure_low_precision_errstr = ".*0683ERR--(3[0-9]){2}.{2}$";

        String consecutive_measure_high_precision_okstr = ".*0683(3[0-9]){3}2E(3[0-9]){4}.{2}$";
        String consecutive_measure_high_precision_errstr = ".*0683ERR---(3[0-9]){2}.{2}$";

        String ctl_laser_power_okstr = "*.068501.{2}$";
        String ctl_laser_power_errstr = "*.068500.{2}$";

        String keep_alive_beat_str = "3030303030313A6865617274";

        //first receive the byte data into an array,
        //secondly, convert the byte data into string format to match the case
        SystemLog.log("+==================================================================+");
        int datalen = ((ByteBuf) msg).readableBytes();
        byte[] databyte = new byte[datalen];
        ((ByteBuf) msg).readBytes(databyte);
        String datastr = BytetoStr(databyte).toUpperCase();
        SystemLog.log("Received from device contents:"+datastr);
        double dis = 0.0d;
        int startpos = 6 + 3;
        PushMeasureDataMessage pushMeasureDataMessage = new PushMeasureDataMessage();
        RequestCmdHeader cmdHeader = new RequestCmdHeader();
        cmdHeader.setClientID(Session.clientID);
        pushMeasureDataMessage.setCmdHeader(cmdHeader);
        MeasureRecord measureRecord = new MeasureRecord();
        long recordtime = System.currentTimeMillis();
        byte[] addrbyte = new byte[6];

        for (int i = 0; i < addrbyte.length;i++)
        {
            addrbyte[i] = databyte[i];
        }
        String addr = BytetoStr(addrbyte);
        measureRecord.setDeviceAddr(addr);
        measureRecord.setTime(recordtime);

        if (msg instanceof ByteBuf)
        {
            if (datastr.endsWith(keep_alive_beat_str))
            {
                SystemLog.log("++Received Device Heart Beat++");
                byte[] heartbeat_reply = {(byte)0xFC,(byte)0x68,(byte)0x65,(byte)0x61,(byte)0x72,(byte)0x74,(byte)0x72,(byte)0x65,(byte)0x61,(byte)0x63,(byte)0x68,(byte)0x0d,(byte)0x0a};
                ByteBuf heartbeat_byteBuf = Unpooled.buffer();
                heartbeat_byteBuf.writeBytes(heartbeat_reply);
                ctx.writeAndFlush(heartbeat_byteBuf);
            }
            if (datastr.endsWith(setaddrokstr))
            {
                SystemLog.log("Set Device Addr OK");
            }
            else if (datastr.endsWith(setaddrerrstr))
            {
                SystemLog.log("Set Device Addr ERROR");
            }
            else if (datastr.endsWith(modifydis_okstr))
            {
                SystemLog.log("Modify Distance OK");
            }
            else if (datastr.endsWith(modifydis_errstr))
            {
                SystemLog.log("Modify Distance ERROR");
            }
            else if (datastr.endsWith(setintervalok_okstr))
            {
                SystemLog.log("Set Interval OK");
            }
            else if (datastr.endsWith(setintervalerror_paramstr))
            {
                SystemLog.log("Set Interval Error: (params wrong)");
            }
            else if (datastr.endsWith(setintervalerrstr))
            {
                SystemLog.log("Set Interval Error");
            }
            else if (datastr.endsWith(setstartpos_okstr))
            {
                SystemLog.log("Set Position OK");
            }
            else if (datastr.endsWith(setstartpos_errstr))
            {
                SystemLog.log("Set Position ERROR");
            }
            else if (datastr.endsWith(setragnge_okstr))
            {
                SystemLog.log("Set Range OK");
            }
            else if (datastr.endsWith(setragnge_errstr))
            {
                SystemLog.log("Set Range ERROR");
            }
            else if (datastr.endsWith(setfreq_okstr))
            {
                SystemLog.log("Set Frequency OK");
            }
            else if (datastr.endsWith(setfreq_errstr))
            {
                SystemLog.log("Set Frequency ERROR");
            }
            else if (datastr.endsWith(setresolution_okstr))
            {
                SystemLog.log("Set Resolution OK");
            }
            else if (datastr.endsWith(setresolution_errstr))
            {
                SystemLog.log("Set Resolution ERROR");
            }
            else if (datastr.endsWith(setonpowerwork_okstr))
            {
                SystemLog.log("Set On Power work OK");
            }
            else if (datastr.endsWith(setonpowerwork_errstr))
            {
                SystemLog.log("Set On Power work ERROR");
            }
            else if (Pattern.compile(single_measure_low_precision_okstr).matcher(datastr).matches())
            {
                SystemLog.log("Single Measure Low Precision OK");
                //calculate the distance from the byte array, see detail on the document "protocal of laser device"
                dis = (databyte[0 + startpos] - 0x30) * 100;
                dis = dis + (databyte[1 + startpos] - 0x30) * 10;
                dis = dis + (databyte[2 + startpos] - 0x30);
                dis = dis + (databyte[4 + startpos] - 0x30) * 0.1;
                dis = dis + (databyte[5 + startpos] - 0x30) * 0.01;
                dis = dis + (databyte[6 + startpos] - 0x30) * 0.001;
                SystemLog.log("Distance: "+dis);
                measureRecord.setDistance(dis);
                pushMeasureDataMessage.setSingleMeasureRecord(measureRecord);
                Main.channel.writeAndFlush(pushMeasureDataMessage);

            }
            else if (Pattern.compile(single_measure_low_precision_errstr).matcher(datastr).matches())
            {
                SystemLog.log("Single Measure Low Precision ERROR");
            }
            else if (Pattern.compile(single_measure_high_precision_okstr).matcher(datastr).matches())
            {
                SystemLog.log("Single Measure High Precision OK");
                //calculate the distance from the byte array, see detail on the document "protocal of laser device"
                dis = (databyte[0 + startpos] - 0x30) * 100;
                dis = dis + (databyte[1 + startpos] - 0x30) * 10;
                dis = dis + (databyte[2 + startpos] - 0x30);
                dis = dis + (databyte[4 + startpos] - 0x30) * 0.1;
                dis = dis + (databyte[5 + startpos] - 0x30) * 0.01;
                dis = dis + (databyte[6 + startpos] - 0x30) * 0.001;
                dis = dis + (databyte[7 + startpos] - 0x30) * 0.0001;
                SystemLog.log("Distance: "+dis);
                measureRecord.setDistance(dis);
                pushMeasureDataMessage.setSingleMeasureRecord(measureRecord);
                Main.channel.writeAndFlush(pushMeasureDataMessage);
            }
            else if (Pattern.compile(single_measure_high_precision_errstr).matcher(datastr).matches())
            {
                SystemLog.log("Single Measure High Precision ERROR");
            }
            //--------------------------------------------------------------------------------------------------
            else if (Pattern.compile(consecutive_measure_low_precision_okstr).matcher(datastr).matches())
            {
                SystemLog.log("Consecutive Measure Low Precision OK");
                //calculate the distance from the byte array, see detail on the document "protocal of laser device"
                dis = (databyte[0 + startpos] - 0x30) * 100;
                dis = dis + (databyte[1 + startpos] - 0x30) * 10;
                dis = dis + (databyte[2 + startpos] - 0x30);
                dis = dis + (databyte[4 + startpos] - 0x30) * 0.1;
                dis = dis + (databyte[5 + startpos] - 0x30) * 0.01;
                dis = dis + (databyte[6 + startpos] - 0x30) * 0.001;
                SystemLog.log("Distance: "+dis);
                measureRecord.setDistance(dis);
                pushMeasureDataMessage.setSingleMeasureRecord(measureRecord);
                Main.channel.writeAndFlush(pushMeasureDataMessage);

            }
            else if (Pattern.compile(consecutive_measure_low_precision_errstr).matcher(datastr).matches())
            {
                SystemLog.log("Consecutive Measure Low Precision ERROR");
            }
            else if (Pattern.compile(consecutive_measure_high_precision_okstr).matcher(datastr).matches())
            {
                SystemLog.log("Consecutive Measure High Precision OK");
                //calculate the distance from the byte array, see detail on the document "protocal of laser device"
                dis = (databyte[0 + startpos] - 0x30) * 100;
                dis = dis + (databyte[1 + startpos] - 0x30) * 10;
                dis = dis + (databyte[2 + startpos] - 0x30);
                dis = dis + (databyte[4 + startpos] - 0x30) * 0.1;
                dis = dis + (databyte[5 + startpos] - 0x30) * 0.01;
                dis = dis + (databyte[6 + startpos] - 0x30) * 0.001;
                dis = dis + (databyte[7 + startpos] - 0x30) * 0.0001;
                SystemLog.log("Distance: "+dis);
                measureRecord.setDistance(dis);
                pushMeasureDataMessage.setSingleMeasureRecord(measureRecord);
                Main.channel.writeAndFlush(pushMeasureDataMessage);
            }
            else if (Pattern.compile(consecutive_measure_high_precision_errstr).matcher(datastr).matches())
            {
                SystemLog.log("Consecutive Measure High Precision ERROR");
            }
            else if (datastr.equals(ctl_laser_power_okstr))
            {
                SystemLog.log("Control Lase Power ON/OFF OK");
            }
            else if (datastr.equals(ctl_laser_power_errstr))
            {
                SystemLog.log("Control Lase Power ON/OFF ERROR");
            }
            else
            {
                SystemLog.log(datastr);
            }
            SystemLog.log("+==================================================================+");
//
//            byte[] cmd_read_param ={(byte)0xFA,(byte)0x06,(byte)0x01,(byte)0xFF};
//            byte[] cmd_read_machine_num = {(byte)0xFA,(byte)0x06,(byte)0x04,(byte)0xFC};
//            byte[] cmd_write_interver10s = {(byte)0xFA,(byte)0x04,(byte)0x05,(byte)0x0a,(byte)0xF3};
//            byte[] cmd_write_interver3s = {(byte)0xFA,(byte)0x04,(byte)0x05,(byte)0x03,(byte)0xFA};
//            byte[] cmd_write_interver1s = {(byte)0xFA,(byte)0x04,(byte)0x05,(byte)0x03,(byte)0xFD};
//            byte[] cmd_resolution_1mm = {(byte)0xFA,(byte)0x04,(byte)0x0C,(byte)0x01,(byte)0xF5};
//            byte[] cmd_resolution_zero1mm = {(byte)0xFA,(byte)0x04,(byte)0x0C,(byte)0x02,(byte)0xF4};
//            byte[] cmd_write_ragnge5 ={(byte)0xFA,(byte)0x04,(byte)0x09,(byte)0x05,(byte)0xF4};
//            byte[] cmd_write_ragnge10 ={(byte)0xFA,(byte)0x04,(byte)0x09,(byte)0x05,(byte)0xF4};
//            byte[] cmd_write_ragnge30 ={(byte)0xFA,(byte)0x04,(byte)0x09,(byte)0x05,(byte)0xF4};
//            byte[] cmd_write_ragnge50 ={(byte)0xFA,(byte)0x04,(byte)0x09,(byte)0x05,(byte)0xF4};
//            byte[] cmd_write_ragnge80 ={(byte)0xFA,(byte)0x04,(byte)0x09,(byte)0x05,(byte)0xF4};
//            byte[] cmd_single_measure = {(byte)0x80,(byte)0x06,(byte)0x02,(byte)0x78};
//            byte[] cmd_consecutive_measure = {(byte)0x80,(byte)0x06,(byte)0x03,(byte)0x77};
//            byte[] cmd_write_start_top_pos = {(byte)0xFA,(byte)0x04,(byte)0x08,(byte)0x01,(byte)0xF9};
//            byte[] cmd_write_start_bottom_pos = {(byte)0xFA,(byte)0x04,(byte)0x08,(byte)0x00,(byte)0xFA};
//            byte[] cmd_write_freq_zero = {(byte)0xFA,(byte)0x04,(byte)0x0A,(byte)0x00,(byte)0xF8};
//            byte[] cmd_write_freq_five = {(byte)0xFA,(byte)0x04,(byte)0x0A,(byte)0x05,(byte)0xF3};
//            byte[] cmd_write_freq_ten = {(byte)0xFA,(byte)0x04,(byte)0x0A,(byte)0x0A,(byte)0xEE};
//            byte[] cmd_write_freq_twenty = {(byte)0xFA,(byte)0x04,(byte)0x0A,(byte)0x14,(byte)0xE4};
//            byte[] cmd_write_measure_poweron_on ={(byte)0xFA,(byte)0x04,(byte)0x0D,(byte)0x00,(byte)0xF5};
//            byte[] cmd_write_measure_poweron_off = {(byte)0xFA,(byte)0x04,(byte)0x0D,(byte)0x01,(byte)0xF4};
//            byte[] cmd_single_measure_broadcast = {(byte)0xFA,(byte)0x06,(byte)0x06,(byte)0xFA};
//            byte[] cmd_read_measurecache = {(byte)0x80,(byte)0x06,(byte)0x07,(byte)0x73};
//            byte[] cmd_turn_on_laser = {(byte)0x80,(byte)0x06,(byte)0x05,(byte)0x01,(byte)0x74};
//            byte[] cmd_turn_off_laser = {(byte)0x80,(byte)0x06,(byte)0x05,(byte)0x00,(byte)0x75};
//            byte[] cmd_turn_off_device = {(byte)0x80,(byte)0x04,(byte)0x02,(byte)0x7A};
//            int choice = 0;
//            ByteBuf byteBuf = Unpooled.buffer();
//            boolean jumpout = true;
//            while(true)
//            {
//                System.out.print("Input the choice(1~25):  ");
//                Scanner sc = new Scanner(System.in);
//                try
//                {
//                    choice = sc.nextInt();
//                }
//                catch (InputMismatchException e)
//                {
//                    SystemLog.log("Input Wrong!");
//                    continue;
//                }
//                catch (Exception e)
//                {
//                    SystemLog.log(Exception2Msg.convertException2Msg(e));
//                }
//                SystemLog.log("You choose  : "+ choice);
//                switch (choice) {
//                    case 1: {
//                        byteBuf.writeBytes(cmd_read_param);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 2: {
//                        byteBuf.writeBytes(cmd_read_machine_num);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
////                    case 3: {
////                        byteBuf.writeBytes(cmd_write_interver10s);
////                        ctx.writeAndFlush(byteBuf);
////                        break;
////                    }
////                    case 3: {
////                        SystemLog.log("Change 3s interval");
////                        byteBuf.writeBytes(cmd_write_interver3s);
////                        ctx.writeAndFlush(byteBuf);
////                        break;
////                    }
//                    case 3: {
//                        SystemLog.log("Change 1s interval");
//                        byteBuf.writeBytes(cmd_write_interver1s);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 4: {
//                        byteBuf.writeBytes(cmd_resolution_1mm);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 5: {
//                        byteBuf.writeBytes(cmd_resolution_zero1mm);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 6: {
//                        byteBuf.writeBytes(cmd_write_ragnge5);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 7: {
//                        byteBuf.writeBytes(cmd_write_ragnge10);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 8: {
//                        byteBuf.writeBytes(cmd_write_ragnge30);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 9: {
//                        byteBuf.writeBytes(cmd_write_ragnge50);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 10: {
//                        byteBuf.writeBytes(cmd_write_ragnge80);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 11: {
//                        byteBuf.writeBytes(cmd_single_measure);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 12: {
//                        byteBuf.writeBytes(cmd_consecutive_measure);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 13: {
//                        byteBuf.writeBytes(cmd_write_start_top_pos);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 14: {
//                        byteBuf.writeBytes(cmd_write_start_bottom_pos);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 15: {
//                        byteBuf.writeBytes(cmd_write_freq_zero);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 16: {
//                        byteBuf.writeBytes(cmd_write_freq_five);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 17: {
//                        byteBuf.writeBytes(cmd_write_freq_ten);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 18: {
//                        byteBuf.writeBytes(cmd_write_freq_twenty);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 19: {
//                        byteBuf.writeBytes(cmd_write_measure_poweron_on);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 20: {
//                        byteBuf.writeBytes(cmd_write_measure_poweron_off);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 21: {
//                        byteBuf.writeBytes(cmd_single_measure_broadcast);
//                        ctx.writeAndFlush(byteBuf);
//                        SystemLog.log("You Sent broadcat measure command , so you can choose: 22 to fetch the result");
//                        jumpout = false;
//                        break;
//                    }
//                    case 22: {
//                        ByteBuf tmpbyteBuf = Unpooled.buffer();
//                        tmpbyteBuf.writeBytes(cmd_read_measurecache);
//                        ctx.writeAndFlush(tmpbyteBuf);
//                        jumpout = true;
//                        SystemLog.log("You Sent cmd_read_measurecache to Laser Device");
//                        break;
//                    }
//                    case 23: {
//                        byteBuf.writeBytes(cmd_turn_on_laser);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 24: {
//                        byteBuf.writeBytes(cmd_turn_off_laser);
//                        ctx.writeAndFlush(byteBuf);
//                        break;
//                    }
//                    case 25: {
//                        byteBuf.writeBytes(cmd_turn_off_device);
//                        ctx.writeAndFlush(byteBuf);
//                        SystemLog.log("You send turnoff the device command!");
//                        break;
//                    }
//                    default:
//                        SystemLog.log("No choice matching your input,Please input again:");
//                        jumpout = false;
//                        break;
//                }
//                if (jumpout == true)
//                    break;
//            }
            SystemLog.log("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
        else
        {
            SystemLog.log("===============ctx.fireChannelRead(msg)");
            ctx.fireChannelRead(msg);
        }
    }
}
