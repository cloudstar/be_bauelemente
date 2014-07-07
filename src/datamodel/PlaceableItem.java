package datamodel;

import datamodel.Item.Addition;

public class PlaceableItem {
	public Item item;
	public Coord posi;
	public Bin bin;
	public int binIndex;
	public int rot = 0;

	public PlaceableItem(Item i, Coord p, Bin b) {
		item = i;
		posi = p;
		bin = b;
	}

	public int getSpace2D() {
		return item.getSpace2D(rot);
	}

	public int order() {
		return item.order;
	}

	public int getHeightRot() {
		return item.getHeightRot(rot);
	}

	public int getWidth() {
		return item.getWidth();
	}

	public Addition getAdditionRot() {
		return item.getAdditionRot(rot);
	}

	public int getHeightLimitByAddi() {
		return item.getHeightLimitByAddi(rot);
	}

	public int getDepthRot() {
		return item.getDepthRot(rot);
	}

	public void setBin(Bin b, int index, Coord p) {
		bin = b;
		binIndex = index;
		posi=p;
	}

	public String toString(){
		return ""+item.toString()+" At: "+posi;
	}
}
