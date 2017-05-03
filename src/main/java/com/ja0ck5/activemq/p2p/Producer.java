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
	public static final String queueName = "first";
	public void send1() {
		try {
			Destination destination = this.getSession().createQueue(queueName);
			MapMessage msg1 = this.getSession().createMapMessage();
			msg1.setStringProperty("name", "HAHA");
			msg1.setString("name", "HAHA");
			msg1.setIntProperty("age", 0);
			msg1.setInt("age", 0);
			msg1.setStringProperty("color", "blue");
			msg1.setIntProperty("sal", 20000);
			int id = 1;

			msg1.setIntProperty("id", id);
			msg1.setStringProperty("receiver", id % 2 == 0 ? "A" : "B");
			
			MapMessage msg2 = this.getSession().createMapMessage();
			msg2.setStringProperty("name", "HEHE");
			msg2.setString("name", "HEHE");
			msg2.setIntProperty("age", 1);
			msg2.setInt("age", 1);
			msg2.setStringProperty("color", "blue");
			msg2.setInt("sal", 200001);
			msg2.setIntProperty("sal", 200001);
			id = 2;
			msg2.setIntProperty("id", id);
			msg2.setStringProperty("receiver", id % 2 == 0 ? "A" : "B");

			MapMessage msg3 = this.getSession().createMapMessage();
			msg3.setStringProperty("name", "WYD");
			msg3.setString("name", "WYD");
			msg3.setIntProperty("age", 2);
			msg3.setInt("age", 2);
			msg3.setStringProperty("color", "blue");
			msg3.setIntProperty("sal", 20000);
			id = 3;
			msg3.setIntProperty("id", id);
			msg3.setStringProperty("receiver", id % 2 == 0 ? "A" : "B");
			
			MapMessage msg4 = this.getSession().createMapMessage();
			msg4.setStringProperty("name", "WTF");
			msg4.setString("name", "WTF");
			msg4.setIntProperty("age", 3);
			msg4.setStringProperty("color", "red");
			msg4.setIntProperty("sal", 20004);
			id = 4;
			msg4.setIntProperty("id", id);
			msg4.setStringProperty("receiver", id % 2 == 0 ? "A" : "B");

			this.messageProducer.send(destination, msg1, DeliveryMode.NON_PERSISTENT, 1, 1000 * 60 * 10L);
			this.messageProducer.send(destination, msg2, DeliveryMode.NON_PERSISTENT, 4, 1000 * 60 * 10L);
			this.messageProducer.send(destination, msg3, DeliveryMode.NON_PERSISTENT, 6, 1000 * 60 * 10L);
			this.messageProducer.send(destination, msg4, DeliveryMode.NON_PERSISTENT, 8, 1000 * 60 * 10L);
		} catch (JMSException e) {
			e.printStackTrace();
		}finally {
			try {
				this.connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		new Producer().send1();
		//new Producer().send2();
	}
}
