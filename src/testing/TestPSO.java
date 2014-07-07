package testing;

import guillotine.Guillotine;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import pso.PSO;

import simulation.GenerateOrders;
import datamodel.Bin;
import datamodel.Item;
import datamodel.PlaceableItem;
import datamodel.SortedItems;
import datamodel.SortedSpace;
import draw.DrawBins;
import evaluate.EvaluateBins;

public class TestPSO {
	// bin value
		final static int Bwidth = 400;
		final static int Bheight = 200;
		final static int Bdepth = 400;

		public static void main(String[] a) {
			SortedSpace allSpace = new SortedSpace();
			ArrayList<Bin> bins = new ArrayList<>();
			// Bin bin1 = new Bin(400, 200, 400, 0, allSpace);
			// bins.add(bin1);

			int items1 = 100;
			int items2 = 10;
//			Iterator<Item> set1 = GenerateOrders.generateSortedItems(items1,false, 10, 200, 60, 1);
//			Iterator<Item> set2 = GenerateOrders.generateSortedItems(items2,true, 10, 200, 120, 2);
//			Iterator<Item> set3 = GenerateOrders.generateSortedItems(items2,false, 10, 100, 30, 3);
//			Iterator<Item> set4 = GenerateOrders.generateSortedItems(items2,true, 10, 50, 60, 4);

		//	Iterator<Item> set1 = 
		//			GenerateOrders.generateSortedItemsWithhAdditions(items1,false, 10, 200, 60, 1,10,50);
			Iterator<Item> set2 = GenerateOrders.generateSortedItems(items2,false, 10, 50, 120, 2);
			Iterator<Item> set3 = GenerateOrders.generateSortedItems(items2,false, 10, 100, 120, 3);
			Iterator<Item> set4 = GenerateOrders.generateSortedItems(items2,false, 10, 150, 120, 4);
			
			SortedItems sort = new SortedItems(); 

			for (; set2.hasNext();) {
				sort.add(new PlaceableItem(set2.next(), null, null));
			}

			for (; set3.hasNext();) {
				sort.add(new PlaceableItem(set3.next(), null, null));
			}
			for (; set4.hasNext();) {
				sort.add(new PlaceableItem(set4.next(), null, null));
			}
//
//			for (; set1.hasNext();) {
//				sort.add(new PlaceableItem(set1.next(), null, null));
//			}
			
			ArrayList<PlaceableItem> list = new ArrayList<>();
			Iterator<PlaceableItem> arra = sort.iterator();
			for(;arra.hasNext();){
			list.add(arra.next());
			}
			bins = PSO.fillBinsInOrderByHeight(list.toArray(new PlaceableItem[0]));

			// fillBinsInOrder(bins, set1, allSpace);
			// fillBinsInOrder(bins, set2, allSpace);
			// fillBinsInOrder(bins, set3, allSpace);
			// fillBinsInOrder(bins, set4, allSpace);

			EvaluateBins ebins = new EvaluateBins(bins);
			ebins.eval();

			JFrame window = new JFrame();
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setBounds(20, 20, 800, 600);
			JScrollPane scroll = new JScrollPane(new DrawBins(bins, 30, 30),
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

			window.getContentPane().add(scroll);

			window.setVisible(true);
		}
}
