package cn.itcast.rabbitmq.ps;
/**
 * 3、路由模式，简单：每个路由队列都会收到相同的消息
 * 		①、不处理路由键
 * 		②、将队列绑定到交换机上
 * 		③、绑定到交换机上的队列都会收到相同的消息
 * 		④、Fanout交换机转发消息是最快的
 */
import cn.itcast.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        //fanout分列、扇出
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//固定写法direct fanout topic

        // 消息内容
        String message = "Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());//路由键需要设置为空
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}