package MatchController.Gui.Components;

import Constants.Constats;
import MatchController.Objects.PlayerObject;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class TournamentTableGroupPanel extends JPanel
{
	private JPanel  mFirstPlayerPanel;
	private JPanel  mSecondPlayerPanel;
	private JPanel  mVersusPanel;
	private JPanel  mPlayingTxtPanel;
	private JLabel  mNameLabel;
	private JLabel  mSNameLabel;
	private JLabel  mPlayingLabel;
	private JLabel  vsLabel;
	private JLabel  lArrow;
	private JLabel  rArrow;
	private int     mRow;
	private int     mColumn;
	private double  mWeightX;
	private double  mWeightY;


	public TournamentTableGroupPanel (ArrayList<PlayerObject> mPlayerList, int row, int column, double weightX, double weightY)
	{
		this (mPlayerList);

		mRow        = row;
		mColumn     = column;
		mWeightX    = weightX;
		mWeightY    = weightY;
	}


	public TournamentTableGroupPanel (int row, int column, double weightX, double weightY)
	{
		this ();

		mRow        = row;
		mColumn     = column;
		mWeightX    = weightX;
		mWeightY    = weightY;
	}


	private TournamentTableGroupPanel (ArrayList<PlayerObject> mPlayerList)
	{
		if (mPlayerList.size () == 1)
			initialization (mPlayerList.get (0).getName(), "?");
		else
			initialization (mPlayerList.get (0).getName(), mPlayerList.get (1).getName());
	}


	private TournamentTableGroupPanel ()
	{
		initialization ("?", "?");
	}


	private void initialization (String firstPlayer, String secondPlayer)
	{
		initializeComponents ();
		buildMainPanel (firstPlayer, secondPlayer);
		buildPanels ();
	}


	private void initializeComponents ()
	{
		mFirstPlayerPanel   = new JPanel ();
		mSecondPlayerPanel  = new JPanel ();
		mVersusPanel        = new JPanel ();
		mPlayingTxtPanel    = new JPanel ();
		mNameLabel          = new JLabel ();
		mSNameLabel         = new JLabel ();
		mPlayingLabel       = new JLabel ("Group to play!");
		vsLabel             = new JLabel ("VS");
		setImages ();
	}


	private void buildMainPanel (String firstPlayer, String secondPlayer)
	{
		setLayout       (new GridBagLayout ());
		setOpaque       (false);
		setBackground   (new Color (255, 255, 255, 0));
		setForeground   (Color.WHITE);

		buildPanels ();
		styleLabels ();
		stylePanels ();
		setPlayersNames (firstPlayer, secondPlayer);

		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		addComponentToPanel (this, mPlayingTxtPanel,    0, 0, new Insets (0, 2, 0, 2), 0, 0, 0, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (this, mFirstPlayerPanel,   0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (this, mVersusPanel,        0, 2, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (this, mSecondPlayerPanel,  0, 3, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void addComponentToPanel (JPanel parent, Component child, int xPos, int yPos, Insets insets, int ipady, double weightx, double weighty, int gridwidth, Integer anchor, GridBagConstraints gbc, Integer fill)
	{
		if (fill != null)
			gbc.fill    = fill;

		gbc.gridx       = xPos;
		gbc.gridy       = yPos;
		gbc.insets      = insets;
		gbc.ipady       = ipady;
		gbc.weightx     = weightx;
		gbc.weighty     = weighty;
		gbc.gridwidth   = gridwidth;

		if (anchor != null)
			gbc.anchor  = anchor;

		parent.add (child, gbc);
	}


	private void buildPanels ()
	{
		mVersusPanel        .add (lArrow);
		mPlayingTxtPanel    .add (mPlayingLabel);
		mVersusPanel        .add (vsLabel);
		mVersusPanel        .add (rArrow);
		mFirstPlayerPanel   .add (mNameLabel);
		mSecondPlayerPanel  .add (mSNameLabel);
	}


	private void styleLabels ()
	{
		vsLabel         .setHorizontalAlignment (SwingConstants.CENTER);
		mPlayingLabel   .setHorizontalAlignment (SwingConstants.CENTER);
		mNameLabel      .setOpaque (true);
		mSNameLabel     .setOpaque (false);
		mNameLabel      .setBackground (new Color(255, 255, 255, 0));
		mSNameLabel     .setBackground (new Color(255, 255, 255, 0));
		mNameLabel      .setForeground (Color.WHITE);
		mSNameLabel     .setForeground (Color.WHITE);
		vsLabel         .setForeground (Color.WHITE);
		mPlayingLabel   .setForeground (Color.WHITE);
	}


	private void setPlayersNames (String firstPlayer, String secondPlayer)
	{
		mNameLabel.setText  (firstPlayer);
		mSNameLabel.setText (secondPlayer);
	}


	private void setImages ()
	{
		Image leftImage     = ImageLoader.getImage(Constats.LEFT_DART_PIC);
		Icon leftIcon       = new ImageIcon(leftImage);
		lArrow              = new JLabel(leftIcon);
		Image rightImage    = ImageLoader.getImage(Constats.RIGHT_DART_PIC);
		Icon rightIcon      = new ImageIcon(rightImage);
		rArrow              = new JLabel(rightIcon);
	}


	private void stylePanels ()
	{
		this.setBorder (new EtchedBorder (EtchedBorder.LOWERED));

		mFirstPlayerPanel   .setOpaque (false);
		mSecondPlayerPanel  .setOpaque (false);
		mVersusPanel        .setOpaque (false);
		mPlayingTxtPanel    .setOpaque (false);
		mFirstPlayerPanel   .setBackground (new Color(255, 255, 255, 0));
		mSecondPlayerPanel  .setBackground (new Color(255, 255, 255, 0));
		mVersusPanel        .setBackground (new Color(255, 255, 255, 0));
		mPlayingTxtPanel    .setBackground (new Color(255, 255, 255, 0));
		mPlayingTxtPanel    .setBorder (new MatteBorder (0, 1, 1, 1, Color.GRAY));
		mFirstPlayerPanel   .setBorder (new MatteBorder (0, 0, 1, 0, Color.LIGHT_GRAY));
		mSecondPlayerPanel  .setBorder (new MatteBorder (1, 0, 0, 0, Color.LIGHT_GRAY));
		mPlayingTxtPanel    .setVisible (false);
	}


	public void setPlayerName (String playerName)
	{
		if (mNameLabel.getText ().equals ("?"))
			mNameLabel.setText (playerName);
		else
			mSNameLabel.setText (playerName);
	}


	public int getRow ()
	{
		return mRow;
	}


	public int getColumn ()
	{
		return mColumn;
	}


	public double getWeightX ()
	{
		return mWeightX;
	}


	public double getWeightY ()
	{
		return mWeightY;
	}


	public void setCurrentPlayingGroup (boolean visibilityState)
	{
		mPlayingTxtPanel.setVisible (visibilityState);
	}
}
