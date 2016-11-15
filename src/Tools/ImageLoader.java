package Tools;

import java.awt.*;
import java.io.*;

/**
 * Created by vladislavs on 14.09.2016..
 */
public class ImageLoader
{
	public static Image getImage (String filePath)
	{
		InputStream inputStream = openFile (filePath);

		if (inputStream == null)
			return null;

		return getImageFormStream (inputStream);
	}


	private static Image getImageFormStream (InputStream inputStream)
	{
		BufferedInputStream bufferedInputStream = new BufferedInputStream (inputStream);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();

		try
		{
			int ch = bufferedInputStream.read ();

			while (ch != - 1)
			{
				byteArrayOutputStream.write (ch);
				ch = bufferedInputStream.read ();
			}

			return Toolkit.getDefaultToolkit ().createImage (byteArrayOutputStream.toByteArray ());
		}
		catch (IOException exception)
		{
			System.err.println ("Error loading image from stream.");
		}

		return null;
	}


	private static FileInputStream openFile (String filePath)
	{
		try
		{
			return new FileInputStream (filePath);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace ();
		}

		return null;
	}
}
