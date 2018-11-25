package cn.hunkier.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
//        log.info(msg.getName());
//        log.info(msg.getAge()+"");
//        log.info(msg.getAddress());
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        log.info("Type: " + dataType);
        switch (dataType){
            case PersonType:
                MyDataInfo.Person person = msg.getPerson();
                log.info(person.getName());
                log.info(person.getAge()+"");
                log.info(person.getAddress());

                break;

            case DogType:
                MyDataInfo.Dog dog = msg.getDog();
                log.info(dog.getName());
                log.info(dog.getAge()+"");
                break;

            case CatType:
                MyDataInfo.Cat cat = msg.getCat();
                log.info(cat.getName());
                log.info(cat.getCity());
                break;

                default:
                    log.error("unkonw type!");
                    break;

        }
    }
}
