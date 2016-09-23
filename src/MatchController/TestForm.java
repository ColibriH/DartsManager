package MatchController;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vladislavs on 23.09.2016..
 */
public class TestForm
{
	private JFrame mJFrame;
	private JPanel panel1;
	private JLabel mTest;


	public static void main (String[] args)
	{
		new TestForm ();
	}


	private TestForm ()
	{
		mJFrame = new JFrame ("GameManagerGuiForm");
		mJFrame.setContentPane (panel1);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);

		ImageIcon reel = new ImageIcon (Constats.TEST);
		JLabel label = new JLabel(reel);
		reel.setImageObserver(label);

		mJFrame.pack ();
		mJFrame.setVisible (true);


	}
}
