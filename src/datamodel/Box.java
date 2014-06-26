package datamodel;

public class Box extends Square {
	public final int depth;

	public Box(int width, int height, int depth) {
		super(width, height);
		this.depth = depth;
	}

	public Square getPlain() {
		return new Square(width, height);
	}

	public int getFreeSpacePlain() {
		// TODO Auto-generated method stub
		return 0;
	}
}
