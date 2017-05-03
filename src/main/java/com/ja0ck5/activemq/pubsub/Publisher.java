package com.ja0ck5.activemq.pubsub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {

	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private MessageProducer messageProducer;

	public Publisher() {
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
	public static final String topic_zoo = "topic_zoo";

	public void send1() {
		try {
			Destination destination = this.getSession().createTopic(topic_zoo);
			TextMessage message = this.getSession().createTextMessage("This is a topic about zoo");
			this.messageProducer.send(destination, message);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				this.connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		new Publisher().send1();
	}
}
