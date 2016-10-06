package MatchController.GUI;

import MatchController.Constats;
import Tools.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

/**
 * Created by vladislavs on 12.09.2016..
 */

// TODO TABLE BTn Style

public class ButtonRenderer extends JButton implements TableCellRenderer
{

	public ButtonRenderer ()
	{
		setOpaque (true);
	}


	public Component getTableCellRendererComponent (JTable table, Object value,
	                                                boolean isSelected, boolean hasFocus, int row, int column)
	{
		String pathToImage = Constats.DELETE_PIC;

		if (value.equals (Constats.EDIT_BTN_ID))
			pathToImage = Constats.EDIT_PIC;

		setIcon (new ImageIcon (ImageLoader.getImage (pathToImage)));
		setBorderPainted(false);
		setBackground(new Color(255, 255, 255, 0));
		setName (value.toString ());

		return this;
	}
}