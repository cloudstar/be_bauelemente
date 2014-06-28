package draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
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
		this.setPreferredSize(new Dimension(bin.getWidth() + yOffset, bin.getHeight()
				+ xOffset));
		this.setToolTipText("");
	}

	public void paint(Graphics g) {

		// g.setColor();
		g.setColor(Color.blue);
		g.fillRect(xOffset, yOffset, bin.getWidth(), bin.getHeight());

		paintLabel(g, "Bin# " + bin.index, xOffset, yOffset - 2);
		// items
		for (Item i : bin.getItems()) {
			// System.out.println("Item painted at: " + i.getPosi());
			g.setColor(new Color((float) 0.2 * i.order % 5,
					1 - (float) (i.order % 5) / 5, (float) 0.1 * i.order % 10));
			fillRectWithOffset(g, i.getPosi().x, i.getPosi().y, i.getWidth(),
					i.getHeightRot());
		}
		// draw all the free spaces

		for (Iterator<Space> iter = bin.getSpaces().descIter(); iter.hasNext();) {
			Space next = iter.next();
			// System.out.println("Free Space painted at: " + next.posi);
			g.setColor(Color.yellow);
			fillRectWithOffset(g, next.posi.x, next.posi.y, next.getWidth(),
					next.getHeight());
		}
	}

	void paintLabel(Graphics g, String str, int x, int y) {
		Font f = new Font("Dialog", Font.BOLD, 15);
		g.setFont(f);
		g.setColor(Color.black);
		g.drawString(str, x, y);
	}

	void fillRectWithOffset(Graphics g, int x, int y, int width, int height) {
		g.fillRect(x + xOffset, y + yOffset, width, height);
		g.setColor(Color.GRAY);
		g.drawRect(x + xOffset, y + yOffset, width, height);
	}

	@Override
	public String getToolTipText(MouseEvent e) {
		return toolTip(e.getPoint());
	}

	String toolTip(Point p){
		String st = null;
		for(Item i:bin.getItems()){
			if(		p.x>=i.getPosi().x+xOffset &&
					p.x<=i.getPosi().x+i.getWidth()+xOffset &&
					p.y>=i.getPosi().y+yOffset &&
					p.y<=i.getPosi().y+i.getHeightRot()+yOffset){
				st=i.toString();
				if(i.getAdditionRot()!=null){
					st=st+""+" with Addition in height: "+i.getHeightLimitByAddi();
				}
						break;
			}
		}
		return st;
	}
}
