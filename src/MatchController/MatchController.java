package MatchController;

import Constants.Constats;
import GameController.GameController;
import GroupsController.GroupTournamentGroupsController;
import GroupsController.TournamentGroupsController;
import MainController.MainController;
import MatchController.Gui.Components.TournamentTableGroupPanel;
import MatchController.Gui.GroupTournamentTable.GroupTournamentTable;
import MatchController.Gui.GroupTournamnetWinnerFrame.GroupTournamentWinnerFrame;
import MatchController.Gui.PlayersRegistration.PlayersRegistration;
import MatchController.Gui.TournamentTable.TournamentTable;
import MatchController.Gui.TournamentWinnerFrame.TournamentWinnerFrame;
import MatchController.Objects.GroupPlayerObject;
import MatchController.Objects.GroupsTreeNode;
import MatchController.Objects.PlayerObject;
import Tools.GroupGenerator;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

public class MatchController
{
	// TODO Create Base class to some of next classes to reduce cnt of it
	private PlayersRegistration             mPlayersRegistration;
	private TournamentGroupsController      mTournamentGroupsController;
	private GroupTournamentGroupsController mGroupsTournamentGroupsController;
	private TournamentTable                 mTournamentTable;
	private GroupTournamentTable            mGroupTournamentTable;
	private TournamentWinnerFrame           mTournamentWinnerFrame;
	private GroupTournamentWinnerFrame      mGroupTournamentWinnerFrame;
	private GameController                  mGameController;


	private ArrayList <PlayerObject>        mPlayerList;
	private Constats.GameType               mGameType;

	private Integer                         mMaxPlayerLosePoints;


	public MatchController (Constats.GameType gameType)
	{
		initializeNewMatch (gameType);
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
//
//		mPlayerList.add(new PlayerObject("8", 8));
//		mPlayerList.add(new PlayerObject("9", 9));
//
//		mPlayerList.add(new PlayerObject("10", 10));
//		mPlayerList.add(new PlayerObject("11", 11));
//		mPlayerList.add(new PlayerObject("12", 12));
//		mPlayerList.add(new PlayerObject("13", 13));
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
		if (isGameTypeTournament ())
			mTournamentGroupsController = new TournamentGroupsController (GroupGenerator.generateTournamentRandomGroups (2, mPlayerList));  // 2 - two people - 1 vs 1
		else if (isGameTypeGroupTournament ())
			mGroupsTournamentGroupsController = new GroupTournamentGroupsController (this, GroupGenerator.generateGroupTournamentGroups (mPlayerList));
	}


	private void displayGameGroups ()
	{
		if (isGameTypeTournament ())
			mTournamentTable = new TournamentTable (this);
		else if (isGameTypeGroupTournament ())
			mGroupTournamentTable = new GroupTournamentTable (this);
	}


	private void resetPlayerLegData (PlayerObject winningPlayerObject)
	{
		winningPlayerObject.setLeg(0);
	}


