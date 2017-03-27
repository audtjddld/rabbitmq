package rabbit.mq.tutorials.duability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runcher {
	private static final Logger LOG = LoggerFactory.getLogger(Runcher.class);
	
	private static MessageSender sender;

	private static MessageSubscriber subscriber;

	public static void main(String[] args) throws Exception {
		sender = new MessageSender();
		subscriber = new MessageSubscriber(100);

		subscriber.getSubscribeMessage();
		
		/* duplicate /
		for (int i = 0; i < 100; i++) {
			sender.sendMessage(i + ":hello world");
		}
		
		for (int i = 51; i < 150; i++) {
			sender.sendMessage(i + ":hello world");
		}
		/**/
		
		/**/
		Integer number = 1;
		
		while(true) {
			sender.sendMessage( (number == Integer.MAX_VALUE ? number = 1 : number++ ) + ":hello world");
		}
		/**/
		
	}
}
