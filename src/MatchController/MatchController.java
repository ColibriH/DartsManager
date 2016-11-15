package MatchController;

import GameController.GameController;
import GroupsController.GroupsController;
import MainController.MainController;
import MatchController.Gui.Components.DisplayGroupPanel;
import MatchController.Gui.PlayersRegistration.PlayersRegistration;
import MatchController.Gui.TournamentTable.TournamentTable;
import MatchController.Gui.WinnerFrame.WinnerFrame;
import MatchController.Objects.GroupsTreeNode;
import MatchController.Objects.PlayerObject;
import Tools.GroupGenerator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vladislavs on 06.09.2016..
 */

//TODO Refactor

public class MatchController
{
	private PlayersRegistration         mPlayersRegistration;
	private TournamentTable             mPlayerGeneratedGroupsGuiForm;
	private WinnerFrame                 mWinnerGuiForm;
	private GameController              mGameController;
	private GroupsController            mGroupsController;

	private ArrayList <PlayerObject>    mPlayerList;
	private Integer                     mPlayersNumberInGroup;


	public MatchController ()
	{
		initializeNewMatch ();
	}


	public void initializeNewMatch ()
	{
		mPlayersNumberInGroup = 2;

		// TODO delete in production
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

		whetherToKeepOldPlayerList ();
		mPlayersRegistration = new PlayersRegistration (this);
	}


	private void whetherToKeepOldPlayerList ()
	{
		// TODO uncoment in production
//		if (mPlayerList != null)
//			if (! keepPlayers ())
//				mPlayerList = null;
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


	private void matchManagerGuiFormClose ()
	{
		mPlayersRegistration.destroy ();
		mPlayersRegistration = null;
	}


	private void playerGeneratedGroupsGuiFormClose ()
	{
		mPlayerGeneratedGroupsGuiForm.setVisibility (false);
	}


	private void initializeMatchGroupsController ()
	{
		mGroupsController = new GroupsController (GroupGenerator.generateRandomGroups (mPlayersNumberInGroup, mPlayerList));
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
		mPlayerGeneratedGroupsGuiForm = new TournamentTable (this);
	}


	public HashMap <Integer, ArrayList <GroupsTreeNode>> getMatchGroups ()
	{
		return mGroupsController.getMatchGroups ();
	}


	private ArrayList <PlayerObject> getGameOpponents () throws Exception
	{
		return mGroupsController.getCurrentPlayingGroup ().getPlayerObjects ();
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
				setNextPanelPlayingText ();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog (null, e);
		}
	}


	private void resetPlayerLegData (PlayerObject winningPlayerObject)
	{
		winningPlayerObject.mLeg = 0;
	}


	private void showMatchWinner (PlayerObject winner)
	{
		mWinnerGuiForm = new WinnerFrame (this, winner);
	}


	private void setNextPanelPlayingText ()
	{
		mPlayerGeneratedGroupsGuiForm.displayCurrentPlayingGroupText ();
		mPlayerGeneratedGroupsGuiForm.setVisibility (true);
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


	private void startGame () throws Exception
	{
		if (mGameController != null)
			mGameController = null;

		mGameController = new GameController (this, getGameOpponents ());
	}


	public void openMenuGuiForm ()
	{
		mPlayersRegistration.destroy ();
		mPlayersRegistration = null;
		MainController.openMenuGui ();
	}


	public void openGameManagerGuiForm ()
	{
		mPlayerGeneratedGroupsGuiForm.destroy ();
		mPlayerGeneratedGroupsGuiForm = null;
		mPlayersRegistration = new PlayersRegistration (this);
	}

	public ArrayList<DisplayGroupPanel> getAllMatchGroupsPanels ()
	{
		return mGroupsController.getAllMatchGroupsPanels ();
	}


	public DisplayGroupPanel getCurrentPlayingGroupPanel ()
	{
		return mGroupsController.getCurrentPlayingGroupPanel ();
	}


	public void exitFromApplication ()
	{
		System.exit (0);
	}
}
