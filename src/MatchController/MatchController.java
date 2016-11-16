package MatchController;

import GameController.GameController;
import GroupsController.GroupsController;
import MainController.MainController;
import MatchController.Gui.Components.TournamentTableGroupPanel;
import MatchController.Gui.PlayersRegistration.PlayersRegistration;
import MatchController.Gui.TournamentTable.TournamentTable;
import MatchController.Gui.WinnerFrame.WinnerFrame;
import MatchController.Objects.GroupsTreeNode;
import MatchController.Objects.PlayerObject;
import Tools.GroupGenerator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

// TODO sort in encapsulation order
public class MatchController
{
	private PlayersRegistration         mPlayersRegistration;
	private TournamentTable             mTournamentTable;
	private WinnerFrame                 mWinnerGuiFrame;
	private GameController              mGameController;
	private GroupsController            mGroupsController;

	private ArrayList <PlayerObject>    mPlayerList;
	private Integer                     mPlayersNumberInGroup;


	public MatchController ()
	{
		initializeNewMatch ();
	}


	private void executeDebugCode ()
	{
		mPlayerList = new ArrayList<>();

		mPlayerList.add(new PlayerObject("0", 0));
		mPlayerList.add(new PlayerObject("1", 1));
		mPlayerList.add(new PlayerObject("2", 2));
		mPlayerList.add(new PlayerObject("3", 3));

		mPlayerList.add(new PlayerObject("4", 4));
		mPlayerList.add(new PlayerObject("5", 5));
		mPlayerList.add(new PlayerObject("6", 6));
		mPlayerList.add(new PlayerObject("7", 7));

		mPlayerList.add(new PlayerObject("8", 8));
		mPlayerList.add(new PlayerObject("9", 9));

		mPlayerList.add(new PlayerObject("10", 10));
		mPlayerList.add(new PlayerObject("11", 11));
		mPlayerList.add(new PlayerObject("12", 12));
		mPlayerList.add(new PlayerObject("13", 13));
//
//		mPlayerList.add(new PlayerObject("14", 14));
//		mPlayerList.add(new PlayerObject("15", 15));
//		mPlayerList.add(new PlayerObject("16", 16));
//		mPlayerList.add(new PlayerObject("17", 17));
//
//		mPlayerList.add(new PlayerObject("18", 18));
//		mPlayerList.add(new PlayerObject("19", 19));
//		mPlayerList.add(new PlayerObject("20", 20));
//		mPlayerList.add(new PlayerObject("21", 21));
	}


	private void whetherToKeepOldPlayerList ()
	{
		if (! MainController.DEBUG_MODE)
			if (mPlayerList != null)
				if (! keepPlayers ())
					mPlayerList = null;
	}


	private boolean keepPlayers ()
	{
		int result = JOptionPane.showConfirmDialog (null, "Do you want to keep old player list?", "alert", JOptionPane.OK_CANCEL_OPTION);
		return result == 0;
	}


	private void setPlayerList (ArrayList <PlayerObject> tablePlayerList)
	{
		mPlayerList =  new ArrayList <> (tablePlayerList);
	}


	//TODO Refactor
	private void ifOnePlayerInGroupPromoteToNextStage () throws Exception
	{
/*		for (Object o : mPlayerGroupsMap.entrySet ())
		{
			Map.Entry <Integer, ArrayList <Integer>> pair = (Map.Entry <Integer, ArrayList <Integer>>) o;   // Could by cast exception at runtime!
			ArrayList <Integer> value =  pair.getValue ();

			if (value.size () < mPlayersNumberInGroup)
			{
				Integer playerId = pair.getKey ();

				mStageWinnerHashMap.put (playerId, getPlayerObjectById (playerId));
				mPlayerGroupsMap.remove (pair.getKey ());
			}
		}*/
	}


	private void destroyPlayerRegistration ()
	{
		mPlayersRegistration.destroy ();
		mPlayersRegistration = null;
	}


	private void closeTournamentTable ()
	{
		mTournamentTable.setVisibility (false);
	}


	private void initializeMatchGroupsController ()
	{
		mGroupsController = new GroupsController (GroupGenerator.generateRandomGroups (mPlayersNumberInGroup, mPlayerList));
	}


	private void displayGameGroups ()
	{
		mTournamentTable = new TournamentTable (this);
	}


	private ArrayList <PlayerObject> getGameOpponents () throws Exception
	{
		return mGroupsController.getCurrentPlayingGroup ().getPlayerObjects ();
	}


	private void resetPlayerLegData (PlayerObject winningPlayerObject)
	{
		winningPlayerObject.setLeg(0);
	}


	private void showMatchWinner (PlayerObject winner)
	{
		mWinnerGuiFrame = new WinnerFrame (this, winner);
	}


	private void setNextGroupPlayingText ()
	{
		mTournamentTable.displayCurrentPlayingGroupText ();
		mTournamentTable.setVisibility (true);
	}


	private void startGame () throws Exception
	{
		if (mGameController != null)
			mGameController = null;

		mGameController = new GameController (this, getGameOpponents ());
	}


	//TODO Refactor
	public void runActionsAfterPlayerRegistration (Integer playersNumberInGroup, ArrayList <PlayerObject> tablePlayerList)
	{
		mPlayersNumberInGroup = playersNumberInGroup;

		setPlayerList (tablePlayerList);
		destroyPlayerRegistration ();
		initializeMatchGroupsController ();

		try
		{
			//ifOnePlayerInGroupPromoteToNextStage ();
			displayGameGroups ();
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	public void initializeNewMatch ()
	{
		mPlayersNumberInGroup = 2;

		if (MainController.DEBUG_MODE)
			executeDebugCode();

		whetherToKeepOldPlayerList ();
		mPlayersRegistration = new PlayersRegistration (this);
	}


	public HashMap <Integer, ArrayList <GroupsTreeNode>> getMatchGroups ()
	{
		return mGroupsController.getMatchGroups ();
	}


	public void runActionsAfterGroupDisplay ()
	{
		try
		{
			closeTournamentTable ();
			startGame ();
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	public void runActionsAfterGameController (PlayerObject winningPlayerObject)
	{
		try
		{
			if (mGroupsController.isLastGroupPlayed ())
			{
				showMatchWinner (winningPlayerObject);
			}
			else
			{
				resetPlayerLegData (winningPlayerObject);
				mGroupsController.promoteWinningPlayerToNextStage (winningPlayerObject);
				mGroupsController.rotateToNextGroup ();
				setNextGroupPlayingText ();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	public ArrayList <PlayerObject> getPlayerList ()
	{
		return mPlayerList;
	}


	public void newMatch ()
	{
		if (mWinnerGuiFrame != null)
			mWinnerGuiFrame = null;

		initializeNewMatch ();
	}


	public void openMenuAndDestroyPlayerRegistration ()
	{
		mPlayersRegistration.destroy ();
		mPlayersRegistration = null;
		MainController.openMenuGui ();
	}


	//TODO Refactor
	public void openGameManager ()
	{
		mTournamentTable.destroy ();
		mTournamentTable = null;
		mPlayersRegistration = new PlayersRegistration (this);
	}


	public ArrayList<TournamentTableGroupPanel> getAllMatchGroupsPanels ()
	{
		return mGroupsController.getAllMatchGroupsPanels ();
	}


	public TournamentTableGroupPanel getCurrentPlayingGroupPanel ()
	{
		return mGroupsController.getCurrentPlayingGroupPanel ();
	}


	public void exitFromApplication ()
	{
		System.exit (0);
	}
}
