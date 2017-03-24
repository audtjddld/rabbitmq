package rabbit.mq.tutorials.duability.duplicate.filter;

public class BufferQueueTest {
	public static void main(String[] args) {
		BufferQueue queue = new BufferQueue(10);
		for(int i = 0 ; i < 10; i++) {
			queue.enQueue(i);
		}
		
		for(int i = 5; i < 15; i ++) {
			queue.enQueue(i);
		}
	}
}
