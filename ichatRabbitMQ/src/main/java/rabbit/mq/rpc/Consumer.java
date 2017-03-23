package rabbit.mq.rpc;

import rabbit.mq.rpc.server.RabbitMQ;

public class Consumer implements Runnable {
	private RabbitMQ server;

	public Consumer(RabbitMQ server) {
		this.server = server;
	}

	public void receivingMessage() throws Exception {
		this.server.subcribeMessage();
	}

	@Override
	public void run() {
		try {
			this.receivingMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
