package Tools;

import Constants.Constats;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader
{
	private static final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();


	public static void loadFont ()
	{
		try
		{
			ge.registerFont (Font.createFont (Font.TRUETYPE_FONT, new File (Constats.CHALK_FONT1)));
			ge.registerFont (Font.createFont (Font.TRUETYPE_FONT, new File (Constats.CHALK_FONT2)));
			ge.registerFont (Font.createFont (Font.TRUETYPE_FONT, new File (Constats.SCRATHC_FONT)));
		}
		catch (IOException | FontFormatException e)
		{
			System.out.println (e);
		}
	}
}
