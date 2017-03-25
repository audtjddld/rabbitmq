package rabbit.mq.tutorials.duability;

public class Runcher {
	
	private static MessageSender sender;
	
	private static MessageSubscriber subscriber;
	
	public static void main(String[] args) throws Exception {
		sender = new MessageSender();
		subscriber = new MessageSubscriber(5000);
		
		subscriber.getSubscribeMessage();
		
		for(int i = 0; i < 100000; i++) {
			sender.sendMessage("hello world" + i);
		}
	}
}
