package com.custom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.err;

public class CollectionsMillionTest extends AbstractTestNGSpringContextTests {

    final private int count = 1000000;
    @Data
    @AllArgsConstructor
    private class A {
        private Integer val;
    }

    @Test
    public void millionHashSetTest() {
        HashSet<A> set = new HashSet<>();
        long time = currentTimeMillis();

        List<Integer> inxOfElementsToFind = generateListOfInx();
        List<A> elementsToFind = new ArrayList<>();

        for( int i=0; i<count; i++ ) {
            A a = new A(i);
            if( inxOfElementsToFind.contains(i) ) {
                elementsToFind.add(a);
            }
            set.add(a);
        }
        err.println("generation time=" + (currentTimeMillis()-time));

        time = currentTimeMillis();
        for( A a : elementsToFind ) {
            boolean isContains = set.contains(a);
        }
        err.println("end search. time=" + (currentTimeMillis()-time));
    }

    @Test
    public void millionHashTableTest() {
        Hashtable<Integer, A> table = new Hashtable<>();
        long time = currentTimeMillis();

        List<Integer> inxOfElementsToFind = generateListOfInx();
        List<A> elementsToFind = new ArrayList<>();

        for( int i=0; i < count; i++ ) {
            A a = new A(i);
            if( inxOfElementsToFind.contains(i) ) {
                elementsToFind.add(a);
            }
            table.put(i, a);
        }
        err.println("generation time=" + (currentTimeMillis()-time));

        time = currentTimeMillis();
        for( A a : elementsToFind ) {
            boolean isContains = table.containsValue(a);
        }
        err.println("end search. time=" + (currentTimeMillis()-time));
    }

    @Test
    public void millionHashMapTest() {
        HashMap<Integer, A> map = new HashMap<>();
        long time = currentTimeMillis();

        List<Integer> inxOfElementsToFind = generateListOfInx();
        List<A> elementsToFind = new ArrayList<>();

        for( int i=0; i<count; i++ ) {
            A a = new A(i);
            if( inxOfElementsToFind.contains(i) ) {
                elementsToFind.add(a);
            }
            map.put(i, a);
        }
        err.println("generation time=" + (currentTimeMillis()-time));

        time = currentTimeMillis();
        for( A a : elementsToFind ) {
            boolean isContains = map.containsValue(a);
        }
        err.println("end search. time=" + (currentTimeMillis()-time));
    }

    @Test
    public void millionArrayListTest() {
        ArrayList<A> list = new ArrayList<>();
        long time = currentTimeMillis();

        List<Integer> inxOfElementsToFind = generateListOfInx();
        List<A> elementsToFind = new ArrayList<>();

        for( int i=0; i < count; i++ ) {
            A a = new A(i);
            if( inxOfElementsToFind.contains(i) ) {
                elementsToFind.add(a);
            }
            list.add(a);
        }
        err.println("generation time=" + (currentTimeMillis()-time));

        time = currentTimeMillis();
        for( A a : elementsToFind ) {
            boolean isContains = list.contains(a);
        }
        err.println("end search. time=" + (currentTimeMillis()-time));
    }

    @Test
    public void millionIteratingArrayListTest() {
        ArrayList<A> list = new ArrayList<>();
        long time = currentTimeMillis();

        List<Integer> inxOfElementsToFind = generateListOfInx();
        List<A> elementsToFind = new ArrayList<>();

        for( int i=0; i < count; i++ ) {
            A a = new A(i);
            if( inxOfElementsToFind.contains(i) ) {
                elementsToFind.add(a);
            }
            list.add(a);
        }
        err.println("generation time=" + (currentTimeMillis()-time));

        time = currentTimeMillis();
        for( A a : elementsToFind ) {
            for( int i=0; i<count; i++ ) {
                if( a.equals(list.get(i))) {
                    break;
                }
            }
        }
        err.println("end search. time=" + (currentTimeMillis()-time));
    }

    @Test
    public void millionLinkedListTest() {
        LinkedList<A> list = new LinkedList<>();
        long time = currentTimeMillis();

        List<Integer> inxOfElementsToFind = generateListOfInx();
        List<A> elementsToFind = new ArrayList<>();

        for( int i=0; i < count; i++ ) {
            A a = new A(i);
            if( inxOfElementsToFind.contains(i) ) {
                elementsToFind.add(a);
            }
            list.add(a);
        }
        err.println("generation time=" + (currentTimeMillis()-time));

        time = currentTimeMillis();
        for( A a : elementsToFind ) {
            boolean isContains = list.contains(a);
        }
        err.println("end search. time=" + (currentTimeMillis()-time));
    }

    private List<Integer> generateListOfInx() {
        List<Integer> inxOfElementsToFind = new ArrayList<>();
        Random rand = new Random();
        for( int j=0; j<50; j++) {
            inxOfElementsToFind.add(rand.nextInt(count));
        }
        return inxOfElementsToFind.stream().sorted().collect(Collectors.toList());
    }
}
