import java.util.*;

public class MyDoublyLinkedList<E> extends MyAbstractList<E> implements Cloneable {
	private Node<E> head = new Node<E>(null);

	/** Create a default list */
	public MyDoublyLinkedList() {
		head.next = head;
		head.previous = head;
	}

	private static class Node<E> {
		E element;
		Node<E> previous;
		Node<E> next;
		public Node(E element) {
			this.element = element;
		}
	}

	public String toString() {
		StringBuilder result = new StringBuilder("[");
		Node<E> current = head.next;
		for (int i = 0; i < size; i++) {
			result.append(current.element);
			current = current.next;
			if (current != head) {
				result.append(", "); // Separate two elements with a comma
			}
		}
		result.append("]"); // Insert the closing ] in the string
		return result.toString();
	}

	private Node<E> getNode(int index) {
		Node<E> current = head;
		if (index < size / 2)
			for (int i = -1; i < index; i++)
				current = current.next;
		else
			for (int i = size; i > index; i--)
				current = current.previous;
		return current;
	}

	@Override
	public void add(int index, E e) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		Node<E> prev = getNode(index - 1);
		Node<E> next = prev.next;
		Node<E> newNode = new Node<E>(e);
		prev.next = newNode;
		next.previous = newNode;
		newNode.previous = prev;
		newNode.next = next;
		size++;
	}

	@Override
	public void clear() {
		head.next = head;
		head.previous = head;
		size = 0;
	}

	@Override
	public boolean contains(E o) {
		for (Node<E> current = head.next; current != head; current = current.next) {
			E e = current.element;
			if (o == null ? e == null : o.equals(e))
				return true;
		}
		return false;
	}

	@Override
	public E get(int index) {
		if( index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return getNode(index).element;
	}

	@Override
	public int indexOf(E e) {
		Node<E> test = head.next;
		for(int i = 0; i < size; i++){
			if(e == null ? test.element == null : e.equals(test.element))
				return i;
			test = test.next;
		}
		// Note: Make sure that you check the equality with == for null  objects and with the equals() for others
		return -1;
	}

	@Override
	public int lastIndexOf(E e) {
		Node<E> curr = head;
		int location = 0;
		for (int i = 0; i < size; i++) {
			curr = curr.next;
			if (e.equals(curr.element)) {
				location = i;
			}
		}
		return location;
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		if (index == 0) {
			removeFirst();
		}
		else if (index == size - 1) {
			removeLast();
		}
		else {
			Node<E> curr = getNode(index);
			(curr.previous).next = curr.next;
			(curr.next).previous = curr.previous;
			size--;
			return curr.element;
		}
		return null;
	}

	@Override
	public Object set(int index, E e) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		Node<E> curr = getNode(index);
		curr.element = e;
		return curr.element;
	}

	public E getFirst() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		else {
			return head.next.element;
		}
	}

	public E getLast() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		else {
			return head.previous.element;
		}
	}

	public void addFirst(E e) {
		add(0, e);
	}

	public void addLast(E e) {
		Node<E> node = new Node<E>(e);
		if (head.previous == null) {
			head.next = head.previous = node;
		}
		else {
			node.previous = head.previous;
			(head.previous).next = node;
			head.previous = node;
			node.next = head;
		}
		size++;
	}

	public E removeFirst() {
		if (size == 0) {
		      throw new NoSuchElementException();
		}
		else {
			Node<E> temp = head.next;
			head.next = (head.next).next;
			head.next.previous = head;
			size--;
			return temp.element;
		}
	}

	public E removeLast() {
		if (size == 0) {
		      throw new NoSuchElementException();
		}
		else if (size == 1) {
			Node<E> temp = head.previous;
			head.next = head.previous = null;
			size = 0;
			return temp.element;
		}
		else {
			Node<E> temp = head.previous;
			head.previous = (head.previous).previous;
			head.previous.next = head;
			size--;
			return temp.element;
		}
	}

	public ListIterator<E> listIterator(int index) {
		return new MyDoublyLinkedListIterator(index);

	}

	private static enum ITERATOR_STATE {
		CANNOT_REMOVE, CAN_REMOVE_PREV, CAN_REMOVE_CURRENT
	};

	private class MyDoublyLinkedListIterator implements ListIterator<E> {
		private Node<E> current; // node that holds the next element in the
									// iteration
		private int nextIndex; // index of current
		private Node<E> emptyNode = null;
		ITERATOR_STATE iterState = ITERATOR_STATE.CANNOT_REMOVE;

		private MyDoublyLinkedListIterator(int index) {
			if (index < 0 || index > size)
				throw new IndexOutOfBoundsException("iterator index out of bounds");
			current = getNode(index);
			nextIndex = index;
		}

		@Override
		public void add(E arg0) {
			Node<E> node = new Node<E>(arg0);
			Node<E> lastNode = current.previous;
			lastNode.next = node;
			node.next = current;
			current.previous = node;
			node.previous = lastNode;
			size++;
			nextIndex++;
		}

		@Override
		public boolean hasNext() {
			return nextIndex < size;
		}

		@Override
		public boolean hasPrevious() {
			return nextIndex > 0;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			emptyNode = current;
			current = current.next;
			nextIndex++;
			return emptyNode.element;
		}

		@Override
		public int nextIndex() {
			return nextIndex;
		}

		@Override
		public E previous() {
			if (nextIndex <= 0) {
				throw new NoSuchElementException();
			}
			current = current.previous;
			nextIndex--;
			iterState = ITERATOR_STATE.CAN_REMOVE_PREV;
			return getNode(nextIndex).element;
		}

		@Override
		public int previousIndex() {
			return nextIndex - 1;
		}

		public void remove() {
			if (emptyNode == null) throw new IllegalStateException();
			else{
				if (emptyNode == current)
					current = current.next;
				else
					nextIndex--;
					emptyNode.previous.next = emptyNode.next;
					emptyNode.next.previous = emptyNode.previous;
					size--;
					emptyNode = null;
			}
		}

		@Override
		public void set(E arg0) {
			if (emptyNode == null) {
				throw new IllegalStateException();
			}
			emptyNode.element = arg0;
		}
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		else if  (!(other instanceof MyList))
			return false;
		MyDoublyLinkedList<?> myList = (MyDoublyLinkedList<?>)other;
		if(myList.size != size)
			return false;
		else{
			ListIterator<E> listIterator = listIterator();
			ListIterator<E> myListIterator = (ListIterator<E>) myList.listIterator();

			while(listIterator.hasNext()){
				E listElement = myListIterator.next();
				E myListElement = listIterator.next();
				if((listElement != null  && myListElement == null) || (listElement == null && myListElement != null))
					return false;
				if(listElement != null  && myListElement != null && !listElement.equals(myListElement))
					return false;
			}
			return true;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return listIterator();
	}

	public java.util.ListIterator<E> listIterator() {
	  	return listIterator(0);
	  }
}
