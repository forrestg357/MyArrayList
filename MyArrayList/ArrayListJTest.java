import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

class ArrayListJTest {
	
	private ArrayList<Integer> test = new ArrayList<Integer>();
	private MyArrayList test2 = new MyArrayList();
	
	@Test
	void testCollectionsConstructor(){
		//Checking to make sure elements of a collection are added via the constructor
		
		populateArrayLists(5000);
		MyArrayList<Integer> testCollections = new MyArrayList<Integer>(test);
		assertEquals(5000, testCollections.size());
		for (int i = 0; i < 5000; i++) {
			assertEquals(test.get(i), testCollections.get(i));
		}
	}
	
	@Test
	void testClear() {
		//Checking the clear method 
		populateArrayLists(5000);
		test2.clear(); 
		test.clear();
		assertEquals(test.size(), 0); 
	}
	
	@Test
	void testGet() {
		test2.clear();
		test.clear();
		Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> test2.get(0));
	    assertEquals(null, exception.getMessage());
		populateArrayLists(50000);
		for(int i = 0; i < 50000; i++) {
			assertEquals(test.get(i), test2.get(i));
		}
	}
	
	@Test
	void testSet() {
		int populateNum = 50000;
		populateArrayLists(populateNum);
		int firstIndex = 0;
		int lastIndex = populateNum - 1;
		int randomIndex = (int) (populateNum * Math.random());
		test.set(0, 1);
		test.set(populateNum - 1, 2);
		test.set(randomIndex, 3);
		
		test2.set(0, 1);
		test2.set(populateNum - 1, 2);
		test2.set(randomIndex, 3);
		
		assertEquals(test.get(0), test2.get(0));
		assertEquals(test.get(populateNum - 1), test2.get(populateNum - 1));
		assertEquals(test.get(randomIndex), test2.get(randomIndex));
	}
	
	@Test
	void testSize() {
		populateArrayLists(50000);
		assertEquals(50000, test2.size());
	}
	
	@Test
	void testIsEmpty() {
		populateArrayLists(50000);
		test2.clear();
		assertEquals(0, test2.size());
		Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> test2.get(0));
	    assertEquals(null, exception.getMessage());
	}
	
	@Test
	void testAdd() {
		//Testing the add at a given index method
		
		int populateNum = 50000;
		populateArrayLists(populateNum);
		for (int j = 0; j < 10000; j++) {
			int randomValue = (int) (Math.random() * 100);
			int randomIndex = (int) (Math.random() * (populateNum + 1 + j));
			test.add(randomIndex, randomValue);
			test2.add(randomIndex, randomValue);
			assertEquals(test.get(randomIndex), test2.get(randomIndex));
		}
	}
	
	@Test
	void testAddToEnd() {
		//Testing the add onto the end of list method

		populateArrayLists(5000000);
		for(int i = 0; i < 5000000; i++) {
			assertEquals(test.get(i), test2.get(i));
		}
	}
	
	@Test
	void testAddAllIndex() {	
		//Adding a collection to an index
		int populateNum = 500;
		populateArrayLists(populateNum);
		int randomIndex = (int) Math.random() * (populateNum + 1);
		test2.addAll(randomIndex, test);
		for (int i = 0; i < test.size(); i++) {
			assertEquals(test.get(i), test2.get(i + randomIndex));
		}
	}

	@Test
	void testAddAllEnd() {
		//Adding a collection to the end 
		int populateNum1 = 50;
		int populateNum2 = 5000;
		ArrayList<Integer> test = new ArrayList<Integer>();
		ArrayList<Integer> test2 = new ArrayList<Integer>();
		for (int i = 0; i < populateNum1; i++) {
			test.add(i);
		}
		for(int i = 0; i < populateNum2; i++) {
			test2.add(i);
		}
		test2.addAll(test);
		for (int i = 0; i < populateNum1; i++) {
			assertEquals(test.get(i), test2.get(populateNum2 + i));
		}
		ArrayList<Integer> test3 = new ArrayList<Integer>();
		test2.addAll(test3);
		
	}

	@Test
	void testRemoveRange() {
		int populateNum = 50000; 
		populateArrayLists(populateNum);
		int startIndex = (int) (Math.random() * (populateNum - 1));
		int endIndex = (int) (1 + startIndex + Math.random() * (50000 - startIndex));
		test2.removeRange(startIndex, endIndex);
		assertEquals(test.size() - (endIndex - startIndex), test2.size());
		assertEquals(test.get(endIndex), test2.get(startIndex));
	}
	
	//Testing the size method after adding some numbers, and taking out some numbers
	@Test
	void testRemoveMiddle() {
		int populateNum = 5000;
		populateArrayLists(populateNum);
		int numRemove = 500;
		for (int i = 0; i < numRemove; i++) {
			test.remove((int) Math.random() * (populateNum - i));
			test2.remove((int) Math.random() * (populateNum - i));
		}
		assertEquals(test.size(), test2.size());
		for (int i = 0; i < populateNum - numRemove; i++) {
			assertEquals(test.get(i), test2.get(i));
		}
	}
	
	@Test
	void testRemoveFromEnd() {
		//Checking the remove method (removing a term at the end)
		
		populateArrayLists(500);
		test.remove(499);
		test2.remove(499);
		assertEquals(test2.get(498), test.get(498));
		Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> test2.get(499));
	    assertEquals(null, exception.getMessage());
	}
	
	@Test
	void testRemoveObject() {
		populateArrayLists(5000);
		test.remove(test.get(10));
		test2.remove(test2.get(10));
		assertEquals(test.size(), test2.size());
		for(int i = 0; i < 4999; i++) {
			assertEquals(test.get(i), test2.get(i));
		}
	}
	
	@Test
	void testContains() {
		int populateNum = 5000;
		test.clear();
		test2.clear();
		for (int i = 0; i < populateNum; i++) {
			test.add(i);
			test2.add(i);
		}
		for (int i = 0; i < populateNum; i++) {
			assertEquals(test.contains(i), test2.contains(i));
		}
	}
	
	@Test 
	void testIndexOf() {
		populateArrayLists(5000);
		assertEquals(-1, test.indexOf(1000));
		assertEquals(-1, test.indexOf(500));
	}
	
	@Test
	void testLastIndexOf() {
		int populateNum = 5000;
		test.clear();
		test2.clear();
		for (int i = 0; i < populateNum; i++) {
			test.add(2);
			test2.add(2);
		}
		test.add(1);
		test2.add(1);
		assertEquals(test.lastIndexOf(2), test2.lastIndexOf(2));
		
	}
	
	@Test
	void testToArray() {
		int populateNum = 500;
		populateArrayLists(populateNum);
		for (int i = 0; i < 500; i++) {
			assertTrue(test.toArray()[i].equals(test2.toArray()[i]));
		}
		assertEquals(test.toArray().length, test2.toArray().length);
	}
	
	@Test
	void testTrimToSize() {
		
	}
	
	@Test
	void testEnsureCapacity() {
		
	}
	
	@Test
	void testToString() {
		//Turning the MyArrayList object into a string
		populateArrayLists(5);
		assertEquals(test.toString(), test2.toString());
	}

	//Method to fill the test arrays
	private void populateArrayLists(int numRandom) {
		test.clear();
		test2.clear();
		for (int j = 0; j < numRandom; j++) {
			int randomInsertion = (int) (Math.random() * 100);
			test.add(randomInsertion);
			test2.add(randomInsertion);
		}
	}


}
