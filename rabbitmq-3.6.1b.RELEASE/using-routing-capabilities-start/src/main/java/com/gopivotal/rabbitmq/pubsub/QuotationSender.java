/**
 * 
 */
package com.gopivotal.rabbitmq.pubsub;

import java.util.concurrent.TimeoutException;

import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.RpcClient;

/**
 *
 */
public class QuotationSender {
	
	private static final QuotationService quotationService = new QuotationService();

	public static void main(String [] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		factory.setHost("localhost");
		factory.setPort(5672);
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		// TODO create the "quotations" fanout
		channel.exchangeDeclare("quotations", "fanout", true);
		
		
		while(true) {
			letsWait();
			String quotation = quotationService.next();
			//channel.basicPublish("quotations", "nasq", null, quotation.getBytes());
			
			try {
				RpcClient rpcClient = new RpcClient (channel,"quotations", "nasq",500);
				byte[] request =quotation.getBytes();
				byte[] reply =rpcClient.primitiveCall(request);
				
				//Do business wit the reply message 
				System.out.println("Reply: "+reply.toString());
				
			}catch (TimeoutException e) {
				
				System.out.println("TimeOut");
				
			}
			
		}
	}

	private static void letsWait() throws Exception {
		Thread.sleep(1000);		
	}
	
}
