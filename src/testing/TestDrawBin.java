package testing;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import datamodel.Bin;
import datamodel.Item;
import datamodel.SortedSpace;
import datamodel.Space;
import draw.DrawBins;
import evaluate.EvaluateBins;

public class TestDrawBin {

	// bin value
	final static int Bwidth = 400;
	final static int Bheight = 200;
	final static int Bdepth = 400;

	public static void main(String[] a) {
		SortedSpace allSpace = new SortedSpace();
		ArrayList<Bin> bins = new ArrayList<>();
		//Bin bin1 = new Bin(400, 200, 400, 0, allSpace);
		//bins.add(bin1);

		int items1 = 100;
		int items2 = 200;
		Iterator<Item> set1 = generateSortedItems(items1, 10, 200, 30, 1);
		Iterator<Item> set2 = generateSortedItems(items2, 10, 200, 30, 2);
		Iterator<Item> set3 = generateSortedItems(items2, 10, 100, 30, 3);
		Iterator<Item> set4 = generateSortedItems(items2, 10, 50, 30, 4);

		fillBinsInOrder(bins, set1, allSpace);
		fillBinsInOrder(bins, set2, allSpace);
		fillBinsInOrder(bins, set3, allSpace);
		fillBinsInOrder(bins, set4, allSpace);

		EvaluateBins ebins = new EvaluateBins(bins);
		ebins.eval();

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 500, 500);
		JScrollPane scroll = new JScrollPane(new DrawBins(bins, 30, 30),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		window.getContentPane().add(scroll);

		window.setVisible(true);
	}

	/**
	 * generates a set of testitems
	 * 
	 * @param items
	 * @param maxW
	 * @param maxH
	 * @param maxD
	 * @param order
	 * @return
	 */
	private static Iterator<Item> generateSortedItems(int items, int maxW,
			int maxH, int maxD, int order) {
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
			Item it = new Item(maxW, maxH - (int) (maxH / 2 * Math.random()),
					maxD - (int) (maxD / 2 * Math.random()), order);
			sorted.put(it, it);
		}
		return sorted.descendingKeySet().descendingIterator();

	}

	/**
	 * fills the bins in a particular order given by the set
	 * 
	 * @param set
	 */
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
			if (freeSpace.bin.getFreeSpace() < 0) {
				System.out.println("Trying to put " + it + "into: " + freeSpace
						+ " of bin: " + freeSpace.bin);
				System.out.println("Freespace Position: "+freeSpace.posi);
			}
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
			}// our conditions
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
}
