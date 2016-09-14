package GameController.GUI;

import GameController.GameController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by vladislavs on 07.09.2016..
 */

// TODO Provide input only for digits to txt field

public class GameControlGuiForm
{
	private final GameController mGameController;

	private JFrame      mJFrame;
	private JTextField  mNewScoreTxtField;
	private JButton     mNewScoreCalculationBtn;
	private JPanel      mJPanel;


	public GameControlGuiForm (GameController gameController)
	{
		mGameController = gameController;

		formInitialization ();
		mNewScoreCalculationBtn.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				sendScoresToController ();
			}
		});
		mNewScoreTxtField.addKeyListener (new KeyAdapter ()
		{
			@Override
			public void keyPressed (KeyEvent e)
			{
				if (e.getKeyCode () == KeyEvent.VK_ENTER)
				{
					sendScoresToController ();
					mNewScoreTxtField.setText ("");
				}
			}
		});
	}


	private void formInitialization ()
	{
		mJFrame = new JFrame ("GameControlGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);
	}


	private void sendScoresToController ()
	{
		// TODO Add try - catch for parsing and predict catch block
		// TODO check for negative value!!!
		int newEarnedScores = Integer.valueOf (mNewScoreTxtField.getText ());
		mGameController.calculateScore (newEarnedScores);
	}


	public void destroy ()
	{
		mJFrame.setVisible (false);
		mJFrame.dispose ();
	}
}
