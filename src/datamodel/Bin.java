package datamodel;

import java.util.LinkedList;

/**
 * Holds items in an ordered list Can calculate the free space
 * 
 * @author nrasic
 * 
 */
public class Bin {

	public final int width;
	public final int height;
	public final int depth;
	public final int space2D;
	public final int index;
	LinkedList<Item> items;
	SortedSpace spaces;
	SortedSpace allSpace;

	public Bin(int width, int height, int depth, int index, SortedSpace allSpace) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.index = index;
		this.allSpace = allSpace;

		spaces = new SortedSpace();
		// add new free space
		Space init = new Space(width, height, null, new Coord(0, 0), this);
		spaces.add(init);
		allSpace.add(init);

		space2D = width * height;
		items = new LinkedList<Item>();
	}

	/**
	 * returns the free space
	 * 
	 * @return
	 */
	public int getFreeSpace() {
		int sp = space2D;
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

	/**
	 * Puts the item into the free space and performs a horizontal cut
	 * 
	 * @param i
	 *            item
	 * @param sp
	 *            the space
	 * @return
	 */
	public boolean addItemGuilHori(Item i, Space sp) {

		if (i.getHeight() > sp.height || i.getWidth() > sp.width) {
			System.out.println("Item does not fit into the Space!!");
			return false;
		}
		if (!spaces.contains(sp)){
		System.out.println("Does not hold the space:"+sp);
		return false;
		}
		
		i.setBin(this, index, sp.posi);
		items.add(i);

		// new space after the item
		Space sp1 = new Space(sp.width - i.getWidth(), i.getHeight(), sp.lean,
				new Coord(sp.posi.x + i.getWidth(), sp.posi.y), this);
		// new space under the item
		Space sp2 = new Space(sp.width, sp.height - i.getHeight(), sp.lean,
				new Coord(sp.posi.x, sp.posi.y + i.getHeight()), this);

		boolean contain = spaces.contains(sp);
		boolean remove = spaces.remove(sp);
		if (!contain || !remove){
			System.out.println("Could not remove internSpace!!");
		System.out.println(sp+" "+sp.posi+" "+sp.otoString());
		}
		spaces.add(sp1);
		spaces.add(sp2);

		boolean containAll = allSpace.contains(sp);
		boolean removeAll = allSpace.remove(sp);
		if (!containAll || !removeAll)
			System.out.println("Could not remove allSpace!!");
		allSpace.add(sp1);
		allSpace.add(sp2);
		return true;
	}

	public String toString() {
		return "Bin#" + index + " SpaceFree#" + getFreeSpace();
	}
}