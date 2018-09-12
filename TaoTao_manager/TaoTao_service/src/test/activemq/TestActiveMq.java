package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/10 0010 9:55
 * 4
 */
public class TestActiveMq {
    //producer

    public void testQueueProducer() throws Exception {
        //1.链接服务端,创建连接工厂,指定ip和端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://120.78.88.198:61616");
        //2.创建connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启链接,调用start方法
        connection.start();
        //4.创建会话session,一般不使用事务,保证数据的最终一致,可以使用消息队列实现
        //如不开启事务，第二个参数为应答模式，自动/手动,一般自动
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建destination对象，两种形式queue,topic
        //参数：消息队列的名称
        Queue queue = session.createQueue("test-queue");
        //6.创建producer对象
        MessageProducer producer = session.createProducer(queue);
        //7.创建textMessage对象
       /* TextMessage textMessage=new ActiveMQTextMessage();
        textMessage.setText("hello active");*/
        TextMessage textMessage = session.createTextMessage("Hello activemq");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }


    public void testQueueConsumer() throws Exception {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://120.78.88.198:61616");
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //开启链接
        connection.start();
        //创建sessionn
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地,应该和发送端一致
        Queue queue = session.createQueue("test-queue");
        //创建consumer对象
        MessageConsumer consumer = session.createConsumer(queue);
        //向consumer对象中设置一个MessageListener对象
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                //取消息的内容
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        //打印消息内容
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //系统等待接收消息
        /*while(true){
            Thread.sleep(100);
        }*/
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    //topic

    public void testTopicProducer() throws Exception {
        //1.链接服务端,创建连接工厂,指定ip和端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://120.78.88.198:61616");
        //2.创建connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启链接,调用start方法
        connection.start();
        //4.创建会话session,一般不使用事务,保证数据的最终一致,可以使用消息队列实现
        //如不开启事务，第二个参数为应答模式，自动/手动,一般自动
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建destination对象，两种形式queue,topic
        //参数：消息队列的名称
        Topic topic = session.createTopic("test-topic");
        //6.创建producer对象
        MessageProducer producer = session.createProducer(topic);
        //7.创建textMessage对象
       /* TextMessage textMessage=new ActiveMQTextMessage();
        textMessage.setText("hello active");*/
        TextMessage textMessage = session.createTextMessage("Hello activemq topic");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }


    public void testTopicConsumer() throws Exception {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://120.78.88.198:61616");
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //开启链接
        connection.start();
        //创建sessionn
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地,应该和发送端一致
        Topic topic = session.createTopic("test-topic");
        //创建目的地,应该和发送端一致
        MessageConsumer consumer = session.createConsumer(topic);
        //向consumer对象中设置一个MessageListener对象
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                //取消息的内容
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        //打印消息内容
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //系统等待接收消息
        /*while(true){
            Thread.sleep(100);
        }*/
        System.out.println("topic消费者2");
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
