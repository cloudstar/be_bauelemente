package guillotine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

import datamodel.Bin;
import datamodel.Box;
import datamodel.Coord;
import datamodel.Item;
import datamodel.SortedSpace;
import datamodel.Space;
import datamodel.Square;

public class Guillotine {

	// bin values
	final static int Bwidth = 400;
	final static int Bheight = 200;
	final static int Bdepth = 400;

	/**
	 * Fills the bins in the provided order
	 * 
	 * @param bins
	 * @param set
	 * @param allSpace
	 */

	public static void fillBinsInOrderByHeight(ArrayList<Bin> bins,
			Iterator<Item> set, SortedSpace allSpace) {
		if (allSpace == null) {
			System.out.println("SorteSpaceList not initilized!");
			return;
		}
		if (bins == null)
			newBin(bins, allSpace);
		// as long as there are items in our list
		for (; set.hasNext();) {
			Item it = set.next();
			if (it.getHeightRot() > Bheight) {
				System.err.println("item too big for a bin!!");
				return;
			}
			// searching for possible spaces
			Space freeSpace = searchBySpace(bins, allSpace, it);
			addItemGuilHori(it, freeSpace, allSpace);
		}
	}

	/**
	 * Fills the bins in the provided order, optimizes the seating
	 * 
	 * 
	 * @param bins
	 * @param set
	 * @param allSpace
	 */

	public static void fillBinsInOrderBySeating(ArrayList<Bin> bins,
			Iterator<Item> set, SortedSpace allSpace) {
		if (allSpace == null)
			allSpace = new SortedSpace();
		if (bins.isEmpty())
			newBin(bins, allSpace);
		// as long as there are items in our list
		for (; set.hasNext();) {
			Item it = set.next();
			if (it.getHeightRot() > bins.get(0).getHeight()) {
				System.err.println("item too big for a bin!!");
				return;
			}
			// searching for possible spaces
			Space freeSpace = searchBySeating(bins, allSpace, it);
			addItemGuilHori(it, freeSpace, allSpace);
		}
	}

	public static void fillBinsInOrderWithAdditions(ArrayList<Bin> bins,
			Iterator<Item> set, SortedSpace allSpace) {
		if (allSpace == null) {
			System.out.println("SorteSpaceList not initilized!");
			return;
		}
		if (bins == null)
			newBin(bins, allSpace);
		// as long as there are items in our list
		for (; set.hasNext();) {
			Item it = set.next();
			if (it.getHeightRot() > Bheight) {
				System.err.println("item too big for a bin!!");
				return;
			}
			// searching for possible spaces
			Space freeSpace = searchWithAddition(bins, allSpace, it);
			addItemGuilHori(it, freeSpace, allSpace);
		}
	}

	/**
	 * Searches for a space we can put the item into.
	 * 
	 * @param bins
	 * @param allSpace
	 * @param it
	 * @return
	 */
	public static Space searchBySpace(ArrayList<Bin> bins,
			SortedSpace allSpace, Item it) {
		Space space = null;
		for (Space s : allSpace.tailSet(it)) {
			if (s.getHeight() < it.getHeightRot()) {
				System.out.println("Wrong height detected! Unplaceble");
				return null;
			}// the conditions beside the height
			if (s.getWidth() < it.getWidth()) {
				continue;
			}
			space = s;
			break;
		}
		if (space == null) {
			newBin(bins, allSpace);
			// bins.add(new Bin(Bwidth, Bheight, Bdepth, bins.size(),
			// allSpace));
			return searchBySpace(bins, allSpace, it);
		}
		return space;
	}

	/**
	 * Searches for a space we can put the item into.
	 * 
	 * @param bins
	 * @param allSpace
	 * @param it
	 * @return
	 */
	public static Space searchBySeating(ArrayList<Bin> bins,
			SortedSpace allSpace, Item it) {
		Space space = null;
		for (Space s : allSpace.tailSet(it)) {
			if (s.getHeight() < it.getHeightRot()) {
				System.out.println("Wrong height detected! Unplaceble");
				return null;
			}// the conditions beside the height
			if (s.getWidth() < it.getWidth()) {
				continue;
			}// if the seating is under 60% we wont place it
			if (s.lean != null && s.lean.getDepthRot() < 0.6 * it.getDepthRot()) {
				System.out.println("Seating conditions!!");
				continue;
			}
			space = s;
			break;
		}
		if (space == null) {
			newBin(bins, allSpace);
			// bins.add(new Bin(Bwidth, Bheight, Bdepth, bins.size(),
			// allSpace));
			return searchBySeating(bins, allSpace, it);
		}
		return space;
	}

