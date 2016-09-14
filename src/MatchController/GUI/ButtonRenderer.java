package MatchController.GUI;

import MatchController.Constats;

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
		String pathToImage = System.getProperty ("user.dir") + File.separator + Constats.PIC_FOLDER_PATH +
				File.separator + Constats.DELETE_PIC;

		if (value.equals (Constats.EDIT_BTN_ID))
			pathToImage = System.getProperty ("user.dir") + File.separator + Constats.PIC_FOLDER_PATH +
					File.separator + Constats.EDIT_PIC;

		try
		{
			getImage(new ImageIcon (ImageIO.read (new File (pathToImage))));
			setIcon (new ImageIcon (ImageIO.read (new File (pathToImage))));
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}

		setBorderPainted(false);
		setBackground(new Color(255, 255, 255, 158));


		setName (value.toString ());
		return this;
	}

	private RenderedImage getImage (ImageIcon read) {

		int width = 19;
		int height = 19;

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = bufferedImage.createGraphics();

		g2d.setComposite(AlphaComposite.Clear);
		g2d.fillRect(0, 0, 19, 19);
		g2d.setComposite(AlphaComposite.Src);
		g2d.dispose();

		return bufferedImage;
	}
}