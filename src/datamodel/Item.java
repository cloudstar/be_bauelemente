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

	//int rot;
	/**
	 * the bin
	 */
	//Bin bin;
	/**
	 * index of the binlist
	 */
	//int index;
	/**
	 * position in the bin
	 */
	//Coord posi;
	/**
	 * possible addition of the Item
	 */
	Addition addi;

	public Item(Box space, int order) {
		this.box = space;
		
		this.order = order;
		addi = null;
	}

	public Item(Box space, int order, Addition addi) {
		this.box = space;
		
		this.order = order;
		this.addi = addi;
	}

	public Item(Item it,int rot) {
		this.box = new Box(it.getWidth(),it.getHeightRot(rot),it.getDepthRot(rot));		
		this.order = it.order;
		this.addi = it.addi;
	}
	
	public Item(Item it){
		this.box = new Box(it.getWidth(),it.getHeightRot(0),it.getDepthRot(0));		
		this.order = it.order;
		this.addi = it.addi;
	}

	public int getWidth() {
		return box.width;
	}

	public int getHeight(){
		return box.height;
	}
	
	public int getHeightRot(int rot) {
		if (rot % 2 == 0)
			return box.height;
		else
			return box.depth;
	}

	public int getDepthRot(int rot) {
		if (rot % 2 == 0)
			return box.depth;
		else
			return box.height;
	}

	public int getSpace2D(int rot) {
		return getWidth() * getHeightRot(rot);
	}

	public void setAddition(Addition addi) {
		this.addi = addi;
	}

	/**
	 * returns the maxHeight regarding the additions
	 * 
	 * @return
	 */
	public int getHeightLimitByAddi(int rot) {
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

	public Addition getAdditionRot(int rot) {
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
