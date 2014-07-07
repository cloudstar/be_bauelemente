package pso;

import java.util.ArrayList;
import java.util.TreeMap;

import datamodel.Bin;
import datamodel.PlaceableItem;

public class Particle {

	public ArrayList<Bin> bins;
	public PlaceableItem[] itemOrder;

	public Bin personalBest;
	public static Bin globalBest;
	public eval e;

	public Particle(PlaceableItem[] itemOrder) {
		this.itemOrder = itemOrder;
		bins = new ArrayList<>();
		personalBest = null;
	}

	public class eval {
		public int space;
		public int spaceFree;
		public int spaceUsed;
		public int bins;

		public double fill;
		public double[] binFill;
		public double[] entropie;

		public double fitness;

		public eval(Particle par) {
			if (par == null || par.bins == null || par.bins.isEmpty()) {
				System.out.println("evaluating empty particle");
				return;
			}
			int i = 0;
			for (Bin b : par.bins) {
				int sizeBin = b.getHeight() * b.getWidth();

				space += b.getHeight() * b.getWidth();
				spaceFree += b.getFreeSpace();
				binFill[i] = (sizeBin - b.getFreeSpace()) / (double) (sizeBin);

				double chaos = 0;
				TreeMap<Integer, Boolean> orders = new TreeMap<>();
				for (PlaceableItem i1 : b.getItems()) {
					if (!orders.containsKey(i1.order())) {
						chaos++;
						orders.put(i1.order(), true);
					}
				}
				entropie[i] = chaos;
				i++;
			}
			fill = (space - spaceFree) / (double) space;
		}
	}
}
