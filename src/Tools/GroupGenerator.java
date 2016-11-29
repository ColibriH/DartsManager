package Tools;

import MatchController.Objects.GroupPlayerObject;
import MatchController.Objects.PlayerObject;

import java.util.*;

public class GroupGenerator
{
	public static HashMap <Integer, ArrayList <PlayerObject>> generateTournamentRandomGroups (Integer playersNumberInGroup, ArrayList <PlayerObject> playerList)
	{
		int groupCount = playerList.size () / playersNumberInGroup;

		if (isOdd (playerList.size ()))
			groupCount++;

		HashMap <Integer, ArrayList <PlayerObject>> generatedGroupMap = new HashMap <> ();
		List<PlayerObject> shuffledListOfPlayerObject= getShuffledListOfPlayerObject (playerList);
		for (int i = 0; i < groupCount; i++)
			generatedGroupMap.put (i, getOneCreatedGroup (playersNumberInGroup, shuffledListOfPlayerObject));

		return generatedGroupMap;
	}


	public static HashMap <PlayerObject, ArrayList <PlayerObject>> generateGroupTournamentGroups (ArrayList<PlayerObject> mPlayerList)
	{
		mPlayerList.sort ((o1, o2) -> o1.getId () - o2.getId ());
		HashMap <PlayerObject, ArrayList <PlayerObject>> groupsMap = new HashMap <> ();

		for (int i = 0; i < mPlayerList.size () - 1; i++)
		{
			ArrayList <PlayerObject> playersGroups = new ArrayList <> ();
			for (int j = i + 1; j < mPlayerList.size (); j++)
				playersGroups.add (mPlayerList.get (j));

			groupsMap.put (mPlayerList.get (i), playersGroups);
		}

		return  groupsMap;
	}


	public static HashMap <Integer, ArrayList <GroupPlayerObject>> generateGroupTournamentRandomGroups (int startPos, ArrayList <GroupPlayerObject> mPlayingGroups) // TODO Make statements for uniqueness of players in playing groups
	{
		HashMap <Integer, ArrayList <GroupPlayerObject>> returnMap = new HashMap <> ();

		Integer i = startPos;
		int added = 0;
		while (mPlayingGroups.size () != 0)
		{
			Random rand = new Random ();
			int n = rand.nextInt (mPlayingGroups.size ());

			if (returnMap.get (i) == null)
			{
				returnMap.put (i, new ArrayList <GroupPlayerObject> ()
				{{
					add (mPlayingGroups.get (n));
				}});
				mPlayingGroups.remove (n);
				added++;
			}
			else
			{
				returnMap.get (i).add (mPlayingGroups.get (n));
				mPlayingGroups.remove (n);
				added++;
			}

			if (added == 2)
			{
				added = 0;
				i++;
			}
		}

		return returnMap;
	}


	private static ArrayList <PlayerObject> getOneCreatedGroup (Integer maxPlayerInGroup, List <PlayerObject> shuffledList)
	{
		ArrayList <PlayerObject> playersGroupValue = new ArrayList <> ();

		for (int i = 0; i < maxPlayerInGroup; i++)
			if (shuffledList.size () != 0)
				playersGroupValue.add (getPlayerFromShuffledListAndRemove (shuffledList));

		return playersGroupValue;
	}


	private static PlayerObject getPlayerFromShuffledListAndRemove (List <PlayerObject> shuffledList)
	{
		PlayerObject playerObjectFromShuffledList = shuffledList.get (0);
		shuffledList.remove (0);

		return playerObjectFromShuffledList;
	}


	private static boolean isOdd (int maxPlayerInGroup)
	{
		return ((maxPlayerInGroup % 2) != 0);
	}


	private static List <PlayerObject> getShuffledListOfPlayerObject (ArrayList <PlayerObject> playerList)
	{
		List <Integer> returnList = new LinkedList <> ();

		for (int i = 0; i < playerList.size (); i++)
			returnList.add (i);

		Collections.shuffle(returnList);

		return createListOfPlayerObject (playerList, returnList);
	}


	private static List <PlayerObject> createListOfPlayerObject (ArrayList <PlayerObject> playerList, List <Integer> playersIdList)
	{
		List <PlayerObject> returnList = new LinkedList <> ();

		for (Integer id : playersIdList)
			returnList.add (findPlayerObject (playerList, id));

		return returnList;
	}


	private static PlayerObject findPlayerObject (ArrayList<PlayerObject> playerList, Integer searchedId)
	{
		PlayerObject foundPlayer = null;

		for (PlayerObject player : playerList)
		{
			if (player.getId().equals (searchedId))
			{
				foundPlayer = player;
				break;
			}
		}

		return foundPlayer;
	}
}
