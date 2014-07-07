package evaluate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.TreeMap;

import datamodel.Bin;
import datamodel.Item;
import datamodel.PlaceableItem;

public class EvaluateBins {
	ArrayList<Bin> bins;
	public int runCount = 0;
	public double util = 0;
	public double chaos = 0;
	public double binsUsed = 0;

	public EvaluateBins(ArrayList<Bin> bins) {
		this.bins = bins;
	}

	public void eval() {
		if (bins != null && !bins.isEmpty())
			System.out.println("Bins filled: " + bins.size());
		for (Bin bin : bins) {
			System.out.println("Bin#" + bin.index + " has "
					+ bin.getItems().size());
			System.out.println("with freeSpace: " + bin.getFreeSpace()
					+ " Ratio: "
					+ ((bin.getHeight() * bin.getWidth()) - bin.getFreeSpace())
					/ (double) (bin.getHeight() * bin.getWidth()));
		}

		System.out.println("freeTotal: " + freeTotal());
		System.out.println("Fill: " + fillRatio());
		System.out.println("Entropie: " + entropie());

		// Global for the run
		newRun();
		System.out.println("-----------------------------");
		System.out.println("runCount: " + runCount);
		System.out.println("Fill: " + util / runCount);
		System.out.println("Chaos: " + chaos / runCount);
		System.out.println("BinsUsed " + binsUsed / runCount);

	}

	public void newRun() {
		runCount++;
		util = (util + fillRatio());
		chaos = (chaos + entropie());
		binsUsed = (binsUsed + bins.size());
	}

	public String getUtil() {
		return crop(util*100 / runCount);
	}

	public String getChaos() {
		return crop(chaos / runCount);
	}

	public String getBinsUsed() {
		return crop(binsUsed / runCount);
	}

	private String crop(double value) {
		NumberFormat formatter = new DecimalFormat("#0.00");  
		return formatter.format(value);
	}

	/**
	 * Total free space
	 * 
	 * @return
	 */
	public int freeTotal() {
		int free = 0;
		for (Bin bin : bins)
			free += bin.getFreeSpace();
		return free;
	}

	/**
	 * relative fill
	 * 
	 * @return
	 */
	public double fillRatio() {
		double size = 0;
		for (Bin bin : bins)
			size += bin.getHeight() * bin.getWidth();
		return (size - freeTotal()) / size;
	}

	/**
	 * degree of chaos
	 * 
	 * @return
	 */
	public double entropie() {
		double chaos = 0;
		for (Bin bin : bins) {
			TreeMap<Integer, Boolean> orders = new TreeMap<>();
			for (PlaceableItem i : bin.getItems()) {
				if (!orders.containsKey(i.order())) {
					chaos++;
					orders.put(i.order(), true);
				}
			}
		}
		return chaos / bins.size();
	}

	public void reset(ArrayList<Bin> bins2) {
		bins = bins2;
	}

	public void printEval() {
		System.out.println("-----------------------------");
		System.out.println("runCount: " + runCount);
		System.out.println("Fill: " + util / runCount);
		System.out.println("Chaos: " + chaos / runCount);
		System.out.println("BinsUsed " + binsUsed / runCount);
	}
}
