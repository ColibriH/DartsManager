package MatchController.Gui.GroupTournamentTable;

import MatchController.MatchController;
import MatchController.Objects.PlayerObject;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class GroupTournamentTable extends GroupTournamentTableGui
{
	private DefaultTableModel       mDefaultTableModel;
	private String []               mPlayerTableHeaders;


	@Override
	protected void updateTable ()
	{
		initializePlayerTableDataModel ();
	}


	@Override
	protected void initializePlayerTableDataModel ()
	{
		mPlayerTableHeaders = new String[] {getCOLUMN_NAME (), getCOLUMN_LOSES (), getCOLUMN_WIN_POINTS ()};
		mDefaultTableModel = new DefaultTableModel ();
		mDefaultTableModel.setDataVector (new Object[][]{}, mPlayerTableHeaders);

		populateTableModelWithPlayersData (getMatchController ().getPlayerList ());
		getPlayersTable ().setModel (mDefaultTableModel);
		setColumnWidth ();
	}


	private void populateTableModelWithPlayersData (ArrayList <PlayerObject> playerObjectArrayList)
	{
		for (PlayerObject playerObject : playerObjectArrayList)
		{
			mDefaultTableModel.addRow (new String[]
			{
				playerObject.getName (),
				playerObject.getLooses ().toString (),
				playerObject.getWinPoints ().toString ()
			});
		}
	}


	public GroupTournamentTable (MatchController matchController)
	{
		super (matchController);
	}
}
