/**
 * This class represents a node of the quadrant tree
 * 
 * @author Eliandro Pizzonia
 */
public class QTreeNode {
    
    // represents the coordinates of the upper left corner of the quadrant represented by this QTreeNode object
    private int x, y;

    // the size of the quadrant
    private int size;

    // the average color of the pixels stored in the quadrant represented by this object
    private int color;

    // the parent of this QTreeNode object
    private QTreeNode parent;

    // stores the children of this QTreeNode object
    private QTreeNode[] children;

    /**
     * the constructor for the class. This method initializes parent to null and creates an array of size 4 for children, setting every entry to null. All other instance variables are initialized to 0.
     */
    public QTreeNode(){        
        parent = null;
        children = new QTreeNode[4];
        x = 0;
        y = 0;
        size = 0;
        color = 0;
    }

    /**
     * 
     * @param theChildren
     * @param xcoord
     * @param ycoord
     * @param theSize
     * @param theColor
     * 
     * A second constructor for the class. Initializes the instance variables to the values passed as parameters
     */
    public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor){
        x = xcoord;
        y = ycoord;
        size = theSize;
        color = theColor;
        children = theChildren;
    }

    /**
     * 
     * @param xcoord
     * @param ycoord
     * @return true if the point (xcoord,ycoord) is contained inside the quadrant represented by this object
     */
    public boolean contains(int xcoord, int ycoord){
      if ( x <= xcoord && xcoord <= (x + size -1) && y <= ycoord && y <= (y + size -1)) {
        return true;
      }
      return false;
    }

    /**
     * @return the value of x
     */
    public int getx(){
        return x;
    }

    /**
     * @return the value of y
     */
    public int gety(){
        return y;
    }

    /**
     * @return the value of size
     */
    public int getSize(){
        return size;
    }

    /**
     * @return the value of colour
     */
    public int getColor(){
        return color;
    }

    /**
     * @return the value of parent
     */
    public QTreeNode getParent(){
        return parent;
    }

    /**
     * @param index
     * @return the child stored at the specified index of array children
     * @throws QTreeException throws a QTreeException if children is null or if index < 0 or if index > 3
     */
    public QTreeNode getChild(int index) throws QTreeException{
        if (children == null || index < 0 || index > 3) {
            throw new QTreeException("");
        }
        return children[index];
    }

    /**
     * sets the variable x to the value passed in the parameter
     * @param newx
     */
    public void setx(int newx){
        x = newx;
    }

    /**
     * sets the variable y to the value passed in the parameter
     * @param newy
     */
    public void sety(int newy){
        y = newy;
    }

    /**
     * sets the variable size to the value passed in the parameter
     * @param newSize
     */
    public void setSize(int newSize){
        size = newSize;
    }

    /**
     * sets the variable of color to the value passed in the parameter
     * @param newColor
     */
    public void setColor(int newColor){
        color = newColor;
    }

    /**
     * Sets the parent of this object to the specified value
     * @param newParent
     */
    public void setParent(QTreeNode newParent){
        this.parent = newParent;
    }

    /**
     * Sets children[index] to newChild
     * throws a QTreeException if children is null or if index < 0 or if index > 3
     * @param newChild
     * @param index
     */
    public void setChild(QTreeNode newChild, int index){
        if (children == null || index < 0 || index > 3) {
            throw new QTreeException("");
        }
        children[index] = newChild;
    }

    /**
     * @return true if children is null or if every entry of children is null
     */
    public boolean isLeaf(){
        
        if(children == null){
            return true;
        }

        for(int i = 0; i < children.length; i++){
            if (children[i] == null) {
                return true;
            }
        }
        return false;
    } 
    
}



















