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
	private final MatchController                       mMatchController;

	private JFrame                                      mJFrame;
	private JButton                                     mGameStartBtn;
	private JPanel                                      mGroupsPanel;
	private JScrollPane                                 mGroupsScrollPane;
	private JPanel                                      mJPanel;
	private JButton                                     mNextStageBtn;

	private HashMap <Integer, ArrayList <Integer>>      mPlayerGroupsMap;
	private ArrayList <PlayerObject>                    mPlayerList;


	public PlayerGeneratedGroupsGuiForm (MatchController matchController, ArrayList<PlayerObject> playerList,
	                                     HashMap <Integer, ArrayList <Integer>> playerGroupsMap)
	{
		mMatchController    = matchController;
		mPlayerList         = playerList;
		mPlayerGroupsMap    = new HashMap <> (playerGroupsMap);

		formInitialization      ();
		componentsModification  ();
		addGroupsToPanelAndUpdateFrame ();
	}


	private void formInitialization ()
	{
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
		addGroupsToMainPanel ();
		updateForm ();
	}


	private void addGroupsToMainPanel ()
	{
		for (int i = 1; i < mPlayerGroupsMap.size () + 1; i++)
			mGroupsPanel.add (new DisplayGroupPanel (mPlayerGroupsMap.get (i), mPlayerList));
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


	// TODO wtf?
	public void displayWinnerPanelInGroup (int groupId, String winnerName)
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