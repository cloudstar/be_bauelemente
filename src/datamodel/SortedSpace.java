package datamodel;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.BoundType;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

/**
 * A sorted tree for all the free spaces
 * 
 * @author nrasic
 * 
 */
public class SortedSpace  {


	TreeSet<Space> spaces;
	public SortedSpace() {
			//spaces =  new BinTree<>();
		spaces = new TreeSet<>(new Comparator<Space>() {
			@Override
			public int compare(Space o1, Space o2) {
				if(o1.equals(o2))return 0;
				int cmp = o1.getCompareByHeight()-o2.getCompareByHeight();
				if(cmp>=1)return 1;
				return -1;
			}
		});
	}


	public void add(Space sp){
		spaces.add(sp);
		//spaces.insert(sp);
	}
	
	public boolean remove(Space sp){
		return spaces.remove(sp);
	}
	
	public boolean contains(Space sp){
		return spaces.contains(sp);
	}
	/**
	 * all elements greater or equal
	 * 
	 * @param i
	 * @return
	 */
	public SortedSet<Space> tailSet(PlaceableItem i) {
		return spaces.tailSet(new Space(new Square(i.getWidth(), i.getHeightRot()),null,null,null,null));
	}
//	public SortedSet<Space> headSet(Item i) {
//		return this.headSet(new Space(new Square(i.getWidth(), i.getHeightRot()),null,null,null,null));
//	}
	
	public NavigableSet<Space> descendingSet() {
		return spaces.descendingSet();
	}

	public Iterator<Space> descIter() {
		return spaces.descendingSet().iterator();
	}

	public String toString() {
		String list = "";

		for (Iterator<Space> it = this.descIter(); it.hasNext();) {
			Space sp = it.next();
			list = list + "Space of bin " + sp.bin + " with X: " + sp.posi.x
					+ " with Y: " + sp.posi.y + " width: "+sp.getWidth()+" height: "+sp.getHeight()+sp.otoString()+"\n";
		}
		return list;
	}
}