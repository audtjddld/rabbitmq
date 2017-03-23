package rabbit.mq.rpc;

import rabbit.mq.rpc.server.RabbitMQ;

public class Test {
	public static void main(String[] args) throws Exception {
		System.out.println("==== mq test start ====");

		RabbitMQ server = new RabbitMQ();
		Consumer consumer = new Consumer(server);
		Producer producer = new Producer(server);

		/*
		  producer.sendToMessage("{\"msg\":\"안녕하세요.\"}");
		  consumer.receivingMessage();
		 */
		while (true) {
			new Thread(producer).start();
			new Thread(consumer).start();
		}

	}
}
