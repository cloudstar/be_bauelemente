package datamodel;

import java.util.LinkedList;

/**
 * Holds items in an ordered list Can calculate the free space
 * 
 * @author nrasic
 * 
 */
public class Bin {

	Box space;
	public final int index;
	LinkedList<Item> items;
	SortedSpace spaces;
	SortedSpace allSpace;

	public Bin(Box space, int index, SortedSpace allSpace) {
		this.space = space;
		this.index = index;
		this.allSpace = allSpace;

		spaces = new SortedSpace();
		// add new free space
		Space init = new Space(space.getPlain(), null, new Coord(0, 0), this,null);
		spaces.add(init);
		allSpace.add(init);

		items = new LinkedList<Item>();
	}

	/**
	 * returns the free space
	 * 
	 * @return
	 */
	public int getFreeSpace() {
		int sp = space.height*space.width;
		for (Item i : items) {
			sp -= i.getSpace2D();
		}
		return sp;
	}

	public SortedSpace getSpaces() {
		return spaces;
	}

	public LinkedList<Item> getItems() {
		return items;
	}
	
	public int getWidth(){
		return space.width;
	}
	
	public int getHeight(){
		return space.height;
	}
	
	public int getDepth(){
		return space.depth;
	}
	

	public String toString() {
		return "Bin#" + index + " SpaceFree#" + getFreeSpace();
	}
}