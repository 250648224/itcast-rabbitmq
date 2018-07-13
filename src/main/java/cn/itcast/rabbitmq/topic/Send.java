package cn.itcast.rabbitmq.topic;
/**
 * 5、通配符模式（topic模式）：模糊匹配交换机--模糊匹配路由模式
 * 		①、路由键和某模式进行匹配
 * 		②、“#”匹配一个或多个词
 * 		③、“*”匹配一个词
 * 		④、
 */
import cn.itcast.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");//topic固定的写法

        // 消息内容
        String message = "商品新增，id=1003";
        channel.basicPublish(EXCHANGE_NAME, "item.insert", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}