package MatchController.GUI;

import MatchController.MatchController;
import MatchController.Objects.PlayerObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladislavs on 06.09.2016..
 */
public class PlayerGeneratedGroupsGuiForm
{
	private final MatchController                       mMatchController;

	private JFrame                                      mJFrame;
	private JButton                                     mGameStartBtn;
	private JPanel                                      mGroupsPanel;
	private JScrollPane                                 mGroupsScrollPane;
	private JPanel                                      mJPanel;
	private JButton                                     mNextStageBtn;
	private JButton backButton;

	private HashMap <Integer, ArrayList <Integer>>      mPlayerGroupsMap;
	private ArrayList <PlayerObject>                    mPlayerList;
	private Integer                                     mCurrentPlayingGroupNumber;


	public PlayerGeneratedGroupsGuiForm (MatchController matchController, ArrayList<PlayerObject> playerList,
	                                     HashMap <Integer, ArrayList <Integer>> playerGroupsMap, Integer currentPlayingGroupNumber)
	{
		mCurrentPlayingGroupNumber  = currentPlayingGroupNumber;
		mMatchController            = matchController;
		mPlayerList                 = playerList;
		mPlayerGroupsMap            = new HashMap <> (playerGroupsMap);

		formInitialization      ();
		componentsModification  ();
		addGroupsToPanelAndUpdateFrame ();
	}


	private void formInitialization ()
	{
		mGroupsPanel.setLayout (new GridBagLayout ());
		mJFrame = new JFrame ("PlayerGeneratedGroupsGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);
		mJFrame.setResizable (false);

		setMJFrameLocation ();
	}


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void addGroupsToPanelAndUpdateFrame ()
	{
		addGroupsToMainPanelAndLinkGroupWithPlayer ();
		setCurrentPlayingGroup ();
		updateForm ();
	}


	private void setCurrentPlayingGroup ()
	{
		mCurrentPlayingGroupNumber = mMatchController.getCurrentPlayingGroupNumber ();
		hideCurrentPlayingGroupPanelForAllGroups ();
		setCurrentPlayingGroup (mPlayerGroupsMap.get (mCurrentPlayingGroupNumber).get (0), true);
	}


	private void setCurrentPlayingGroup (Integer firstPlayerId, boolean state)
	{
		try
		{
			mMatchController.getPlayerObjectById (firstPlayerId).getDisplayGroupPanel ().setCurretPlayingGroup (state);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}


	private void hideCurrentPlayingGroupPanelForAllGroups ()
	{
		for (Object o : mPlayerGroupsMap.entrySet ())
		{
			Map.Entry pair = (Map.Entry) o;
			setCurrentPlayingGroup (((ArrayList <Integer>) pair.getValue ()).get (0), false);
		}
	}


	private void addComponentToPanel (JPanel parent, Component child, int xPos, int yPos, Insets insets, int ipady,
	                                  double weightx, double weighty, int gridwidth, int anchor, GridBagConstraints gbc)
	{
		//gbc.fill        = GridBagConstraints.;
		gbc.gridx       = xPos;
		gbc.gridy       = yPos;
		gbc.insets      = insets;
		gbc.ipady       = ipady;
		gbc.weightx     = weightx;
		gbc.weighty     = weighty;
		gbc.gridwidth   = gridwidth;
		gbc.anchor      = anchor;

		parent.add (child, gbc);
	}


	private void addGroupsToMainPanelAndLinkGroupWithPlayer ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();

		for (int i = 0; i < mPlayerGroupsMap.size (); i++)
		{
			ArrayList <Integer> playersIds = mPlayerGroupsMap.get (i);
			DisplayGroupPanel dgp = new DisplayGroupPanel (playersIds, mPlayerList);

			addComponentToPanel (mGroupsPanel, dgp,   0, i, new Insets (0, 0, 0, 0), 0, 1, 0, 1, GridBagConstraints.NORTHWEST, gbc);

			mMatchController.setPlayersGeneratedGroupLink (playersIds, dgp);
		}
	}


	private void componentsModification ()
	{
		// Could be style method
		addComponentsListener ();
	}


	public void setVisibility (boolean visibilityFlag)
	{
		mJFrame.setVisible (visibilityFlag);
	}


	public void displayWinnerPanelInGroup (PlayerObject winner)
	{
		setCurrentPlayingGroup ();
		showWinner (winner);
	}


	public void displayWinnerAndNextStage (PlayerObject winner)
	{
		hideCurrentPlayingGroupPanelForAllGroups ();
		showWinner (winner);
		mGameStartBtn.setVisible (false);
		mNextStageBtn.setVisible (true);
	}


	private void showWinner (PlayerObject winner)
	{
		try
		{
			mMatchController.getPlayerObjectById (winner.mId).getDisplayGroupPanel ().showWinner (winner.mName);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}


	private void updateForm ()
	{
		mJFrame.invalidate();
		mJFrame.validate();
		mJFrame.repaint();
	}


	public void destroy ()
	{
		mJFrame.dispose ();
	}


	private void addComponentsListener ()
	{
		mGameStartBtn.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				mMatchController.runActionsAfterGroupDisplay ();
			}
		});

		mNextStageBtn.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				mMatchController.nextStageTrigger ();
			}
		});

		backButton.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				mMatchController.openGameManagerGuiForm ();
			}
		});
	}
}