package rabbit.mq.tutorials.duability;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

public class MQConfig {

	private final String QUEUE_NAME = "task_queue";
	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;

	public MQConfig() throws Exception {
		factory = new ConnectionFactory();
		factory.setHost("localhost");

		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
	}

	public void setConsumerChannel(Consumer consumer) throws Exception {
		boolean autoAck = true;
		channel.basicQos(1);
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}

	public void sendMessage(String message) throws Exception {
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
	}
}
