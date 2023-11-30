package AllAckUniformReliableBroadcast;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;

/**
 * @author Kai Kang
 * @date 2023/11/25 12:16 上午
 */
public class ReceiverHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello from Client!", StandardCharsets.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("Received from server: " + ctx.channel().remoteAddress() + ".  " );
        System.out.println(" Message is: "+ byteBuf.toString(StandardCharsets.UTF_8));
    }
}
