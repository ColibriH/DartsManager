package MatchController.GUI;

import MatchController.GUI.Components.DisplayGroupPanel;
import MatchController.GUI.Components.GroupPanelLines;
import MatchController.MatchController;
import MatchController.Objects.GroupsTreeNode;
import MatchController.Objects.Stages;
import MatchController.Objects.PlayerObject;
import Constants.Constats;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladislavs on 07.10.2016..
 */

// TODO Fix scroll to work
// TODO Change group design
// TODO Set some BG
// TODO Style buttons
// TODO Thing to replace in player object panel on node
// TODO Refactor

public class GroupsGui
{
	private final MatchController mMatchController;

	private JFrame                                              mJFrame;

	private JPanel                                              mJPanel;
	private JPanel                                              mControlJPanel;
	private JPanel                                              mGroupsPanel;
	private GroupPanelLines mGlassPanel;
	private JScrollPane                                         mGroupsScrollPane;

	private JButton                                             mGameStartBtn;
	private JButton                                             mBackBtn;

	private Integer                                             mCurrentPlayingGroupNumber;


	public GroupsGui (MatchController matchController, Integer currentPlayingGroupNumber)
	{
		mCurrentPlayingGroupNumber  = currentPlayingGroupNumber;
		mMatchController            = matchController;

		initializeComponents ();
		addComponentsListener ();
		buildMainFrame ();
	}


	private void buildMainFrame ()
	{
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.setContentPane (mJPanel);
		setMJFrameLocation ();
		mainPanelBuilder ();
		mJFrame.pack ();
		mJFrame.setVisible (true);
	}


