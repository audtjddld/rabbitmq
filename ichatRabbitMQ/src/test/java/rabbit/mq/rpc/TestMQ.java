package rabbit.mq.rpc;

import org.junit.Ignore;

import rabbit.mq.rpc.server.RabbitMQ;

public class TestMQ {

	@Ignore
	@org.junit.Test
	public void test() throws Exception {
		RabbitMQ server = new RabbitMQ();
		Consumer consumer = new Consumer(server);
		Producer producer = new Producer(server);
		producer.sendToMessage("{\"msg\":\"안녕하세요.\"}");
		consumer.receivingMessage();
	}

}
