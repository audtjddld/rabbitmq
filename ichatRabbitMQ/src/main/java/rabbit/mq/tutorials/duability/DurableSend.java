package rabbit.mq.tutorials.duability;

import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class DurableSend {

	private final static String QUEUE_NAME = "task_queue";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		long num = 0L;
		while(true) {
			String message = "hello world --> " + num;
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
			num ++;
			
			TimeUnit.MILLISECONDS.sleep(10);
			if(num == 10000) {
				break;
			}
		}
		
		//channel.close();
		//connection.close();
	}
}
