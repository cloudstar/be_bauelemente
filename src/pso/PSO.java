package pso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import datamodel.Bin;
import datamodel.Item;
import datamodel.PlaceableItem;
import datamodel.SortedSpace;
import datamodel.Space;

public class PSO {
	// bin values
	final static int Bwidth = 400;
	final static int Bheight = 200;
	final static int Bdepth = 400;

	/**
	 * Fills the bins in the provided order
	 * 
	 * @param bins
	 * @param items
	 * @param allSpace
	 */

	public static ArrayList<Bin> fillBinsInOrderByHeight(Item[] items) {
		SortedSpace allSpace = new SortedSpace();
		ArrayList<Bin> bins = Bin.createBins(allSpace);

		HashMap<Integer, Integer> startOrder = new HashMap<>();
		HashMap<Integer, Integer> endOrder = new HashMap<>();
		getArrayPositions(items, startOrder, endOrder);

		PlaceableItem[] solution1 = createPlaceable(items);
		
		for (int i : startOrder.keySet())
			shuffle(solution1, startOrder.get(i), endOrder.get(i));

		// as long as there are items in our list
		for (PlaceableItem it : solution1) {
			if (it.item.getHeightRot() > bins.get(0).getHeight()) {
				System.err.println("item too big for a bin!!");
				continue;
			}
			System.out.println("Item: " + it.item );
		}
		System.out.println("Size: " + solution1.length);

		return bins;
	}

	/**
	 * shuffels the items between the bounds
	 * 
	 * @param items
	 * @param start
	 * @param end
	 * @return
	 */
	public static void shuffle(PlaceableItem[] items, int start, int end) {
		LinkedList<PlaceableItem> list = new LinkedList<>();
		for (int i = start; i <= end; i++)
			list.add(items[i]);

		Collections.shuffle(list);

		for (int i = start; i <= end; i++)
			items[i] = list.pop();
	}

	/**
	 * creates a new list of placeable items
	 * 
	 * @return
	 */
	public static PlaceableItem[] createPlaceable(Item[] items) {
		PlaceableItem[] ret = new PlaceableItem[items.length];
		for (int i = 0; i < items.length; i++)
			ret[i] = new PlaceableItem(items[i], null, null);
		return ret;
	}

	/**
	 * determines where our order starts and ends
	 * 
	 * @param items
	 * @param startOrder
	 * @param endOrder
	 */
	public static void getArrayPositions(Item[] items,
			HashMap<Integer, Integer> startOrder,
			HashMap<Integer, Integer> endOrder) {
		int lastOrder = -1;
		Item lastItem = null;

		for (int i = 0; i < items.length; i++) {
			if (lastOrder != items[i].order) {
				lastOrder = items[i].order;
				startOrder.put(items[i].order, i);
				if (lastItem != null)
					endOrder.put(lastItem.order, i - 1);
				lastItem = items[i];
			}
		}
		endOrder.put(lastOrder, items.length - 1);
	}
}
