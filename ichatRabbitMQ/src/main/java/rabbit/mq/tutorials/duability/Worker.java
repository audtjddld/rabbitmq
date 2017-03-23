package rabbit.mq.tutorials.duability;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Worker {
	private final static String QUEUE_NAME = "task_queue1";

	private static Map<String, Object> map = new HashMap<>();

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		System.out.println("[*] Waiting for messages, To exit press CTRL+C");

		channel.basicQos(1);

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				// envelope -> routingKey is quene name.
				// System.out.println(" [x] Received '" + message + "'" +
				// envelope.getRoutingKey());
				messageProcess(message);
				//channel.basicAck(envelope.getDeliveryTag(), false); // <-- 겁나 느림 =_=;
				System.out.println("message " + message + " [x] Done");
			}

		};
		boolean autoAck = true;
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}

	public static void messageProcess(String message) {
		String key = message.split(":")[0];
		if (!map.containsKey(key)) {
			map.put(key, message);
		} else if (map.containsKey(key)) {
			System.err.println("message duplicate error!! " + message);
		}
	}
}
