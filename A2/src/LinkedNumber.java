/*
 * Laura Gomez
 * This class stores different based numbers in double linked lists 
 */

public class LinkedNumber {
	// initializing variables

	private int base;

	private DLNode<Digit> front;

	private DLNode<Digit> rear;
// constructor
	public LinkedNumber(String num, int baseNum) throws LinkedNumberException {

		base = baseNum;
		// checks if no numbers are given 
		if (num.isEmpty()) {

			throw new LinkedNumberException("no digits given");

		}

		for (int i = 0; i < num.length(); i++) {

			Digit digit = new Digit(num.charAt(i));
			// creates a node for each number given
			DLNode<Digit> newNode = new DLNode<Digit>(digit);

			if (front == null) {
				front = newNode;
				rear = newNode;
			} else {
				// is "appending to the list from back"
				rear.setNext(newNode);
				newNode.setPrev(rear);
				rear = newNode;
			}

		}
	}
	// constructor for when no string is given but actual number
	public LinkedNumber(int num) throws LinkedNumberException {

		this(String.valueOf(num), 10); // calls previous constructor
	}

	// checks whether integers are positive and part of base system
	public boolean isValidNumber() {

		DLNode<Digit> curr = front;

		while (curr != null) {
			// traverses list while checking if valid 
			int val = curr.getElement().getValue();
			if (val <= -1 || val > base - 1) {

				return false;

			}

			curr = curr.getNext();

		}

		return true;

	}
    
	// getters 
	public int getBase() {

		return base;

	}

	public DLNode<Digit> getFront() {

		return front;

	}

	public DLNode<Digit> getRear() {

		return rear;

	}

	public int getNumDigits() {

		int count = 0;

		DLNode<Digit> curr = front;

		while (curr != null) {

			count++;

			curr = curr.getNext();

		}

		return count; // length of number list 

	}

	public String toString() {

		String number = "";

		DLNode<Digit> curr = front;

		while (curr != null) {
			// traverses whilst getting the data from each node and appending to a string 
			number += curr.getElement().toString();
			curr = curr.getNext();

		}

		return number;

	}
	// checks if two linked list objects are equal
	public boolean equals(LinkedNumber other) {

		if (this.getNumDigits() != other.getNumDigits() && this.base != other.base) {

			return false;

		}

		DLNode<Digit> currthis = this.front;

		DLNode<Digit> currother = other.front;

		while (currthis != null) {
			// traverses whilst checking in equal
			if (!currthis.getElement().equals(currother.getElement())) {

				return false;

			}

			currthis = currthis.getNext();

			currother = currother.getNext();

		}

		return true;

	}

	
	// converts the base to other base  and so changing the list 
	public LinkedNumber convert(int newBase) throws LinkedNumberException {
		if (!this.isValidNumber()) {
			throw new LinkedNumberException("cannot convert invalid number");
		}
		// if no need to change base return object as it is
		if (this.base == newBase) {
			return this;
		}
		// to change to base 10
		if (newBase == 10) {
			return this.NonDecToDec();
		} else {
			LinkedNumber decimalTemp; // linkednumber object 
			if (this.base != 10) {
				decimalTemp = this.NonDecToDec(); // changing to base 10
			} else {
				decimalTemp = this;
			}
			return decimalTemp.DectoNonDec(newBase); // changing to desired base from base 10
		}
	}
	
	// adds a new node at a specific position
	public void addDigit(Digit digit, int position) throws LinkedNumberException {

		int length = getNumDigits();
		// checks valid position
		if (position >= 0 && position <= length) {
			// node to insert 
			DLNode<Digit> holder = new DLNode<Digit>(digit);
			// if want to add at back
			if (position == 0) {
				rear.setNext(holder);
				holder.setPrev(rear);
				rear = holder; // updates rear
			}
			
			// to add at front 
			else if (position == length) {

				holder.setNext(front);

				front.setPrev(holder);

				front = holder; // updates front

			} else { // add somewhere in middle 

				DLNode<Digit> curr = rear;

				int i = 0;
				// traverse until one before position is reached
				while (i != position - 1) {
					curr = curr.getPrev();

					i++;
				}
				
				// inserting between two nodes and re- attaching accordingly
				curr.getPrev().setNext(holder);

				holder.setPrev(curr.getPrev());

				holder.setNext(curr);

				curr.setPrev(holder);

			}

		} else {

			throw new LinkedNumberException("invalid position");

		}

	}

	// takes off digit at specific position
	public int removeDigit(int position)  throws LinkedNumberException  {

		int length = getNumDigits();

		int result = 0;
		// checking position is valid
		if (position >= 0 || position <= length) {

			DLNode<Digit> curr = rear;

			int i = 0;
			// traversing until position reached
			while (i != position) {
				curr = curr.getPrev();
				i++;
			}
			 
			// gets data from the node and is out to exponent so it shows its value relative to the number
			result = curr.getElement().getValue();
			result *= Math.pow(10, i);

			// delete rear
			if (position == 0) {
				rear.getPrev().setNext(null);
			} else if (position == length - 1) {
				// delete front 
				front = curr.getNext();
				front.setPrev(null);
			} else {
				// delete one somewhere in middle 
				curr = curr.getPrev();
				
				// return value relative to number
				result = curr.getElement().getValue();
				result *= Math.pow(10, i);

				// re- attach
				curr.getPrev().setNext(curr.getNext());
				curr.getNext().setPrev(curr.getPrev());

				rear = getRear().getPrev();
			}
			// creates an object with the result and returns object in base 10 (decimal)
			LinkedNumber resLL = new LinkedNumber(String.valueOf(result), this.base);

			return Integer.valueOf(resLL.NonDecToDec().toString());

		} else {

			throw new LinkedNumberException("invalid position");

		}
	}

	// conversions helpers 

	private LinkedNumber DectoNonDec(int newBase) {

		int sum = 0;

		int position = -1;

		String result = "";

		DLNode<Digit> curr = rear;

		while (curr != null) {
			// traverses thru list whilst performing conversion math
			int value = curr.getElement().getValue();
			position++; // keeps track of position
			sum += value * Math.pow(10, position);

			curr = curr.getPrev();
		}

		while (sum != 0) {
			// more conversion math
			int remainder = sum % newBase;
			sum = sum / newBase;

			if (remainder < 10) {
				result += String.valueOf(remainder);
			} else { // if bigger than 9 use the letter equivalents 
				String letter = "";

				if (remainder == 10) {
					letter = "A";
				} else if (remainder == 11) {
					letter = "B";
				} else if (remainder == 12) {
					letter = "C";
				} else if (remainder == 13) {
					letter = "D";
				} else if (remainder == 14) {
					letter = "E";
				} else if (remainder == 15) {
					letter = "F";
				}
				result += letter;
			}



		}
		// adds everything into a string 

		String finalResult = "";
		for (int i = result.length() - 1; i >= 0; i--) {
			// appends backwards
			finalResult += result.charAt(i);
		}
		//returns the converted object 
		return new LinkedNumber(finalResult, newBase);

	}

	private LinkedNumber NonDecToDec() {

		int sum = 0;

		int position = 0;

		DLNode<Digit> curr = rear;

		while (curr != null) {
			// traverses through linked list while getting data and doing conversion math

			int value = curr.getElement().getValue();

			sum += value * Math.pow(base, position);

			position++;// keeps track of position

			curr = curr.getPrev();

		}
		// returns object with base 10 (calls second constructor)
		return new LinkedNumber(sum);

	}

}
// the end :)