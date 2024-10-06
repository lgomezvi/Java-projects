// Laura Gomez 
// CS1027 
// This class creates a queue with array implementation that sorts its items in ascending order and has unique elements only 
public class ArrayUniquePriorityQueue<T> implements UniquePriorityQueueADT<T>{
	private T[] queue;
	private double[] priority;
	private int count;
	
	// constructor
	public ArrayUniquePriorityQueue () {
		count = 0; 
		queue = (T[])new Object[10];
		priority = new double[10];
		
	}
	
	// adds elements
	public void add (T data, double prio) {
		// checking if data already in queue
		if(contains(data)) {
			return;
		}
			if (count == queue.length) {
				expandCapacity(); // if full 
			}
			int index = 0;
			
			 while (index < count && priority[index] < prio) {
			        index++; // searching to where data should be added
			    }
			    
			 while (index < count && priority[index] == prio) {
			        index++;
			    }
					
			// shifting elements	
			 for (int i = count - 1; i >= index; i--) {
			        queue[i + 1] = queue[i];
			        priority[i + 1] = priority[i];
			    }
				
				
			queue[index] = data;
			priority[index] = prio;
			count++;
			
		
	}
	// changes priority of existing data 
	public void updatePriority (T data, double newPrio) throws CollectionException {
		if (!contains(data)) {
			throw new CollectionException("Item not found in PQ");
		}
		remove(data);
		add(data,newPrio);
		
	}
	
	// helper method to remove any data 
	private void remove(T data) {
		int index = 0;
		for(int i = 0; i < count; i++) {
			if (queue[i].equals(data)) {
		        index = i; // update index if the data is found
		            break;		
				
			}
		}
		for (int x = index; x < count -1; x++) {
			// re writing and shifting
			  queue[x] = queue[x + 1];
			  priority[x] = priority[x + 1];
			    
		}
		count--;
		
	}
	
	// helper method to expand array when full 
	private void expandCapacity() {
		T[] newQueue = (T[])new Object[queue.length+5];
		double[] newPriority = new double[queue.length + 5];
		for (int i = 0; i < count; i++ ) {
			newPriority[i] = priority[i];
			newQueue[i] = queue[i];
			
		}
		priority = newPriority;
		queue = newQueue;
		
	}
	
	// checks if data in the queue 
	public boolean contains(T data) {
	    for (int i = 0; i < count; i++) {
	        if (queue[i].equals(data)) {
	            return true;
	        }
	    }
	    return false; 
	
			
	}
	
	// returns item at index 0 
	public T peek() throws CollectionException {
	    if (isEmpty()) {
	        throw new CollectionException("PQ is empty");
	    } else {
	        return queue[0];
	    }
	}
	
	// removes and returns min 
	public T removeMin() throws CollectionException {
	    if (isEmpty()) {
	        throw new CollectionException("PQ is empty");
	    }
	    
	    // find the index of the item with the smallest priority
	    int minIndex = 0;
	    for (int i = 1; i < count; i++) {
	        if (priority[i] < priority[minIndex]) {
	            minIndex = i;
	        }
	    }
	    
	    // item to be removed
	    T removedItem = queue[minIndex];
	    
	    // shift elements to the left to fill the gap
	    for (int i = minIndex; i < count - 1; i++) {
	        queue[i] = queue[i + 1];
	        priority[i] = priority[i + 1];
	    }
	    
	   
	    count--;
	    
	    return removedItem;
	}
	
	// to know how many elems 
	public int size() {
		return count;
		
	}
	
	// capacity
	public int getLength() {
		return queue.length;
	}
	
	
	public boolean isEmpty () {
		if (count == 0) {
			return true;
		} else {
			return false;
		}		
	}
	
	// returns a string representation of the queue 
	public String toString () {
		String result = "";
		if (isEmpty()){
			return "The PQ is empty";
		} else {
			for(int i = 0; i < count; i++) {
				if(i == count-1) {
					result += queue[i] + " ["+ priority[i]+"]";
				}else {
					result += queue[i] + " ["+ priority[i]+"], ";
					
				}
				
			}
		}
		return result;
	}
	
// the end :)
}
