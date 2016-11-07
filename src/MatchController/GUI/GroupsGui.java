package MatchController.GUI;

import MatchController.GUI.Components.DisplayGroupPanel;
import MatchController.GUI.Components.GroupPanelLines;
import MatchController.MatchController;
import MatchController.Objects.GroupsTreeNode;
import Constants.Constats;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vladislavs on 07.10.2016..
 */

// TODO Change group design
// TODO Set some BG
// TODO Style buttons
// TODO Fix scroll to work

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


	public GroupsGui (MatchController matchController)
	{
		mMatchController = matchController;

		initializeComponents ();
		addComponentsListener ();
		buildMainFrame ();
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


	private void buildMainFrame ()
	{
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.setContentPane (mJPanel);
		//mJFrame.setResizable (false);
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
		mGroupsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mGroupsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mGroupsPanel.setLayout (new GridBagLayout ());
		addGroupsToMainPanel ();
		setCurrentPlayingGroupText ();
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


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void createGroupLines ()
	{
		mGlassPanel	= new GroupPanelLines (mMatchController.getMatchGroups ());
	}


	private void setCurrentPlayingGroupText ()
	{
		hideCurrentPlayingGroupPanelForAllGroups ();
		setCurrentPlayingGroupText (mMatchController.getCurrentPlayingGroupPanel (), true);
	}


	private void setCurrentPlayingGroupText (DisplayGroupPanel panel, boolean state)
	{
		try
		{
			panel.setCurrentPlayingGroup (state);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}


	private void hideCurrentPlayingGroupPanelForAllGroups ()
	{
		ArrayList <DisplayGroupPanel> allMatchGroupsPanels = mMatchController.getAllMatchGroupsPanels ();
		for (int i = 0; i < allMatchGroupsPanels.size (); i++)
			setCurrentPlayingGroupText (allMatchGroupsPanels.get (i), false);
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


	private void addGroupsToMainPanel ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();
		HashMap <Integer, ArrayList <GroupsTreeNode>> matchGroups = mMatchController.getMatchGroups ();
		for (int i = 0; i < matchGroups.size (); i++)
		{
			ArrayList <GroupsTreeNode> nodes = matchGroups.get (i);
			for (int j = 0; j < nodes.size (); j++)
			{
				DisplayGroupPanel dgp = nodes.get (j).getDisplayGroupPanel ();
				addComponentToPanel (mGroupsPanel, dgp,   dgp.getColumn (), dgp.getRow (), new Insets (0, 0, 0, 0), 0, dgp.getWeightX (), dgp.getWeightY (), 1, GridBagConstraints.NORTHWEST, gbc, GridBagConstraints.HORIZONTAL);
			}
		}
	}


	private void addComponentsListener ()
	{
		mGameStartBtn.  addActionListener (e -> mMatchController.runActionsAfterGroupDisplay ());
		mBackBtn.       addActionListener (e -> mMatchController.openGameManagerGuiForm ());
	}


	public void setVisibility (boolean visibilityFlag)
	{
		mJFrame.setVisible (visibilityFlag);
	}


	public void displayWinnerPanelInGroup ()
	{
		setCurrentPlayingGroupText ();
	}


	public void destroy ()
	{
		mJFrame.dispose ();
	}
}
