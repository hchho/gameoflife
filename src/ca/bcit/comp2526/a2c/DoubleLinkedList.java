package ca.bcit.comp2526.a2c;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.Serializable;

/**
 * DoublyLinkedList class.
 * @author Henry
 * @version 2017
 * @param <E> for Generic type that extends Serializable
 */
public class DoubleLinkedList<E extends Serializable> implements Iterable<E>, 
Serializable {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Node<E> class.
     * @author Henry
     * @param <E> for generic type
     */
    public static class Node<E> implements Serializable {

        /**
         * Serial Version ID. 
         */
        private static final long serialVersionUID = 1L;

        private E data;
        private Node<E> prev;
        private Node<E> next;

        /**
         * Constructs Node.
         * @param e for E
         */
        public Node(E e) {
            data = e;
            prev = null;
            next = null;
        }
    }

    private Node<E> head;
    private Node<E> tail;

    /**
     * Constructs DoubleLinkedList.
     */
    public DoubleLinkedList() {
    }

    /**
     * Returns Iterator<E>.
     * @return E or null
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            
            private Node<E> firstNode = head;
            private Node<E> pointer;

            /**
             * Overrides abstract hasNext method
             * @return true if the next element is not null, else return false
             */
            @Override
            public boolean hasNext() {
                if (firstNode == null) {
                    return false;
                } else if (pointer == null) {
                    return true;
                } else {
                    return pointer != tail;
                }
            }

            /**
             * Overrides abstract next method
             * @return current Node data as an E
             */
            @Override
            public E next() throws NoSuchElementException {
                if (firstNode == null) {
                    throw new NoSuchElementException("List is empty");
                } else if (pointer == null) {
                    pointer = firstNode;
                    return pointer.data;
                } else if (pointer.next == null) {
                    throw new NoSuchElementException("End of list.");
                } else {
                    pointer = pointer.next;
                    return pointer.data;
                }
            }
        };
    }

    /**
     * Adds E to front of list.
     * @param e for Generic type.
     * @throws CouldNotAddException if unable to add.
     */
    public void addToFront(E e) throws CouldNotAddException {
        if (e == null) {
            throw new CouldNotAddException(e + " can not be null.");
        } else {
            Node<E> temp = new Node<>(e);
            if (head != null) {               
                temp.next  = head;
                head.prev = temp;
                head = temp;
            } else {
                head = temp;
                tail = temp;
                temp.next = null;
                temp.prev = null;
            }
        }
    }

    /**
     * Removes first element in DoubleLinkedList.
     * @return E for element
     * @throws CouldNotRemoveException if list is empty
     */
    public E removeFromFront() throws CouldNotRemoveException {
        if (size() == 0) {
            throw new CouldNotRemoveException("List is empty.");
        } else {
            E removedData = head.data;
            if (head.next == null && head.prev == null) {
                head = null;
                tail = null;
                return removedData;
            } else {
                Node<E> newHead = head.next;
                head = newHead;
                head.next = newHead.next;
                head.prev = null;
                return removedData;
            }
        }
    }

    /**
     * Adds E to end of list.
     * @param e for Generic type.
     * @throws CouldNotAddException if unable to add.
     */
    public void addToEnd(E e) throws CouldNotAddException {
        if (e == null) {
            throw new CouldNotAddException(e + " can not be null.");
        } else {
            Node<E> temp = new Node<>(e);
            if (tail != null) {               
                temp.prev  = tail;
                tail.next = temp;
                tail = temp;
            } else {
                head = temp;
                tail = temp;
                temp.next = null;
                temp.prev = null;
            }
        }
    }

    /**
     * Removes E from the end of DoubleLinkedList.
     * @return E as removed E
     * @throws CouldNotRemoveException if list is empty
     */
    public E removeFromEnd() throws CouldNotRemoveException {
        if (tail == null) {
            throw new CouldNotRemoveException("List is empty.");
        } else {
            Node<E> temp = tail;
            E removeData = temp.data;
            if (temp.next == null && temp.prev == null) {
                head = null;
                tail = null;
                return removeData;
            } else {
                Node<E> newTail = tail.prev;
                tail = newTail;
                tail.prev = newTail.prev;
                tail.next = null;
                return removeData;
            }
        }
    }

    /**
     * Gets first Element in list.
     * @return null if list is empty, else return E
     */
    public E getFirst() {
        if (size() == 0) {
            return null;
        } else {
            return head.data;
        }
    }

    /**
     * Gets last Element in list.
     * @return null if list is empty, else return E
     */
    public E getLast() {
        if (size() == 0) {
            return null;
        } else {
            return tail.data;
        }
    }

    /**
     * Gets size of DoubleLinkedList.
     * @return size as an int
     */
    public int size() {
        int counter = 0;
        for (E elem : this) {
            counter++;
        }
        return counter;
    }
}
