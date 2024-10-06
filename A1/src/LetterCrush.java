/*
 * Laura Gomez
 * This program is pretty much the backend of candy cursh 
 * CS1027B 
 */

public class LetterCrush {
	private char[][] grid; 
	public static final char EMPTY = ' ';

	// Constructor
	public LetterCrush(int width, int height, String initial) {
		grid = new char[height][width];
	
		int row = 0;
		int col = 0;
		
		int index = 0;
		// Goes thru the grid whilst filling up with EMPTY
		for(index = 0; index < height * width; index++) {
			grid[row][col] = EMPTY;
			if(index<initial.length()) {
				// and then with actual letters
			grid[row][col] = initial.charAt(index);
			}
			
			// moves to next column 
			col++;
			
			if (col == width ) {
				// next row once end is reached
				row++;
				col = 0; 
				
				if (row == height) {
					break;
				}				
			}	
		}
		
	}
	// Shows the grid
	public String toString() {
			String image = new String("LetterCrush\n");
			
			int row = 0;
			int col = 0;
			
			// formatting 
			for(row = 0; row < grid.length; row++) {
				image += ("|");
				
				
				// adds actual values(letters)
				for(col = 0; col < grid[0].length; col++) {
					image += grid[row][col];
				}
				
				image += "|";
				image += row;
				image += "\n";
			
			}
		
			// column numbers
			image += "+";
			for (col = 0; col < grid[0].length; col++) {
				image += col;
				}
			image += "+";

		    return image.toString();
	}
	// Checks if there is empty spaces underneath 
	public boolean isStable() {
		int row = 0;
		int col = 0;
		
		// to keep track of the letters 
		char up;
		char down;
		
		// searches by duos (up and down in a column)
		for(row = 0; row < grid.length-1; row++) {
			for(col = 0; col < grid[0].length; col++) {
				up = grid[row][col];
				down = grid[row+1][col];
				
				// checks if stable
				if (up != EMPTY && down == EMPTY) {
					return false;
					
				}
				
			}
			
		}
		return true;		
		
	}
	// Fills up the empty spaces with "gravity"
	public void applyGravity() {
		int row = 0;
		int col = 0;
		// similar to the above method - i am checking in groups of two
		char up;
		char down = 'x';
		for(row = grid.length-1; row >= 1; row--) {
			for(col = 0; col < grid[0].length; col++) {
				// Keeps track of the duo of characters in column
				up = grid[row-1][col];
				down = grid[row][col];
				
				//Swaps them if empty so the empty is left at top
				if(up != EMPTY && down == EMPTY) {
					grid[row-1][col] = down;
					grid[row][col] = up;
					
				}		
			}
		}	
		
	}
	
	// Checks  a line from class Line is part of the grid and if so it makes it into EMPTY spaces
	public boolean remove(Line theLine) {
		int row = grid.length;
		int col = grid[0].length;
		
		// to save coordinates of the line
	    int[] start = theLine.getStart();
	    int lineLength = theLine.length();
	     // isH stands for is Horizontal
	    boolean isH = theLine.isHorizontal();
	    
	    // based on its direction it checks if in bounds
	    if(isH) {
		    if(
			    	start[0] >= row
			    	|| start[1] >= col
			    	|| (start[1]+lineLength) > col
			    ){
			    	return false;
			    }
	    } else {
		    if(
			    	start[0] >= row
			    	|| start[1] >= col
			    	|| (start[0]+lineLength) > row
			    ){
			    	return false;
			    }
	    }
	    
	    // line is valid so it "deletes" it 
	    for(int i = 0; i < lineLength; i++) {
	    	if(isH) {
	    		grid[start[0]][start[1]+i] = EMPTY;
	    	} else {
	    		grid[start[0]+i][start[1]] = EMPTY;
	    	}
	    	
	    }
	    
	    return true;
		
	}
	
	// Overloads toString and is another demonstration of the grid except it shows characters part of a line in small caps
	public String toString(Line theLine) {
		// very similar to the toString pervious method 
		String image = new String("CrushLine\n");
		
		int row = 0;
		int col = 0;
		
		for(row = 0; row < grid.length; row++) {
			image += ("|");
			
			for(col = 0; col < grid[0].length; col++) {
				// if its part then show in lower case
				if (theLine.inLine(row, col)) {
					image += Character.toLowerCase(grid[row][col]);
				
				} else {
					image += grid[row][col];
				}
			}
			
			image += "|";
			image += row;
			image += "\n";
		
		}
	
		image += "+";
		for (col = 0; col < grid[0].length; col++) {
			image += col;
			}
		image += "+";

	    return image.toString();

    }
	
	
	
	// Finds the longest line in the grid 
	public Line longestLine() {
		// makes a line
		Line longLine = new Line(0, 0, true, 1);
		int largest = 0;
		
		int row = 0;
		int col = 0;
		//starts searching thru grid from bottom
		for(row = grid.length-1; row >= 0; row--) {
			// saves first char
			char letter = grid[row][0];
			int adjacent = 1;
			// starts moving in the columns
			for(col = 1; col < grid[0].length; col++) {
				if(letter == grid[row][col] && letter != EMPTY) {
					// if letter adjacent is found then increase that 
					adjacent++;
					if(adjacent > largest) {
						// keeps track of longest line found
						largest = adjacent;
						longLine = new Line(row, col - adjacent+1, true, adjacent); // the col is set that way so it start on second column index[1]
					}
					
				} else {
					letter = grid[row][col];
					adjacent = 1;
				}
			}
		}
		// similar process but now searches by columns first then rows 
		for(col = 0; col < grid[0].length; col++) {
			char letter = grid[grid.length-1][col];
			int adjacent = 1;
			for(row = grid.length-2; row >= 0; row--) {
				if(letter == grid[row][col] && letter != EMPTY) {
					// increase adjacent since found
					adjacent++;
					if(adjacent > largest) {
						// update largest
						largest = adjacent;
						longLine = new Line(row, col, false, adjacent);
					}
					
				} else {
					letter = grid[row][col];
					adjacent = 1;
				}
	
			}
		}
		
		
		// if the length of longline is larger than 2 then return longline else return null
		if(longLine.length() > 2) {
			return longLine;
		} else {
			return null;
		}
	}
	

	// Applies mutliple methods at once to remove and fill up the spaces made until no more long lines exist 
	public void cascade() {
	    Line longestLine = longestLine();
	    
	    while (longestLine != null) {
	        remove(longestLine);
	        if(!isStable()) {
	        	applyGravity();
	        }
	        longestLine = longestLine();
	    }
	}	
	
}

// the end :)