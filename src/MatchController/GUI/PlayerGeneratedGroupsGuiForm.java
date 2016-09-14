package MatchController.GUI;

import MatchController.MatchController;
import MatchController.Objects.NewPlayerObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vladislavs on 06.09.2016..
 */
public class PlayerGeneratedGroupsGuiForm
{
	private JFrame                      mJFrame;
	private JButton                     mGameStartBtn;
	private JPanel                      mGroupsPanel;
	private JPanel                      mJPanel;
	private JButton mNextStageBtn;

	private final MatchController       mMatchController;

	private HashMap <String, String[]>  mPlayerGroupsMap;
	private ArrayList<NewPlayerObject>  mPlayerList;

	public PlayerGeneratedGroupsGuiForm (MatchController matchController, ArrayList<NewPlayerObject> playerList,
	                                     HashMap <String, String[]> playerGroupsMap)
	{
		mMatchController    = matchController;
		mPlayerList         = playerList;
		mPlayerGroupsMap    = new HashMap <> (playerGroupsMap);

		formInitialization ();
		componentsModification ();
		addGroupsToPanel ();
	}


	private void addGroupsToPanel ()
	{
		// TODO CODE REFACTOR - TO SEPARATE FUNCTIONS!!!!

		// TODO Some Styling and etc
		// TODO Add some groups names

		for (int i = 1; i < mPlayerGroupsMap.size () + 1; i++)
		{
			JPanel playerGroupPanel = new JPanel ();       // TODO Some Styling for panel
			playerGroupPanel.setLayout (new GridLayout ());

			Integer key = i;
			String [] playersIds = mPlayerGroupsMap.get (key);

			// TODO ist hack try to solve problem with key
			if (playersIds == null)
				playersIds = mPlayerGroupsMap.get (key.toString ());

			mGroupsPanel.add (new DisplayGroupPanel (playersIds, mPlayerList));
		}

		updateForm ();
	}


	private String getPlayerNameById (String playerId)
	{
		for (NewPlayerObject player : mPlayerList)
			if (player.mId.equals (playerId))
				return player.mName;

		return "FALSE";      // NOT Possible if correct work!!
	}


	private void formInitialization ()
	{
		mJFrame = new JFrame ("PlayerGeneratedGroupsGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);

		mJFrame.setResizable (false);
	}


	private void componentsModification ()
	{
		// Created Events And Listeners
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
	}


	public void setVisibility (boolean visibilityFlag)      // TODO Rename variable - flag not suitable here
	{
		mJFrame.setVisible (visibilityFlag);
	}


	private void updateForm ()
	{
		mJFrame.invalidate();
		mJFrame.validate();
		mJFrame.repaint();
	}


	public void displayWinner (int groupId, String winnerName)
	{
		Component[] groupPanels = mGroupsPanel.getComponents ();

		((JPanel) groupPanels[groupId - 1]).add (new JLabel ("Winner: " + winnerName));
	}


	public void displayWinner (int groupId, String winnerName, boolean isNextStage)
	{
		Component[] groupPanels = mGroupsPanel.getComponents ();

		((JPanel) groupPanels[groupId - 1]).add (new JLabel ("Winner: " + winnerName));

		mGameStartBtn.setVisible (false);
		mNextStageBtn.setVisible (true);
	}


	public void destroy ()
	{
		mJFrame.dispose ();
	}
}