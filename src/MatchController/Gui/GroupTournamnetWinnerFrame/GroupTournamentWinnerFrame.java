package MatchController.Gui.GroupTournamnetWinnerFrame;

import MainController.MainController;
import MatchController.MatchController;
import MatchController.Objects.PlayerObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class GroupTournamentWinnerFrame extends GroupTournamentWinnerFrameGui
{
	private DefaultTableModel       mDefaultTableModel;
	private String []               mPlayerTableHeaders;


	public GroupTournamentWinnerFrame (MatchController matchController)
	{
		super (matchController);
	}


	@Override
	protected void menuButtonAction ()
	{
		MainController.openMenuGui ();
		getMainJFrame ().setVisible (false);
		getMainJFrame ().dispose ();
	}


	@Override
	protected void exitButtonAction ()
	{
		menuButtonAction ();
		getMatchController ().exitFromApplication ();
	}


	@Override
	protected void saveResultAction ()
	{
		if (getMatchController ().savePlayersResult ())
			JOptionPane.showMessageDialog (null, "File successfully saved!");
		else
			JOptionPane.showMessageDialog (null, "Error: occurred: file can be saved!");
	}


	@Override
	protected void initializePlayerTableDataModel ()
	{
		mPlayerTableHeaders = new String[] {getCOLUMN_NAME (), getCOLUMN_LOSES (), getCOLUMN_WIN_POINTS ()};
		mDefaultTableModel  = new DefaultTableModel ();

		mDefaultTableModel.setDataVector (new Object[][]{}, mPlayerTableHeaders);
		populateTableModelWithPlayersData (getMatchController ().getPlayerList ());
		getPlayersTable ().setModel (mDefaultTableModel);
	}


	private void populateTableModelWithPlayersData (ArrayList<PlayerObject> playerObjectArrayList)
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
}
