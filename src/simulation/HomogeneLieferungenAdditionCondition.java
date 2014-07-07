package simulation;

import guillotine.Guillotine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import datamodel.Bin;
import datamodel.Box;
import datamodel.Item;
import datamodel.PlaceableItem;
import datamodel.SortedItems;
import evaluate.EvaluateBins;

public class HomogeneLieferungenAdditionCondition {

	static int runs = 2000;

	public static void main(String[] a) {
		ArrayList<Bin> bins = null;

		// item
		int itemsPerOrder = 100;
		// container dimensions
		Box binBox = new Box(950, 2050, 2142);

		int orderto = 9;
		int ordersFrom =5;
		//objekts
		int[] oH={500,500,750,1000,1500};
		int[] oD={1000,1500,1000,1500,2000};
		int w=70;
		//close bins after each order
		boolean close = true;
		//own Comparator
		Comparator<PlaceableItem> comp = new Comparator<PlaceableItem>() {
			@Override
			public int compare(PlaceableItem o1, PlaceableItem o2) {
				if (o1.equals(o2))
					return 0;
				if (o2.order() > o1.order())
					return -1;
				if (o2.order() < o1.order())
					return 1;

				int cmp = o2.getDepthRot() - o1.getDepthRot();
				if (cmp > 0)
					return 1;
				return -1;
			}
		};
		
		double mean =0;
		for(int size=0;size<oH.length;size++)
		for (int order = ordersFrom; order <= orderto; order++) {
			EvaluateBins ebins = new EvaluateBins(bins);
			//System.out.println("\n Orders loaded: " + order + " with "
			//		+ itemsPerOrder + " items per Order.");
			for (int i = runs; i != 0; i--) {
				SortedItems sort = new SortedItems();
				// generate items
				for (int o = order; o != 0; o--) {
					Iterator<Item> set1 = 
							GenerateOrders.generateSortedItems(itemsPerOrder-10, false, w, oH[size], oD[size], o);
							Iterator<Item> set2 = GenerateOrders.generateSortedItemsWithhAdditions(itemsPerOrder-90,false, w, oH[size], oD[size], o,10,10);
					for (; set1.hasNext();) {
						sort.add(new PlaceableItem(set1.next(), null, null));
					}
					for (; set2.hasNext();) {
						sort.add(new PlaceableItem(set2.next(), null, null));
					}
				}
				long time = System.currentTimeMillis();
				bins = Guillotine.fillBinsInOrderWithAdditions(sort.iterator(),
						binBox,close);
				time = System.currentTimeMillis() - time;
				// System.out.println("Time used: "+time);
				ebins.reset(bins);
				ebins.newRun();
				bins = new ArrayList<>();
			}
			//format for latex
			String s =oH[size]+"X"+oD[size]+" & "+order+" & "+ebins.getUtil()+"\\% & "+ebins.getChaos()+" & "+ebins.getBinsUsed()+ " \\\\ \\hline";
			if(order==orderto) s= s+" \\hline";
			System.out.println(s);
			mean += ebins.util/runs;
			//ebins.printEval();
		}
		System.out.println("Mean: "+mean/25 );
	}
}
