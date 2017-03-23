package rabbit.mq.tutorials;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages, To exit press CTRL+C");

		Consumer consumer1 = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Consumer1 Received '" + message + "'");
			}
		};
		Consumer consumer2 = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Consumer2 Received '" + message + "'");
			}
		};
		
		channel.basicConsume(QUEUE_NAME, true, consumer1);
		channel.basicConsume(QUEUE_NAME, true, consumer2);
		
		/*
 			
 			queue attached two consumer.
 			and meassge send to queue
 			
 			receiver got a message was here. 

 			 [*] Waiting for messages, To exit press CTRL+C
			 [x] Consumer1 Received 'hello world'
			 [x] Consumer2 Received 'hello world'
			 [x] Consumer1 Received 'hello world'
			 [x] Consumer2 Received 'hello world'
			 [x] Consumer1 Received 'hello world'
			 [x] Consumer2 Received 'hello world'
			 
			 consumer 1, cousumer 2 round robin got a message.
		 */
	}
}
