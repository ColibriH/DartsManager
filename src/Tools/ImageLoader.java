package Tools;

import java.awt.*;
import java.io.*;


/**
 * Created by vladislavs on 14.09.2016..
 */
public class ImageLoader
{
	private ImageLoader ()
	{
	}


	// TODO REFACTOR
	public static Image getImage (String filePath)
	{
		Image returnValue = null;
		InputStream is = null;

		try
		{
			is = new FileInputStream (filePath);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace ();
		}

		if (is != null)
		{
			BufferedInputStream bis = new BufferedInputStream (is);
			ByteArrayOutputStream baos = new ByteArrayOutputStream ();
			try
			{
				int ch = bis.read ();

				while (ch != - 1)
				{
					baos.write (ch);
					ch = bis.read ();
				}

				returnValue = Toolkit.getDefaultToolkit ().createImage (baos.toByteArray ());
			}
			catch (IOException exception)
			{
				System.err.println ("Error loading: " + filePath);
			}
		}

		return returnValue;
	}
}
