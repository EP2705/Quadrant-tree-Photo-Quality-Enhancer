/**
 * This class constructs a quadrant tree from a 2d array of pixels where each node represents a quadrant of the image
 * 
 * @author Eliandro Pizzonia
 */
public class QuadrantTree {

	// the root of the quadrant tree
	private QTreeNode root;

	/**
	 * Builds a quadrant tree corresponding to the pixels in the given 2-dimensional array thePixels
	 * @param thePixels
	 */
	public QuadrantTree(int[][] thePixels) {

		// making the variable root an instance of QTreeNode and assigning it values
		root = new QTreeNode();

		root.setx(0);
		root.sety(0);
		root.setSize(thePixels.length);
		root.setColor(Gui.averageColor(thePixels, root.getx(), root.gety(), root.getSize()));
		root.setParent(null);

		// if the size of root is one then it will have no children 
		if (root.getSize() == 1) {
			for (int i = 0; i < 4; i++) {
				root.setChild(null, i);
			}

		}
		// if the size of root is more then one then it will have 4 children
		if (root.getSize() > 1) {
			division(root, thePixels, root.getSize());
		}
	}

	/**
	 * private helper method to divide the parent node to have 4 children nodes
	 * @param node
	 * @param thePixels
	 * @param size
	 */
	private void division(QTreeNode node, int[][] thePixels, int size) {
		
		// base case that stops the recusion (if the size is one the node will have no more children)
		if (size == 1) { 
			return;
		}

		// setting the size to half the original and creating a new 2D array of integers with the deminsions of the new size
		int newSize = size/2;
		int[][] first2DArray = new int[newSize][newSize];
		
		// copying the the top left quadrant of the original array to the new array
		for(int i = 0; i < newSize; i++) {
			for(int j = 0; j < newSize; j++) {
				first2DArray[i][j] = thePixels[i][j];
			}
		}

		// calculating the average colour of the top left subquadrant
		int firstColor = Gui.averageColor(first2DArray, 0, 0, newSize);

		// creating a new QTreeNode for the top left subquadrant
		QTreeNode firstNode = new QTreeNode(new QTreeNode[4], node.getx(), node.gety(), newSize, firstColor);
		
		// setting the new node as a child of the original node
		node.setChild(firstNode, 0);

		// setting the original node as the parent of the new node
		firstNode.setParent(node);

		// recursive division
		division(firstNode, first2DArray, newSize);
		
		
		// creating a new 2D array of integers with the deminsions of the new size
		int[][] second2DArray = new int[newSize][newSize];
		
		// copying the the top right quadrant of the original array to the new array
		for(int i= 0; i < newSize; i++) {
			for(int j = 0; j < newSize; j++) {
				second2DArray[i][j] = thePixels[i][j + newSize];
			}
		}
	
		// calculating the average colour of the top right subquadrant
		int secondColor = Gui.averageColor(second2DArray, 0, 0, newSize);

		// creating a new QTreeNode for the top right subquadrant
		QTreeNode secondNode = new QTreeNode(new QTreeNode[4], node.getx() + newSize, node.gety(), newSize, secondColor);
		
		// setting the new node as a child of the original node
		node.setChild(secondNode, 1);
		
		// setting the original node as the parent of the new node
		secondNode.setParent(node);

		// recursive division
		division(secondNode, second2DArray, newSize);
		
		
		// same process for the bottom left and bottom right sub quadrants
		int[][] third2DArray = new int[newSize][newSize];
			for(int i= 0; i < newSize; i++) {
				for(int j = 0; j < newSize; j++) {
					third2DArray[i][j] = thePixels[i + newSize][j];
			}
		}

		
		int thirdColor = Gui.averageColor(third2DArray, 0, 0, newSize);
		QTreeNode thirdNode = new QTreeNode(new QTreeNode[4], node.getx(), node.gety() + newSize, newSize, thirdColor);
		node.setChild(thirdNode, 2);
		thirdNode.setParent(node);
		division(thirdNode, third2DArray, newSize);
		
		
		
		int[][] fourth2DArray = new int[newSize][newSize];
		for(int i= 0; i < newSize; i++) {
			for(int j = 0; j < newSize; j++) {
				fourth2DArray[i][j] = thePixels[i + newSize][j + newSize];
			}
		}

		
		
		int fourthColor = Gui.averageColor(fourth2DArray, 0, 0, newSize);
		QTreeNode fourthNode = new QTreeNode(new QTreeNode[4], node.getx() + newSize, node.gety() + newSize, newSize, fourthColor);
		node.setChild(fourthNode, 3);
		fourthNode.setParent(node);
		division(fourthNode, fourth2DArray, newSize);
		
	}
	
	/**
	 * @return the root of this quadrant tree
	 */
	public QTreeNode getRoot() {
		return root;
	}
	
