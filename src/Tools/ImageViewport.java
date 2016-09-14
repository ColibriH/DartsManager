package Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by vladislavs on 12.09.2016..
 */
public class ImageViewport extends JViewport
{
	private BufferedImage mBackGroundImage;


	public ImageViewport(BufferedImage backGroundImage)
	{
		mBackGroundImage = backGroundImage;
	}


	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (mBackGroundImage != null)
		{
			Rectangle bounds = getViewRect();
			int x = Math.max(0, (bounds.width - mBackGroundImage.getWidth()) / 2);
			int y = Math.max(0, (bounds.height - mBackGroundImage.getHeight()) / 2);
			g.drawImage(mBackGroundImage, x, y, this);
		}
	}
}