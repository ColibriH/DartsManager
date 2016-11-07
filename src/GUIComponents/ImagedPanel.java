package GUIComponents;

import Constants.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislavs on 30.09.2016..
 */
public class ImagedPanel extends JPanel
{
	private	Image mBg;

	// TODO remove this constructor
	public ImagedPanel ()
	{
		mBg = ImageLoader.getImage (Constats.MENU_BG_PIC);
	}


	public ImagedPanel (Image bg)
	{
		mBg = bg;
	}


	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(mBg, 0, 0, mBg.getWidth(null), mBg.getHeight(null), this);
	}
}
