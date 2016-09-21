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

// TODO May me can split on game logic and rule logic and GUI logic

public class GameController
{
	private final Integer               MAX_PLAYER_SHOTS    = 3;
	private final Integer               WINING_SCORES_COUNT = 0;
	private final Integer               WINING_LEG_COUNT    = 3;
	private final Integer               PLAYER_START_SCORES = 301;

	private final MatchController       mMatchController;

	private GameControlGuiForm          mGameControlGuiForm;
	private GameDisplayGuiForm          mGameDisplayGuiForm;

	private PlayerObject                mCurrentPlayer;
	private PlayerObject                mPlayer;
	private PlayerObject                mOpponentPlayer;

	private Integer                     mCurrentPlayerShots;


	public GameController (MatchController matchController, ArrayList <PlayerObject> playersList)
	{
		mCurrentPlayerShots     = 0;
		mMatchController        = matchController;
		mPlayer                 = playersList.get (0);
		mOpponentPlayer         = playersList.get (1);

		startGame ();
	}


	private void startGame ()
	{
		setCurrentPlayer (mPlayer);

		mGameDisplayGuiForm     = new GameDisplayGuiForm (mCurrentPlayer, mOpponentPlayer);
		mGameControlGuiForm     = new GameControlGuiForm (this);
	}


	private void setCurrentPlayer (PlayerObject player)
	{
		mCurrentPlayer = player;
	}


	public void handleScoreInputFromControlGuiForm (int newEarnedScores)
	{
		updatePlayerScore (newEarnedScores);

		if (hasPlayerWonLeg ())
			beginNewLeg ();
		else
			beginRotation ();
	}


	private void updatePlayerScore (int newEarnedScores)
	{
		mCurrentPlayer.mPrevScore = mCurrentPlayer.mScore;
		mCurrentPlayer.mScore -= newEarnedScores;

		checkAndChangeScoreBasedOnRules ();
	}


	private void checkAndChangeScoreBasedOnRules ()
	{
		if (isScoreNegativeOrNumberOne (mCurrentPlayer.mScore))
			mCurrentPlayer.mScore = mCurrentPlayer.mPrevScore;
	}


	private void beginNewLeg ()
	{
		gameLegUpdate ();
		mGameDisplayGuiForm.updateGameLegData ();
	}


	private void gameLegUpdate ()
	{
		addWinningLegForCurrentPlayer ();
		gameLegRuleCheck ();
		resetCurrentPlayerShots ();
		resetPlayersScore ();
	}


	private void beginRotation ()
	{
		mGameDisplayGuiForm.updatePlayerData ();
		playerShotsRotation ();
		rotatePlayersSequence ();
	}


	private void playerShotsRotation ()
	{
		mCurrentPlayerShots++;
	}


	private boolean hasPlayerWonLeg ()
	{
		return mCurrentPlayer.mScore.equals (WINING_SCORES_COUNT);
	}


	private void addWinningLegForCurrentPlayer ()
	{
		mCurrentPlayer.mLeg ++;
	}


	private void resetPlayersScore ()
	{
		mPlayer.mScore          = PLAYER_START_SCORES;
		mOpponentPlayer.mScore  = PLAYER_START_SCORES;
	}


	private void gameLegRuleCheck ()
	{
		if (mCurrentPlayer.mLeg.equals (WINING_LEG_COUNT))
		{
			destroyGuiForms ();
			mMatchController.runActionsAfterGameController (getPlayersAsArrayList ());
		}
	}


	private ArrayList <PlayerObject> getPlayersAsArrayList ()
	{
		return new ArrayList <PlayerObject> ()
		{
			{
				add (mPlayer);
				add (mOpponentPlayer);
			}
		};
	}

	private void destroyGuiForms ()
	{
		mGameDisplayGuiForm.destroy ();
		mGameControlGuiForm.destroy ();
	}


	private boolean isScoreNegativeOrNumberOne (Integer scores)
	{
		return (scores == 1 || scores < 0);
	}


	private void rotatePlayersSequence ()
	{
		if (mCurrentPlayerShots.equals (MAX_PLAYER_SHOTS))
		{
			assignCurrentPlayer ();
			resetCurrentPlayerShots ();
		}
	}


	private void assignCurrentPlayer ()
	{
		if (mCurrentPlayer.equals (mPlayer))                mCurrentPlayer = mOpponentPlayer;
		else if (mCurrentPlayer.equals (mOpponentPlayer))   mCurrentPlayer = mPlayer;
	}


	private void resetCurrentPlayerShots ()
	{
		mCurrentPlayerShots = 0;
	}


	// Communication methods between forms
	// =============================================================================================================
	public Dimension getGameDisplayGuiSize ()
	{
		return mGameDisplayGuiForm.getSize ();
	}


	public Point getGameDisplayGuiLocation ()
	{
		return mGameDisplayGuiForm.getLocation ();
	}
}
