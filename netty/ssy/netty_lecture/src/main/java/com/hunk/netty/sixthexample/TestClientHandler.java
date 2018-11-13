package com.hunk.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        MyDataInfo.MyMessage.Builder builder = MyDataInfo.MyMessage.newBuilder();
        switch (randomInt){
            case 0:
                builder.setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                        .setPerson(MyDataInfo.Person.newBuilder()
                                .setName("hunk").setAge(20).setAddress("广州").build());
                break;

            case 1:
                builder.setDataType(MyDataInfo.MyMessage.DataType.DogType)
                        .setDog(MyDataInfo.Dog.newBuilder().setName("Dog").setAge(21).build());

                break;

            case 2:
                builder.setDataType(MyDataInfo.MyMessage.DataType.CatType)
                        .setCat(MyDataInfo.Cat.newBuilder().setName("Cat").setCity("佛山").build());

                break;

                default:
                    return;
        }
        ctx.writeAndFlush(builder.build());
    }
}
