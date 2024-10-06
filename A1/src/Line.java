/*
 * Laura Gomez
 * CS1027B
 * Creates a vector like line - has direction and magnitude 
 */
public class Line {
	private int[] start;
	private int[] end;
	
	// Constructor
	public Line(int row, int col, boolean horizontal, int length) {
		start = new int[]{row,col};
		end = new int[2];
	
		
		if(horizontal) {
			end[0]= row;
			end[1]= col + length -1;
			
		} else {
			end[0] = row + length -1; 
			end[1] = col;
		}
		
	}
	
	// Returns a copy of the line as an integer array
	public int[] getStart() {
		int[] copy = new int[start.length];
		for (int i = 0; i < start.length; i++) {
			copy[i] = start[i];
		}
		return copy;
		
	}
	//Returns the length of the line
	public int length() {
		if (isHorizontal()) {
			return end[1] - start[1] +1;
		} else {
			return end[0] - start[0]+1;
		}
	}
	// Checks whether is horizontal
	public boolean isHorizontal() {
		if(start[0]== end[0]) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Checks if coordinates given are part of our line
	public boolean inLine(int row, int col){
	    if (start[0] <= row && row <= end[0] && start[1] <= col && col <= end[1]){	    	
	        return true;
	    }
	    return false; 
	    
	}
	
	// Shows where the line started and ended
	public String toString(){
	return ("Line:["+ start[0]+","+ start[1] + "]->[" + end[0]+ "," + end[1]+ "]");

	}

}

// the end :)






