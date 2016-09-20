package GameController;

import GameController.GUI.GameControlGuiForm;
import GameController.GUI.GameDisplayGuiForm;
import MatchController.Objects.PlayerObject;
import MatchController.MatchController;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by vladislavs on 07.09.2016..
 */
// TODO DAta binf
// TODO huge refactor need bad code spotted ( code was written in hurry )

public class GameController
{
	private final MatchController       mMatchController;

	private GameControlGuiForm          mGameControlGuiForm;
	private GameDisplayGuiForm          mGameDisplayGuiForm;

	private ArrayList <PlayerObject>    mPlayers;

	private int                         playersSequenceNumber;
	private int                         mShotsCount;
	private boolean                     mLegEndFlag;

	public GameController (MatchController matchController, ArrayList <PlayerObject> players)
	{
		playersSequenceNumber   = 1;
		mShotsCount             = 0;
		mLegEndFlag             = false;

		mMatchController        = matchController;
		mPlayers                = players;

		mGameDisplayGuiForm     = new GameDisplayGuiForm (mPlayers.get (0), mPlayers.get (1));
		mGameControlGuiForm     = new GameControlGuiForm (this);
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
		mGameDisplayGuiForm.destroy ();
		mGameControlGuiForm.destroy ();
	}


	public Dimension getGameDisplayGuiSize ()
	{
		return mGameDisplayGuiForm.getSize ();
	}


	public Point getGameDisplayGuiLocation ()
	{
		return mGameDisplayGuiForm.getLocation ();
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
