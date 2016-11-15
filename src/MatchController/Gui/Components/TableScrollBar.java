package MatchController.Gui.Components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * Created by vladislavs on 06.10.2016..
 */
public class TableScrollBar extends BasicScrollBarUI
{
	private final Dimension mDimension = new Dimension ();


	@Override
	protected JButton createDecreaseButton (int orientation)
	{
		return new JButton ()
		{
			@Override
			public Dimension getPreferredSize ()
			{
				return mDimension;
			}
		};
	}


	@Override
	protected JButton createIncreaseButton (int orientation)
	{
		return new JButton ()
		{
			@Override
			public Dimension getPreferredSize ()
			{
				return mDimension;
			}
		};
	}


	@Override
	protected void paintTrack (Graphics g, JComponent c, Rectangle r)
	{
		//SBE
	}


	@Override
	protected void paintThumb (Graphics g, JComponent c, Rectangle r)
	{
		JScrollBar sb = (JScrollBar) c;
		Graphics2D g2 = (Graphics2D) g.create ();

		g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Color color;
		if (! sb.isEnabled () || r.width > r.height)
			return;
		else if (isDragging)
			color = Color.LIGHT_GRAY;
		else if (isThumbRollover ())
			color = Color.LIGHT_GRAY;
		else
			color = Color.DARK_GRAY;

		g2.setPaint (color);
		g2.fillRoundRect (r.x, r.y, r.width, r.height, 12, 12);
		g2.setPaint (Color.WHITE);
		g2.dispose ();
	}


	@Override
	protected void setThumbBounds (int x, int y, int width, int height)
	{
		super.setThumbBounds (x, y, width, height);
		scrollbar.repaint ();
	}
}