	/**
	 * transverses and creates a linked list (preorder transversal)
	 * @param r
	 * @param theLevel
	 * @return  a list containing all the nodes in this quadrant tree at the level specified by theLevel
	 */
	public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel){
		
		if (r == null) {
			return null;
		}
		
		// if the level is 0 a new listnode is created with the value of r
		if (theLevel == 0) {
			return new ListNode<QTreeNode>(r);
		}

		// if the level is greater than 0 and r is a leaf a new listnode is created with the value of r
		else if (theLevel > 0 && r.isLeaf()) {
			return new ListNode<QTreeNode>(r);
		}

		else {

			// setting head to null
			ListNode<QTreeNode> head = null;

			// iterating through the children and recursivly calling getpixels on the children of r at index i and the level decremented by 1
			for (int i = 0; i < 4; i++) {
				ListNode<QTreeNode> newNode = getPixels(r.getChild(i), theLevel - 1);
				
				// if head is null head is set to the new node
				if (head == null) {
					head = newNode;
					
				}

				// else the list is transversed until reaches the end
				else {
					ListNode<QTreeNode> current = head;
					while (current.getNext() != null) {
						current = current.getNext();
					}
					current.setNext(newNode);
				}
			}
			
			return head;
		}
	}

	/**
	 * 
	 * @param r
	 * @param theColor
	 * @param theLevel
	 * @return an object of the class Duple 
	 */
	public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
	
		// transversing the tree 
        ListNode<QTreeNode> head = findMatching_recursion(r, theColor, theLevel);
        int counter = 0;
        
        ListNode<QTreeNode> curr = head;
		
		// counting the number of nodes in the list (in the level)
        while (curr != null) {
			counter++;
			curr = curr.getNext();
        }

        Duple objDuple = new Duple();

		
        if(r.isLeaf() || theLevel == 0){

			// if r is a leaf and the level is 0 and if the colour of r is similar to theColor, the Duple object objDuple is returned with the list and the value of 1
            if (Gui.similarColor(r.getColor(), theColor)) {
                objDuple.setFront(head);
                objDuple.setCount(1);
            }

			// if r is a leaf and the level is 0 and if the colour of r is not similar to theColor, the Duple object objDuple is returned with null and the value of 0
            else if(!Gui.similarColor(r.getColor(), theColor)){
                objDuple.setFront(null);
                objDuple.setCount(0);
            }
        }

		// if r is not a leaf and the level is greater then one, objDuple is returned with the list and the count of the amount of nodes
        else if (!r.isLeaf() && theLevel > 0) {
            objDuple.setFront(head);
            objDuple.setCount(counter);
        }
        return objDuple;

    }

	/**
	 * private helper method to transverse the tree (preorder transversal)
	 * @param r
	 * @param theColor
	 * @param theLevel
	 * @return the nodes on the specified level
	 */
    private ListNode<QTreeNode> findMatching_recursion(QTreeNode r, int theColor, int theLevel){
    
        if (r == null) {
            return null;
        }
    
		// if the level is 0 and r is leaf, and if r's colour is similar to thecolor , a list node is returned with the value of r
        if (theLevel == 0 || r.isLeaf()) {
            if (Gui.similarColor(r.getColor(), theColor)) {
                    return new ListNode<QTreeNode>(r);
            }
            else{
            	return null;
            }
        }

		// similar to the getPixels method
        else {
        ListNode<QTreeNode> head = null;
            for (int i = 0; i < 4; i++) {
                ListNode<QTreeNode> newNode = findMatching_recursion(r.getChild(i), theColor, theLevel - 1);
                
                if (head == null) {
                    head = newNode;
                    
                }
                else {
                    ListNode<QTreeNode> current = head;
                    while (current.getNext() != null) {
                        current = current.getNext();
                    }
                    current.setNext(newNode);
                }
            }
        
        return head;
        }
    }   
    
	/**
	 * 
	 * @param r
	 * @param theLevel
	 * @param x
	 * @param y
	 * @return a node in the subtree rooted at r and at level theLevel representing a quadrant containing the point (x,y); it returns null if such a node does not exist
	 */
    public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y){

       if(r == null){
        	return null;
       }

	   	// if theLevel equals 0 r's value of x and y are equal to the values passed in the parameters, r is returned
        if (theLevel == 0){
            if (r.getx() == x && r.gety() == y) {
                return r;
            }
            else{
                return null;
            }

        }

		// else for each child node, findNode is recursivley called and theLevel is decremnented by 1
        else{
            for(int i = 0; i < 4; i++){
                QTreeNode node = findNode(r.getChild(i), theLevel - 1, x, y);

                if (node != null) {
                    return node;
                }
            }

            return null;
        }
    }

}
