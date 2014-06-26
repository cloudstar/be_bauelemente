package datamodel;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A sorted tree for all the free spaces
 * 
 * @author nrasic
 * 
 */
public class SortedSpace extends TreeSet<Space>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SortedSpace() {
		super(new Comparator<Space>() {
			@Override
			public int compare(Space o1, Space o2) {
				if (o1==o2) {
					return 0;
					}
				int cmp = o1.getCompareByHeight() - o2.getCompareByHeight();
				if (cmp > 0)
					return 1;
				return -1;
			}

		});
	}


	/**
	 * all elements greater or equal
	 * 
	 * @param i
	 * @return
	 */
	public SortedSet<Space> tailSet(Item i) {
		return this.tailSet(new Space(new Square(i.getWidth(), i.getHeightRot()),null,null,null,null));
	}

	public Iterator<Space> descIter() {
		return this.descendingIterator();
	}

	public String toString() {
		String list = "";

		for (Iterator<Space> it = this.descendingIterator(); it.hasNext();) {
			Space sp = it.next();
			list = list + "Space of bin " + sp.bin + " with X: " + sp.posi.x
					+ " with Y: " + sp.posi.y + " width: "+sp.getWidth()+" height: "+sp.getHeight()+sp.otoString()+"\n";
		}
		return list;
	}
}