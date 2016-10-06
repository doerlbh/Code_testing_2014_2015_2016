
import java.util.*;

public class sortMain {

	public static void main(String[] args) {
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(4);
		q.add(7);
		q.add(-11);
		q.add(12);
		q.add(15);
		q.add(-18);
		q.add(-22);
		q.add(55);
		q.add(-102);
		q.add(107);

		System.out.println("before sorting: ");
		System.out.println(q);
		sort(q);
		System.out.println("after sorting: ");
		System.out.println(q);
	}

	public static void sort(Queue<Integer> q) {
		Stack<Integer> s = new Stack<Integer>();
		int next = q.remove();
		while (Math.abs(next) < Math.abs(q.peek())) {
			if (next < 0) {
				s.push(next);
			} else {
				q.add(next);
			}
		}
		while (!s.isEmpty()) {
			q.add(s.pop());
		}
		while (q.peek() > 0) {
			q.add(q.remove());
		}
	}



}

