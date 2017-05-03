package com.ja0ck5.activemq.pubsub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Subscriber1 {

	
	
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private MessageConsumer messageConsumer;
	
	public Subscriber1() {
		try {
			this.connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
					ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
			this.connection = this.connectionFactory.createConnection();
			this.connection.start();
			this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public Session getSession() {
		return this.session;
	}
	
	public static final String queueName = "first";
	public static final String topic_zoo = "topic_zoo";
	
	public void receive() {
		try {
			Destination destination = this.getSession().createTopic(topic_zoo);
			this.messageConsumer = this.getSession().createConsumer(destination);
			this.messageConsumer.setMessageListener(new Listener());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	
	}
	
	class Listener implements MessageListener{
		public void onMessage(Message message) {
			try {
				TextMessage msg = (TextMessage ) message;
				System.out.println(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new Subscriber1().receive();
	}
	
	
	

}
