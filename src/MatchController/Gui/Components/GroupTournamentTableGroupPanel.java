package MatchController.Gui.Components;

import Constants.Constats;
import MatchController.Objects.GroupPlayerObject;
import MatchController.Objects.GroupTournamentMethod;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GroupTournamentTableGroupPanel extends JPanel
{
	private JPanel                  mFirstPlayerPanel;
	private JPanel                  mSecondPlayerPanel;
	private JPanel                  mVersusPanel;
	private JPanel                  mPlayingTxtPanel;
	private JLabel                  mNameLabel;
	private JLabel                  mSNameLabel;
	private JLabel                  vsLabel;
	private JLabel                  lArrow;
	private JLabel                  rArrow;
	private GroupPlayerObject       mFirstGroup;
	private GroupPlayerObject       mSecondGroup;
	private GroupTournamentMethod updateTable;
	private GroupTournamentMethod   notifyGroupPlayed;
	private boolean                 isGamePlayed = false;


	public GroupTournamentTableGroupPanel (ArrayList <GroupPlayerObject> playersGroupList, GroupTournamentMethod proceedWinnerAndLoserGroupMethod)
	{
		initialization (playersGroupList.get (0), playersGroupList.get (1), proceedWinnerAndLoserGroupMethod);
	}


	private void initialization (GroupPlayerObject firstGroup, GroupPlayerObject secondGroup, GroupTournamentMethod proceedWinnerAndLoserGroupMethod)
	{
		initializeComponents ();

		mFirstGroup     = firstGroup;
		mSecondGroup    = secondGroup;

		buildMainPanel ();
		buildPanels ();

		addComponentsListeners (proceedWinnerAndLoserGroupMethod);
	}


	private void initializeComponents ()
	{
		mFirstPlayerPanel   = new JPanel ();
		mSecondPlayerPanel  = new JPanel ();
		mVersusPanel        = new JPanel ();
		mPlayingTxtPanel    = new JPanel ();
		mNameLabel          = new JLabel ();
		mSNameLabel         = new JLabel ();
		vsLabel             = new JLabel ("VS");
		setImages ();
	}


	private void addComponentsListeners (GroupTournamentMethod proceedWinnerAndLoserGroupMethod)
	{
		mFirstPlayerPanel.addMouseListener (new MouseAdapter ()
		{

			@Override
			public void mouseEntered (MouseEvent e)
			{
				mouseEnterAction (mFirstPlayerPanel);
			}


			@Override
			public void mouseExited (MouseEvent e)
			{
				System.out.println ("exit");
				mouseExitAction (mFirstPlayerPanel);
			}
		});

		mFirstPlayerPanel.addMouseListener (new MouseAdapter ()
		{
			@Override
			public void mouseClicked (MouseEvent e)
			{
				clickedOnPlayersGroupPanel (proceedWinnerAndLoserGroupMethod, mFirstPlayerPanel, mSecondGroup,  mFirstGroup);
			}
		});

		mSecondPlayerPanel.addMouseListener (new MouseAdapter ()
		{
			@Override
			public void mouseClicked (MouseEvent e)
			{
				clickedOnPlayersGroupPanel (proceedWinnerAndLoserGroupMethod, mSecondPlayerPanel, mFirstGroup , mSecondGroup);
			}
		});
	}


	private void mouseEnterAction (JPanel panel)
	{
		//panel.setBackground (Color.LIGHT_GRAY);
	}


	private void mouseExitAction (JPanel panel)
	{
		//panel.setBackground (new Color (255, 255, 255, 0));
	}


	private void clickedOnPlayersGroupPanel (GroupTournamentMethod proceedWinnerAndLoserGroupMethod, JPanel winnerGroupPanel,  GroupPlayerObject looserGroup,  GroupPlayerObject winnerGroup)
	{
		if (! isGamePlayed)
		{
			isGamePlayed = true;
			changeWinnerPanelStyle (winnerGroupPanel);
			proceedWinnerAndLoserGroupMethod.execute (looserGroup, winnerGroup);
			updateTable.execute ();
			notifyGroupPlayed.execute ();
		}
	}


	private void changeWinnerPanelStyle (JPanel mFirstPlayerPanel)
	{
		mFirstPlayerPanel.setOpaque (true);
		mFirstPlayerPanel.setBackground (Color.LIGHT_GRAY);
	}


	private void buildMainPanel ()
	{
		setLayout (new GridBagLayout ());
		setOpaque (false);
		setBackground (new Color (255, 255, 255, 0));
		setForeground (Color.WHITE);

		buildPanels ();
		styleLabels ();
		stylePanels ();
		setPlayersNames ();

		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		addComponentToPanel (this, mPlayingTxtPanel,    0, 0, new Insets (0, 2, 0, 2), 0, 0, 0, 1, GridBagConstraints.CENTER,       mPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (this, mFirstPlayerPanel,   0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER,       mPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (this, mVersusPanel,        0, 2, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER,       mPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (this, mSecondPlayerPanel,  0, 3, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER,       mPanelGbc, GridBagConstraints.HORIZONTAL);
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
		mVersusPanel        .add (vsLabel);
		mVersusPanel        .add (rArrow);
		mFirstPlayerPanel   .add (mNameLabel);
		mSecondPlayerPanel  .add (mSNameLabel);
	}


	private void setPlayersNames ()
	{
		mNameLabel.setText  (mFirstGroup.getFirstPlayer ().getName () + " - " + mFirstGroup.getSecondPlayer ().getName ());
		mSNameLabel.setText (mSecondGroup.getFirstPlayer ().getName () + " - " + mSecondGroup.getSecondPlayer ().getName ());
	}


	private void styleLabels ()
	{
		vsLabel         .setHorizontalAlignment (SwingConstants.CENTER);
		mNameLabel      .setOpaque (true);
		mSNameLabel     .setOpaque (false);
		mNameLabel      .setBackground (new Color(255, 255, 255, 0));
		mSNameLabel     .setBackground (new Color(255, 255, 255, 0));
		mNameLabel      .setForeground (Color.WHITE);
		mSNameLabel     .setForeground (Color.WHITE);
		vsLabel         .setForeground (Color.WHITE);
		mNameLabel      .setFont (new Font ("Eraser Regular", Font.TRUETYPE_FONT, 12));
		mSNameLabel     .setFont (new Font ("Eraser Regular", Font.TRUETYPE_FONT, 12));
		vsLabel         .setFont (new Font ("Eraser Regular", Font.TRUETYPE_FONT, 12));
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

		mPlayingTxtPanel.setVisible (false);
	}


	public void setTableUpdateMethod (GroupTournamentMethod tableUpdateMethod)
	{
		updateTable = tableUpdateMethod;
	}


	public void setTableNotifyGroupPlayedMethod (GroupTournamentMethod tableNotifyGroupPlayedMethod)
	{
		notifyGroupPlayed = tableNotifyGroupPlayedMethod;
	}
}
