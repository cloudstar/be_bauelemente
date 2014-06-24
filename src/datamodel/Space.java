package datamodel;

/**
 * 
 * represents a free space in the bin, in which we can put new items
 */
public class Space {
	public final int width, height;
	/**
	 * the item behind the free we lean on
	 */
	public final Item lean;
	public final Coord posi;
	public final Bin bin;

	public Space(int width, int height, Item lean, Coord posi, Bin bin) {
		this.width = width;
		this.height = height;
		this.lean = lean;
		this.posi = posi;
		this.bin = bin;
	}

	/**
	 * Retruns the y as compare for sorting
	 * 
	 * @return
	 */
	public int getCompareByHeight() {
		return height;
	}

	public String toString() {
		return "Space with width:" + width + " height: " + height+" of bin#"+bin.index;
	}
	
	public String otoString() {
		return super.toString();
	}
}
