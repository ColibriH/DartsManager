package GuiComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislavs on 30.09.2016..
 */
public class ImagedPanel extends JPanel
{
	private	Image mBg;


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
