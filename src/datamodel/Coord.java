package datamodel;
/**
 * Describes the coordinates in the bin
 * 
 * @author nrasic
 *
 */
public class Coord {
	public final int x;
	public final int y;
	public Coord(int x, int y) {
		this.x=x;
		this.y=y;
	}
	public String toString(){
		return "Coor at X: "+x+" Y: "+y;
	}
}
