package GroupsController;

import MatchController.Gui.Components.GroupTournamentTableGroupPanel;
import MatchController.MatchController;
import MatchController.Objects.GroupPlayerObject;
import MatchController.Objects.PlayerObject;
import Tools.GroupGenerator;

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
		if (isLeftMinimumPlayers ())
		{
			// todo winner panel
		}

		generateGameGroups ();
		linkGroupsWithPlayersAndCreatePanelObject ();
	}


	private void generateGameGroups ()
	{
		mGameGroups = new HashMap <> ();
		mGameGroups.clear ();

		ArrayList <GroupPlayerObject> generatedPlayingGroups = generatePlayingGroups ();
		ArrayList <GroupPlayerObject> generatedPlayingGroups2 = generatePlayingGroups ();

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


	private ArrayList <GroupPlayerObject> generatePlayingGroups ()
	{
		ArrayList <GroupPlayerObject> playingGroups = new ArrayList <> ();

		for (Map.Entry <PlayerObject, ArrayList <PlayerObject>> hashMapPair : getSortedHashMapCopy ().entrySet ())
		{
			PlayerObject player = hashMapPair.getKey ();
			PlayerObject playerOpponent = tryToGetPlayerOpponent (playingGroups, hashMapPair.getValue (), player.getId ());

			if (isPlayerOpponentNullOrPlayerHasLastChanceToPlay (player, playerOpponent))
				continue;

			playingGroups.add (new GroupPlayerObject (player, playerOpponent));
		}

		return playingGroups;
	}


	private PlayerObject tryToGetPlayerOpponent (ArrayList <GroupPlayerObject> playingGroups, ArrayList <PlayerObject> playerOpponents, Integer playerId)
	{
		int playerOpponentCount;
		while ((playerOpponentCount = playerOpponents.size ()) != 0)
		{
			int randomNumber = new Random ().nextInt (playerOpponentCount);

			PlayerObject playerRandomOpponent = playerOpponents.get (randomNumber);

			if (playingGroups.size () == 0)     // if first entry
				return playerRandomOpponent;

			if (! isOpponentAlreadyInGameGroup (playingGroups, playerRandomOpponent, playerId))
				playerOpponents.remove (randomNumber);
			else if (hasPlayersLastChanceToWin (playerRandomOpponent))
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
		if (mMatchGroups.size () == 2)
			return getLeftPlayerCount () == mMinimumPlayers;

		return false;
	}


	private boolean isOpponentAlreadyInGameGroup (ArrayList <GroupPlayerObject> playingGroups, PlayerObject playerRandomOpponent, Integer playerId)
	{
		for (GroupPlayerObject playersInOneGroup : playingGroups)
			if ((playersInOneGroup.getSecondPlayer ().getId ().equals (playerRandomOpponent.getId ())) || (playersInOneGroup.getSecondPlayer ().getId ().equals (playerId)))
				return true;

		return false;
	}


	private boolean isPlayerOpponentNullOrPlayerHasLastChanceToPlay (PlayerObject player, PlayerObject playerOpponent)
	{
		if (playerOpponent == null)
			return true;

		if (mGameGroups.size () != 0)
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
		Iterator iterator  = map.entrySet ().iterator ();

		while (iterator.hasNext ())
		{
			Map.Entry <PlayerObject, ArrayList <PlayerObject>> pair = (Map.Entry <PlayerObject, ArrayList <PlayerObject>>) iterator.next ();    // TODO how to avoid this warning
			copy.put (pair.getKey (), new ArrayList <> (pair.getValue ()));
		}

		return copy;
	}


	private static HashMap <PlayerObject, ArrayList <PlayerObject>> sortHashMapByKey (HashMap <PlayerObject, ArrayList <PlayerObject>> map)    // TODO how to avoid this warning
	{
		List list = new LinkedList (map.entrySet ());

		Collections.sort(list, (o1, o2) -> ((Comparable) ((Map.Entry <PlayerObject, ArrayList <PlayerObject>>) (o1)).getKey ().getId ()).compareTo (((Map.Entry <PlayerObject, ArrayList <PlayerObject>>) (o2)).getKey ().getId ()));

		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap ();
		for (Iterator it = list.iterator(); it.hasNext ();)
		{
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey (), entry.getValue ());
		}

		return sortedHashMap;
	}


	public void removePlayersFromMatchGroup (ArrayList <PlayerObject> losers)
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
