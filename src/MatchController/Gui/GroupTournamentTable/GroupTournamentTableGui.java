package MatchController.Gui.GroupTournamentTable;

import BaseAbstractClasses.DartsGuiFormBase;
import GuiComponents.ImagedPanel;
import GuiComponents.MenuButton;
import MatchController.Gui.Components.GroupTournamentTableGroupPanel;
import MatchController.Gui.Components.TableScrollBar;
import MatchController.MatchController;
import MatchController.Objects.GroupPlayerObject;
import Constants.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

// TODO REFACTOR

abstract class GroupTournamentTableGui extends DartsGuiFormBase
{
	protected abstract void updateTable ();
	protected abstract void initializePlayerTableDataModel ();

	private final String            COLUMN_ID                       = "Id";
	private final String            COLUMN_NAME                     = "Name";
	private final String            COLUMN_LOSES                    = "Loses";

	private JPanel                                              mControlJPanel;
	private JPanel                                              mGroupsPanelContent;
	private JPanel                                              mGroupsPanel;

	private JTable                                              mPlayersTable;

	private JScrollPane                                         mGroupsScrollPane;
	private JScrollPane                                         mPlayerTableJScrollPane;

	private JButton                                             mBackBtn;


	GroupTournamentTableGui (MatchController matchController)
	{
		super (matchController);
		setMainFrameResizable (false);
	}


