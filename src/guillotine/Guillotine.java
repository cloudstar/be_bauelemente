package guillotine;

import java.util.ArrayList;
import java.util.Iterator;
import datamodel.Bin;
import datamodel.Box;
import datamodel.Coord;
import datamodel.Item;
import datamodel.SortedSpace;
import datamodel.Space;
import datamodel.Square;

public class Guillotine {


	/**
	 * Fills the bins in the provided order
	 * 
	 * @param set
	 */

	public static ArrayList<Bin>  fillBinsInOrderByHeight(Iterator<Item> set) {
		SortedSpace allSpace = new SortedSpace();
		ArrayList<Bin> bins = Bin.createBins(allSpace);
		
		// as long as there are items in our list
		for (; set.hasNext();) {
			Item it = set.next();
			if (it.getHeightRot() > bins.get(0).getHeight()) {
				System.err.println("item too big for a bin!!");
				continue;
			}
			// searching for possible spaces
			Space freeSpace = searchBySpace(bins, allSpace, it);
			addItemGuilHori(it, freeSpace, allSpace);
		}		
		return bins;
	}

	/**
	 * Fills the bins in the provided order, optimizes the seating
	 * 
	 * 
	 * @param set
	 */
	public static ArrayList<Bin> fillBinsInOrderBySeating(Iterator<Item> set) {
		
		SortedSpace allSpace = new SortedSpace();
		ArrayList<Bin> bins = Bin.createBins(allSpace);
		
		// as long as there are items in our list
		for (; set.hasNext();) {
			Item it = set.next();
			if (it.getHeightRot() > bins.get(0).getHeight()) {
				System.err.println("item too big for a bin!!");
				continue;
			}
			// searching for possible spaces
			Space freeSpace = searchBySeating(bins, allSpace, it);
			addItemGuilHori(it, freeSpace, allSpace);
		}
		return bins;
	}
/**
 * Fills bin in the provided order regarding the hangover
 * 
 * @param set
 * @return
 */
	public static ArrayList<Bin> fillBinsInOrderWithAdditions(Iterator<Item> set) {
		SortedSpace allSpace = new SortedSpace();
		ArrayList<Bin> bins = Bin.createBins(allSpace);
		
		// as long as there are items in our list
		for (; set.hasNext();) {
			Item it = set.next();
			if (it.getHeightRot() > bins.get(0).getHeight()) {
				System.err.println("item too big for a bin!!");
				continue;
			}
			// searching for possible spaces
			Space freeSpace = searchWithAddition(bins, allSpace, it);
			addItemGuilHori(it, freeSpace, allSpace);
		}
		return bins;
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
			}
			if (s.deleted)
				continue; // dead space
			// the conditions beside the height
			if (s.getWidth() < it.getWidth()) {
				continue;
			}
			space = s;
			break;
		}
		if (space == null) {
			Bin.newBin(bins, allSpace);
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
			}
			if (s.deleted)
				continue; // dead space
			// the conditions beside the height
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
			Bin.newBin(bins, allSpace);
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
			if (s.deleted)
				continue; // dead space
			if (s.getWidth() < it.getWidth()) {
				continue;
			}// if the seating is under 60% we wont place it
			if (s.lean != null && s.lean.getDepthRot() < 0.6 * it.getDepthRot()) {
				// System.out.println("Seating condition!!");
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
			Bin.newBin(bins, allSpace);
			return searchWithAddition(bins, allSpace, it);
		}
		return space;
	}

	/**
	 * Puts the item into the free space and performs a horizontal cut in the
	 * bin. If an overhang exist, places the new overhang into the created space
	 * 
	 * @param i
	 *            item
	 * @param sp
	 *            the space
	 * @return
	 */
	public static boolean addItemGuilHori(Item i, Space sp, SortedSpace allSpace) {
		//sanity checks
		if (i.getHeightRot() > sp.getHeight() || i.getWidth() > sp.getWidth()) {
			System.out.println("Item does not fit into the Space!!");
			return false;
		}
		if (!sp.bin.getSpaces().contains(sp)) {
			System.out.println("Does not hold the space:" + sp);
			// return false;
		}
		if (i.getDepthRot()>sp.getOverhangLimit()) {
			System.out.println("Overhang conflict with Item: "+i);
			System.out.println("Overhang limi: t"+sp.getOverhangLimit()+" exceded in: " + sp);
			// return false;
		}
		
		// add item to the bin
		i.setBin(sp.bin, sp.bin.index, sp.posi);
		sp.bin.getItems().add(i);

		// calculate the overhang for the new space
		Space.Overhang overhang = null;
		if (i.getAdditionRot() != null) {
			// System.out.println("Addition detected!!");
			Box overhangBox = new Box(i.getAdditionRot().width,
					i.getAdditionRot().width, i.getAdditionRot().depth);
			overhang = sp.new Overhang(overhangBox, new Coord(
					i.getAdditionRot().posi.x, i.getHeightLimitByAddi()));
			//overhang in the space
		} else if (sp.overHang != null && sp.overHang.getDepth() > i.getWidth()) {
			Box overhangBox = new Box(sp.overHang.getWidth(),
					sp.overHang.getHeight(), sp.overHang.getDepth()
							- i.getWidth());
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
			sp.deleted = true;
		}

		sp.bin.getSpaces().add(sp1);
		sp.bin.getSpaces().add(sp2);

		boolean containAll = allSpace.contains(sp);
		if (!containAll) { // debugging
			containAll = allSpace.contains(sp);
		}
		boolean removeAll = allSpace.remove(sp);
		if (!containAll || !removeAll) {
			System.out.println("Could not remove allSpace!!");
			System.out.println(sp + " " + sp.posi + " " + sp.otoString());
			sp.deleted = true;
			if (allSpace.descendingSet().remove(sp))
				System.out.println("Descending set removed it !!");
		}

		allSpace.add(sp1);
		allSpace.add(sp2);
		return true;
	}

}
