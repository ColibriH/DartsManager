package MatchController.Gui.TournamentTable;

import BaseAbstractClasses.DartsGuiFormBase;
import GuiComponents.ImagedPanel;
import MatchController.Gui.Components.TournamentTableGroupPanel;
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

// TODO Refactor

abstract class TournamentTableGui extends DartsGuiFormBase
{
	protected abstract void setCurrentPlayingGroupText ();

	private JPanel                                              mControlJPanel;
	private JPanel                                              mGroupsPanelContent;
	private JPanel                                              mGroupsPanel;
	private GroupPanelLines                                     mGlassPanel;
	private JScrollPane                                         mGroupsScrollPane;

	private JButton                                             mGameStartBtn;
	private JButton                                             mBackBtn;


	TournamentTableGui (MatchController matchController)
	{
		super (matchController);
		setMainFrameResizable (true);
	}


	@Override
	protected void initializeComponents ()
	{
		mGroupsPanel        = new JPanel (); //new ImagedPanel (ImageLoader.getImage (Constats.CHALK_BOARD)); // TODO replace with moved image along scroll
		mControlJPanel      = new JPanel ();
		mGroupsPanelContent = new JPanel ();
		mGlassPanel 		= new GroupPanelLines (null);
		mGroupsScrollPane   = new JScrollPane (mGroupsPanelContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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

		addComponentToPanel (getMainJPanel (), mGroupsScrollPane,   0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (getMainJPanel (), mControlJPanel,      0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void buildGroupsPanel ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();
		mGroupsScrollPane.getVerticalScrollBar ().setUnitIncrement (20);
		mGroupsPanelContent.setLayout (new GridBagLayout ());
		mGroupsPanel.setLayout (new GridBagLayout ());
		mGroupsPanel.setBackground (Color.BLACK);

		addGroupsToMainPanel ();
		setCurrentPlayingGroupText ();
		drawGroupsLines ();

		addComponentToPanel (mGroupsPanelContent, mGlassPanel,  0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
		addComponentToPanel (mGroupsPanelContent, mGroupsPanel, 0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
	}


	private void buildControlPanel ()
	{
		GridBagConstraints mControlPanelGbc = new GridBagConstraints ();
		mControlJPanel.setLayout (new GridBagLayout ());
		controlPanelComponentModification ();

		addComponentToPanel (mControlJPanel, mBackBtn,      0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (mControlJPanel, mGameStartBtn, 1, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
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
				TournamentTableGroupPanel dgp = node.getDisplayGroupPanel ();
				addComponentToPanel (mGroupsPanel, dgp, dgp.getColumn (), dgp.getRow (), new Insets (0, 0, 0, 0), 0, dgp.getWeightX (), dgp.getWeightY (), 1, GridBagConstraints.NORTHWEST, gbc, null);
			}
		}
	}

}
