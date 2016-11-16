package Tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageLoader
{
	public static Image getImage (String filePath)
	{
		InputStream inputStream = openFile (filePath);
		Image img = new BufferedImage (10,10, BufferedImage.TYPE_BYTE_GRAY);

		if (inputStream == null)
			return img;

		img = getImageFormStream (inputStream);

		return img;
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
