// Laura Gomez
// CS 1027
// This class represents the a node from the tree 
public class QTreeNode {
	// instance variables
	private int x,y;
	private int size;
	private int color;
	private QTreeNode parent;
	private QTreeNode[] children;
	
	// Constructors 
	public QTreeNode() {
		parent = null;
		children = new QTreeNode[4];
		x = 0;
		y = 0;
		size = 0;
		color = 0;
	}
	
	public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
		x = xcoord;
		y = ycoord;
		size = theSize;
		color = theColor;
		children = theChildren;
		parent = null;
	}
	
	// checks whether the quadrant has the coordinates in it 
	public boolean contains(int xcoord, int ycoord) {
		//System.out.println("contains size "+ size);
		//System.out.println("contains X "+ x);
		//System.out.println("contains Y "+ y);
		if (x <= xcoord && xcoord <= x+size-1) {
			if (y <= ycoord && ycoord <= y+size-1)
			return true;
		}
		return false;
	}
	
	// getters and setters
	public int getx() {
		return x;
		
	}
	
	public int gety() {
		return y;
		
	} 
	
	public int getSize() {
		return size;
		
	} 
	
	public int getColor() {
		return color;
		
	} 
	
	public QTreeNode getParent() {
		return parent;
		
	}
	
	public QTreeNode getChild(int index) throws QTreeException{
		if (children == null || index < 0 || index > 3) {
			throw new QTreeException(null);	
		}
		return children[index];
	}
	
	public void setx (int newx) {
		x = newx;		
	}
	
	public void sety(int newy) {
		y = newy;		
	}
	
	public void setSize(int newSize) {
		size = newSize;	
	} 
	
	public void  setColor(int newColor) {
		color = newColor;	
	}
	
	public void setParent(QTreeNode newParent) {
		parent = newParent;	
	}
	
	public void setChild(QTreeNode newChild, int index) throws QTreeException{
		if (children == null || index < 0 || index > 3) {
			throw new QTreeException(null);	
		}
		children[index] = newChild;
		
	}
	// checks if the node is a leaf
	public boolean isLeaf() {
		if (children == null) {
			return true;
		} 
		for(int i = 0; i < 4; i++) {
			if(children[i] != null) {
				return false;
			}
		}		
		return true;
		
	}

	
}

// the end :)