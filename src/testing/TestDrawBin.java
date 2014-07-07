package testing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import simulation.GenerateOrders;

import datamodel.Bin;
import datamodel.Item;
import datamodel.PlaceableItem;
import datamodel.SortedItems;
import datamodel.SortedSpace;
import datamodel.Space;
import draw.DrawBins;
import evaluate.EvaluateBins;
import guillotine.Guillotine;

public class TestDrawBin {

	public static void main(String[] a) {
		ArrayList<Bin> bins =null;


		int items1 = 100;
		int items2 = 200;
		
//		Iterator<Item> set1 = GenerateOrders.generateSortedItems(items1,false, 10, 200, 60, 1);
//		Iterator<Item> set2 = GenerateOrders.generateSortedItems(items2,true, 10, 200, 120, 2);
//		Iterator<Item> set3 = GenerateOrders.generateSortedItems(items2,false, 10, 100, 30, 3);
//		Iterator<Item> set4 = GenerateOrders.generateSortedItems(items2,true, 10, 50, 60, 4);

		Iterator<Item> set1 = 
				GenerateOrders.generateSortedItemsWithhAdditions(items1,false, 12, 186, 250, 1,10,10);
		Iterator<Item> set2 = GenerateOrders.generateSortedItems(items2,false, 12, 186, 250, 2);
		Iterator<Item> set3 = GenerateOrders.generateSortedItems(items2,false, 12, 186, 250, 3);
		Iterator<Item> set4 = GenerateOrders.generateSortedItems(items2,false, 12, 186, 250, 4);
		
		
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

		for (; set1.hasNext();) {
			sort.add(new PlaceableItem(set1.next(), null, null));
		}
		long time = System.currentTimeMillis();
		bins = Guillotine.fillBinsInOrderByHeight(sort.iterator(),false);
		time = System.currentTimeMillis()-time;
		System.out.println("Time used: "+time);
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
