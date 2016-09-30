package MatchController.GUI;

import MatchController.Objects.PlayerObject;
import MatchController.MatchController;
import MatchController.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vladislavs on 09.09.2016..
 */

//TODO Make better style

public class WinnerGuiForm
{
	private MatchController mMatchController;

	private JFrame          mJFrame;
	private JButton         playAgainButton;
	private JLabel          mWinnerNameLabel;
	private JPanel          mJPanel;
	private JButton         exitButton;
	private JLabel          mWinnerName;
	private JPanel          mWinnerPanel;
	private JPanel          mCntrBtnPanel;
	private PlayerObject    mWinner;


	public WinnerGuiForm (MatchController matchController, PlayerObject winner)
	{
		mWinner = winner;
		mMatchController = matchController;

		formInitialization ();
		componentsModification ();
	}


	private void formInitialization ()
	{
		mJFrame = new JFrame ("WinnerGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);

		setMJFrameLocation ();
		imageInitialization ();
	}


	private void imageInitialization ()
	{
		mWinnerName.setIcon (new ImageIcon (ImageLoader.getImage (Constats.WINNER_PIC)));
		mWinnerName.setText (mWinner.mName);
	}


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void componentsModification ()
	{
		playAgainButton.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				mMatchController.newMatch ();
				mJFrame.setVisible (false);
				mJFrame.dispose ();
			}
		});
	}
}
