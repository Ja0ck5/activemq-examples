package com.ja0ck5.activemq.p2p;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private MessageProducer messageProducer;

	public Producer() {
		try {
			this.connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
					ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
			this.connection = this.connectionFactory.createConnection();
			this.connection.start();
			this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			this.messageProducer = this.session.createProducer(null);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public Session getSession() {
		return this.session;
	}

	public void send1() {
		try {
			Destination destination = this.getSession().createQueue("first");
			MapMessage msg1 = this.getSession().createMapMessage();
			msg1.setString("name", "HAHA");
			msg1.setString("age", "0");
			msg1.setString("color", "blue");
			msg1.setString("sal", "20000");
			int id = 1;

			msg1.setInt("id", id);
			msg1.setStringProperty("receiver", id % 2 == 0 ? "A" : "B");
			MapMessage msg2 = this.getSession().createMapMessage();
			msg2.setString("name", "HAHA");
			msg2.setString("age", "0");
			msg2.setString("color", "blue");
			msg2.setString("sal", "20000");
			id = 2;
			msg2.setInt("id", id);
			msg2.setStringProperty("receiver", id % 2 == 0 ? "A" : "B");
			MapMessage msg3 = this.getSession().createMapMessage();
			msg3.setString("name", "HAHA");
			msg3.setString("age", "0");
			msg3.setString("color", "blue");
			msg3.setString("sal", "20000");
			id = 3;
			msg3.setInt("id", id);
			msg3.setStringProperty("receiver", id % 2 == 0 ? "A" : "B");
			MapMessage msg4 = this.getSession().createMapMessage();
			msg4.setString("name", "HAHA");
			msg4.setString("age", "0");
			msg4.setString("color", "blue");
			msg4.setString("sal", "20000");
			id = 4;
			msg4.setInt("id", id);
			msg4.setStringProperty("receiver", id % 2 == 0 ? "A" : "B");

			this.messageProducer.send(destination, msg1, DeliveryMode.NON_PERSISTENT, 1, 1000 * 60 * 10L);
			this.messageProducer.send(destination, msg2, DeliveryMode.NON_PERSISTENT, 4, 1000 * 60 * 10L);
			this.messageProducer.send(destination, msg3, DeliveryMode.NON_PERSISTENT, 6, 1000 * 60 * 10L);
			this.messageProducer.send(destination, msg4, DeliveryMode.NON_PERSISTENT, 8, 1000 * 60 * 10L);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Producer().send1();
		//new Producer().send2();
	}
}
