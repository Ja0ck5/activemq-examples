package com.ja0ck5.activemq.p2p;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
	
	public static final String selector_1 = "color = 'blue'";
	public static final String selector_2 = "color = 'blue' and sal > 20000";
	public static final String selector_3 = "receiver = 'A'";

	public static final String queueName = "first";


		private ConnectionFactory connectionFactory;
		private Connection connection;
		private Session session;
		private MessageConsumer messageConsumer;
		private Destination destination;

		public Consumer() {
			try {
				this.connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
						ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
				this.connection = this.connectionFactory.createConnection();
				this.connection.start();
				this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
				this.destination = this.session.createQueue(queueName);
				this.messageConsumer = this.session.createConsumer(this.destination,selector_3);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		public Session getSession() {
			return this.session;
		}


		public void receive(){
			try {
				this.messageConsumer.setMessageListener(new Listener());
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

		class Listener implements MessageListener{
			public void onMessage(Message message) {
				try {
					MapMessage mapMessage = (MapMessage) message;
					System.out.println(mapMessage.toString());
					System.out.println(mapMessage.getString("name"));
					System.out.println(mapMessage.getString("age"));
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static void main(String[] args) {
			Consumer consumer = new Consumer();
			consumer.receive();
		}
}
