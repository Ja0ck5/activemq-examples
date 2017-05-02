package com.ja0ck5.activemq.demo3;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
	
	private static final long TIME_2_LIVE_SECONDS = 1000 * 60 *2;

	public static void main(String[] args) throws Exception {
		// 1
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://127.0.0.1:61616");
		// 2
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();

		// 3 use transaction
//		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);

		// 4
		Destination destination = session.createQueue("queue1");

		// 5
		MessageProducer producer = session.createProducer(null);

		// 6
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		for (int i = 0; i < 5; i++) {
			// 7
			TextMessage textMessage = session.createTextMessage("this is a textMessage.");
			textMessage.setText("\r\nI am a message-" + i + "!!!");
			// 8
			producer.send(destination, textMessage, DeliveryMode.NON_PERSISTENT, i, TIME_2_LIVE_SECONDS );
		}
		
		//commit
		session.commit();
		
		connection.close();

	}

}
