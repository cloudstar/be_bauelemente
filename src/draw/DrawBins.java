package draw;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import datamodel.Bin;
import datamodel.Item;
import datamodel.Space;

public class DrawBins extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Bin> bins;
	int xOffset = 20;
	int yOffset = 20;

	public DrawBins(ArrayList<Bin> bins, int xOffset, int yOffset) {
		this.bins = bins;
		this.xOffset = xOffset;
		this.yOffset = yOffset;

		if (bins != null && bins.size() != 0) {
			setLayout(new GridLayout((int) Math.floor(bins.size() / 3), 3));
			int xoffI = xOffset;
			int yoffI = yOffset;
			for (Bin bin : bins) {
				this.add(new DrawBin(bin, xoffI, yoffI));
			}
		}
	}
}
