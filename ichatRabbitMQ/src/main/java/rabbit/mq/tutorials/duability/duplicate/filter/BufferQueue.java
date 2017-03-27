package rabbit.mq.tutorials.duability.duplicate.filter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufferQueue {

	private static final Logger LOG = LoggerFactory.getLogger(BufferQueue.class);

	private int front = 0;
	private int rear = 0;
	private int maxSize;
	private Object[] queue;

	public BufferQueue(int maxSize) {
		this.maxSize = maxSize;
		this.queue = new Object[maxSize];
	}

	public void enQueue(Object item) {

		if (isFull()) {
			LOG.info("queue is full");
			for (int i = 0; i < maxSize / 2; i++) {
				deQueue();
			}
			//queueToString();
		}

		if (!isDuplicate(item)) {
			this.queue[rear] = item;
			rear = ++rear % maxSize;
			// queueToString();
			// 	TODO honor api call!!
		}
	}

	public Object deQueue() {

		if (front == rear) {
			throw new RuntimeException("Queue is empty");
		}

		Object item = this.queue[front];
		this.queue[front] = null;
		front = ++front % maxSize;
		return item;
	}

	public boolean isFull() {
		return ((rear + 1) % maxSize == front);
	}

	public void queueToString() {
		// LOG.info("front: {} , rear : {} = {}", this.front, this.rear, ToStringBuilder.reflectionToString(this.queue));
		System.out.println(ToStringBuilder.reflectionToString(this.queue));
	}

	public boolean isDuplicate(Object item) {

		/*
		 * for (int i = 0; i < maxSize; i++) { if (queue[i] != null &&
		 * queue[i].equals(item)) { LOG.info("duplicate item : {} ", item);
		 * return true; } }
		 */

		int tempMaxSize = (rear > front) ? rear : maxSize + rear;

		for (int i = front; i < tempMaxSize; i++) {
			Object storageItemp = queue[i % maxSize];
			if (storageItemp != null && storageItemp.equals(item)) {
				LOG.info("duplicate item : {} ", item);
				return true;
			}
		}

		return false;
	}
}
