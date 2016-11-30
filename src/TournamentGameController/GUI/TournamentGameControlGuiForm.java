package TournamentGameController.GUI;

import TournamentGameController.TournamentGameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TournamentGameControlGuiForm
{
	private final TournamentGameController mTournamentGameController;

	private JFrame      mJFrame;
	private JPanel      mJPanel;
	private JPanel      mInnerJPanel;
	private JTextField  mNewScoreTxtField;
	private JButton     mNewScoreCalculationBtn;


	public TournamentGameControlGuiForm (TournamentGameController tournamentGameController)
	{
		mTournamentGameController = tournamentGameController;
		initializeForm ();
	}


	private void initializeForm ()
	{
		mJFrame = new JFrame ("GameControlGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);

		modifyComponents ();
	}


	private void modifyComponents ()
	{
		setMJFrameLocation ();
		addComponentsListeners ();
	}


	private void setMJFrameLocation ()
	{
		Dimension gameDisplayGuiSize = new Dimension (mTournamentGameController.getGameDisplayGuiSize ());
		Point gameDisplayGuiLocation = new Point (mTournamentGameController.getGameDisplayGuiLocation ());

		mJFrame.setLocation (gameDisplayGuiLocation.x, gameDisplayGuiLocation.y + gameDisplayGuiSize.height);
	}


	private void addComponentsListeners ()
	{
		mNewScoreCalculationBtn.addActionListener (e -> handleInput ());
		mNewScoreTxtField.addKeyListener (new KeyAdapter ()
		{
			@Override
			public void keyPressed (KeyEvent e)
			{
				if (e.getKeyCode () == KeyEvent.VK_ENTER)
					handleInput ();
			}
		});
	}


	private void handleInput ()
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
			mTournamentGameController.handleScoreInputFromControlGuiForm (newEarnedScores);
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
