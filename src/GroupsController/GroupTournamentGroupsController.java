package GroupsController;

import MatchController.Gui.Components.GroupTournamentTableGroupPanel;
import MatchController.MatchController;
import MatchController.Objects.GroupPlayerObject;
import MatchController.Objects.PlayerObject;
import Tools.GroupGenerator;

import javax.swing.*;
import java.util.*;

// TODO BIG REFACTOR

public class GroupTournamentGroupsController
{
	private MatchController mMatchController;

	private HashMap <PlayerObject, ArrayList <PlayerObject>>        mMatchGroups;
	private HashMap <Integer, ArrayList <GroupPlayerObject>>        mGameGroups;

	private int groupsPlayed;
	private int mMinimumPlayers = 3;

	public GroupTournamentGroupsController (MatchController matchController, HashMap <PlayerObject, ArrayList <PlayerObject>> matchGroups)
	{
		mMatchController = matchController;
		mMatchGroups = matchGroups;
		initialize ();
	}


	private void initialize ()
	{
		ifExistHasMapKeyRemove ();

		if (isLeftMinimumPlayers ())
		{
			JOptionPane.showMessageDialog (null, "Game ends");
			return;
			// todo winner panel
		}

		generateGameGroups ();
		linkGroupsWithPlayersAndCreatePanelObject ();
	}
	
	private void ifExistHasMapKeyRemove ()
	{
		Iterator iterator = mMatchGroups.entrySet ().iterator ();
		
		while (iterator.hasNext ())
		{
			Map.Entry <PlayerObject, ArrayList <PlayerObject>> pair = (Map.Entry <PlayerObject, ArrayList <PlayerObject>>) iterator.next ();    // TODO how to avoid this warning
			if (pair.getValue ().size () == 0)
				iterator.remove();
		}
	}
	
	
	private void generateGameGroups ()
	{
		mGameGroups = new HashMap <> ();
		mGameGroups.clear ();

		ArrayList <GroupPlayerObject> generatedPlayingGroups = generatePlayingGroups (true);
		ArrayList <GroupPlayerObject> generatedPlayingGroups2 = generatePlayingGroups (false);

		if (generatedPlayingGroups.size () % 2 != 0)
		{
			int randomNumber = new Random ().nextInt (generatedPlayingGroups2.size ());
			GroupPlayerObject groupFromSecondGeneratedGroups = generatedPlayingGroups2.get (randomNumber);

			generatedPlayingGroups.add (groupFromSecondGeneratedGroups);
		}

		if (generatedPlayingGroups2.size () % 2 != 0)
		{
			int randomNumber = new Random ().nextInt (generatedPlayingGroups2.size ());
			generatedPlayingGroups2.remove (randomNumber);
		}

		mGameGroups.putAll (GroupGenerator.generateGroupTournamentRandomGroups (mGameGroups.size (), generatedPlayingGroups));

		if (generatedPlayingGroups2.size () != 0)
			mGameGroups.putAll (GroupGenerator.generateGroupTournamentRandomGroups (mGameGroups.size (), generatedPlayingGroups2));
	}


	private ArrayList <GroupPlayerObject> generatePlayingGroups (boolean isFirstGeneratedGameGroups)
	{
		ArrayList <GroupPlayerObject> playingGroups = new ArrayList <> ();

		for (Map.Entry <PlayerObject, ArrayList <PlayerObject>> hashMapPair : getSortedHashMapCopy ().entrySet ())
		{
			PlayerObject player = hashMapPair.getKey ();
			PlayerObject playerOpponent = tryToGetPlayerOpponent (playingGroups, hashMapPair.getValue (), player.getId (), isFirstGeneratedGameGroups);

			if (isPlayerOpponentNullOrPlayerHasLastChanceToPlay (player, playerOpponent, isFirstGeneratedGameGroups))
				continue;

			playingGroups.add (new GroupPlayerObject (player, playerOpponent));
		}

		return playingGroups;
	}


	private PlayerObject tryToGetPlayerOpponent (ArrayList <GroupPlayerObject> playingGroups, ArrayList <PlayerObject> playerOpponents, Integer playerId, boolean isFirstGeneratedGameGroups)
	{
		int playerOpponentCount;
		while ((playerOpponentCount = playerOpponents.size ()) != 0)
		{
			int randomNumber = new Random ().nextInt (playerOpponentCount);

			PlayerObject playerRandomOpponent = playerOpponents.get (randomNumber);

			if (playingGroups.size () == 0)     // if first entry
				return playerRandomOpponent;

			if (isOpponentAlreadyInGameGroup (playingGroups, playerRandomOpponent, playerId))
				playerOpponents.remove (randomNumber);
			else if (! isFirstGeneratedGameGroups && hasPlayersLastChanceToWin (playerRandomOpponent))
				playerOpponents.remove (randomNumber);
			else
				return playerRandomOpponent;
		}

		return null;
	}


