package Tools;

import MatchController.Constats;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by vladislavs on 29.09.2016..
 */
public class FontLoader
{
	public static final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();


	public static void loadFont ()
	{
		try
		{
			ge.registerFont (Font.createFont (Font.TRUETYPE_FONT, new File (Constats.CHALK_FONT1)));
			ge.registerFont (Font.createFont (Font.TRUETYPE_FONT, new File (Constats.CHALK_FONT2)));
		}
		catch (IOException | FontFormatException e)
		{
			System.out.println (e);
		}
	}
}
