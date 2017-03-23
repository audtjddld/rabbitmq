package rabbit.mq.rpc;

import rabbit.mq.rpc.server.RabbitMQ;

public class Producer implements Runnable {
	private RabbitMQ server;

	public Producer(RabbitMQ server) {
		this.server = server;
	}

	public void sendToMessage(String message) throws Exception {
		this.server.publishedMessage(message);
	}

	@Override
	public void run() {
		try {
			this.sendToMessage("{\"msg\":\"안녕하세요.\"}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
