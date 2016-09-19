package MatchController.GUI;

import MatchController.MatchController;
import MatchController.Objects.PlayerObject;

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
	private final MatchController           mMatchController;

	private JFrame                          mJFrame;
	private JButton                         mGameStartBtn;
	private JPanel                          mGroupsPanel;
	private JScrollPane                     mGroupsScrollPane;
	private JPanel                          mJPanel;
	private JButton                         mNextStageBtn;

	private HashMap <Integer, Integer[]>    mPlayerGroupsMap;
	private ArrayList <PlayerObject>        mPlayerList;

	public PlayerGeneratedGroupsGuiForm (MatchController matchController, ArrayList<PlayerObject> playerList,
	                                     HashMap <Integer, Integer[]> playerGroupsMap)
	{
		mMatchController    = matchController;
		mPlayerList         = playerList;
		mPlayerGroupsMap    = new HashMap <> (playerGroupsMap);

		formInitialization      ();
		componentsModification  ();
		addGroupsToPanel        ();
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


	private void addGroupsToPanel ()
	{
		addGroupsToMainPanel ();
		updateForm ();
	}


	private void addGroupsToMainPanel ()
	{
		// TODO Add some groups names
		// TODO Some Styling for panel
		for (int i = 1; i < mPlayerGroupsMap.size () + 1; i++)
		{
			JPanel playerGroupPanel = new JPanel ();
			playerGroupPanel.setLayout (new GridLayout ());

			mGroupsPanel.add (new DisplayGroupPanel (mPlayerGroupsMap.get (i), mPlayerList));
		}
	}


	private void componentsModification ()
	{
		addComponentsListener ();
	}


	public void setVisibility (boolean visibilityFlag)
	{
		mJFrame.setVisible (visibilityFlag);
	}


	public void displayWinner (int groupId, String winnerName)
	{
		Component[] groupPanels = mGroupsPanel.getComponents ();

		((JPanel) groupPanels[groupId - 1]).add (new JLabel ("Winner: " + winnerName));
	}


	// TODO wtf?
	public void displayWinner (int groupId, String winnerName, boolean isNextStage)
	{
		Component[] groupPanels = mGroupsPanel.getComponents ();

		((JPanel) groupPanels[groupId - 1]).add (new JLabel ("Winner: " + winnerName));

		mGameStartBtn.setVisible (false);
		mNextStageBtn.setVisible (true);
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
	}
}