	private int getLeftPlayerCount ()
	{
		ArrayList <PlayerObject> leftPlayers = new ArrayList <> ();

		for (Map.Entry <PlayerObject, ArrayList <PlayerObject>> hashMapPair : mMatchGroups.entrySet ())
		{
			if (! leftPlayers.contains (hashMapPair.getKey ()))
				leftPlayers.add (hashMapPair.getKey ());

			for (PlayerObject player : hashMapPair.getValue ())
			{
				if (! leftPlayers.contains (player))
					leftPlayers.add (player);
			}
		}

		return leftPlayers.size ();
	}


	private boolean isLeftMinimumPlayers ()
	{
		return mMatchGroups.size () == 2 && getLeftPlayerCount () <= mMinimumPlayers;
	}


	private boolean isOpponentAlreadyInGameGroup (ArrayList <GroupPlayerObject> playingGroups, PlayerObject playerRandomOpponent, Integer playerId)
	{
		for (GroupPlayerObject playersInOneGroup : playingGroups)
			if ((playersInOneGroup.getSecondPlayer ().getId ().equals (playerRandomOpponent.getId ())) || (playersInOneGroup.getSecondPlayer ().getId ().equals (playerId)))
				return true;

		return false;
	}


	private boolean isPlayerOpponentNullOrPlayerHasLastChanceToPlay (PlayerObject player, PlayerObject playerOpponent, boolean isFirstGeneratedGameGroups)
	{
		if (playerOpponent == null)
			return true;

		if (! isFirstGeneratedGameGroups)
			if (hasPlayersLastChanceToWin (player))
				return true;

		return false;
	}


	private boolean hasPlayersLastChanceToWin (PlayerObject player)
	{
		return player.getLooses ().equals (mMatchController.getMaxPlayerLosePoints () - 1);
	}


	private HashMap <PlayerObject, ArrayList <PlayerObject>> getSortedHashMapCopy ()
	{
		return sortHashMapByKey (hashMapDeepCopy (mMatchGroups));
	}


	private void linkGroupsWithPlayersAndCreatePanelObject ()
	{
		for (int i = 0; i < mGameGroups.size (); i++)
		{
			GroupTournamentTableGroupPanel gp = new GroupTournamentTableGroupPanel (mGameGroups.get (i), this::proceedLoserGroup);
			GroupPlayerObject fGroup = mGameGroups.get (i).get (0);
			GroupPlayerObject sGroup = mGameGroups.get (i).get (1);

			fGroup.setGroupTournamentGroupPanel (gp);
			sGroup.setGroupTournamentGroupPanel (gp);
		}
	}


	private void proceedLoserGroup (GroupPlayerObject loserGroup)
	{
		PlayerObject fLoser = loserGroup.getFirstPlayer ();
		PlayerObject sLoser = loserGroup.getSecondPlayer ();

		fLoser.setLooses (fLoser.getLooses () + 1);
		sLoser.setLooses (sLoser.getLooses () + 1);
	}


	private HashMap <PlayerObject, ArrayList <PlayerObject>> hashMapDeepCopy (HashMap <PlayerObject, ArrayList <PlayerObject>> map)
	{
		HashMap <PlayerObject, ArrayList <PlayerObject>> copy = new HashMap <> ();

		for (Map.Entry <PlayerObject, ArrayList <PlayerObject>> pair : map.entrySet ())
			copy.put (pair.getKey (), new ArrayList <> (pair.getValue ()));

		return copy;
	}


	private static HashMap <PlayerObject, ArrayList <PlayerObject>> sortHashMapByKey (HashMap <PlayerObject, ArrayList <PlayerObject>> map)
	{
		LinkedList <Map.Entry <PlayerObject, ArrayList <PlayerObject>>> linkedList = new LinkedList <> (map.entrySet ());

		Collections.sort (linkedList, (o1, o2) -> (o1.getKey ().getId ()).compareTo (o2.getKey ().getId ()));

		HashMap <PlayerObject, ArrayList <PlayerObject>> sortedHashMap = new LinkedHashMap <> ();
		for (Map.Entry <PlayerObject, ArrayList <PlayerObject>> aLinkedList : linkedList)
			sortedHashMap.put (aLinkedList.getKey (), aLinkedList.getValue ());

		return sortedHashMap;
	}


	@SuppressWarnings ("unchecked")
	public void removePlayersFromMatchGroup (ArrayList <PlayerObject> losers)       // TODO remove suppress
	{
		Iterator iterator = mMatchGroups.entrySet ().iterator ();
		while (iterator.hasNext ())
		{
			Map.Entry <PlayerObject, ArrayList <PlayerObject>> pair = (Map.Entry <PlayerObject, ArrayList <PlayerObject>>) iterator.next ();    // TODO how to avoid this warning

			if (losers.contains (pair.getKey ()))
			{
				iterator.remove();
				continue;
			}

			pair.getValue ().removeAll (losers);
		}
	}


	public void rotateGame ()
	{
		groupsPlayed = 0;
		initialize ();
	}


	public void incrementGroupPlayedCount ()
	{
		groupsPlayed++;
	}


	public boolean isAllGroupsPlayed ()
	{
		return groupsPlayed == mGameGroups.size ();
	}


	public HashMap <Integer, ArrayList <GroupPlayerObject>> getGameGroups ()
	{
		return mGameGroups;
	}
}
