package datamodel;

import java.util.ArrayList;
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
	LinkedList<PlaceableItem> items;
	SortedSpace spaces;
	private boolean close;

	// bin values
	public final static int Bwidth = 175;
	public final static int Bheight = 256;
	public final static int Bdepth = 272;

	public Bin(Box space, int index, SortedSpace allSpace) {
		this.space = space;
		this.index = index;

		spaces = new SortedSpace();
		// add new free space
		Space init = new Space(space.getPlain(), null, new Coord(0, 0), this,
				null);
		spaces.add(init);
		allSpace.add(init);

		items = new LinkedList<PlaceableItem>();
	}

	/**
	 * returns the free space
	 * 
	 * @return
	 */
	public int getFreeSpace() {
		int sp = space.height * space.width;
		for (PlaceableItem i : items) {
			sp -= i.getSpace2D();
		}
		return sp;
	}

	public SortedSpace getSpaces() {
		return spaces;
	}

	public LinkedList<PlaceableItem> getItems() {
		return items;
	}

	public int getWidth() {
		return space.width;
	}

	public int getHeight() {
		return space.height;
	}

	public int getDepth() {
		return space.depth;
	}

	/**
	 * Creates a new bin in the list uses default values if no initial bin is
	 * set
	 * 
	 * @param bins
	 * @param allSpace
	 * @return
	 */
	public static void newBin(ArrayList<Bin> bins, SortedSpace allSpace) {
		if (bins == null) {
			System.out.println("Binlist is not initilized!");
			return;
		}
		if (allSpace == null) {
			System.out.println("sortedSpace is not initilized!");
			return;
		}
		if (bins.isEmpty()) {
			bins.add(new Bin(new Box(Bwidth, Bheight, Bdepth), 0, allSpace));
		} else {
			Bin bin = bins.get(0);
			Box binB = new Box(bin.getWidth(), bin.getHeight(), bin.getDepth());
			bins.add(new Bin(binB, bins.size(), allSpace));
		}
	}

	public static ArrayList<Bin> createBins(SortedSpace allSpace, Box box) {
		ArrayList<Bin> bins = new ArrayList<>();
		// newBin(bins, allSpace);
		bins.add(new Bin(box, bins.size(), allSpace));
		return bins;
	}

	public static ArrayList<Bin> createBins(SortedSpace allSpace) {
		ArrayList<Bin> bins = new ArrayList<>();
		newBin(bins, allSpace);
		return bins;
	}

	public String toString() {
		return "Bin#" + index + " SpaceFree#" + getFreeSpace();
	}

	/**
	 * close this bin, so no more items can be put into
	 * 
	 * @param allSpace
	 */
	public void close(SortedSpace allSpace) {
		this.close = true;
		for (Space s : spaces.descendingSet()) {
			s.deleted = true;
			allSpace.remove(s);
		}
	}
}