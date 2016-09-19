package MatchController;

import GameController.GameController;
import MatchController.Objects.PlayerObject;
import MatchController.GUI.GameManagerGuiForm;
import MatchController.GUI.PlayerGeneratedGroupsGuiForm;
import MatchController.GUI.WinnerGuiForm;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vladislavs on 06.09.2016..
 */

// TODo Create FORM controller

// TODo Create data binding to automatic data view if possible

// TODO Game Save for unexpected exit

// TODO all player result?

// TODO allPlayer places?

// TODO more than 2 people in one group -> little bit changes in rules check this! (for this need huge refactor and logic changes)

// TODO Create documentation

// TODO Check and put try-catch blocs where is needed

// TODO Create huge code refactor

// TODO Solve problem with even player count

// TODO Add edit option to main form

// TODO style configuration
// TODO Solve all bugs and hacks
// TODO Draw style and component uniqueness
// TODO style players turn

// TODO Solve problem with player turn sequence

// TODO Create menus, back button

// TODO Create functionality to display next group after each players game
// TODO Create functionality to save each player scores and etc



// Specific: matchController can communicate with forms (to retrieve data)

public class MatchController
{
	private GameManagerGuiForm                            gameManagerGuiForm;
	private PlayerGeneratedGroupsGuiForm                  playerGeneratedGroupsGuiForm;
	private WinnerGuiForm                                 winnerGuiForm;
	private GameController                                mGameController;

	private ArrayList <PlayerObject>                      mPlayerList;
	private HashMap <Integer, Integer[]>                  mPlayerGroupsMap;
	private HashMap <Integer, PlayerObject>               mStageWinnerHashMap;

	private int                                           mGroupSequenceNumber;


	public MatchController ()
	{
		initializeMatchController ();
	}


	private void initializeMatchController ()
	{
		mGroupSequenceNumber        = 0;
		mStageWinnerHashMap         = new HashMap <> ();
		gameManagerGuiForm          = new GameManagerGuiForm (this);
	}


	private void setPlayerList (ArrayList <PlayerObject> tablePlayerList)
	{
		mPlayerList =  new ArrayList <> (tablePlayerList);
	}


	public void runActionsAfterPlayerRegistration (ArrayList <PlayerObject> tablePlayerList)
	{
		setPlayerList               (tablePlayerList);
		matchManagerGuiFormClose    ();
		initializePlayersGroups     ();
		displayGameGroups           ();
	}


	private void matchManagerGuiFormClose ()
	{
		// TODO Create correct handle of form to close form and reuse of form (if need to go back)
		gameManagerGuiForm.setVisibility (false);
	}


	private void playerGeneratedGroupsGuiFormClose ()
	{
		// TODO Create correct handle of form to close form and reuse of form (if need to go back)
		playerGeneratedGroupsGuiForm.setVisibility (false);
	}


	private void initializePlayersGroups ()
	{
		mPlayerGroupsMap = GroupGenerator.generateRandomGroups (mPlayerList);
	}


	public void runActionsAfterGroupDisplay ()
	{
		playerGeneratedGroupsGuiFormClose ();
		startGame ();
	}


	private void displayGameGroups()
	{
		playerGeneratedGroupsGuiForm = new PlayerGeneratedGroupsGuiForm (this, mPlayerList, mPlayerGroupsMap);
	}


	private ArrayList <PlayerObject> getGameOpponents ()
	{
		ArrayList <PlayerObject> playerObjectsList = new ArrayList <> ();

		Integer [] playersIds = mPlayerGroupsMap.get (getNextPlayersGroupNumber ());
		for (Integer playerId : playersIds)
			playerObjectsList.add (getPlayerObjectById (playerId));     // TODO NULL possible - handle this!

		return playerObjectsList;
	}


	private int getNextPlayersGroupNumber ()
	{
		return ++mGroupSequenceNumber;
	}


	private PlayerObject getPlayerObjectById (Integer playerId)
	{
		for (PlayerObject player : mPlayerList)
			if (player.mId.equals (playerId))
				return player;

		return null;      // NOT Possible if correct work!!
	}


	// TODO refactor
	public void runActionsAfterGameController (ArrayList <PlayerObject> playerObjectArrayListResult)
	{
		PlayerObject winner = getWinnersPlayerObject (playerObjectArrayListResult);
		mStageWinnerHashMap.put (mGroupSequenceNumber, winner);

		if (mPlayerGroupsMap.size () == mGroupSequenceNumber)
		{
			boolean isFinalStage = groupsStageRotationAndCheckFinalStage ();
			if (isFinalStage)
			{
				winnerGuiForm = new WinnerGuiForm (this, winner);
				return;
			}

			displayWinner (winner.mName, true);

			return;
		}

		displayWinner (winner.mName);
	}


	private void displayWinner (String winnerName)
	{
		playerGeneratedGroupsGuiForm.displayWinner (mGroupSequenceNumber, winnerName);
		playerGeneratedGroupsGuiForm.setVisibility (true);
	}


	private void displayWinner (String winnerName, boolean isNextStage)
	{
		playerGeneratedGroupsGuiForm.displayWinner (mGroupSequenceNumber, winnerName, isNextStage);
		playerGeneratedGroupsGuiForm.setVisibility (true);
	}


	public void nextStageTrigger ()
	{
		playerGeneratedGroupsGuiForm.destroy ();
		mGroupSequenceNumber = 0;
		displayGameGroups();
	}


	private boolean groupsStageRotationAndCheckFinalStage ()
	{
		mPlayerGroupsMap.clear ();
		int winnersHashMapSize = mStageWinnerHashMap.size ();

		if (winnersHashMapSize == 1)
			return true;

		for (int i = 1; i < winnersHashMapSize; i = i + 1)
		{
			PlayerObject currentPlayer = mStageWinnerHashMap.get (i);
			PlayerObject nextPlayer = mStageWinnerHashMap.get (i + 1);

			Integer [] newPlayersGroup = {};
			if (currentPlayer != null && nextPlayer != null)
				newPlayersGroup = new Integer[] {currentPlayer.mId, nextPlayer.mId};


			if (i + 1 > winnersHashMapSize)
				mPlayerGroupsMap.put (i, new Integer[]{currentPlayer.mId}); // TODO handle null
			else
				mPlayerGroupsMap.put (i, newPlayersGroup);
		}

		mStageWinnerHashMap.clear ();

		return false;
	}


	private PlayerObject getWinnersPlayerObject (ArrayList<PlayerObject> playerObjectArrayListResult)
	{
		return (playerObjectArrayListResult.get (0).mLeg > playerObjectArrayListResult.get (1).mLeg) ?
				playerObjectArrayListResult.get (0) :
				playerObjectArrayListResult.get (1);    // TODO Refactor bad code
	}


	public void newMatch ()
	{
		if (winnerGuiForm != null)
			winnerGuiForm = null;

		initializeMatchController ();
	}


	private void startGame ()
	{
		if (mGameController != null)
			mGameController = null;

		mGameController = new GameController (this, getGameOpponents ());
	}
}
