package GroupsController;

import MatchController.Gui.Components.GroupTournamentMethod;
import MatchController.Gui.Components.GroupTournamentTableGroupPanel;
import MatchController.MatchController;
import MatchController.Objects.GroupPlayerObject;
import MatchController.Objects.PlayerObject;
import Tools.GroupGenerator;

import java.util.*;

public class GroupTournamentGroupsController
{
	private MatchController                                         mMatchController;
	private HashMap <PlayerObject, ArrayList <PlayerObject>>        mMatchGroups;
	private HashMap <Integer, ArrayList <GroupPlayerObject>>        mGameGroups;
	private int                                                     groupsPlayed;
	private int                                                     mMinimumPlayers = 3;


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
			mMatchController.proceedGroupTournamentWinnerForm ();
			return;
		}

		generateGameGroups ();
		linkGroupsWithPlayersAndCreatePanelObject ();
	}


	@SuppressWarnings ("unchecked")
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
			GroupPlayerObject groupFromSecondGeneratedGroups;
			if (generatedPlayingGroups2.size () == 1)
				groupFromSecondGeneratedGroups = generatedPlayingGroups2.get (0);
			else
				groupFromSecondGeneratedGroups = getGroupWithLessLoses (generatedPlayingGroups2);

			generatedPlayingGroups.add (groupFromSecondGeneratedGroups);
			generatedPlayingGroups2.remove (groupFromSecondGeneratedGroups);
		}

		if ((generatedPlayingGroups2.size () % 2 != 0) && (generatedPlayingGroups2.size () != 0))
		{
			if (generatedPlayingGroups2.size () == 1)
				generatedPlayingGroups2.remove (0);
			else
				generatedPlayingGroups2.remove (getGroupIndexWithMostLoses (generatedPlayingGroups2));
		}

		mGameGroups.putAll (GroupGenerator.generateGroupTournamentRandomGroups (mGameGroups.size (), generatedPlayingGroups));

		if (generatedPlayingGroups2.size () != 0)
			mGameGroups.putAll (GroupGenerator.generateGroupTournamentRandomGroups (mGameGroups.size (), generatedPlayingGroups2));
	}


	private int getGroupIndexWithMostLoses (ArrayList <GroupPlayerObject> generatedPlayingGroups)
	{
		int returnIndex = 0;
		int maxLoses = -1;
		int i = 0;
		for (GroupPlayerObject playerGroup : generatedPlayingGroups)
		{
			int loopMaxLoses = playerGroup.getFirstPlayer ().getLooses () + playerGroup.getSecondPlayer ().getLooses ();
			if (loopMaxLoses > maxLoses)
			{
				maxLoses = loopMaxLoses;
				returnIndex = i;
			}

			i++;
		}
		return returnIndex;
	}


	private GroupPlayerObject getGroupWithLessLoses (ArrayList<GroupPlayerObject> generatedPlayingGroups)
	{
		int minimumLoses = -1;
		GroupPlayerObject returnGroup = null;
		for (GroupPlayerObject playerGroup : generatedPlayingGroups)
		{
			int loopMinimumLoses = playerGroup.getFirstPlayer ().getLooses () + playerGroup.getSecondPlayer ().getLooses ();

			if (minimumLoses == -1 || loopMinimumLoses < minimumLoses)
			{
				minimumLoses = loopMinimumLoses;
				returnGroup = playerGroup;
			}
		}

		return returnGroup;
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
		return mMatchGroups.size () <= 2;
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
			GroupTournamentTableGroupPanel gp = new GroupTournamentTableGroupPanel (mGameGroups.get (i), new GroupTournamentMethod ()
			{
				@Override
				public void execute ()
				{

				}


				@Override
				public void execute (GroupPlayerObject loserGroup, GroupPlayerObject winnerGroup)
				{
					proceedWinnerLoserGroup (loserGroup, winnerGroup);
				}
			});
			GroupPlayerObject fGroup = mGameGroups.get (i).get (0);
			GroupPlayerObject sGroup = mGameGroups.get (i).get (1);

			fGroup.setGroupTournamentGroupPanel (gp);
			sGroup.setGroupTournamentGroupPanel (gp);
		}
	}


	private void proceedWinnerLoserGroup (GroupPlayerObject loserGroup, GroupPlayerObject winnerGroup)
	{
		PlayerObject firstPlayerLoser   = loserGroup.getFirstPlayer ();
		PlayerObject secondPlayer       = loserGroup.getSecondPlayer ();
		PlayerObject firstWinner        = winnerGroup.getFirstPlayer ();
		PlayerObject secondWinner       = winnerGroup.getSecondPlayer ();

		firstPlayerLoser.setLooses  (firstPlayerLoser.getLooses ()      + 1);
		secondPlayer.setLooses      (secondPlayer.getLooses ()          + 1);
		firstWinner.setWinPoints    (firstPlayerLoser.getWinPoints ()   + 1);
		secondWinner.setWinPoints   (secondPlayer.getWinPoints ()       + 1);
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
