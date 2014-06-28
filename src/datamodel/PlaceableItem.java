package datamodel;

public class PlaceableItem {
	public Item item;
	public Coord posi;
	public Bin bin;

	public PlaceableItem(Item i,Coord p,Bin b) {
	item = i;
	posi = p;
	bin = b;
	}
}
