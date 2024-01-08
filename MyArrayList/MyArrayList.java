import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

//Forrest Gao, Data Structures and Algorithms
//9_16_2022
//Java ArrayList Class

public class MyArrayList<E> {
	
	private E[] arrayList;
	private int nextIndex;
	
	//Constructor 1: creates an ArrayList with length 10
	public MyArrayList() {
		nextIndex = 0;
		arrayList = (E[]) new Object[10];
	}
	
	//Constructor 2: creates an ArrayList with specified length
	public MyArrayList(int spaceInit) {
		if (spaceInit < 0) {
			throw new IllegalArgumentException();
		}
		arrayList = (E[]) new Object[spaceInit];
	}
	
	//Constructor 3: creates an ArrayList with the elements of a collection
	public MyArrayList(Collection<? extends E> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		arrayList = (E[]) new Object[c.size()];
		Iterator<? extends E> iterator = c.iterator();
		for (int i = 0; i < c.size(); i++) {
			arrayList[i] = iterator.next();
		}
		nextIndex = c.size();
	}
	
	//Clear the array
	//Big O efficiency is O(1)
	public void clear() { 
		nextIndex = 0;
	}
	
	//Gets the element at a given index
	//Big O efficiency is O(1)
	public E get(int index) {
		if (index >= nextIndex || index < 0) {
			throw new IndexOutOfBoundsException();
		} 
		return (arrayList[index]);
	}
	
	//Sets a given element to a different object
	//Big O efficiency is O(1)
	public E set(int index, E element) {
		if ((index < 0 || index >= size())) {
			throw new IndexOutOfBoundsException();
		}
		E previousElement = arrayList[index];
		arrayList[index] = element;
		return previousElement;
	}
	
	//Size of the array
	//Big O efficiency is O(1)
	public int size() {
		return nextIndex;
	}
	
	//Checks if the ArrayList is empty
	//Big O efficiency is O(1)
	public boolean isEmpty(){
		return (nextIndex == 0); 
	}
	
	//Add to the ArrayList at a given index
	//Big O efficiency is O(n), because in the worst case, we need to copy everything 
	//over when we double the size of the array
	public void add(int index, E element) {
		if (index > nextIndex || index < 0) {
			throw new IndexOutOfBoundsException();
		} 
		if (nextIndex == arrayList.length) {
			doubleSpace();
		}
		for (int i = nextIndex ; i > index; i--) {
			arrayList[i] = arrayList[i - 1];
		}
		arrayList[index] = element;
		nextIndex++;
	}
	
	//Add to the end of the ArrayList
	//Big O efficiency is O(n), because in the worst case, we need to copy everything
	//over when we double the size of the array
	public boolean add(E element) {
		add(nextIndex, element);
		return true;
	}
	
	//Add a collection to the end of the ArrayList
	//Big O efficiency is O(mn)
	//Let us consider the worst case scenario, we have to add an extremely long collection
	//to the current list of size n. If the collection is of size m, we must double 
	//whenever the current ArrayList does not have enough capacity. Thus, we have to copy over
	//n objects, then 2n objects, all the way up to log base 2 of m. So, the operations
	//sum to n + 2n + 4n +.....2^(ceil(log_2_m))n. This simplifies to
	//n(1 + 2 + 4 + ...2^ceil(log_2_m)). Utilizing the rule for summing powers of 2,
	//we have n(2^(ceil(log_2_m) + 1) - 1). This simplifies to approximately mn time
	public boolean addAll(int index, Collection<? extends E> c){
		if(c == null) {
			throw new NullPointerException();
		}
		if (index > nextIndex || index < 0) {
			throw new IndexOutOfBoundsException();
		} 
		if (c.size() == 0) {
			return false;
		}
		Iterator<? extends E> iterator = c.iterator();
		for (int i = index; i < index + c.size(); i++) {
			add(i, iterator.next());
		}	
		return true;
	}
	
	//Big O efficiency is O(mn)
	public void addAll(Collection<? extends E> c) {
		addAll(nextIndex, c);
	}
	