	private void mainPanelBuilder ()
	{
		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		mJPanel.setLayout (new GridBagLayout ());
		mJPanel.setPreferredSize (new Dimension (Constats.MAIN_WIDTH + 200, Constats.MAIN_HEIGHT + 100));
		mJPanel.setBackground (Color.BLUE);

		controlPanelBuilder ();
		groupsPanelBuilder ();
		createGroupLines ();

		addComponentToPanel (mJPanel, mGlassPanel,    0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 2, GridBagConstraints.NORTHWEST,       mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (mJPanel, mGroupsPanel,   0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 2, GridBagConstraints.NORTHWEST,       mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (mJPanel, mControlJPanel, 0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.LAST_LINE_START, mPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void groupsPanelBuilder ()
	{
		mGroupsPanel.setLayout (new GridBagLayout ());
		addGroupsToPanelAndUpdateFrame ();
		setCurrentPlayingGroup ();
	}


	private void controlPanelBuilder ()
	{
		GridBagConstraints mControlPanelGbc = new GridBagConstraints ();
		mControlJPanel.setLayout (new GridBagLayout ());
		controlPanelComponentModification ();

		addComponentToPanel (mJPanel, mBackBtn,      0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (mJPanel, mGameStartBtn, 1, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void controlPanelComponentModification ()
	{
		mGameStartBtn.setPreferredSize (new Dimension (100, 50));
		mBackBtn.setPreferredSize (new Dimension (100, 50));
	}


	private void initializeComponents ()
	{
		mJFrame = new JFrame ();

		mJPanel             = new JPanel ();
		mGroupsPanel        = new JPanel ();
		mControlJPanel      = new JPanel ();
		mGlassPanel 		= new GroupPanelLines (null);
		mGroupsScrollPane   = new JScrollPane (mGroupsPanel);

		mGameStartBtn   	= new JButton ("Start Game");
		mBackBtn        	= new JButton ("Back");
	}

	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void addGroupsToPanelAndUpdateFrame ()
	{
		addGroupsToMainPanelAndLinkGroupWithPlayer ();
	}

	private void createGroupLines ()
	{
		mGlassPanel	= new GroupPanelLines (mGroupsPanels);
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


	private void addGroupsToMainPanelAndLinkGroupWithPlayer ()
	{
		ArrayList <GroupsTreeNode>   groups    = new ArrayList <> ();
		Stages stages = new Stages (mPlayerGroupsMap.size ());
		GridBagConstraints              gbc       = new GridBagConstraints ();

		int lvlAdd = 2;
		int prevLvlFirstElPos = 2;

		for (int i = 0; i < mPlayerGroupsMap.size (); i++)      // Fill 1st lvl groups
		{
			ArrayList <Integer> playersIds = mPlayerGroupsMap.get (i);
			DisplayGroupPanel dgp = null; //new DisplayGroupPanel (playersIds, mPlayerList);

			int weightX = (i == 0) ? 1 : 0;
			int weightY = (i == mPlayerGroupsMap.size () - 1) ? 1 : 0;

			addComponentToPanel (mGroupsPanel, dgp,   0, i * lvlAdd, new Insets (0, 0, 0, 0), 0, weightX, weightY, 1, GridBagConstraints.NORTHWEST, gbc, GridBagConstraints.HORIZONTAL);

			groups.add(new GroupsTreeNode (dgp));

			mMatchController.setPlayersGeneratedGroupLink (playersIds, dgp);
		}

		mGroupsPanels.put(1, new ArrayList <> (groups));

		for (int i = 2; i < stages.mGroupCountOnStages.size () + 1; i++)  // Fill Future groups // start from second lvl
		{
			groups.clear();
			lvlAdd = (int) Math. pow ((double) 2, (double) i);

			if (i != 2)
				prevLvlFirstElPos *= 2;

			int position = prevLvlFirstElPos;

			for (int j = 0; j < stages.mGroupCountOnStages.get(i); j++)
			{
				if (j > 0)
					position += lvlAdd;

				DisplayGroupPanel dgp = new DisplayGroupPanel ();

				addComponentToPanel (mGroupsPanel, dgp,   i - 1, position - 1, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.NORTHWEST, gbc, GridBagConstraints.HORIZONTAL);

				groups.add(new GroupsTreeNode (dgp));
			}

			mGroupsPanels.put(i, new ArrayList <> (groups));
		}

		createGroupsTree ();
	}


	private void createGroupsTree ()
	{
		for (Integer i = mGroupsPanels.size (); i > 1; i--)
		{
			ArrayList groups = mGroupsPanels.get (i);
			for (int j = 0; j < groups.size (); j++)
			{
				addChildren (mGroupsPanels.get (i-1), j * 2, (GroupsTreeNode) groups.get (j), i);
			}
		}
	}


	private void addChildren (ArrayList groups, int spos, GroupsTreeNode parent, int key)
	{
		GroupsTreeNode node = (GroupsTreeNode) groups.get (spos);
		GroupsTreeNode node2;

		if (groups.size () > spos + 1)
			node2 = (GroupsTreeNode) groups.get (spos + 1);
		else
			node2 = findNode (key);

		parent.addChildren (node);
		parent.addChildren (node2);

		node.setParent (parent);
		node2.setParent (parent);
	}


	private GroupsTreeNode findNode (Integer key)
	{
		for (Integer i = mGroupsPanels.size (); i > 0; i--)
		{
			if (i < key - 1)
			{
				ArrayList valueArr = mGroupsPanels.get (i);
				if (valueArr.size () % 2 != 0)
					return (GroupsTreeNode)valueArr.get (valueArr.size () - 1);
			}
		}

		return null;
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
	}


	private void showWinner (PlayerObject winner)
	{
		try
		{
			GroupsTreeNode winnerNode = findNode (mMatchController.getPlayerObjectById (winner.mId).getDisplayGroupPanel ());
			winnerNode.getParent ().getDisplayGroupPanel ().setPlayerName (winner.mName);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}


	private GroupsTreeNode findNode (DisplayGroupPanel displayGroupPanel)
	{
		for (int i = 1; i < mGroupsPanels.size (); i++)
		{
			ArrayList <GroupsTreeNode> nodes = mGroupsPanels.get (i);
			for (int j = 0 ; j< nodes.size (); j++)
			{
				if (nodes.get (j).getDisplayGroupPanel ().equals (displayGroupPanel))
				{
					return nodes.get (j);
				}
			}
		}

		return null;
	}


	public void destroy ()
	{
		mJFrame.dispose ();
	}


	private void addComponentsListener ()
	{
		mGameStartBtn.  addActionListener (e -> mMatchController.runActionsAfterGroupDisplay ());
		mBackBtn.       addActionListener (e -> mMatchController.openGameManagerGuiForm ());
	}
}
