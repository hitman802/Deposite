package initializer;

import lombok.Getter;
import lombok.Setter;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Created by Admin on 02.06.2017.
 */
public class ReverseLinkedList {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        for( int i=1; i<11; i++) {
            list.add(i);
        }
        System.err.println("BEFORE:" + list);
        System.err.println("AFTER1:" + revertList1(list));

        MyList<Integer> myList = new MyList<>();
        for( int i=1; i<11; i++) {
            myList.add(i);
        }
        System.err.println("BEFORE:" + myList);
        myList.reverse();
        System.err.println("AFTER:" + myList);
    }

    private static LinkedList<Integer> revertList1(LinkedList<Integer> list) {
        LinkedList<Integer> revertedList = new LinkedList<Integer>();
        Iterator<Integer> iter = list.descendingIterator();
        while( iter.hasNext() ) {
            revertedList.add(iter.next());
        }
        return revertedList;
    }
}

class MyList<T> {
    @Getter @Setter Node<T> head;
    void add(T val){
        if( head == null ) {
            head = new Node<T>(val);
            return;
        }
        Node<T> curNode = head;
        while( curNode.getNextNode() != null ) {
            curNode = curNode.getNextNode();
        }
        curNode.setNextNode(new Node<T>(val));
    }

    void reverse() {
        Node<T> currentNode = head;
        Node<T> previousNode = null;
        Node<T> nextNode;
        while(currentNode != null) {
            nextNode = currentNode.getNextNode();
            currentNode.setNextNode(previousNode);
            previousNode = currentNode;
            head = currentNode;
            currentNode = nextNode;
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node temp = head;
        while (temp != null) {
            sb.append(temp);
            temp = temp.getNextNode();
            if( temp!= null )
                sb.append(", ");
        }
        sb.append("]");

        return sb.toString();
    }
}

class Node<T> {
    @Getter @Setter private Node<T> nextNode;
    @Getter @Setter T val;
    Node(T val) {
        this.val = val;
    }
    @Override
    public String toString() {
        return val.toString();
    }
}