package draw;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JComponent;

import datamodel.Bin;
import datamodel.Item;
import datamodel.Space;

/**
 * Draws a bin with its items an its free spaces
 * 
 * @author nrasic
 * 
 */
public class DrawBin extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Bin bin;
	int xOffset = 20;
	int yOffset = 30;

	public DrawBin(Bin bin, int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.bin = bin;
	}

	public void paint(Graphics g) {

		// g.setColor();
		g.setColor(Color.blue);
		g.fillRect(xOffset, yOffset, bin.width, bin.height);

		// items
		for (Item i : bin.getItems()) {
			//System.out.println("Item painted at: " + i.getPosi());
			g.setColor(new Color((float) 0.2 * i.order%5, 1-(float)(i.order%5)/5,
					(float) 0.1 * i.order%10));
			fillRectWithOffset(g, i.getPosi().x, i.getPosi().y, i.getWidth(),
					i.getHeight());
		}
		// draw all the free spaces
		for (Iterator<Space> iter = bin.getSpaces().descIter(); iter.hasNext();) {
			Space next = iter.next();
			g.setColor(Color.yellow);
			//System.out.println("Free Space painted at: " + next.posi);
			fillRectWithOffset(g, next.posi.x, next.posi.y, next.width,
					next.height);
		}

	}

	void fillRectWithOffset(Graphics g, int x, int y, int width, int height) {
		g.fillRect(x + xOffset, y + yOffset, width, height);
		g.setColor(Color.GRAY);
		g.drawRect(x + xOffset, y + yOffset, width, height);
	}

}
