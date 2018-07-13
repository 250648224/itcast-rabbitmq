package cn.itcast.rabbitmq.work;

import cn.itcast.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class Recv2 {

    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] argv) throws Exception {

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 同一时刻服务器只会发一条消息给消费者
        //表示服务器同一时刻只会发送一条消息给消费者，但并不指定是哪个消费者，处理能力高的人会接收到更多的消息。
        channel.basicQos(1);

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成状态
        //false表示监听队列，手动返回完成状态，true表示自动返回
        channel.basicConsume(QUEUE_NAME, false, consumer);

        // 获取消息
        while (true) {
        	//Delivery交付
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            // 休眠1秒
            Thread.sleep(1000);
            //basicAck 基本消,Envelope 信封
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}