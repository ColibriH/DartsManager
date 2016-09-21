package GameController.GUI;

import GameController.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by vladislavs on 07.09.2016..
 */

public class GameControlGuiForm
{
	private final GameController mGameController;

	private JFrame      mJFrame;
	private JTextField  mNewScoreTxtField;
	private JButton     mNewScoreCalculationBtn;
	private JPanel      mJPanel;
	private JPanel      mInnerJPanel;


	public GameControlGuiForm (GameController gameController)
	{
		mGameController = gameController;
		formInitialization ();
	}


	private void formInitialization ()
	{
		mJFrame = new JFrame ("GameControlGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);

		componentsModification ();
	}


	private void componentsModification ()
	{
		setMJFrameLocation ();
		addComponentsListeners ();
	}


	private void setMJFrameLocation ()
	{
		Dimension gameDisplayGuiSize = new Dimension (mGameController.getGameDisplayGuiSize ());
		Point gameDisplayGuiLocation = new Point (mGameController.getGameDisplayGuiLocation ());

		mJFrame.setLocation (gameDisplayGuiLocation.x, gameDisplayGuiLocation.y + gameDisplayGuiSize.height);
	}


	private void addComponentsListeners ()
	{
		mNewScoreCalculationBtn.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				inputHandler ();
			}
		});
		mNewScoreTxtField.addKeyListener (new KeyAdapter ()
		{
			@Override
			public void keyPressed (KeyEvent e)
			{
				if (e.getKeyCode () == KeyEvent.VK_ENTER)
					inputHandler ();
			}
		});
	}


	private void inputHandler ()
	{
		if (isInputInCorrectFormat ())
		{
			sendScoresToController ();
			mNewScoreTxtField.setText ("");
		}
		else
		{
			JOptionPane.showMessageDialog (null, "Input should be only positive digits");
		}
	}


	private void sendScoresToController ()
	{
		try
		{
			int newEarnedScores = Integer.valueOf (mNewScoreTxtField.getText ());
			mGameController.handleScoreInputFromControlGuiForm (newEarnedScores);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	private boolean isInputInCorrectFormat ()
	{
		return mNewScoreTxtField.getText ().matches ("[0-9]+");
	}


	public void destroy ()
	{
		mJFrame.setVisible (false);
		mJFrame.dispose ();
	}
}
