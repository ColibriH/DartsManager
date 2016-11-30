package MatchController.Gui.GroupTournamnetWinnerFrame;

import BaseAbstractClasses.DartsGuiFormBase;
import MatchController.MatchController;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

abstract class GroupTournamentWinnerFrameGui extends DartsGuiFormBase
{
	protected abstract void menuButtonAction ();
	protected abstract void exitButtonAction ();
	protected abstract void initializePlayerTableDataModel ();

	private final String            COLUMN_WIN_POINTS               = "WinPoints";
	private final String            COLUMN_NAME                     = "Name";
	private final String            COLUMN_LOSES                    = "Loses";

	private JPanel          mContentPanel;
	private JPanel          mControlPanel;
	private JPanel          mHeaderPanel;
	private JTable          mPlayersTable;
	private JButton         mMenuButton;
	private JButton         mExitButton;
	private JButton         mSaveResultButton;
	private JScrollPane     mPlayersTableScrollPane;


	GroupTournamentWinnerFrameGui (MatchController matchController)
	{
		super (matchController);
	}


	@Override
	protected void initializeComponents ()
	{
		mContentPanel           = new JPanel ();
		mHeaderPanel            = new JPanel ();
		mControlPanel           = new JPanel ();
		mPlayersTable           = new JTable ();
		mMenuButton             = new JButton ("Menu");
		mExitButton             = new JButton ("Exit");
		mSaveResultButton       = new JButton ("Save Results");
		mPlayersTableScrollPane = new JScrollPane (mPlayersTable);
	}


	@Override
	protected void addComponentsListener ()
	{
		mMenuButton.addActionListener (e -> menuButtonAction ());
		mExitButton.addActionListener (e -> exitButtonAction ());
	}


	@Override
	protected void buildMainPanel ()
	{
		getMainJPanel ().setLayout (new GridBagLayout ());

		buildContentPanel ();
		buildControlPanel ();

		GridBagConstraints gbc = new GridBagConstraints ();
		addComponentToPanel (getMainJPanel (), mContentPanel, 0, 0, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
		addComponentToPanel (getMainJPanel (), mControlPanel, 0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
	}


	private void buildControlPanel ()
	{
		mControlPanel.setLayout (new GridBagLayout ());
		GridBagConstraints gbc = new GridBagConstraints ();
		addComponentToPanel (mControlPanel, mMenuButton, 1, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (mControlPanel, mExitButton, 0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.HORIZONTAL);
	}


	private void buildContentPanel ()
	{
		mContentPanel.setLayout (new GridBagLayout ());

		buildHeaderPanel ();
		buildTablePanel ();

		GridBagConstraints gbc = new GridBagConstraints ();
		addComponentToPanel (mContentPanel, mHeaderPanel,            0, 0, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
		addComponentToPanel (mContentPanel, mPlayersTableScrollPane, 0, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.CENTER, gbc, GridBagConstraints.BOTH);
	}


	private void buildHeaderPanel ()
	{
		mHeaderPanel.setLayout (new GridBagLayout ());

		JLabel headerLabel  = new JLabel ("Top 3 winners:");
		JLabel winnersLabel = new JLabel (getMatchController ().getGroupTournamentTopThreeWinnersString ());
		JLabel dateLabel    = new JLabel (new Date ().toString ());

		GridBagConstraints gbc = new GridBagConstraints ();
		addComponentToPanel (mHeaderPanel, headerLabel,        0, 0, new Insets (0, 0, 0, 0), 0, 0.5, 1, 1, GridBagConstraints.WEST, gbc, GridBagConstraints.BOTH);
		addComponentToPanel (mHeaderPanel, winnersLabel,       0, 1, new Insets (0, 0, 0, 0), 0, 0.5, 1, 1, GridBagConstraints.WEST, gbc, GridBagConstraints.BOTH);
		addComponentToPanel (mHeaderPanel, dateLabel,          1, 0, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.NORTH, gbc, GridBagConstraints.BOTH);
		addComponentToPanel (mHeaderPanel, mSaveResultButton,  1, 1, new Insets (0, 0, 0, 0), 0, 0, 0, 1, GridBagConstraints.NORTH, gbc, GridBagConstraints.BOTH);
	}


	private void buildTablePanel ()
	{
		initializePlayerTableDataModel ();
		setTableStyle ();
	}


	private void setTableStyle ()
	{
		mPlayersTable.setOpaque                 (false);
		mPlayersTable.setEnabled                (false);
		mPlayersTable.setBackground             (new Color (255, 255, 255, 0));
		mPlayersTable.setIntercellSpacing       (new Dimension (5, 0));
		mPlayersTable.setRowSelectionAllowed    (false);
		mPlayersTable.setCellSelectionEnabled   (false);
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
