package utility;

public class Queue<T> {

	private class Node {
		public Node next;
		public T data;

		public Node(T data) {
			this.data = data;
		}
	}

	Node head;
	Node tail;

	public Queue() {
		head = null;
		tail = null;
	}

	public void enqueue(T item) {
		if (head == null) {
			head = new Node(item);
			;
			tail = head;
		} else {
			tail.next = new Node(item);
			tail = tail.next;
		}
	}

	public T dequeue() {
		if (head == null) {
			return null;
		}
		T data = head.data;
		head = head.next;
		return data;
	}

	public T peek() {
		return head.data;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public void print() {
		Node curr = head;
		while (curr != null) {
			System.out.println(curr.data);
			curr = curr.next;
		}
	}
}
