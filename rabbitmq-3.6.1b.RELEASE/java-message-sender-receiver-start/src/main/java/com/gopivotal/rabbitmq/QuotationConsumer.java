/**
 * 
 */
package com.gopivotal.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;


/**
 *
 */
public class QuotationConsumer {

	public static void main(String [] args) throws Exception {

		// TODO setup the connection with a ConnectionFactory (see slides for examples)
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		factory.setHost("localhost");
		factory.setPort(5672);
		Connection connection = factory.newConnection();
		
		// TODO setup a new channel using the newly created connection
		Channel channel = connection.createChannel();
		
		// TODO  create a DefaultConsumer instance and call basicConsume() on the channel
	
		
		channel.basicConsume("quotations", true,new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println("receiving quotation: "+new String(body));				
			}
		});		
		
	}
	
}
