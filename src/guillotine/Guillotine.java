package guillotine;

import java.util.ArrayList;
import java.util.Iterator;
import datamodel.Bin;
import datamodel.Coord;
import datamodel.Item;
import datamodel.SortedSpace;
import datamodel.Space;

public class Guillotine {

	// bin values
	final static int Bwidth = 400;
	final static int Bheight = 200;
	final static int Bdepth = 400;
	
/**
 * Fills the bins in the provided order
 * @param bins
 * @param set
 * @param allSpace
 */
	@SuppressWarnings("unused")
	private static void fillBinsInOrder(ArrayList<Bin> bins,
			Iterator<Item> set, SortedSpace allSpace) {
		if (bins == null)
			bins = new ArrayList<>();
		if (allSpace == null )
			allSpace = new SortedSpace();

		// as long as there are items in our list
		for (; set.hasNext();) {
			Item it = set.next();
			if (it.getHeight() > Bheight) {
				System.err.println("item too big for a bin!!");
				return;
			}
			// searching for possible spaces
			Space freeSpace = searchBySpace(bins, allSpace, it);
			freeSpace.bin.addItemGuilHori(it, freeSpace);
		}
	}

	public static Space searchBySpace(ArrayList<Bin> bins,
			SortedSpace allSpace, Item it) {
		Space space = null;
		for (Space s : allSpace.tailSet(it)) {
			if (s.height < it.getHeight()) {
				System.out.println("Wrong height detected! Unplaceble");
				return null;
			}// the conditions beside the height
			if (s.width < it.getWidth()) {
				continue;
			}
			space = s;
			break;
		}
		if (space == null) {
			bins.add(new Bin(Bwidth, Bheight, Bdepth, bins.size(), allSpace));
			return searchBySpace(bins, allSpace, it);
		}
		return space;
	}
	
	/**
	 * Puts the item into the free space and performs a horizontal cut in the bin
	 * 
	 * @param i
	 *            item
	 * @param sp
	 *            the space
	 * @return
	 */
	public static boolean addItemGuilHori(Item i, Space sp,SortedSpace allSpace) {

		if (i.getHeight() > sp.height || i.getWidth() > sp.width) {
			System.out.println("Item does not fit into the Space!!");
			return false;
		}
		if (!sp.bin.getSpaces().contains(sp)){
		System.out.println("Does not hold the space:"+sp);
		return false;
		}
		
		i.setBin(sp.bin, sp.bin.index, sp.posi);
		sp.bin.getItems().add(i);

		// new space after the item
		Space sp1 = new Space(sp.width - i.getWidth(), i.getHeight(), sp.lean,
				new Coord(sp.posi.x + i.getWidth(), sp.posi.y), sp.bin);
		// new space under the item
		Space sp2 = new Space(sp.width, sp.height - i.getHeight(), sp.lean,
				new Coord(sp.posi.x, sp.posi.y + i.getHeight()), sp.bin);

		boolean contain = sp.bin.getSpaces().contains(sp);
		boolean remove = sp.bin.getSpaces().remove(sp);
		if (!contain || !remove){
			System.out.println("Could not remove internSpace!!");
		System.out.println(sp+" "+sp.posi+" "+sp.otoString());
		}
		sp.bin.getSpaces().add(sp1);
		sp.bin.getSpaces().add(sp2);

		boolean containAll = allSpace.contains(sp);
		boolean removeAll = allSpace.remove(sp);
		if (!containAll || !removeAll)
			System.out.println("Could not remove allSpace!!");
		allSpace.add(sp1);
		allSpace.add(sp2);
		return true;
	}
}

