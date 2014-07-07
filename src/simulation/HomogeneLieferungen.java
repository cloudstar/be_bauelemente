package simulation;

import guillotine.Guillotine;

import java.util.ArrayList;
import java.util.Iterator;

import datamodel.Bin;
import datamodel.Box;
import datamodel.Item;
import datamodel.PlaceableItem;
import datamodel.SortedItems;
import evaluate.EvaluateBins;

public class HomogeneLieferungen {

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
		boolean close = true;
		
		for(int size=0;size<oH.length;size++)
		for (int order = ordersFrom; order <= orderto; order++) {
			EvaluateBins ebins = new EvaluateBins(bins);
			//System.out.println("\n Orders loaded: " + order + " with "
			//		+ itemsPerOrder + " items per Order.");
			for (int i = runs; i != 0; i--) {
				SortedItems sort = new SortedItems();
				// generate items
				for (int o = order; o != 0; o--) {
					Iterator<Item> set1 = GenerateOrders.generateSortedItems(
							itemsPerOrder, true, w, oH[size], oD[size], o);
					for (; set1.hasNext();) {
						sort.add(new PlaceableItem(set1.next(), null, null));
					}
				}
				long time = System.currentTimeMillis();
				bins = Guillotine.fillBinsInOrderByHeight(sort.iterator(),
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
			//ebins.printEval();
		}
	}
}
