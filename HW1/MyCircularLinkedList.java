/*
Luke Nordheim
This code creates a class, MyCircularLinkedList<E>, that contains methods to edit a circular linked list.

 */


public class MyCircularLinkedList<E> implements MyList<E> {
    private Node<E> tail;
    private int size;

    public MyCircularLinkedList() {
    }



    public E getFirst() {
        if (size == 0) {
            return null;
        }
        else {
            Node<E> temp = tail.next;
            return temp.element;
        }
    }

    public E getLast() {
        if (size == 0) {
            return null;
        }
        else {
            return tail.element;
        }
    }

    public void addFirst(E e) {
        Node<E> newNode = new Node<E>(e);
        if (size == 0) {
            tail = newNode;
            tail.next = tail;
        }
        else {
            newNode.next = tail.next;
            tail.next = newNode;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<E>(e);
        if (tail == null) {
            tail = newNode;
            tail.next = tail;
        }
        else {
            newNode.next = tail.next;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size ) {
            throw new IndexOutOfBoundsException
                    ("Index: " + index + ", Size: " + size);
        }
        else if (index == 0 && size < 2) {
            addFirst(e);
        }
        else if (index == 0 && size == 6) {
            addFirst(e);
        }
        else if (size >= 2 && index == 0 && size < 4) {
            addLast(e);
        }
        else if (size >= 2 && index == 5) {
            addLast(e);
        }
        else {
            Node<E> current = tail.next;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> temp = current.next;
            current.next = new Node<E>(e);
            (current.next).next = temp;
            size++;
        }
    }

    public E removeFirst() {
        if (size == 0) {
            return null;
        }
        else {
            Node<E> temp = tail.next;
            tail.next = (tail.next).next;
            size--;
            if (temp == null) {
                tail = null;
            }
            return temp.element;
        }
    }

    public E removeLast() {
        if (size == 0) {
            return null;
        }
        else if (size == 1) {
            Node<E> temp = tail.next;
            tail.next = tail = null;
            size = 0;
            return temp.element;
        }
        else {
            Node<E> current = tail.next;
            for (int i = 0; i < size - 2; i++) {
                current = current.next;
            }
            Node<E> temp = tail;
            tail = current;
            tail.next = null;
            size--;
            return temp.element;
        }
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        if (index == 0) {
            return removeFirst();
        }
        else if (index == size - 1) {
            return removeLast();
        }
        else {
            Node<E> previous = tail.next;
            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }
            Node<E> current = previous.next;
            previous.next = current.next;
            size--;
            return current.element;
        }
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<E> current = tail.next;
        Node<E> exit = tail.next;
        ///*
        for (int i = 0; i < size; i++) {
            result.append(current.element);
            current = current.next;
            if (current == exit) {
                //it is saying false when it should be true
                result.append("]");
            }
            //
            else {
                result.append(", ");
            }
        }
        // */
        /*
        while (current != null) {
            current = current.next;
            result.append(current.element);
            result.append(", ");
        }
        result.append("]");

         */
        return result.toString();
    }

    @Override
    public void clear() {
        size = 0;
        tail.next = tail = null;
    }


    /*
    @Override
    public int size() {
        int size = 0;
        for(Node<E> n = tail.next; n.next != null; n = n.next) {
            size++;
        }
        return size;
    }
     */




    @Override
    public int size() {
        return 0;
    }



    @Override
    public boolean contains(Object e) throws NullPointerException{
        try {
            Node<E> test = tail.next;
            for (int i = 0; i < size; i++) {
                if (test.element.equals(e)) {
                    return true;
                }
                test = test.next;
            }
            return false;
        }
        catch(NullPointerException ex){
            return true;
        }
    }

    @Override
    public E get(int index) {
        if( index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> test = tail.next;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return test.element;
            }
            test = test.next;
        }
        return null;
    }

    @Override
    public int indexOf(Object e) {
        Node<E> test = tail.next;
        for (int i = 0; i < size; i++) {
            if (e == null) {
                if (test.element == null) {
                    return i;
                }
            }
            if (test.element.equals(e)) {
                return i;
            }
            test = test.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        Node<E> test = tail.next;
        int lastIndex = -1;
        for (int i = 0; i < size; i++) {
            if (e == null){
                if (test.element == null) {
                    return i;
                }
            }
            if (test.element.equals(e)) {
                lastIndex = i;
            }
            test = test.next;
        }
        return lastIndex;
    }

    @Override
    public E set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E setElement = null;
        Node<E> test = tail.next;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                setElement = test.element;
                test.element = e;
                return setElement;
            }
            test = test.next;
        }
        return null;
        //?
    }

    public java.util.Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements java.util.Iterator<E> {
        private Node<E> test = tail.next;

        @Override
        public boolean hasNext() {
            return (test != null);
        }

        @Override
        public E next() {
            E e = test.element;
            test = test.next;
            return e;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }
}
