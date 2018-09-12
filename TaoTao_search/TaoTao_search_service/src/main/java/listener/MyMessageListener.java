package listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/11 0011 17:50
 * 4    接收activemq发送的消息
 */
public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            //接收到消息
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
