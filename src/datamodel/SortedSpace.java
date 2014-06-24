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
public class SortedSpace {

	TreeSet<Space> set;

	public SortedSpace() {
		set = new TreeSet<Space>(new Comparator<Space>() {
			@Override
			public int compare(Space o1, Space o2) {
				if (o1.equals(o2))
					return 0;
				int cmp = o1.getCompareByHeight() - o2.getCompareByHeight();
				if (cmp == 0 && o1.posi != null)
					return 1;
				return cmp;
			}

		});
	}

	public void add(Space sp) {
		set.add(sp);
	}

	public boolean remove(Space sp) {
		return set.remove(sp);
	}

	public Space floor(Space sp) {
		return set.floor(sp);
	}

	public SortedSet<Space> headSet(Space sp) {
		return set.headSet(sp);
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	/**
	 * all elements greater or equal
	 * 
	 * @param i
	 * @return
	 */
	public SortedSet<Space> tailSet(Item i) {
		return set.tailSet(new Space(i.width, i.height, null, null, null));
	}

	public Iterator<Space> descIter() {
		return set.descendingIterator();
	}

	public String toString() {
		String list = "";

		for (Iterator<Space> it = set.descendingIterator(); it.hasNext();) {
			Space sp = it.next();
			list = list + "Space of bin " + sp.bin + " with X: " + sp.posi.x
					+ " with Y: " + sp.posi.y + "\n";
		}
		return list;
	}

	public boolean contains(Space sp) {
		return set.contains(sp);
	}
}