package com.ja0ck5.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Reciever {

	public static void main(String[] args) throws Exception {

		// 1
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://127.0.0.1:61616");
		// 2
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();

		// 3
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

		// 4
		Destination destination = session.createQueue("queue1");

		// 5
		MessageConsumer consumer = session.createConsumer(destination);

		// 6
		while(true){
			TextMessage receive = (TextMessage) consumer.receive();
			if(null == receive) break;
			System.out.println("recieve : " + receive);
		}
				
		connection.close();

	
	}
}
