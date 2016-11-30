package MatchController;

import Constants.Constats;
import TournamentGameController.TournamentGameController;
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
	// TODO Divide Match controller on fame types
	// TODO Create Base class to some of next classes to reduce cnt of it
	private PlayersRegistration             mPlayersRegistration;
	private TournamentGroupsController      mTournamentGroupsController;
	private GroupTournamentGroupsController mGroupsTournamentGroupsController;
	private TournamentTable                 mTournamentTable;
	private GroupTournamentTable            mGroupTournamentTable;
	private TournamentWinnerFrame           mTournamentWinnerFrame;
	private GroupTournamentWinnerFrame      mGroupTournamentWinnerFrame;
	private TournamentGameController        mTournamentGameController;
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


	private void displayTournamentGameGroups ()
	{
		if (isGameTypeTournament ())
			mTournamentTable = new TournamentTable (this);
		else if (isGameTypeGroupTournament ())
			mGroupTournamentTable = new GroupTournamentTable (this);
	}


	private void showTournamentWinner (PlayerObject winner)
	{
		mTournamentWinnerFrame = new TournamentWinnerFrame (this, winner);
	}


	private void resetPlayerLegData (PlayerObject winningPlayer)
	{
		winningPlayer.setLeg(0);
	}


	private void startTournamentGame () throws Exception
	{
		if (mTournamentGameController != null)
			mTournamentGameController = null;

		mTournamentGameController = new TournamentGameController (this, getTournamentGameOpponents ());
	}


	private void promotePlayerAndRotateToNextStage (PlayerObject winningPlayer)
	{
		mTournamentGroupsController.promoteWinningPlayerToNextStage (winningPlayer);
		mTournamentGroupsController.rotateToNextGroup ();
		setNextGroupPlayingText ();
	}


	private ArrayList<PlayerObject> tryToFindLosers ()
	{
		ArrayList <PlayerObject> losers = new ArrayList <> ();
		for (PlayerObject player : mPlayerList)
			if (player.getLooses ().equals (mMaxPlayerLosePoints))
				losers.add (player);

		return losers.size () != 0 ? losers : null;
	}


	private void setNextGroupPlayingText ()
	{
		mTournamentTable.displayCurrentPlayingGroupText ();
		mTournamentTable.setVisibility (true);
	}


	private void setPlayerList (ArrayList <PlayerObject> tablePlayerList)
	{
		mPlayerList =  new ArrayList <> (tablePlayerList);
	}


	public void initializeNewMatch (Constats.GameType gameType)
	{
		mMaxPlayerLosePoints = 5;   // TODO set default + in UI
		mGameType = gameType;

		if (MainController.DEBUG_MODE)
			executeDebugCode();

		whetherToKeepOldPlayerList ();
		mPlayersRegistration = new PlayersRegistration (this);
	}


	public void runActionsAfterPlayerRegistration (ArrayList <PlayerObject> tablePlayerList)
	{
		setPlayerList (tablePlayerList);
		destroyPlayerRegistration ();
		initializeMatchGroupsController ();

		try
		{
			displayTournamentGameGroups ();
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
			startTournamentGame ();
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	public void runActionsAfterTournamentGameController (PlayerObject winningPlayer)
	{
		try
		{
			if (mTournamentGroupsController.isLastGroupPlayed ())
			{
				showTournamentWinner (winningPlayer);
			}
			else
			{
				resetPlayerLegData (winningPlayer);
				promotePlayerAndRotateToNextStage (winningPlayer);

				if (mTournamentGroupsController.getCurrentPlayingGroup ().getPlayer ().size () == 1)   // Possible only on first stage
					promotePlayerAndRotateToNextStage (mTournamentGroupsController.getCurrentPlayingGroup ().getPlayer ().get (0));
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


	private ArrayList <PlayerObject> getTournamentGameOpponents () throws Exception
	{
		return mTournamentGroupsController.getCurrentPlayingGroup ().getPlayer ();
	}


	public HashMap <Integer, ArrayList <GroupsTreeNode>> getTournamentMatchGroups ()
	{
		return mTournamentGroupsController.getMatchGroups ();
	}


	public ArrayList<TournamentTableGroupPanel> getAllTournamentMatchGroupsPanels ()
	{
		return mTournamentGroupsController.getAllTournamentMatchGroupsPanels ();
	}


	public TournamentTableGroupPanel getCurrentTournamentPlayingGroupPanel ()
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


	public String getGroupTournamentTopThreeWinnersString ()
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
