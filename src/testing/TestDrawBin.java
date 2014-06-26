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
import datamodel.SortedItems;
import datamodel.SortedSpace;
import datamodel.Space;
import draw.DrawBins;
import evaluate.EvaluateBins;
import guillotine.Guillotine;

public class TestDrawBin {

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
		int items2 = 200;
//		Iterator<Item> set1 = GenerateOrders.generateSortedItems(items1,false, 10, 200, 60, 1);
//		Iterator<Item> set2 = GenerateOrders.generateSortedItems(items2,true, 10, 200, 120, 2);
//		Iterator<Item> set3 = GenerateOrders.generateSortedItems(items2,false, 10, 100, 30, 3);
//		Iterator<Item> set4 = GenerateOrders.generateSortedItems(items2,true, 10, 50, 60, 4);

		Iterator<Item> set1 = 
				GenerateOrders.generateSortedItemsWithhAdditions(items1,false, 10, 200, 60, 1,10,50);
		Iterator<Item> set2 = GenerateOrders.generateSortedItems(items2,true, 10, 200, 120, 2);
		Iterator<Item> set3 = GenerateOrders.generateSortedItems(items2,false, 10, 100, 120, 3);
		Iterator<Item> set4 = GenerateOrders.generateSortedItems(items2,true, 10, 50, 120, 4);
		
		SortedItems sort = new SortedItems(); 

		for (; set2.hasNext();) {
			sort.add(set2.next());
		}

		for (; set3.hasNext();) {
			sort.add(set3.next());
		}
		for (; set4.hasNext();) {
			sort.add(set4.next());
		}

		for (; set1.hasNext();) {
			sort.add(set1.next());
		}
		Guillotine.fillBinsInOrderWithAdditions(bins, sort.iterator(), allSpace);

		// fillBinsInOrder(bins, set1, allSpace);
		// fillBinsInOrder(bins, set2, allSpace);
		// fillBinsInOrder(bins, set3, allSpace);
		// fillBinsInOrder(bins, set4, allSpace);

		EvaluateBins ebins = new EvaluateBins(bins);
		ebins.eval();

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(3, 3, 1100, 1500);
		JScrollPane scroll = new JScrollPane(new DrawBins(bins, 30, 30),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		window.getContentPane().add(scroll);

		window.setVisible(true);
	}
}
