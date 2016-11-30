package TournamentGameController;

import TournamentGameController.GUI.TournamentGameControlGuiForm;
import TournamentGameController.GUI.TournamentGameDisplayGuiForm;
import MatchController.Objects.PlayerObject;
import MatchController.MatchController;

import java.awt.*;
import java.util.ArrayList;

// TODO May me can split on game logic and rule logic and Gui logic

public class TournamentGameController
{
	private final Integer                   MAX_PLAYER_SHOTS    = 3;
	private final Integer                   WINING_SCORES_COUNT = 0;
	private final Integer                   WINING_LEG_COUNT    = 3;
	private final Integer                   PLAYER_START_SCORES = 301;
	private final MatchController           mMatchController;

	private TournamentGameControlGuiForm    mTournamentGameControlGuiForm;
	private TournamentGameDisplayGuiForm    mTournamentGameDisplayGuiForm;
	private PlayerObject                    mCurrentPlayer;
	private PlayerObject                    mPlayer;
	private PlayerObject                    mOpponentPlayer;
	private Integer                         mCurrentPlayerShots;


	public TournamentGameController (MatchController matchController, ArrayList <PlayerObject> playersList)
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

		mTournamentGameDisplayGuiForm = new TournamentGameDisplayGuiForm (mCurrentPlayer, mOpponentPlayer);
		mTournamentGameControlGuiForm = new TournamentGameControlGuiForm (this);

		showCurrentPlayerTurn (true);
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
		mCurrentPlayer.setPrevScore (mCurrentPlayer.getScore ());
		mCurrentPlayer.setScore (mCurrentPlayer.getScore () - newEarnedScores);

		checkAndChangeScoreBasedOnRules ();
	}


	private void checkAndChangeScoreBasedOnRules ()
	{
		if (isScoreNegativeOrNumberOne (mCurrentPlayer.getScore()))
			mCurrentPlayer.setScore(mCurrentPlayer.getPrevScore());
	}


	private void beginNewLeg ()
	{
		updateGameLeg ();
		mTournamentGameDisplayGuiForm.updateGameLegData ();
	}


	private void updateGameLeg ()
	{
		addWinningLegForCurrentPlayer ();
		checkGameLegRule ();
		resetCurrentPlayerShots ();
		resetPlayersScore ();
	}


	private void beginRotation ()
	{
		mTournamentGameDisplayGuiForm.updatePlayerData ();
		rotatePlayerShots ();
		rotatePlayersSequence ();
	}


	private void rotatePlayerShots ()
	{
		mCurrentPlayerShots++;
	}


	private void addWinningLegForCurrentPlayer ()
	{
		mCurrentPlayer.setLeg (mCurrentPlayer.getLeg() + 1);
	}


	private void resetPlayersScore ()
	{
		mPlayer.setScore (PLAYER_START_SCORES);
		mOpponentPlayer.setScore (PLAYER_START_SCORES);
	}


	private void checkGameLegRule ()
	{
		if (mCurrentPlayer.getLeg().equals (WINING_LEG_COUNT))
		{
			destroyTournamentGameGuiForms ();
			mMatchController.runActionsAfterTournamentGameController (mCurrentPlayer);
		}
	}


	private void destroyTournamentGameGuiForms ()
	{
		mTournamentGameDisplayGuiForm.destroy ();
		mTournamentGameControlGuiForm.destroy ();
	}


	private void rotatePlayersSequence ()
	{
		if (mCurrentPlayerShots.equals (MAX_PLAYER_SHOTS))
		{
			assignCurrentPlayer ();
			resetCurrentPlayerShots ();
			showCurrentPlayerTurn ();
		}
	}


	private void showCurrentPlayerTurn ()
	{
		hidePlayersArrows ();
		showCurrentPlayerTurn (true);
	}


	private void showCurrentPlayerTurn (boolean state)
	{
		mCurrentPlayer.getTurnArrow ().setVisible (state);
	}


	private void hidePlayersArrows ()
	{
		mPlayer         .getTurnArrow ().setVisible (false);
		mOpponentPlayer .getTurnArrow ().setVisible (false);
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


	private boolean hasPlayerWonLeg ()
	{
		return mCurrentPlayer.getScore().equals (WINING_SCORES_COUNT);
	}


	private boolean isScoreNegativeOrNumberOne (Integer scores)
	{
		return (scores == 1 || scores < 0);
	}


	private void setCurrentPlayer (PlayerObject player)
	{
		mCurrentPlayer = player;
	}


	// Communication methods between forms
	// =============================================================================================================
	public Dimension getGameDisplayGuiSize ()
	{
		return mTournamentGameDisplayGuiForm.getSize ();
	}


	public Point getGameDisplayGuiLocation ()
	{
		return mTournamentGameDisplayGuiForm.getLocation ();
	}
}
