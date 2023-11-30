package AllAckUniformReliableBroadcast;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Kai Kang
 * @date 2023/11/25 12:02 上午
 */
public class SenderHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Connection is built.");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        AAURB aaurb = new AAURB();
        List<String> msg = aaurb.urb();
        int len = msg.size();
        String msgtxt = "";
        String senderP = "";
        boolean txt = true;
        for (int i = 0; i < len; i++) {
            if (txt) {
                if (!msg.get(i).equals("*")) {
                    msgtxt = msgtxt + msg.get(i);
                } else {
                    txt = false;
                }
            } else {
                senderP += msg.get(i) + ",";
            }
        }
        int total = aaurb.total();
        String totalP = "";
        for(int i = 0; i < total; i++) {
            totalP = totalP + i + ',';
        }
        String finalMSG = "MSG is: " + msgtxt + ".\n" + "Sending from: Process: " + senderP + " Total Process are: Process " + totalP;
        ctx.writeAndFlush(Unpooled.copiedBuffer(finalMSG, StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) throws Exception {
        ctx.close();
    }
}
