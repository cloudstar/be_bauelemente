package pso;

import guillotine.Guillotine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.google.common.collect.Iterators;

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
	private static int initSolutions = 100;
	
	/**
	 * Fills the bins in the provided order
	 * 
	 * @param bins
	 * @param items
	 * @param allSpace
	 */

	public static ArrayList<Bin> fillBinsInOrderByHeight(PlaceableItem[] items) {
		SortedSpace allSpace = new SortedSpace();
		ArrayList<Bin> bins = Bin.createBins(allSpace);

		HashMap<Integer, Integer> startOrder = new HashMap<>();
		HashMap<Integer, Integer> endOrder = new HashMap<>();
		getArrayPositions(items, startOrder, endOrder);

		//solutions;particles
		ArrayList<Particle> solutions = new ArrayList<>();
		for(int t=initSolutions; t>0;t--){
			PlaceableItem[] sol= createPlaceable(items);
			if(Math.random()<0.25){
				for(int i:startOrder.keySet())
				shuffle(items, startOrder.get(i), endOrder.get(i));
			solutions.add(new Particle(sol));
			}else
				for(int i:startOrder.keySet())
				swap(items,(int)items.length/startOrder.size()/4, startOrder.get(i), endOrder.get(i));
			solutions.add(new Particle(sol));
		}
		return bins;
		
//global best solution
		
		
		//for (int i : startOrder.keySet())
	//		shuffle(solution1, startOrder.get(i), endOrder.get(i));
		//swap(solution1, 2,startOrder.get(i), endOrder.get(i));
		
//		// as long as there are items in our list
//		for (PlaceableItem it : solution1) {
//			if (it.getHeightRot() > bins.get(0).getHeight()) {
//				System.err.println("item too big for a bin!!");
//				continue;
//			}
//			System.out.println("Item: " + it.item);
//		}
//		System.out.println("Size: " + solution1.length);
//
//		return Guillotine.fillBinsInOrderByHeight(Arrays.asList(solution1)
//				.iterator());
	}

	/**
	 * Shuffles the items between the bounds
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
	 * swaps items between the bounds
	 * 
	 * @param items
	 * @param times
	 * @param start
	 * @param end
	 */
	public static void swap(PlaceableItem[] items, int times, int start, int end) {
		PlaceableItem buff;
		int i;
		int i2;
		for (int it = times; it > 0; it--) {
			i = (int) Math.floor(Math.random() * end - start)+start;
			i2 = (int) Math.floor(Math.random() * end - start)+start;
			buff = items[i];
			items[i] = items[i2];
			items[i2] = buff;
		}
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
	 * creates a new list of placeable items
	 * 
	 * @return
	 */
	public static PlaceableItem[] createPlaceable(PlaceableItem[] items) {
		PlaceableItem[] ret = new PlaceableItem[items.length];
		for (int i = 0; i < items.length; i++)
			ret[i] = new PlaceableItem(items[i].item, null, null);
		return ret;
	}

	/**
	 * determines where our order starts and ends
	 * 
	 * @param items
	 * @param startOrder
	 * @param endOrder
	 */
	public static void getArrayPositions(PlaceableItem[] items,
			HashMap<Integer, Integer> startOrder,
			HashMap<Integer, Integer> endOrder) {
		int lastOrder = -1;
		PlaceableItem lastItem = null;

		for (int i = 0; i < items.length; i++) {
			if (lastOrder != items[i].order()) {
				lastOrder = items[i].order();
				startOrder.put(items[i].order(), i);
				if (lastItem != null)
					endOrder.put(lastItem.order(), i - 1);
				lastItem = items[i];
			}
		}
		endOrder.put(lastOrder, items.length - 1);
	}
}
