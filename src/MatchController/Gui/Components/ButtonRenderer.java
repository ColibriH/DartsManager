package MatchController.Gui.Components;

import Constants.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer
{

	public ButtonRenderer ()
	{
		setOpaque (true);
	}


	public Component getTableCellRendererComponent (JTable table, Object value,
	                                                boolean isSelected, boolean hasFocus, int row, int column)
	{
		String pathToImage = Constats.TABLE_DELETE_PIC;

		if (value.equals (Constats.EDIT_BTN_ID))
			pathToImage = Constats.TABLE_EDIT_PIC;

		setIcon (new ImageIcon (ImageLoader.getImage (pathToImage)));
		setPressedIcon (new ImageIcon (ImageLoader.getImage (pathToImage)));
		setName (value.toString ());

		return this;
	}
}