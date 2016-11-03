package MatchController;

import GameController.GameController;
import MainController.MainController;
import MatchController.GUI.DisplayGroupPanel;
import MatchController.Objects.PlayerObject;
import MatchController.GUI.GameManagerGuiForm;
import MatchController.GUI.WinnerGuiForm;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladislavs on 06.09.2016..
 */

//TODO Refactor

public class MatchController
{
	private GameManagerGuiForm                            mGameManagerGuiForm;
	private PlayerGeneratedGroupsGuiForm                  mPlayerGeneratedGroupsGuiForm;
	private WinnerGuiForm                                 mWinnerGuiForm;
	private GameController                                mGameController;

	private ArrayList <PlayerObject>                      mPlayerList;
	private HashMap <Integer, ArrayList <Integer>>        mPlayerGroupsMap;
	private HashMap <Integer, PlayerObject>               mStageWinnerHashMap;

	private Integer                                       mCurrentPlayingGroupNumber;
	private Integer                                       mPlayersNumberInGroup;


	public MatchController ()
	{
		initializeNewMatch ();
	}


	public void initializeNewMatch ()
	{
		mPlayersNumberInGroup       = 2;
		mCurrentPlayingGroupNumber  = 0;
		mStageWinnerHashMap         = new HashMap <> ();
		whetherToKeepOldPlayerList ();
		mGameManagerGuiForm = new GameManagerGuiForm (this);
	}


	private void whetherToKeepOldPlayerList ()
	{
		if (mPlayerList != null)
			if (! keepPlayers ())
				mPlayerList = null;
	}


	private boolean keepPlayers ()
	{
		int result = JOptionPane.showConfirmDialog (null, "Do you want to keep old player list?",
		                                            "alert", JOptionPane.OK_CANCEL_OPTION);
		return result == 0;
	}


	private void setPlayerList (ArrayList <PlayerObject> tablePlayerList)
	{
		mPlayerList =  new ArrayList <> (tablePlayerList);
	}


