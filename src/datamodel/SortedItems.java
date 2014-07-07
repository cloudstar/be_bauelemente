package datamodel;

import java.util.Comparator;
import java.util.TreeSet;

public class SortedItems extends TreeSet<PlaceableItem> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default we sort by order and then by height
	 */
	public SortedItems() {
		super(new Comparator<PlaceableItem>() {
			@Override
			public int compare(PlaceableItem o1, PlaceableItem o2) {
				if (o1.equals(o2))
					return 0;
				if (o2.order() > o1.order())
					return -1;
				if (o2.order() < o1.order())
					return 1;

				int cmp = o2.getHeightRot() - o1.getHeightRot();
				if (cmp > 0)
					return 1;
				return -1;
			}
		});
	}

	public SortedItems(Comparator<PlaceableItem> comp) {
		super(comp);
	}
}
