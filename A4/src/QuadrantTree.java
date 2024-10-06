// Laura Gomez
// CS 1027
// Creates the tree
public class QuadrantTree {
	// instance variable
	QTreeNode root;
	
	// constructor
	public QuadrantTree (int[][] thePixels) {
		// thePixels.length rows and thePixels.length columns.
		// You can assume that thePixels.length is a power of 2.
		 this.root = FindQTreeNode(0, 0, thePixels.length, thePixels);
		
	}
	// helper method
	private QTreeNode FindQTreeNode (int x, int y, int size, int[][] thePixels) {
		int color = Gui.averageColor(thePixels, x, y, size); // gets colour 
		
		QTreeNode parent = new QTreeNode(); 
		parent.setColor(color);
		parent.setSize(size);
		parent.setx(x);
		parent.sety(y);
		
		if (size == 1) { // if has no children also base case
			QTreeNode node = new QTreeNode(null, x, y, size, color);
			return node;
		} else { // recursion (has four kids)
			int half = size / 2; // since size changes when more kids are made
			for(int i = 0; i < 4; i++) { // interiations through the children
				if(i == 0) {	
					QTreeNode c1 = FindQTreeNode(x, y, half, thePixels); // creates kids over and over
					parent.setChild(c1, i);	// linking both together
					c1.setParent(parent);
				} else if (i == 1) {	
					QTreeNode c2 = FindQTreeNode(x + half, y, half, thePixels);
					parent.setChild(c2, i);
					c2.setParent(parent);
				} else if (i == 2) {			
					QTreeNode c3 = FindQTreeNode(x, y + half, half, thePixels);
					parent.setChild(c3, i);
					c3.setParent(parent);
				} else {			
					QTreeNode c4 = FindQTreeNode(x + half, y + half, half, thePixels);
					parent.setChild(c4, i);
					c4.setParent(parent);
				} 
			}
		}
		return parent; // the root
	}
	
	// getter
	public QTreeNode getRoot() {
		return root;
	}
	
	// returns a linked list with the nodes at the given level 
	public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel){		
		if(r.isLeaf() || theLevel ==0) {
			return new ListNode<>(r);
		} // base case when root is reached or when there are no children
		
	    ListNode<QTreeNode> head = null; // references to then join all the lists 
	    ListNode<QTreeNode> tail = null;
	    
		for (int i = 0; i < 4; i++) {
		    QTreeNode child = r.getChild(i); // traverses through tree 

		    ListNode<QTreeNode> nodeList = new ListNode<>(r); 
		    nodeList = getPixels(child, theLevel - 1); // makes nodes for list per every kid when desired level is reached 

		// joins all the lists 
		    if (nodeList != null) {
            	if (head == null) {
                	head = nodeList;
                	tail = head;
                	while (tail.getNext() != null) {
                    	tail = tail.getNext();
                	}
            	} else {
                	tail.setNext(nodeList);
                	while (tail.getNext() != null) {
                    	tail = tail.getNext();
                	}
            	}
        	}
		}
		
		return head; // reference to the list 
	}
	
	// returns a list with the pixels that have the same colour and the amount of nodes it contains 
	public Duple findMatching (QTreeNode r, int theColor, int theLevel) {

		ListNode list = new ListNode<>(r);
		Duple base = new Duple(list, 1);
		
		if(r.isLeaf() || theLevel ==0) {
			if (Gui.similarColor(r.getColor(), theColor)) {
				return base; // base case when root is the level or no kids 
			} else {
				return new Duple(); // empty list with count 0 if there are no similar but still base case
			}
		}
		 // references to then join the lists 
	    ListNode<QTreeNode> head = null; 
	    ListNode<QTreeNode> tail = null;
	    int totalmatches = 0; // keep track of amount
	    
		for (int i = 0; i < 4; i++) {
		    QTreeNode child = r.getChild(i); // traverses through kids 

		    ListNode<QTreeNode> nodeList = new ListNode<>(r); // makes nodes
		    
		    Duple dupleObj = new Duple(); // creates duple with count 0 and no nodes
		    dupleObj = findMatching(child, theColor, theLevel - 1); // recursion 
		    totalmatches += dupleObj.getCount(); // adding amount of nodes

		    nodeList = dupleObj.getFront(); // so we can add to the list other nodes 
		// adds 
		    if (nodeList != null) {
            	if (head == null) {
                	head = nodeList;
                	tail = head;
                	while (tail.getNext() != null) {
                    	tail = tail.getNext();
                	}
            	} else {
                	tail.setNext(nodeList);
                	while (tail.getNext() != null) {
                    	tail = tail.getNext();
                	}
            	}
        	}
		}
		
		return new Duple(head, totalmatches);
	}
	
	// finds a specific node from the tree 
	public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
	    if (r == null || theLevel < 0) {
	        return null; // if the node does not exist
	    }
	    
	    if (theLevel == 0 && r.contains(x, y)) {
	        return r; // if the node is the root
	    }

	    int nextLevel = theLevel - 1; // to traverse through levels
	    
	    for (int i = 0; i < 4; i++) {
	        QTreeNode child = r.getChild(i); // going through children
	        if (child != null && child.contains(x, y)) { // checking for node
	            QTreeNode result = findNode(child, nextLevel, x, y); // recursion until found 
	            if (result != null) {
	                return result;
	            }
	        }
	    }
	    
	    return null; // if not found
	}

	
}

// the end :)