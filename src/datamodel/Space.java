package datamodel;

/**
 * 
 * represents a free space in the bin, in which we can put new items
 */
public class Space implements Comparable<Space> {
	 public final Square plain;
	/**
	 * the item behind the free we lean on
	 */
	public final PlaceableItem lean;
	public Coord posi;
	public final Bin bin;
	public final Space father;
	public final Overhang overHang;
	public boolean deleted=false;
	
	public Space(Square plain, PlaceableItem lean, Coord posi, Bin bin,Space parent) {
		this.plain = plain;
		this.lean = lean;
		this.posi = posi;
		this.bin = bin;
		this.father =parent;
		this.overHang = null;
	}
	
	public Space(Square plain, PlaceableItem lean, Coord posi, Bin bin,Space parent,Overhang overhang) {
		this.plain = plain;
		this.lean = lean;
		this.posi = posi;
		this.bin = bin;
		this.father =parent;
		this.overHang = overhang;
	}

	/**
	 * Returns the height for sorting
	 * 
	 * @return
	 */
	public int getCompareByHeight() {
		return plain.height;
	}
	/**
	 * returns the height of the first overhang which functions as a border
	 * @return
	 */
	public int getOverhangLimit(){
		if(overHang == null)
			return Integer.MAX_VALUE;
		//System.out.println("Overhang returned: "+overHang.posi.y);
		return overHang.posi.y;
	}

	public String toString() {
		return "Space with width:" + plain.width + " height: " + plain.height+" of bin#"+bin.index+" "+otoString();
	}
	
	public int getWidth(){
		return plain.width;
	}
	
	public int getHeight(){
		return plain.height;
	}
	/**
	 * debugging-output
	 * @return
	 */
	public String otoString() {
		return super.toString();
	}

	public class Overhang{
		public final Box box;
		//position from the bottom left
		public final Coord posi;
		
		public Overhang(Box box,Coord posi) {
			this.box = box;
			this.posi = posi;
		}
		public int getWidth(){
			return box.width;
		}
		public int getHeight(){
			return box.height;
		}
		public int getDepth(){
			return box.depth;
		}
	}

	@Override
	public int compareTo(Space o) {
		return this.getCompareByHeight()-o.getCompareByHeight();
	}
}