	/**
	 * Searches for a space we can put the item into.
	 * 
	 * @param bins
	 * @param allSpace
	 * @param it
	 * @return
	 */
	public static Space searchWithAddition(ArrayList<Bin> bins,
			SortedSpace allSpace, Item it) {
		Space space = null;
		for (Space s : allSpace.tailSet(it)) {
			if (s.getHeight() < it.getHeightRot()) {
				System.out.println("Wrong height detected! Unplaceble");
				return null;
			}// the conditions beside the height
			if (s.getWidth() < it.getWidth()) {
				continue;
			}// if the seating is under 60% we wont place it
			if (s.lean != null && s.lean.getDepthRot() < 0.6 * it.getDepthRot()) {
				System.out.println("Seating condition!!");
				continue;
			}// if the space has a addition from the lean-item.
			if (s.getOverhangLimit() < it.getDepthRot()) {
				System.out.println("Addition condition!!");
				continue;
			}
			space = s;
			break;
		}
		if (space == null) {
			newBin(bins, allSpace);
			// bins.add(new Bin(Bwidth, Bheight, Bdepth, bins.size(),
			// allSpace));
			return searchWithAddition(bins, allSpace, it);
		}
		return space;
	}

	/**
	 * Puts the item into the free space and performs a horizontal cut in the
	 * bin
	 * 
	 * @param i
	 *            item
	 * @param sp
	 *            the space
	 * @return
	 */
	public static boolean addItemGuilHori(Item i, Space sp, SortedSpace allSpace) {

		if (i.getHeightRot() > sp.getHeight() || i.getWidth() > sp.getWidth()) {
			System.out.println("Item does not fit into the Space!!");
			return false;
		}

		if (!sp.bin.getSpaces().contains(sp)) {
			System.out.println("Does not hold the space:" + sp);
			return false;
		}
		// add item to the bin
		i.setBin(sp.bin, sp.bin.index, sp.posi);
		sp.bin.getItems().add(i);

		// calculate the overhang for the new space
		Space.Overhang overhang = null;
		if (sp.overHang != null && sp.overHang.getDepth() > i.getWidth()
				|| i.getAdditionRot() != null) {
			int lastAdditionDepth = Math.max(
					sp.overHang.getDepth() - i.getWidth(),
					i.getAdditionRot().depth);
			Box overhangBox = new Box(sp.overHang.getWidth(),
					sp.overHang.getHeight(), lastAdditionDepth);
			overhang = sp.new Overhang(overhangBox, sp.overHang.posi);
		}

		// new space after the item
		Square sp1S = new Square(sp.getWidth() - i.getWidth(), i.getHeightRot());
		Space sp1 = new Space(sp1S, i, new Coord(sp.posi.x + i.getWidth(),
				sp.posi.y), sp.bin, sp, overhang);
		// new space under the item
		Square sp2S = new Square(sp.getWidth(), sp.getHeight()
				- i.getHeightRot());
		Space sp2 = new Space(sp2S, sp.lean, new Coord(sp.posi.x, sp.posi.y
				+ i.getHeightRot()), sp.bin, sp, sp.overHang);

		boolean contain = sp.bin.getSpaces().contains(sp);
		boolean remove = sp.bin.getSpaces().remove(sp);
		if (!contain || !remove) {
			System.out.println("Could not remove internSpace!!");
			System.out.println(sp + " " + sp.posi + " " + sp.otoString());
		}

		sp.bin.getSpaces().add(sp1);
		sp.bin.getSpaces().add(sp2);

		boolean containAll = allSpace.contains(sp);
		boolean removeAll = allSpace.remove(sp);
		if (!containAll || !removeAll) {
			System.out.println("Could not remove allSpace!!");
			System.out.println(sp + " " + sp.posi + " " + sp.otoString());
			while(!allSpace.remove(sp))System.out.println("Fuckyou");
		}

		allSpace.add(sp1);
		allSpace.add(sp2);
		return true;
	}

	/**
	 * Creates a new bin in the list Uses default values if no initial bin is
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
		if (bins.isEmpty()) {
			bins.add(new Bin(new Box(Bwidth, Bheight, Bdepth), 0, allSpace));
		} else {
			Bin bin = bins.get(0);
			Box binB = new Box(bin.getWidth(), bin.getHeight(), bin.getDepth());
			bins.add(new Bin(binB, bins.size(), allSpace));
		}
	}
}
