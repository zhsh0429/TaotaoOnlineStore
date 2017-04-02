package com.taotao.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class TestActiveMQ {

	// queue-producer
	@Test
	public void testQueueProducer() throws Exception {
		// 第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
		ConnectionFactory connectionFactory = new org.apache.activemq.ActiveMQConnectionFactory(
				"tcp://192.168.25.175:61616");
		// 第二步：使用ConnectionFactory对象创建一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		// 第三步：开启连接，调用Connection对象的start方法。
		connection.start();
		// 第四步：使用Connection对象创建一个Session对象。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
		Queue queue = session.createQueue("test-queue");
		// 第六步：使用Session对象创建一个Producer对象。
		MessageProducer producer = session.createProducer(queue);
		// 第七步：创建一个Message对象，创建一个TextMessage对象。
		TextMessage textMessage = session.createTextMessage("hello activeMQ, test2");
		// 第八步：使用Producer对象发送消息。
		producer.send(textMessage);
		// 第九步：关闭资源。
		producer.close();
		session.close();
		connection.close();
	}

	// queue-consumer
	@Test
	public void testQueueConsumer() throws Exception {
		// 第一步：创建一个ConnectionFactory对象。
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.175:61616");
		// 第二步：从ConnectionFactory对象中获得一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		// 第三步：开启连接。调用Connection对象的start方法。
		connection.start();
		// 第四步：使用Connection对象创建一个Session对象。
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		// 第五步：使用Session对象创建一个Destination对象。和发送端保持一致queue，并且队列的名称一致。
		Queue queue = session.createQueue("test-queue");
		// 第六步：使用Session对象创建一个Consumer对象。
		MessageConsumer consumer = session.createConsumer(queue);
		// 第七步：接收消息。
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				if (message instanceof TextMessage) {
					try {
						TextMessage textMessage = (TextMessage) message;
						String text = textMessage.getText();
						// 第八步：打印消息。
						System.out.println(text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		// 系统等待接收消息
		System.in.read();
		// 第九步：关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	// topic-producer
	@Test
	public void testTopicProducer() throws Exception {
		// 第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
		// 第二步：使用ConnectionFactory对象创建一个Connection对象。
		// 第三步：开启连接，调用Connection对象的start方法。
		// 第四步：使用Connection对象创建一个Session对象。
		// 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Topic对象。
		// 第六步：使用Session对象创建一个Producer对象。
		// 第七步：创建一个Message对象，创建一个TextMessage对象。
		// 第八步：使用Producer对象发送消息。
		// 第九步：关闭资源。
	}

	// topic-producer
	@Test
	public void testTopicConsumer() throws Exception {
		//第一步：创建一个ConnectionFactory对象。
		//第二步：从ConnectionFactory对象中获得一个Connection对象。
		//第三步：开启连接。调用Connection对象的start方法。
		//第四步：使用Connection对象创建一个Session对象。
		//第五步：使用Session对象创建一个Destination对象。和发送端保持一致topic，并且话题的名称一致。
		//第六步：使用Session对象创建一个Consumer对象。
		//第七步：接收消息。
		//第八步：打印消息。
		//第九步：关闭资源
	}
}
