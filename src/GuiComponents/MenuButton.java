package GuiComponents;

import Constants.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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


	public MenuButton (String text)
	{
		setText (text);
		initialize ();
	}


	private void initialize ()
	{
		setFont                     (new Font ("Eraser Regular", Font.TRUETYPE_FONT, 12));
		setIcon                     (new ImageIcon (ImageLoader.getImage (Constats.BTN_TEXTURE_PIC)));
		setOpaque                   (false);
		setBorder                   (new EmptyBorder (0, 0, 0, 0));
		setBackground               (new Color (255, 255, 0, 0));
		setForeground               (Color.WHITE);
		setPreferredSize            (new Dimension (mWidth, mHeight));
		setBorderPainted            (false);
		setFocusPainted             (false);
		setRolloverIcon             (new ImageIcon (ImageLoader.getImage (Constats.BTN_FILLER_PIC)));
		setContentAreaFilled        (false);
		setHorizontalTextPosition   (SwingConstants.CENTER);

		initializeListeners ();
	}


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


	public void setFontSize (int fontSize)
	{
		mFontSize = fontSize;
	}
}
