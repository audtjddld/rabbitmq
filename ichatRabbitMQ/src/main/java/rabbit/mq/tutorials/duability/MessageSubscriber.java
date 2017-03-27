package rabbit.mq.tutorials.duability;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import rabbit.mq.tutorials.duability.duplicate.filter.BufferQueue;

public class MessageSubscriber {
	private static final Logger LOG = LoggerFactory.getLogger(MessageSubscriber.class);
	private BufferQueue filter;
	private MQConfig mq;
	private Channel channel;

	public MessageSubscriber(int filterSize) throws Exception {
		filter = new BufferQueue(filterSize);
		mq = new MQConfig();

		// TODO consumer setting 부분 추가?
	}

	public void getSubscribeMessage() throws Exception {

		System.out.println("[*] Waiting for messages");

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				// LOG.info(" [x] sent message : {}", message);
				getKeyAndMessageCheck(message); // filter check
			}
		};
		mq.setConsumerChannel(consumer);
	}

	public void getKeyAndMessageCheck(String message) {
		String key = message.split(":")[0];
		filter.enQueue(key);
	}
}
