package MatchController.GUI;

import MatchController.Objects.PlayerObject;
import MatchController.MatchController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vladislavs on 09.09.2016..
 */
public class WinnerGuiForm
{
	private JFrame mJFrame;
	private JButton playAgainButton;
	private JLabel mWinnerNameLabel;
	private JPanel mJPanel;
	private PlayerObject mWinner;
	private MatchController mMatchController;


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
	}


	private void componentsModification ()
	{
		mWinnerNameLabel.setText (mWinner.mName + " is the WINNER!");

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
