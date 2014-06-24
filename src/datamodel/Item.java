package datamodel;

/**
 * An Item which can be put in a bin
 * 
 * @author nrasic
 * 
 */
public class Item {
	final int width, height, depth;
	public final int order;
	
	int rot;
	/**
	 * the bin
	 */
	Bin bin;
	/**
	 * index of the binlist
	 */
	int index;
	/**
	 * position in the bin
	 */
	Coord posi;
	
	

	public Item(int witdh, int height, int depth, int order) {
		this.width = witdh;
		this.height = height;
		this.depth = depth;
		this.rot = 0;
		this.order = order;
	}

	/**
	 * sets the bin
	 */
	public void setBin(Bin bin, int index, Coord posi2) {
		this.bin = bin;
		this.index = index;
		this.posi = posi2;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		if (rot % 2 == 0)
			return height;
		else
			return depth;
	}

	public int getDepth() {
		if (rot % 2 == 0)
			return depth;
		else
			return height;
	}

	public int getSpace2D() {
		return getWidth()*getHeight();
	}


	/**
	 * rotations clockwise
	 * 
	 * @return
	 */
	public int getRot() {
		return rot;
	}

	public void setRot(int rot) {
		this.rot = rot;
	}

	public Coord getPosi() {
		return posi;
	}

	public void setPosi(Coord posi) {
		this.posi = posi;
	}

	public String toString(){
		return "Item with width: "+width+" height: "+height+" order: "+order;
	}
}