	public void runActionsAfterPlayerRegistration (Integer playersNumberInGroup, ArrayList <PlayerObject> tablePlayerList)
	{
		mPlayersNumberInGroup = playersNumberInGroup;

		setPlayerList (tablePlayerList);
		matchManagerGuiFormClose ();
		initializePlayersGroups ();

		try
		{
			ifOnePlayerInGroupPromoteToNextStage ();
			displayGameGroups ();
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	private void ifOnePlayerInGroupPromoteToNextStage () throws Exception
	{
		for (Object o : mPlayerGroupsMap.entrySet ())
		{
			Map.Entry <Integer, ArrayList <Integer>> pair = (Map.Entry <Integer, ArrayList <Integer>>) o;   // Could by cast exception at runtime!
			ArrayList <Integer> value =  pair.getValue ();

			if (value.size () < mPlayersNumberInGroup)
			{
				Integer playerId = pair.getKey ();

				mStageWinnerHashMap.put (playerId, getPlayerObjectById (playerId));
				mPlayerGroupsMap.remove (pair.getKey ());
			}
		}
	}


	private void matchManagerGuiFormClose ()
	{
		mGameManagerGuiForm.destroy ();
		mGameManagerGuiForm = null;
	}


	private void playerGeneratedGroupsGuiFormClose ()
	{
		// TODO Create correct handle of form to close form and reuse of form (if need to go back)
		mPlayerGeneratedGroupsGuiForm.setVisibility (false);
	}


	private void initializePlayersGroups ()
	{
		mPlayerGroupsMap = GroupGenerator.generateRandomGroups (mPlayersNumberInGroup, mPlayerList);
	}


	public void runActionsAfterGroupDisplay ()
	{
		try
		{
			playerGeneratedGroupsGuiFormClose ();
			startGame ();
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	private void displayGameGroups ()
	{
		mPlayerGeneratedGroupsGuiForm = new PlayerGeneratedGroupsGuiForm (this, mPlayerList, mPlayerGroupsMap, mCurrentPlayingGroupNumber);
	}


	private ArrayList <PlayerObject> getGameOpponents () throws Exception
	{
		ArrayList <PlayerObject> playerObjectsList = new ArrayList <> ();

		ArrayList <Integer> playersIds = mPlayerGroupsMap.get (mCurrentPlayingGroupNumber);
		for (Integer playerId : playersIds)
			playerObjectsList.add (getPlayerObjectById (playerId));

		return playerObjectsList;
	}


	public PlayerObject getPlayerObjectById (Integer playerId) throws Exception
	{
		for (PlayerObject player : mPlayerList)
			if (player.mId.equals (playerId))
				return player;

		throw new Exception ("Can`t find player object by id! Bug spotted");
	}


	public void setPlayersGeneratedGroupLink (ArrayList <Integer> pIds, DisplayGroupPanel dgp)
	{
		try
		{
			for (Integer pId : pIds)
				getPlayerObjectById (pId).setDisplayGroupPanel (dgp);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}


	public void runActionsAfterGameController (ArrayList <PlayerObject> playerObjectArrayListResult)
	{
		try
		{
			PlayerObject winner = getWinnersPlayerObject (playerObjectArrayListResult);
			resetPlayerLegData (playerObjectArrayListResult);
			mStageWinnerHashMap.put (mCurrentPlayingGroupNumber, winner);

			mCurrentPlayingGroupNumber++;

			if (ifLastGroupPlayed ())
				handleLastGroupPlayedState (winner);
			else
				displayWinner (winner);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	private void resetPlayerLegData (ArrayList<PlayerObject> playerObjectArrayList)
	{
		for (PlayerObject player : playerObjectArrayList)
			player.mLeg = 0;
	}


	private void handleLastGroupPlayedState (PlayerObject winner) throws Exception
	{
		if (ifFinalStage ())
		{
			showMatchWinner (winner);
		}
		else
		{
			groupsStageRotation ();
			displayWinnerAndNextStage (winner);
		}
	}


	private void showMatchWinner (PlayerObject winner)
	{
		mWinnerGuiForm = new WinnerGuiForm (this, winner);
	}


	private boolean ifLastGroupPlayed ()
	{
		return getGroupCount ().equals (mCurrentPlayingGroupNumber);
	}


	private Integer getGroupCount ()
	{
		return mPlayerGroupsMap.size ();
	}


	private void displayWinner (PlayerObject winner)
	{
		mPlayerGeneratedGroupsGuiForm.displayWinnerPanelInGroup (winner);
		mPlayerGeneratedGroupsGuiForm.setVisibility (true);
	}


	private void  displayWinnerAndNextStage (PlayerObject winner)
	{
		mPlayerGeneratedGroupsGuiForm.displayWinnerAndNextStage (winner);
		mPlayerGeneratedGroupsGuiForm.setVisibility (true);
	}


	public void nextStageTrigger ()
	{
		mPlayerGeneratedGroupsGuiForm.destroy ();
		mCurrentPlayingGroupNumber = 0;

		displayGameGroups();
	}


	private void groupsStageRotation () throws Exception
	{
		mPlayerGroupsMap.clear ();

		int winnersHashMapSize = mStageWinnerHashMap.size ();

		for (int i = 0; i < winnersHashMapSize - 1; i++)
		{
			PlayerObject currentPlayer  = mStageWinnerHashMap.get (i);
			PlayerObject nextPlayer     = mStageWinnerHashMap.get (i + 1);

			if (currentPlayer == null || nextPlayer == null)
				throw new Exception ("Can`t retrieve id from current player object");

			if (i + 1 > winnersHashMapSize)
				mPlayerGroupsMap.put (i, new ArrayList <Integer> ()
				{
					{
						add (currentPlayer.mId);
					}
				});
			else
				mPlayerGroupsMap.put (i, new ArrayList <Integer> ()
				{
					{
						add (currentPlayer.mId);
						add (nextPlayer.mId);
					}
				});
		}

		mStageWinnerHashMap.clear ();
	}


	private boolean ifFinalStage ()
	{
		return mStageWinnerHashMap.size () == 1;
	}


	private PlayerObject getWinnersPlayerObject (ArrayList<PlayerObject> playerObjectArrayListResult)
	{
		return (playerObjectArrayListResult.get (0).mLeg > playerObjectArrayListResult.get (1).mLeg) ?
				playerObjectArrayListResult.get (0) :
				playerObjectArrayListResult.get (1);
	}


	public ArrayList <PlayerObject> getPlayerList ()
	{
		return mPlayerList;
	}


	public void newMatch ()
	{
		if (mWinnerGuiForm != null)
			mWinnerGuiForm = null;

		initializeNewMatch ();
	}


	public Integer getCurrentPlayingGroupNumber ()
	{
		return mCurrentPlayingGroupNumber;
	}


	private void startGame () throws Exception
	{
		if (mGameController != null)
			mGameController = null;

		mGameController = new GameController (this, getGameOpponents ());
	}


	public void openMenuGuiForm ()
	{
		mGameManagerGuiForm.destroy ();
		mGameManagerGuiForm = null;
		MainController.openMenuGui ();
	}


	public void openGameManagerGuiForm ()
	{
		mPlayerGeneratedGroupsGuiForm.destroy ();
		mPlayerGeneratedGroupsGuiForm = null;
		mGameManagerGuiForm = new GameManagerGuiForm (this);
	}
}
