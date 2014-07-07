package simulation;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import datamodel.Box;
import datamodel.Coord;
import datamodel.Item;

public class GenerateOrders {

	/**
	 * generates a set of items of a given order
	 * 
	 * @param items
	 * @param same
	 * @param width
	 * @param maxH
	 * @param maxD
	 * @param order
	 * @return
	 */
	public static Iterator<Item> generateSortedItems(int items, boolean same,
			int width, int maxH, int maxD, int order) {
		TreeMap<Item, Item> sorted = new TreeMap<Item, Item>(
				new Comparator<Item>() {
					@Override
					public int compare(Item o1, Item o2) {
						if (o1.equals(o2))
							return 0;
						int cmp = o2.getHeight() - o1.getHeight();
						if (cmp == 0)
							return 1;
						return cmp;
					}
				});

		for (int i = 0; i < items; i++) {
			Box iB = new Box(width, maxH - (int) (maxH / 2 * Math.random()),
					maxD - (int) (maxD / 2 * Math.random()));
			Item it = new Item(iB, order);
			if (!same)
				sorted.put(it, it);
			else {
				for (int j = i; j < items; j++) {
					Item it_cp = new Item(it);
					sorted.put(it_cp, it_cp);
				}
				break;
			}
		}
		return sorted.descendingKeySet().descendingIterator();
	}

	/**
	 * creates a items with additions with a given offset
	 * 
	 * @param items
	 * @param same
	 * @param width
	 * @param maxH
	 * @param maxD
	 * @param order
	 * @param addiOffset
	 * @return
	 */
	public static Iterator<Item> generateSortedItemsWithhAdditions(int items,
			boolean same, int width, int maxH, int maxD, int order,
			int addiOffset, int addiMaxD) {
            TreeMap<Item, Item> sorted = new TreeMap<Item, Item>(
				new Comparator<Item>() {
					@Override
					public int compare(Item o1, Item o2) {
						if (o1.equals(o2))
							return 0;
						int cmp = o2.getHeight() - o1.getHeight();
						if (cmp == 0)
							return 1;
						return cmp;
					}
				});
            
		Iterator<Item> it = generateSortedItems(items, same, width, maxH, maxD,
				order);
		if (!same) {
			for (; it.hasNext();) {
				Item next = it.next();
				int depth = addiMaxD - (int) (addiMaxD / 2 * Math.random());
				next.setAddition(next.new Addition(width, addiOffset, depth,
						new Coord(0, addiOffset)));
                                sorted.put(next, next);
			}
		} else {
			int depth = addiMaxD - (int) (addiMaxD / 2 * Math.random());
			for (; it.hasNext();) {
				Item next = it.next();
				next.setAddition(next.new Addition(width, addiOffset, depth,
						new Coord(0, addiOffset)));
                                sorted.put(next, next);
			}
                }
		return sorted.descendingKeySet().iterator();
	}
}
