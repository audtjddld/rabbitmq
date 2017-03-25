package rabbit.mq.tutorials.duability;

public class MessageSender {

	private MQConfig mq;
	private int key = 0;

	public MessageSender() throws Exception {
		this.mq = new MQConfig();
	}

	public void sendMessage(String message) throws Exception {
		message = getKey() + ":" + message;
		mq.sendMessage(message);
	}

	public int getKey() {
		return ++key;
	}

}