	private void showMatchWinner (PlayerObject winner)
	{
		mTournamentWinnerFrame = new TournamentWinnerFrame (this, winner);
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


	private void promotePlayerAndRotateToNextStage (PlayerObject winningPlayerObject)
	{
		mTournamentGroupsController.promoteWinningPlayerToNextStage (winningPlayerObject);
		mTournamentGroupsController.rotateToNextGroup ();
		setNextGroupPlayingText ();
	}


	public void initializeNewMatch (Constats.GameType gameType)
	{
		mMaxPlayerLosePoints = 5;
		mGameType = gameType;

		if (MainController.DEBUG_MODE)
			executeDebugCode();

		whetherToKeepOldPlayerList ();
		mPlayersRegistration = new PlayersRegistration (this);
	}


	private ArrayList<PlayerObject> tryToFindLosers ()
	{
		ArrayList <PlayerObject> losers = new ArrayList <> ();
		for (PlayerObject player : mPlayerList)
			if (player.getLooses ().equals (mMaxPlayerLosePoints))
				losers.add (player);

		return losers.size () != 0 ? losers : null;
	}


	public void runActionsAfterPlayerRegistration (ArrayList <PlayerObject> tablePlayerList)
	{
		setPlayerList (tablePlayerList);
		destroyPlayerRegistration ();
		initializeMatchGroupsController ();

		try
		{
			displayGameGroups ();
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	public void runActionsAfterTournamentTable ()
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
			if (mTournamentGroupsController.isLastGroupPlayed ())
			{
				showMatchWinner (winningPlayerObject);
			}
			else
			{
				resetPlayerLegData (winningPlayerObject);
				promotePlayerAndRotateToNextStage (winningPlayerObject);

				if (mTournamentGroupsController.getCurrentPlayingGroup ().getPlayerObjects ().size () == 1)   // Possible only on first stage
					promotePlayerAndRotateToNextStage (mTournamentGroupsController.getCurrentPlayingGroup ().getPlayerObjects ().get (0));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}



	public void proceedGroupTournamentWinnerForm ()
	{
		mGroupTournamentTable.destroy ();
		mGroupTournamentTable = null;

		mGroupTournamentWinnerFrame = new GroupTournamentWinnerFrame (this);
	}


	public void newMatch ()
	{
		if (mTournamentWinnerFrame != null)
			mTournamentWinnerFrame = null;

		initializeNewMatch (mGameType);
	}


	public void openMenuAndDestroyPlayerRegistration ()
	{
		mPlayersRegistration.destroy ();
		mPlayersRegistration = null;
		MainController.openMenuGui ();
	}


	public void openPlayerRegistration ()
	{
		if (isGameTypeTournament ())
		{
			mTournamentTable.destroy ();
			mTournamentTable = null;
		}
		else
		{
			mGroupTournamentTable.destroy ();
			mGroupTournamentTable = null;
		}

		mPlayersRegistration = new PlayersRegistration (this);
	}


	public void notifyGroupTournamentGroupPlayed ()
	{
		mGroupsTournamentGroupsController.incrementGroupPlayedCount ();

		if (mGroupsTournamentGroupsController.isAllGroupsPlayed ())
		{
			ArrayList <PlayerObject> losers = tryToFindLosers ();
			if (losers != null)
				mGroupsTournamentGroupsController.removePlayersFromMatchGroup (losers);

			mGroupsTournamentGroupsController.rotateGame ();

			if (mGroupTournamentTable != null)
				mGroupTournamentTable.reloadGroupPanel ();
		}
	}


	public double roundHalfUp (double d)
	{
		return new BigDecimal (d).setScale (0, RoundingMode.UP).doubleValue ();
	}


	private void orderPlayersArrayList ()
	{
		mPlayerList.sort ((o1, o2) -> o2.getWinPoints ().compareTo(o1.getWinPoints ()));
	}


	private boolean isGameTypeTournament ()
	{
		return mGameType == Constats.GameType.Tournament;
	}


	private boolean isGameTypeGroupTournament ()
	{
		return mGameType == Constats.GameType.GroupTournament;
	}


	private ArrayList <PlayerObject> getGameOpponents () throws Exception
	{
		return mTournamentGroupsController.getCurrentPlayingGroup ().getPlayerObjects ();
	}


	public HashMap <Integer, ArrayList <GroupsTreeNode>> getTournamentMatchGroups ()
	{
		return mTournamentGroupsController.getMatchGroups ();
	}


	public ArrayList<TournamentTableGroupPanel> getAllMatchGroupsPanels ()
	{
		return mTournamentGroupsController.getAllMatchGroupsPanels ();
	}


	public TournamentTableGroupPanel getCurrentPlayingGroupPanel ()
	{
		return mTournamentGroupsController.getCurrentPlayingGroupPanel ();
	}


	public HashMap <Integer, ArrayList <GroupPlayerObject>> getGroupTournamentGameGroups ()
	{
		return mGroupsTournamentGroupsController.getGameGroups ();
	}


	public ArrayList <PlayerObject> getPlayerList ()
	{
		return mPlayerList;
	}


	public Integer getMaxPlayerLosePoints ()
	{
		return mMaxPlayerLosePoints;
	}


	public String getTopThreeWinnersString ()
	{
		orderPlayersArrayList ();

		String returnString = "";
		for (int i = 0; i < 3; i++)
			returnString += (i + 1) + ". " + mPlayerList.get (i).getName () + " ";

		return returnString.trim ();
	}


	public void exitFromApplication ()
	{
		System.exit (0);
	}
}
