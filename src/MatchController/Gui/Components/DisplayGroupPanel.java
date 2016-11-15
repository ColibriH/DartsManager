package MatchController.Gui.Components;

import Constants.Constats;
import MatchController.Objects.PlayerObject;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by vladislavs on 13.09.2016..
 */

// TODO set better style
// TODO set preferred size to mPanel
// TODO refactor

public class DisplayGroupPanel extends JPanel
{
	private JPanel mFirstPlayerPanel;
	private JPanel mSecondPlayerPanel;
	private JPanel mVersusPanel;
	private JPanel mPlayingTxtPanel;

	private JLabel mNameLabel;
	private JLabel mSNameLabel;

	private JLabel mPlayingLabel;
	private JLabel vsLabel;
	private JLabel lArrow;
	private JLabel rArrow;

	private int mRow;
	private int mColumn;
	private double mWeightX;
	private double mWeightY;


	public DisplayGroupPanel (ArrayList<PlayerObject> mPlayerList)
	{
		initialization (mPlayerList.get (0).mName, mPlayerList.get (1).mName);
	}


	public DisplayGroupPanel (ArrayList<PlayerObject> mPlayerList, int row, int column, double weightX, double weightY)
	{
		this (mPlayerList);

		mRow        = row;
		mColumn     = column;
		mWeightX    = weightX;
		mWeightY    = weightY;
	}


	public DisplayGroupPanel ()
	{
		initialization ("?", "?");
	}


	public DisplayGroupPanel (int row, int column, double weightX, double weightY)
	{
		this ();

		mRow        = row;
		mColumn     = column;
		mWeightX    = weightX;
		mWeightY    = weightY;
	}


	private void initialization (String fPlayer, String sPlayer)
	{
		initializeComponents ();
		buildMainPanel (fPlayer, sPlayer);
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


	private void buildMainPanel (String fPlayer, String sPlayer)
	{
		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		this.setLayout (new GridBagLayout ());
		this.setPreferredSize (new Dimension (100, 150));
		this.setBackground (new Color (255, 255, 255, 0));
		this.setOpaque (false);
		this.setForeground (Color.WHITE);

		buildPanels ();
		styleLabels ();
		stylePanels ();
		setPlayersNames (fPlayer, sPlayer);

		addComponentToPanel (this, mPlayingTxtPanel,    0, 0, new Insets (0, 2, 0, 2), 0, 0, 0, 1, GridBagConstraints.CENTER,       mPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (this, mFirstPlayerPanel,   0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER,       mPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (this, mVersusPanel,        0, 2, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER,       mPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (this, mSecondPlayerPanel,  0, 3, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER,       mPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void addComponentToPanel (JPanel parent, Component child, int xPos, int yPos, Insets insets, int ipady,
	                                  double weightx, double weighty, int gridwidth, Integer anchor, GridBagConstraints gbc, Integer fill)
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


	private void setPlayersNames (String fPlayer, String sPlayer)
	{
		mNameLabel.setText (fPlayer);
		mSNameLabel.setText (sPlayer);
	}


	public void setPlayerName (String playerName)
	{
		if (mNameLabel.getText ().equals ("?"))
			mNameLabel.setText (playerName);
		else
			mSNameLabel.setText (playerName);
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


	private void setImages ()
	{
		Image leftImage = ImageLoader.getImage(Constats.LEFT_DART_PIC);
		Icon leftIcon = new ImageIcon(leftImage);
		lArrow = new JLabel(leftIcon);

		Image rightImage = ImageLoader.getImage(Constats.RIGHT_DART_PIC);
		Icon rightIcon = new ImageIcon(rightImage);
		rArrow = new JLabel(rightIcon);
	}


//	private static Color hex2Rgb (String colorStr)
//	{
//		// TODO Check colorStr pattern.
//		return new Color (
//				Integer.valueOf (colorStr.substring (1, 3), 16),
//				Integer.valueOf (colorStr.substring (3, 5), 16),
//				Integer.valueOf (colorStr.substring (5, 7), 16)
//		);
//	}

//
//	@Override
//	protected void paintComponent (Graphics g)
//	{
//		super.paintComponent (g);
//		Graphics2D g2d = (Graphics2D) g;
//
//		int width = getWidth ();
//		int height = getHeight ();
//
//		Color lowerColor = hex2Rgb ("#e5ffd4");
//		Color upperColor = hex2Rgb ("#d4ffda");
//
//		GradientPaint gp = new GradientPaint (3, 4, lowerColor, 3, height, upperColor);
//
//		g2d.setRenderingHint (RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//		g2d.setPaint (gp);
//		g2d.fillRect (0, 0, width, height);
//	}


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


		mPlayingTxtPanel.setVisible (false);
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
