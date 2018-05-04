/**
 * 
 */
package com.gopivotal.rabbitmq;

import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 */
public class QuotationSender {

	private static final QuotationService quotationService = new QuotationService();
	
	private final static String USER_NAME="guest";
	private final static String PASSWORD="guest";
	private final static String VIRTUAL_HOST="/";
	private final static String HOST="localhost";
	private final static int PORT=5672;
	
	
	private final static String EXCHANGE_NAME="quotations";
	private final static String ROUTING_KEY="nasq";
	
	
	
	

	public static void main(String[] args) throws Exception {
		
		// TODO setup the connection with a ConnectionFactory (see slides for examples)
		ConnectionFactory factory=new ConnectionFactory();
		factory.setUsername(USER_NAME);
		factory.setPassword(PASSWORD);
		factory.setVirtualHost(VIRTUAL_HOST);
		factory.setHost(HOST);
		factory.setPort(PORT);
		
		Connection connection=factory.newConnection();
		
		
		// TODO setup a new channel using the newly created connection
		Channel channel=connection.createChannel();
		
		// TODO In the infinite loop while(true) add the following: 
		// 1) wait for some period of time, call letsWait() 
		// 2) get a quotation from the service by calling next()
		// 3) Send a message to the quotations exchange with routing key "nasq" by calling basicPublish() on the channel
		while(true) {
			letsWait();
			String quotation = quotationService.next();
			System.out.println("Quotation message "+quotation);
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, quotation.getBytes());
		}
		
		
		
	}

	private static void letsWait() throws Exception {
		Thread.sleep(1000);
	}

}
