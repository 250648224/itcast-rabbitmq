package cn.itcast.rabbitmq.routing;
/**
 * 4、路由模式：指定不同的交换机
 * 		①、处理路由键
 * 		②、要求消息与一个特定的路由键完全匹配
 */
import cn.itcast.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        // 直接的direct
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");//direct fanout topic

        // 消息内容
        String message = "删除商品！";
        channel.basicPublish(EXCHANGE_NAME, "delete", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}