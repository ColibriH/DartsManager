package MatchController.Gui.PlayersRegistration;

import Constants.Constats;
import MatchController.MatchController;
import MatchController.Objects.PlayerObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Vector;

public class PlayersRegistration extends PlayersRegistrationGui
{
	private DefaultTableModel       mDefaultTableModel;
	private String []               mPlayerTableHeaders;


	public PlayersRegistration (MatchController matchController)
	{
		super (matchController);
	}


	@Override
	protected void initializePlayerTableDataModel ()
	{
		mPlayerTableHeaders = new String[] {getCOLUMN_ID (), getCOLUMN_NAME (), Constats.DELETE_BTN_ID, Constats.EDIT_BTN_ID};
		mDefaultTableModel = new DefaultTableModel ()
		{
			public boolean isCellEditable (int row, int column)
			{
				return isTableInnerButton (column, true);
			}
		};

		mDefaultTableModel.setDataVector (new Object[][]{}, mPlayerTableHeaders);

		if (getMatchController ().getPlayerList () != null)
			populateTableModelWithOldPlayers (getMatchController ().getPlayerList ());

		getPlayersTable ().setModel (mDefaultTableModel);
	}


	@Override
	protected void tryToRegisterPlayers ()
	{
		ArrayList <PlayerObject> createdPlayers = getPlayersFromTable ();

		// 2 - minimum 2 players 1 vs 1
		if (createdPlayers.size () < 2)
		{
			JOptionPane.showMessageDialog (null, "Number of players have to be at least " + 2 + ".");
			return;
		}

		getMatchController ().setMaxPlayerLosePoints (Integer.parseInt (getPlayerLooseCntTxtField ().getText ()));
		getMatchController ().runActionsAfterPlayerRegistration (createdPlayers);
	}


	@Override
	protected void addNewPlayer ()
	{
		String playerName = getPlayerNameTxtField ().getText ();
		if (getPlayerNameTxtFieldDefaultValue ().equals (playerName) || playerName.isEmpty ())
			return;

		if (! isPlayerNameUnique (playerName))
		{
			JOptionPane.showMessageDialog(null, "That name already exist. Please, try another one.");
			return;
		}

		addNewPlayer (playerName);
		getPlayerNameTxtField ().setText (getPlayerNameTxtFieldDefaultValue ());
	}


	@Override
	protected ArrayList<PlayerObject> getPlayersFromTable ()
	{
		ArrayList <PlayerObject> returnPlayerList = new ArrayList <> ();
		Vector tableData = mDefaultTableModel.getDataVector ();

		try
		{
			for (Object rowData : tableData)
				returnPlayerList.add (getPlayerObjectNewInstance ((Vector) rowData));
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}

		return returnPlayerList;
	}


	@Override
	protected PlayerObject getPlayerObjectNewInstance (Vector rowData)
	{
		try
		{
			String playerName       = (String) rowData.get (getColumnNumberByName (getCOLUMN_NAME ()));
			String playerIdString   = (String) rowData.get (getColumnNumberByName (getCOLUMN_ID ()));
			Integer playerId        = Integer.parseInt (playerIdString);

			return new PlayerObject (playerName, playerId);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}

		return null;
	}


	@Override
	protected PlayersRegistration getPlayerRegistrationObject ()
	{
		return this;
	}


	@Override
	protected boolean isTableInnerButton (int column, boolean hackFlag)
	{
		int hackInt = 0;
		if (hackFlag)
			hackInt = 1;

		//TODO resolve issue based on hidden column
		return getPlayersTable ().getColumnName (column - hackInt).equals (Constats.DELETE_BTN_ID) ||
				getPlayersTable ().getColumnName (column - hackInt).equals (Constats.EDIT_BTN_ID);
	}


	private boolean isPlayerNameUnique (String playerName)
	{
		for (Object rowData : mDefaultTableModel.getDataVector ())
			if (getPlayerObjectNewInstance ((Vector) rowData).getName ().equals (playerName))
				return false;

		return true;
	}


	private void populateTableModelWithOldPlayers (ArrayList <PlayerObject> playersArrayList)
	{
		for (PlayerObject playerObject : playersArrayList)
		{
			mDefaultTableModel.addRow (new String[]
			{
				String.valueOf (playerObject.getId ()),
				playerObject.getName (),
				Constats.DELETE_BTN_ID,
				Constats.EDIT_BTN_ID
			});
		}
	}


	private void addNewPlayer (String Name)
	{
		// Players Id`s are connected with data model size (to get last id we need to get data model size)
		int lastInsertedId = mDefaultTableModel.getRowCount ();
		mDefaultTableModel.addRow (new String []
		{
			String.valueOf (lastInsertedId),
			Name,
			Constats.DELETE_BTN_ID,
			Constats.EDIT_BTN_ID
		});
	}


	private int getColumnNumberByName (String columnName)
	{
		int columnNumber = 0;
		for (String cName : mPlayerTableHeaders)
		{
			if (cName.equals (columnName))
				return columnNumber;

			columnNumber++;
		}

		return -1;
	}


	private String getEditedText (String oldTxt)
	{
		JFrame frame = new JFrame();
		String result = JOptionPane.showInputDialog (frame, "Enter new name:");

		return  (result.isEmpty ()) ? oldTxt : result;
	}


	private boolean isDeleteConfirmed ()
	{
		int result = JOptionPane.showConfirmDialog (null, "Are you sure, you want to delete player?",
		                                            "alert", JOptionPane.OK_CANCEL_OPTION);
		return result == 0;
	}

	@Override
	public void deleteNewPlayerFromTable ()
	{
		int selectedRow = getPlayersTable ().getSelectedRow ();

		if (mDefaultTableModel.getRowCount () == 0)
			return;

		if (selectedRow == -1)
		{
			JOptionPane.showMessageDialog (null, "Please select player in the table to delete.");
			return;
		}

		if (isDeleteConfirmed ())
			mDefaultTableModel.removeRow (selectedRow);
	}


	public void editNewPlayerInTable ()
	{
		int selectedRow     = getPlayersTable ().getSelectedRow ();
		int selectedColumn  = getPlayersTable ().getColumn (getCOLUMN_NAME ()).getModelIndex ();

		if (mDefaultTableModel.getRowCount () == 0)
			return;

		if (selectedRow == -1)
		{
			JOptionPane.showMessageDialog (null, "Please select player in the table to Edit.");
			return;
		}

		String currentCellValue = (String) mDefaultTableModel.getValueAt (selectedRow, selectedColumn);
		mDefaultTableModel.setValueAt (getEditedText (currentCellValue), selectedRow, selectedColumn);
	}
}
