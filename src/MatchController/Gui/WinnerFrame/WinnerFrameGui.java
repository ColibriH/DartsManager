package MatchController.Gui.WinnerFrame;

import BaseAbstractClasses.DartsGuiFormBase;
import Constants.Constats;
import MatchController.MatchController;
import MatchController.Objects.PlayerObject;
import Tools.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislavs on 14.11.2016..
 */
abstract class WinnerFrameGui extends DartsGuiFormBase
{
	protected abstract void menuButtonAction ();
	protected abstract void exitButtonAction ();

	private JPanel          mWinnerPanel;
	private JPanel          mControlPanel;

	private JButton         mMenuButton;
	private JButton         mExitButton;

	private JLabel          mWinnerName;

	private PlayerObject    mWinner;


	WinnerFrameGui (MatchController matchController, PlayerObject winner)
	{
		super (matchController);
		mWinner = winner;
		mWinnerName.setText (mWinner.mName);
	}


	@Override
	protected void initializeComponents ()
	{
		mWinnerPanel        = new JPanel ();
		mControlPanel       = new JPanel ();
		mMenuButton         = new JButton ("MainController/Menu");
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
		GridBagConstraints gbc = new GridBagConstraints ();
		getMainJPanel ().setLayout (new GridBagLayout ());

		imageInitialization ();
		buildWinnerPanel ();
		buildControlPanel ();

		addComponentToPanel (getMainJPanel (), mWinnerPanel,  0, 0, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
		addComponentToPanel (getMainJPanel (), mControlPanel, 0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
	}


	private void buildControlPanel ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();
		mControlPanel.setLayout (new GridBagLayout ());

		addComponentToPanel (mControlPanel, mMenuButton, 1, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (mControlPanel, mExitButton, 0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.HORIZONTAL);
	}


	private void buildWinnerPanel ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();
		mWinnerPanel.setLayout (new GridBagLayout ());

		addComponentToPanel (mWinnerPanel, mWinnerName, 0, 0, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
	}


	private void imageInitialization ()
	{
		mWinnerName.setVerticalAlignment      (SwingConstants.CENTER);
		mWinnerName.setVerticalTextPosition   (SwingConstants.CENTER);
		mWinnerName.setHorizontalAlignment    (SwingConstants.CENTER);
		mWinnerName.setHorizontalTextPosition (SwingConstants.CENTER);

		mWinnerName.setIcon (new ImageIcon (ImageLoader.getImage (Constats.WINNER_PIC)));
	}
}