	@Override
	protected void initializeComponents ()
	{
		setMainJPanel (new ImagedPanel (ImageLoader.getImage (Constats.TOURNAMENT_TABLE_BG)));

		mGroupsPanel                = new JPanel ();
		mControlJPanel              = new JPanel ();
		mGroupsPanelContent         = new JPanel ();
		mPlayersTable               = new JTable ();
		mPlayerTableJScrollPane     = new JScrollPane (mPlayersTable,       ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mGroupsScrollPane           = new JScrollPane (mGroupsPanelContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mBackBtn        	        = new MenuButton ("Back");
	}


	@Override
	protected void addComponentsListener ()
	{
		mBackBtn.       addActionListener (e -> getMatchController ().openPlayerRegistration ());
	}


	@Override
	protected void buildMainPanel ()
	{
		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		getMainJPanel ().setLayout (new GridBagLayout ());
		getMainJPanel ().setPreferredSize (new Dimension (Constats.MAIN_WIDTH + 200, Constats.MAIN_HEIGHT + 100));

		buildControlPanel ();
		buildGroupsPanel ();

		addComponentToPanel (getMainJPanel (), mPlayerTableJScrollPane, 0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (getMainJPanel (), mGroupsScrollPane,       0, 1, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (getMainJPanel (), mControlJPanel,          0, 2, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void buildGroupsPanel ()
	{
		buildTableScrollPane ();
		buildGroupsScrollPane ();

		mGroupsPanelContent.setLayout (new GridBagLayout ());
		mGroupsPanelContent.setOpaque (false);
		mGroupsPanelContent.setBackground (new Color (255, 255, 255, 0));

		mPlayersTable.setLayout (new GridBagLayout ());
		mPlayersTable.setBackground (new Color (255, 255, 255, 0));
		mPlayersTable.setOpaque (false);

		initializePlayerTableDataModel ();
		setTableStyle ();

		mGroupsPanel.setLayout (new GridBagLayout ());
		mGroupsPanel.setBackground (new Color (255, 255, 255, 0));

		addGroupsToMainPanel ();

		GridBagConstraints gbc = new GridBagConstraints ();
		addComponentToPanel (mGroupsPanelContent, mGroupsPanel, 0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
	}


	private void setTableStyle ()
	{
		Color transparentColor = new Color (255, 255, 255 ,0);

		mPlayerTableJScrollPane.setViewportView                         (mPlayersTable);
		mPlayerTableJScrollPane.setBorder                               (new EmptyBorder (0, 0, 0, 0));
		mPlayerTableJScrollPane.getVerticalScrollBar().setUI            (new TableScrollBar ());
		mPlayerTableJScrollPane.getVerticalScrollBar().setBackground    (Color.GRAY);
		mPlayerTableJScrollPane.getVerticalScrollBar().setPreferredSize (new Dimension(10, 0));
		mPlayerTableJScrollPane.setHorizontalScrollBarPolicy    (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mPlayerTableJScrollPane.setPreferredSize                (new Dimension (161, 275));

		mPlayersTable.setFont                    (new Font ("Eraser Regular", Font.TRUETYPE_FONT, 9));
		mPlayersTable.setForeground              (Color.WHITE);
		mPlayersTable.setAutoResizeMode          (JTable.AUTO_RESIZE_OFF);
		mPlayersTable.setOpaque                  (false);
		mPlayersTable.setBackground              (transparentColor);
		mPlayersTable.setIntercellSpacing        (new Dimension (5, 0));
	}


	private void buildTableScrollPane ()
	{
		mPlayerTableJScrollPane.getVerticalScrollBar ().setUI (new TableScrollBar ());
		mPlayerTableJScrollPane.getVerticalScrollBar ().setBackground (Color.GRAY);
		mPlayerTableJScrollPane.setOpaque (false);
		mPlayerTableJScrollPane.getViewport ().setOpaque (false);
		mPlayerTableJScrollPane.setBackground (new Color (255, 255, 255, 0));
	}


	private void buildGroupsScrollPane ()
	{
		mGroupsScrollPane.getVerticalScrollBar ().setUnitIncrement (20);
		mGroupsScrollPane.getVerticalScrollBar ().setUI (new TableScrollBar ());
		mGroupsScrollPane.getVerticalScrollBar ().setBackground (Color.GRAY);
		mGroupsScrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension(10, 0));
		mGroupsScrollPane.setOpaque (false);
		mGroupsScrollPane.getViewport ().setOpaque (false);
		mGroupsScrollPane.setBackground (new Color (255, 255, 255, 0));
	}


	private void buildControlPanel ()
	{
		mControlJPanel.setLayout (new GridBagLayout ());
		mControlJPanel.setBackground (Color.DARK_GRAY);

		modifyControlPanelComponents ();

		GridBagConstraints mControlPanelGbc = new GridBagConstraints ();
		addComponentToPanel (mControlJPanel, mBackBtn,      0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void modifyControlPanelComponents ()
	{
		mBackBtn.setPreferredSize (new Dimension (100, 50));
	}


	private void notifyGroupPlayed ()
	{
		getMatchController ().notifyGroupTournamentGroupPlayed ();
	}


	public void reloadGroupPanel ()
	{
		mGroupsPanel.removeAll ();
		addGroupsToMainPanel ();
		repaintMainFrame ();
	}


	private void repaintMainFrame ()
	{
		getMainJFrame ().revalidate ();
		getMainJFrame ().repaint ();
	}


	private void addGroupsToMainPanel ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();
		HashMap <Integer, ArrayList <GroupPlayerObject>> matchGroups = getMatchController ().getGroupTournamentGameGroups ();

		int size = matchGroups.size ();
		for (int i = 0; i < size; i++)
		{
			int row = i;

			GroupPlayerObject groups = matchGroups.get (i).get (0);
			GroupTournamentTableGroupPanel dgp = groups.getGroupTournamentGroupPanel ();

			dgp.setTableUpdateMethod (this::updateTable);
			dgp.setTableNotifyGroupPlayedMethod (this::notifyGroupPlayed);

			addComponentToPanel (mGroupsPanel, dgp, 0, row, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.NORTHWEST, gbc, null);
		}
	}


	public JTable getPlayersTable ()
	{
		return mPlayersTable;
	}


	public String getCOLUMN_ID ()
	{
		return COLUMN_ID;
	}


	public String getCOLUMN_NAME ()
	{
		return COLUMN_NAME;
	}


	public String getCOLUMN_LOSES ()
	{
		return COLUMN_LOSES;
	}
}
