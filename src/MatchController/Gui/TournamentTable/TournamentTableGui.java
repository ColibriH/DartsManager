package MatchController.Gui.TournamentTable;

import BaseAbstractClasses.DartsGuiFormBase;
import GuiComponents.ImagedPanel;
import GuiComponents.MenuButton;
import MatchController.Gui.Components.TableScrollBar;
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
		setMainJPanel (new ImagedPanel (ImageLoader.getImage (Constats.TOURNAMENT_TABLE_BG)));

		mGroupsPanel        = new JPanel ();
		mControlJPanel      = new JPanel ();
		mGlassPanel 		= new GroupPanelLines (null);
		mGroupsPanelContent = new JPanel ();
		mGameStartBtn   	= new MenuButton ("Start Game");
		mBackBtn        	= new MenuButton ("Back");
		mGroupsScrollPane   = new JScrollPane (mGroupsPanelContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}


	@Override
	protected void addComponentsListener ()
	{
		mGameStartBtn.  addActionListener (e -> getMatchController ().runActionsAfterTournamentTable ());
		mBackBtn.       addActionListener (e -> getMatchController ().openPlayerRegistration ());
	}


	@Override
	protected void buildMainPanel ()
	{
		getMainJPanel ().setLayout          (new GridBagLayout ());
		getMainJPanel ().setPreferredSize   (new Dimension (Constats.MAIN_WIDTH + 200, Constats.MAIN_HEIGHT + 100));

		buildControlPanel ();
		buildGroupsPanel ();

		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		addComponentToPanel (getMainJPanel (), mGroupsScrollPane, 0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (getMainJPanel (), mControlJPanel,    0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void buildGroupsPanel ()
	{
		buildGroupsScrollPane ();

		mGroupsPanel.setLayout              (new GridBagLayout ());
		mGroupsPanel.setBackground          (new Color (255, 255, 255, 0));
		mGroupsPanelContent.setLayout       (new GridBagLayout ());
		mGroupsPanelContent.setOpaque       (false);
		mGroupsPanelContent.setBackground   (new Color (255, 255, 255, 0));

		addGroupsToMainPanel ();
		drawGroupsLines ();
		setCurrentPlayingGroupText ();

		GridBagConstraints gbc = new GridBagConstraints ();
		addComponentToPanel (mGroupsPanelContent, mGlassPanel,  0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
		addComponentToPanel (mGroupsPanelContent, mGroupsPanel, 0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
	}


	private void buildGroupsScrollPane ()
	{
		mGroupsScrollPane.setOpaque                                 (false);
		mGroupsScrollPane.getViewport                               ().setOpaque (false);
		mGroupsScrollPane.setBackground                             (new Color (255, 255, 255, 0));
		mGroupsScrollPane.getVerticalScrollBar ().setUI             (new TableScrollBar ());
		mGroupsScrollPane.getVerticalScrollBar ().setBackground     (Color.GRAY);
		mGroupsScrollPane.getVerticalScrollBar ().setUnitIncrement  (20);
		mGroupsScrollPane.getVerticalScrollBar ().setPreferredSize  (new Dimension(10, 0));
	}


	private void buildControlPanel ()
	{
		mControlJPanel.setLayout        (new GridBagLayout ());
		mControlJPanel.setBackground    (Color.DARK_GRAY);

		modifyControlPanelComponents ();

		GridBagConstraints mControlPanelGbc = new GridBagConstraints ();
		addComponentToPanel (mControlJPanel, mBackBtn,      0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (mControlJPanel, mGameStartBtn, 1, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void modifyControlPanelComponents ()
	{
		mBackBtn        .setPreferredSize (new Dimension (100, 50));
		mGameStartBtn   .setPreferredSize (new Dimension (100, 50));
	}


	private void drawGroupsLines ()
	{
		mGlassPanel = new GroupPanelLines (getMatchController ().getTournamentMatchGroups ());
	}


	private void addGroupsToMainPanel ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();
		HashMap <Integer, ArrayList <GroupsTreeNode>> matchGroups = getMatchController ().getTournamentMatchGroups ();
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
