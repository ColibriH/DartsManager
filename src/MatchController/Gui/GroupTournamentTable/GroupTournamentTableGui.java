package MatchController.Gui.GroupTournamentTable;

import BaseAbstractClasses.DartsGuiFormBase;
import GuiComponents.ImagedPanel;
import GuiComponents.MenuButton;
import MatchController.Gui.Components.GroupTournamentMethod;
import MatchController.Gui.Components.GroupTournamentTableGroupPanel;
import MatchController.Gui.Components.TableScrollBar;
import MatchController.MatchController;
import MatchController.Objects.GroupPlayerObject;
import Constants.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

abstract class GroupTournamentTableGui extends DartsGuiFormBase
{
	protected abstract void updateTable ();
	protected abstract void initializePlayerTableDataModel ();

	private final String            COLUMN_WIN_POINTS               = "WinPoints";
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
		mBackBtn        	        = new MenuButton    ("Back");
		mPlayerTableJScrollPane     = new JScrollPane   (mPlayersTable,       ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mGroupsScrollPane           = new JScrollPane   (mGroupsPanelContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}


	@Override
	protected void addComponentsListener ()
	{
		mBackBtn.addActionListener (e -> getMatchController ().openPlayerRegistration ());
	}


	@Override
	protected void buildMainPanel ()
	{
		getMainJPanel ().setLayout          (new GridBagLayout ());
		getMainJPanel ().setPreferredSize   (new Dimension (Constats.MAIN_WIDTH + 200, Constats.MAIN_HEIGHT + 100));

		buildControlPanel   ();
		buildGroupsPanel    ();

		GridBagConstraints mPanelGbc = new GridBagConstraints ();
		addComponentToPanel (getMainJPanel (), mPlayerTableJScrollPane, 0, 0, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, mPanelGbc, null);
		addComponentToPanel (getMainJPanel (), mGroupsScrollPane,       0, 1, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.BOTH);
		addComponentToPanel (getMainJPanel (), mControlJPanel,          0, 2, new Insets (0, 0, 0, 0), 0, 1, 0, 1, GridBagConstraints.CENTER, mPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void buildGroupsPanel ()
	{
		buildTableScrollPane ();
		buildGroupsScrollPane ();

		mGroupsPanelContent.setLayout       (new GridBagLayout ());
		mGroupsPanelContent.setOpaque       (false);
		mGroupsPanelContent.setBackground   (new Color (255, 255, 255, 0));

		mPlayersTable.setLayout     (new GridBagLayout ());
		mPlayersTable.setOpaque     (false);
		mPlayersTable.setBackground (new Color (255, 255, 255, 0));

		initializePlayerTableDataModel ();
		setTableStyle ();

		mGroupsPanel.setLayout      (new GridBagLayout ());
		mGroupsPanel.setBackground  (new Color (255, 255, 255, 0));

		addGroupsToMainPanel ();

		GridBagConstraints gbc = new GridBagConstraints ();
		addComponentToPanel (mGroupsPanelContent, mGroupsPanel, 0, 0, new Insets (0, 0, 0, 0), 0, 1, 1, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
	}


	private void setTableStyle ()
	{
		Color transparentColor = new Color (255, 255, 255 ,0);

		mPlayersTable.setFont                                           (new Font ("Eraser Regular", Font.TRUETYPE_FONT, 12));
		mPlayersTable.setOpaque                                         (false);
		mPlayersTable.setBackground                                     (transparentColor);
		mPlayersTable.setForeground                                     (Color.WHITE);
		mPlayersTable.setAutoResizeMode                                 (JTable.AUTO_RESIZE_OFF);
		mPlayersTable.setIntercellSpacing                               (new Dimension (5, 0));
		mPlayersTable.setRowSelectionAllowed                            (false);
		mPlayersTable.setCellSelectionEnabled                           (false);
		mPlayersTable.getTableHeader ().setFont                         (new Font ("Eraser Regular", Font.TRUETYPE_FONT, 14));
		mPlayersTable.getTableHeader ().setBackground                   (transparentColor);
		mPlayersTable.getTableHeader ().setForeground                   (Color.WHITE);
		mPlayersTable.getTableHeader ().setResizingAllowed              (false);
		mPlayersTable.getTableHeader ().setReorderingAllowed            (false);

		setColumnWidth ();
	}


	private void buildTableScrollPane ()
	{
		Color transparentColor = new Color (255, 255, 255 ,0);

		mPlayerTableJScrollPane.setOpaque                                   (false);
		mPlayerTableJScrollPane.setBackground                               (transparentColor);
		mPlayerTableJScrollPane.setPreferredSize                            (new Dimension (300, 200));
		mPlayerTableJScrollPane.getViewport ().setOpaque                    (false);
		mPlayerTableJScrollPane.getVerticalScrollBar ().setUI               (new TableScrollBar ());
		mPlayerTableJScrollPane.getVerticalScrollBar ().setBackground       (Color.GRAY);
		mPlayerTableJScrollPane.getVerticalScrollBar ().setPreferredSize    (new Dimension(10, 0));
	}


	private void buildGroupsScrollPane ()
	{
		mGroupsScrollPane.setOpaque                                 (false);
		mGroupsScrollPane.setBackground                             (new Color (255, 255, 255, 0));
		mGroupsScrollPane.getViewport ().setOpaque                  (false);
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
		addComponentToPanel (mControlJPanel, mBackBtn, 0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.SOUTH, mControlPanelGbc, GridBagConstraints.HORIZONTAL);
	}


	private void modifyControlPanelComponents ()
	{
		mBackBtn.setPreferredSize (new Dimension (100, 50));
	}


	private void notifyGroupPlayed ()
	{
		getMatchController ().notifyGroupTournamentGroupPlayed ();
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

		int size                = matchGroups.size ();
		int groupCountInColumn  = (int) getMatchController ().roundHalfUp (size / 2.0);
		int column              = 0;
		int row                 = 0;

		for (int i = 0; i < size; i++)
		{
			if (row >= groupCountInColumn)
			{
				row = 0;
				column++;
			}

			GroupPlayerObject groups = matchGroups.get (i).get (0);
			GroupTournamentTableGroupPanel dgp = groups.getGroupTournamentGroupPanel ();

			dgp.setTableUpdateMethod (new GroupTournamentMethod ()
			{
				@Override
				public void execute ()
				{
					updateTable ();
				}


				@Override
				public void execute (GroupPlayerObject loserGroup, GroupPlayerObject winnerGroup)
				{

				}
			});
			dgp.setTableNotifyGroupPlayedMethod (new GroupTournamentMethod ()
			{
				@Override
				public void execute ()
				{
					notifyGroupPlayed ();
				}


				@Override
				public void execute (GroupPlayerObject loserGroup, GroupPlayerObject winnerGroup)
				{

				}
			});

			addComponentToPanel (mGroupsPanel, dgp, column, row, new Insets (45, 30, 45, 30), 0, 0, 0, 1, GridBagConstraints.NORTHWEST, gbc, null);

			row++;
		}
	}


	public void reloadGroupPanel ()
	{
		mGroupsPanel.removeAll ();
		addGroupsToMainPanel ();
		repaintMainFrame ();
	}


	void setColumnWidth ()
	{
		mPlayersTable.getColumnModel ().getColumn (0).setPreferredWidth (99);
		mPlayersTable.getColumnModel ().getColumn (1).setPreferredWidth (99);
		mPlayersTable.getColumnModel ().getColumn (2).setPreferredWidth (99);

		repaintMainFrame ();
	}


	JTable getPlayersTable ()
	{
		return mPlayersTable;
	}


	String getCOLUMN_WIN_POINTS ()
	{
		return COLUMN_WIN_POINTS;
	}


	String getCOLUMN_NAME ()
	{
		return COLUMN_NAME;
	}


	String getCOLUMN_LOSES ()
	{
		return COLUMN_LOSES;
	}
}
