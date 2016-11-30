package MatchController.Gui.TournamentWinnerFrame;

import BaseAbstractClasses.DartsGuiFormBase;
import Constants.Constats;
import MatchController.MatchController;
import MatchController.Objects.PlayerObject;
import Tools.ImageLoader;

import javax.swing.*;
import java.awt.*;

abstract class TournamentWinnerFrameGui extends DartsGuiFormBase
{
	protected abstract void menuButtonAction ();
	protected abstract void exitButtonAction ();

	private JPanel          mWinnerPanel;
	private JPanel          mControlPanel;
	private JButton         mMenuButton;
	private JButton         mExitButton;
	private JLabel          mWinnerName;
	private PlayerObject    mWinner;


	TournamentWinnerFrameGui (MatchController matchController, PlayerObject winner)
	{
		super (matchController);
		mWinner = winner;
		mWinnerName.setText (mWinner.getName ());
	}


	@Override
	protected void initializeComponents ()
	{
		mWinnerPanel        = new JPanel ();
		mControlPanel       = new JPanel ();
		mMenuButton         = new JButton ("Menu");
		mExitButton         = new JButton ("Exit");
		mWinnerName         = new JLabel ();
	}


	@Override
	protected void addComponentsListener ()
	{
		mMenuButton.addActionListener (e -> menuButtonAction ());
		mExitButton.addActionListener (e -> exitButtonAction ());
	}


	@Override
	protected void buildMainPanel ()
	{
		getMainJPanel ().setLayout (new GridBagLayout ());

		imageInitialization ();
		buildWinnerPanel ();
		buildControlPanel ();

		GridBagConstraints gbc = new GridBagConstraints ();
		addComponentToPanel (getMainJPanel (), mWinnerPanel,  0, 0, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
		addComponentToPanel (getMainJPanel (), mControlPanel, 0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
	}


	private void buildControlPanel ()
	{
		mControlPanel.setLayout (new GridBagLayout ());
		GridBagConstraints gbc = new GridBagConstraints ();
		addComponentToPanel (mControlPanel, mMenuButton, 1, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (mControlPanel, mExitButton, 0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.HORIZONTAL);
	}


	private void buildWinnerPanel ()
	{
		mWinnerPanel.setLayout (new GridBagLayout ());
		GridBagConstraints gbc = new GridBagConstraints ();
		addComponentToPanel (mWinnerPanel, mWinnerName, 0, 0, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
	}


	private void imageInitialization ()
	{
		mWinnerName.setIcon                     (new ImageIcon (ImageLoader.getImage (Constats.WINNER_PIC)));
		mWinnerName.setVerticalAlignment        (SwingConstants.CENTER);
		mWinnerName.setHorizontalAlignment      (SwingConstants.CENTER);
		mWinnerName.setVerticalTextPosition     (SwingConstants.CENTER);
		mWinnerName.setHorizontalTextPosition   (SwingConstants.CENTER);
	}
}
