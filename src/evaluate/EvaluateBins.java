package evaluate;

import java.util.ArrayList;
import java.util.TreeMap;

import datamodel.Bin;
import datamodel.Item;

public class EvaluateBins {
	ArrayList<Bin> bins;

	public EvaluateBins(ArrayList<Bin> bins) {
		this.bins = bins;
	}

	public void eval() {
		if (bins != null && !bins.isEmpty())
			System.out.println("Bins filled: " + bins.size());
		for (Bin bin : bins) {
			System.out.println("Bin#" + bin.index + " has "
					+ bin.getItems().size());
			System.out.println("with freeSpace: " + bin.getFreeSpace());
			System.out.println("freeTotal: " + freeTotal());
			System.out.println("Fill: " + fillRatio());
			System.out.println("Entropie: "+entropie());
		}
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
			size += bin.space2D;
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
			for (Item i : bin.getItems()) {
				if(!orders.containsKey(i.order)){
					chaos++;
					orders.put(i.order, true);
				}
			}
		}
		return chaos / bins.size();
	}
}
