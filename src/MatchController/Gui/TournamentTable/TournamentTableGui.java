package MatchController.Gui.TournamentTable;

import BaseAbstractClasses.DartsGuiFormBase;
import GuiComponents.ImagedPanel;
import MatchController.Gui.Components.DisplayGroupPanel;
import MatchController.Gui.Components.GroupPanelLines;
import MatchController.MatchController;
import MatchController.Objects.GroupsTreeNode;
import Constants.Constats;
import Tools.ImageLoader;

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

abstract class TournamentTableGui extends DartsGuiFormBase
{
	protected abstract void setCurrentPlayingGroupText ();

	private JPanel                                              mControlJPanel;
	private ImagedPanel                                         mGroupsPanel;
	private GroupPanelLines                                     mGlassPanel;
	private JScrollPane                                         mGroupsScrollPane;

	private JButton                                             mGameStartBtn;
	private JButton                                             mBackBtn;


	TournamentTableGui (MatchController matchController)
	{
		super (matchController);
	}


	@Override
	protected void initializeComponents ()
	{
		mGroupsPanel        = new ImagedPanel (ImageLoader.getImage (Constats.CHALK_BOARD));
		mControlJPanel      = new JPanel ();
		mGlassPanel 		= new GroupPanelLines (null);
		mGroupsScrollPane   = new JScrollPane (mGroupsPanel);

		mGameStartBtn   	= new JButton ("Start Game");
		mBackBtn        	= new JButton ("Back");
	}


	@Override
	protected void addComponentsListener ()
	{
		mGameStartBtn.  addActionListener (e -> getMatchController ().runActionsAfterGroupDisplay ());
		mBackBtn.       addActionListener (e -> getMatchController ().openGameManagerGuiForm ());
	}


	@Override
	protected void buildMainPanel ()
	{
		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		getMainJPanel ().setLayout (new GridBagLayout ());
		getMainJPanel ().setPreferredSize (new Dimension (Constats.MAIN_WIDTH + 200, Constats.MAIN_HEIGHT + 100));

		buildControlPanel ();
		buildGroupsPanel ();
		drawGroupsLines ();

		addComponentToPanel (getMainJPanel (), mGlassPanel,    0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 2, GridBagConstraints.NORTHWEST,       mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (getMainJPanel (), mGroupsPanel,   0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 2, GridBagConstraints.NORTHWEST,       mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (getMainJPanel (), mControlJPanel, 0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.LAST_LINE_START, mPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void buildGroupsPanel ()
	{
		mGroupsScrollPane.setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mGroupsScrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mGroupsPanel.setLayout (new GridBagLayout ());
		mGroupsPanel.setBackground (Color.BLACK);

		addGroupsToMainPanel ();
		setCurrentPlayingGroupText ();
	}


	private void buildControlPanel ()
	{
		GridBagConstraints mControlPanelGbc = new GridBagConstraints ();
		mControlJPanel.setLayout (new GridBagLayout ());
		controlPanelComponentModification ();

		addComponentToPanel (getMainJPanel (), mBackBtn,      0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (getMainJPanel (), mGameStartBtn, 1, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void controlPanelComponentModification ()
	{
		mGameStartBtn   .setPreferredSize (new Dimension (100, 50));
		mBackBtn        .setPreferredSize (new Dimension (100, 50));
	}


	private void drawGroupsLines ()
	{
		mGlassPanel = new GroupPanelLines (getMatchController ().getMatchGroups ());
	}


	private void addGroupsToMainPanel ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();
		HashMap <Integer, ArrayList <GroupsTreeNode>> matchGroups = getMatchController ().getMatchGroups ();
		for (int i = 0; i < matchGroups.size (); i++)
		{
			ArrayList <GroupsTreeNode> nodes = matchGroups.get (i);
			for (GroupsTreeNode node : nodes)
			{
				DisplayGroupPanel dgp = node.getDisplayGroupPanel ();
				addComponentToPanel (mGroupsPanel, dgp, dgp.getColumn (), dgp.getRow (), new Insets (0, 0, 0, 0), 0, dgp.getWeightX (), dgp.getWeightY (), 1, GridBagConstraints.NORTHWEST, gbc, null);
			}
		}
	}

}