	//Removes elements in the ArrayList given an inclusive starting and exclusive ending
	//Big O efficiency is O(n), because at worst, the whole array needs to be
	//deleted, thus making the number of operations go linearly
	protected void removeRange(int fromIndex, int toIndex) {
		if ((fromIndex < 0 || fromIndex >= size() || toIndex > size() || toIndex < fromIndex)) {
			throw new IndexOutOfBoundsException();
		}
		int numRemove = toIndex - fromIndex;
		for (int i  = toIndex; i < nextIndex; i++) {
			arrayList[i - numRemove] = arrayList[i];
		}
		nextIndex -= numRemove;
	}
	
	//Removes the element at a given index 
	//Big O efficiency is O(n)
	public E remove(int index) {
		if((index < 0 || index >= size())) {
			throw new IndexOutOfBoundsException();
		}
		E removed = arrayList[index];
		removeRange(index, index + 1);
		return(removed);
	}
	
	//Removes the first instance of a given element in the list
	//Big O efficiency is O(n), because in the worst case scenario, the whole array needs to
	//be traversed
	public boolean remove(Object o) {
		for (int i = 0; i < nextIndex; i++) {
			if (arrayList[i].equals(o)) {
				for (int j = i; j < nextIndex - 1; j++) {
					arrayList[j] = arrayList[j + 1];
				}
				nextIndex--;
				return true;
			}
		}
		return false;
	}
			
	//Checks if the ArrayList contains a certain element
	//Big O efficiency is O(n), because at worst, the whole array is checked
	public boolean contains(Object o) {
		boolean contains = false;
		for (int i = 0; i < nextIndex; i++) {
			if (arrayList[i].equals(o)) {
				contains = true;
			}
		} 
		return contains;
	}

	//Finds the location of a given element in the ArrayList (Not present => return -1)
	//Big O efficiency is O(n), because at worst, the whole array is checked
	public int indexOf(Object o) {
		for (int i = 0; i < arrayList.length; i++) {
			if (arrayList[i].equals(o)) {
				return i;
			}
		} return -1;
	}
	
	//Returns the last index of an element. If not present, returns -1
	//Big O efficiency is O(n), because at worst, the whole array is checked
	public int lastIndexOf(Object o) {
		int lastIndex = -1;
		for (int i = 0; i < nextIndex; i++) {
			if (arrayList[i].equals(o)) {
				lastIndex = i;
			}
		}
		return lastIndex;
	}
	
	//Returns an array containing all of the elements of the ArrayList
	//Big O efficiency is O(n), because the whole array is checked
	public E[] toArray() {
		E[] values = (E[]) new Object[nextIndex];
		for (int i = 0; i < nextIndex; i++) {
			values[i] = get(i);
		}
		return values;
	}

	//Trims the current available space to the size of the list
	//Big O efficiency is O(n), because number of operations changes linearly
	//with the size of the array because the whole array is copied over
	public void trimToSize() {
		E[] newArray = (E[]) new Object[nextIndex];
		for (int i = 0; i < nextIndex; i++) {
			newArray[i] = arrayList[i];
		}
		arrayList = newArray;
	}
	
	//Ensures a capacity to save space (ie if # of spots needed is roughly known)
	//Big O efficiency is O(n), because every element in the current array has to be
	//copied over, and thus the efficiency goes linearly with ArrayList size
	public void ensureCapcity(int minCapacity) {
		if (minCapacity > arrayList.length) {
			E[] newArray = (E[]) new Object[minCapacity];
			for (int i = 0; i < nextIndex; i++) {
				newArray[i] = arrayList[i];
			}
			arrayList = newArray; 
		}
	}
	
	//Big O efficiency is O(n), because you take each index, convert it to a string,
	//and then add it to the string.
	public String toString() {
		String convertString = "[";
		convertString += String.valueOf(get(0));
		for (int i = 1; i < nextIndex; i++) {
			convertString += ", ";
			convertString += String.valueOf(get(i));
		}
		convertString += "]";
		return convertString;
	}

	private void doubleSpace() {
		E[] newArray = (E[]) new Object[arrayList.length * 2];
		for (int i = 0; i < nextIndex; i++) { 
			newArray[i] = arrayList[i];
		}
		arrayList = newArray;
	}

}
