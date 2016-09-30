package MenuGui;

import MatchController.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// TODO Add pressed pic

/**
 * Created by vladislavs on 30.09.2016..
 */
public class MenuButton extends JButton
{
	private int mWidth;
	private int mHeight;
	private int mFontSize;

	public MenuButton (int width, int height)
	{
		mWidth  = width;
		mHeight = height;

		initialize ();
	}

	public MenuButton (String txt, int width, int height)
	{
		mWidth  = width;
		mHeight = height;

		setText (txt);
		initialize ();
	}


	private void initialize ()
	{
		setPreferredSize (new Dimension (mWidth, mHeight));
		setFont (new Font ("Eraser Regular", Font.TRUETYPE_FONT, 12));

		setBackground (new Color (255, 255, 0, 0));
		setForeground (Color.WHITE);

		setHorizontalTextPosition (SwingConstants.CENTER);
		setOpaque (false);

		setBorder (new EmptyBorder (0, 0, 0, 0));
		setBorderPainted (false);

		setContentAreaFilled (false);
		setFocusPainted (false);

		setIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_TEXTURE_PIC)));
		setRolloverIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_FILLER_PIC)));

		initializeListeners ();
	}

	// TODO Setter getter
	private void initializeListeners ()
	{
		addMouseListener (new MouseAdapter ()
		{
			@Override
			public void mouseEntered (MouseEvent e)
			{
				setForeground (Color.BLACK);
			}


			@Override
			public void mouseExited (MouseEvent e)
			{
				setForeground (Color.WHITE);
			}
		});
	}


	public int getFontSize ()
	{
		return mFontSize;
	}


	public void setFontSize (int mFontSize)
	{
		this.mFontSize = mFontSize;
	}
}
