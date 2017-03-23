package rabbit.mq.rpc.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitMQ {
	private static final Logger LOG = LoggerFactory.getLogger(RabbitMQ.class);
	
	private static final String RPC_QUEUE_NAME = "rpc_queue";
	private static final String EXCHANGE_KEY = "move";
	private ConnectionFactory factory;
	Connection connection;
	Channel channel;

	public RabbitMQ() throws Exception {
		factory = new ConnectionFactory();
		factory.setHost("localhost");
		//factory.setUsername("guest");
		//factory.setPassword("guest");
		connection = factory.newConnection();
		channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_KEY, "direct");
		channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
		channel.queueBind(RPC_QUEUE_NAME, EXCHANGE_KEY, "");

		// RPC Redis Queue를 생성 수 이 큐를 이용하여 consumer와 producer를 개발하자.
		// RPC Queue 관련
		/*
		 * Request - Response로 Client와 Server를 이어주기 위해 RPC라는 개념으로 기능을 제공하는데.
		 * Server가 처리한 결과를 알맞은 Client요청에 대한 응답으로 전달 할 수 있는 방법이라고 함.
		 */
	}

	public void publishedMessage(String message) throws IOException {
		if (message != null) {
			LOG.info("publishedMessage : {}", message);
			//System.out.println("publishedMessage : "+ message);
			channel.basicPublish(EXCHANGE_KEY, "", new AMQP.BasicProperties.Builder()
					.contentType("application/json")
					.deliveryMode(2)	// 메시지 저장? 전략?
					.priority(1)
					.build(),
					message.getBytes());
		}
	}

	public void subcribeMessage() throws IOException {
		boolean autoAck = false;

		channel.basicConsume(RPC_QUEUE_NAME, autoAck, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String routingKey = envelope.getRoutingKey();
				String contentType = properties.getContentType();
				long deliveryTag = envelope.getDeliveryTag();
				// (process the message components here ...)
				LOG.info("{} : {} ", routingKey, contentType);
				getMessage(body);
				channel.basicAck(deliveryTag, false);
			}
		});
	}

	public void getMessage(byte[] body) {
		LOG.info(" i got a message : {} and send to filter server", new String(body));
		//System.out.println("i got a message : " + new String(body));
		//TODO message send to Filter server  
	}
}
