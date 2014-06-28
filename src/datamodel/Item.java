package datamodel;

/**
 * An Item which can be put in a bin
 * 
 * @author nrasic
 * 
 */
public class Item {
	public final Box box;
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
	/**
	 * possible addition of the Item
	 */
	Addition addi;

	public Item(Box space, int order) {
		this.box = space;
		this.rot = 0;
		this.order = order;
		addi = null;
	}

	public Item(Box space, int order, Addition addi) {
		this.box = space;
		this.rot = 0;
		this.order = order;
		this.addi = addi;
	}

	public Item(Item it) {
		this.box = new Box(it.getWidth(),it.getHeightRot(),it.getDepthRot());
		this.rot = 0;
		this.order = it.order;
		this.posi = it.getPosi();
		this.addi = it.addi;
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
		return box.width;
	}

	public int getHeightRot() {
		if (rot % 2 == 0)
			return box.height;
		else
			return box.depth;
	}

	public int getDepthRot() {
		if (rot % 2 == 0)
			return box.depth;
		else
			return box.height;
	}

	public int getSpace2D() {
		return getWidth() * getHeightRot();
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

	public void setAddition(Addition addi) {
		this.addi = addi;
	}

	/**
	 * returns the maxHeight regarding the additions
	 * 
	 * @return
	 */
	public int getHeightLimitByAddi() {
		if (addi != null) {
			if (rot % 4 == 0)
				return box.depth - addi.posi.y - addi.height;
			if (rot % 4 == 1)
				return box.height - addi.posi.x - addi.width;
			if (rot % 4 == 2)
				return addi.posi.y;

			return addi.posi.x;
		}
		return Integer.MAX_VALUE;
	}

	public Addition getAdditionRot() {
		if (addi == null)
			return null;
		if (rot % 4 == 0)
			return addi;
		if (rot % 4 == 1)
			return new Addition(addi.height, addi.width, addi.depth, new Coord(
					box.depth-addi.posi.y - addi.height, addi.posi.x));
		if (rot % 4 == 2)
			return new Addition(addi.width, addi.height, addi.depth, new Coord(
					box.height - addi.posi.x - addi.width, box.depth - addi.posi.y-addi.height));
		
			return new Addition(addi.height, addi.width, addi.depth, new Coord(
					addi.posi.y, box.height - addi.posi.x - addi.width));
		
	}

	public String toString() {
		return "Item with width: " + box.width + " height: " + box.height + " depth: "
				+ box.depth + " order: " + order;
	}

	/**
	 * addition of the item
	 * 
	 * @author nrasic
	 * 
	 */
	public class Addition {
		public final int width;
		public final int height;
		public final int depth;
		public final Coord posi;

		public Addition(int width, int height, int depth, Coord posi) {
			this.width = width;
			this.height = height;
			this.depth = depth;
			this.posi = posi;
		}
	}
}
