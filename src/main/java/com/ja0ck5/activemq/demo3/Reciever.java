package com.ja0ck5.activemq.demo3;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
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

		// 3 use Transaction
//		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		//3.2
		Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);

		// 4
		Destination destination = session.createQueue("queue1");

		// 5
		MessageConsumer consumer = session.createConsumer(destination);

		// 6
		while(true){
			TextMessage recieve = (TextMessage) consumer.receive();
			if(null == recieve) break;
			//because 3.2 . 
			recieve.acknowledge();
			System.out.println("recieve : " + recieve);
		}
		
		connection.close();
	
	}
}
