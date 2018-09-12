package activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/11 0011 16:53
 * 4
 */
public class SpringActivemq {
    //使用jmsTemplate发送

    public void testJmsTemplate() throws Exception {
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-Activemq.xml");
        //获取jmsTemplate对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        //获取Destination对象
        Destination destination = (Destination) applicationContext.getBean("test-queue");
        //发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("测试aaa");
            }
        });
    }
}
