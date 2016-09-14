package GameController;

import GameController.GUI.GameControlGuiForm;
import GameController.GUI.GameDisplayGuiForm;
import GameController.Object.PlayerObject;
import MatchController.MatchController;
import MatchController.Objects.NewPlayerObject;

import java.util.ArrayList;

/**
 * Created by vladislavs on 07.09.2016..
 */

// TODO huge refactor need bad code spotted ( code was written in hurry )

public class GameController
{
	private final MatchController       mMatchController;

	private GameControlGuiForm          mGameControlGuiForm;
	private GameDisplayGuiForm          mGameDisplayGuiForm;

	private ArrayList <PlayerObject>    mPlayers;

	private int     playersSequenceNumber = 1;
	private int     mShotsCount = 0;
	private boolean mLegEndFlag = false;

	public GameController (MatchController matchController, ArrayList <NewPlayerObject> players)
	{
		mMatchController    = matchController;
		mPlayers            = convertToPlayerObjectArrayList (new ArrayList <NewPlayerObject> (players));

		mGameControlGuiForm = new GameControlGuiForm (this);
		mGameDisplayGuiForm = new GameDisplayGuiForm (mPlayers.get (0), mPlayers.get (1));
	}


	private ArrayList<PlayerObject> convertToPlayerObjectArrayList (ArrayList<NewPlayerObject> newPlayerObjects)
	{
		ArrayList <PlayerObject> playerObjects = new ArrayList <> ();

		for (NewPlayerObject newPlayerObject: newPlayerObjects)
			playerObjects.add (new PlayerObject (newPlayerObject));

		return playerObjects;
	}

	// TODO Rename function
	public void calculateScore (int newEarnedScores)
	{
		updatePlayerScore (newEarnedScores);

		if (! mLegEndFlag)
		{
			mGameDisplayGuiForm.setPlayerScore (playersSequenceNumber, getCurrentPlayerScore ());
			mShotsCount++;

			rotatePlayerSequence ();
		}
		else
		{
			beginNewLeg ();
		}
	}


	private void beginNewLeg ()
	{
		gameLegUpdate ();
		mGameDisplayGuiForm.updateGameLegData (mPlayers.get (0), mPlayers.get (1));
	}


	private void gameLegUpdate ()
	{
		PlayerObject tmpObj = new PlayerObject (mPlayers.get (playersSequenceNumber - 1));
		tmpObj.mLeg += 1;

		mPlayers.set (playersSequenceNumber - 1, tmpObj);
		gameLegRuleCheck ();
		resetPlayersScore ();
		mLegEndFlag = false;
	}


	private void resetPlayersScore ()
	{
		for (PlayerObject playerObject : mPlayers)
			playerObject.mScore = 301;
	}


	// TODO Rethink place of function
	private void gameLegRuleCheck ()
	{
		for (PlayerObject playerObject : mPlayers)
		{
			if (playerObject.mLeg == 3)
			{
				destroyGuiForms ();
				mMatchController.runActionsAfterGameController (mPlayers);
				return;
			}
		}
	}


	private void destroyGuiForms ()
	{
		mGameControlGuiForm.destroy ();
		mGameDisplayGuiForm.destroy ();
	}


	private int getCurrentPlayerScore ()
	{
		return mPlayers.get (playersSequenceNumber - 1).mScore;
	}


	// TODO Refactor name of function
	private void updatePlayerScore (int newEarnedScores)
	{
		PlayerObject tmpObj = new PlayerObject (mPlayers.get (playersSequenceNumber - 1));
		tmpObj.mPrevScore = tmpObj.mScore;
		tmpObj.mScore -= newEarnedScores;

		int currentPlayerScore = tmpObj.mScore;

		if (currentPlayerScore == 0)
		{
			mLegEndFlag = true;
		}
		else if (currentPlayerScore == 1 || currentPlayerScore < 0)
		{
			tmpObj.mScore = tmpObj.mPrevScore;
		}

		mPlayers.set (playersSequenceNumber - 1, tmpObj);
	}


	private void rotatePlayerSequence ()
	{
		if (mShotsCount == 3)
		{
			if (playersSequenceNumber == 1) playersSequenceNumber = 2;
			else if (playersSequenceNumber == 2) playersSequenceNumber = 1;

			mShotsCount = 0;
		}
	}
}
