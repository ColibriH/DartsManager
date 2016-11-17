package Tools;

import Constants.Constats;
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


	public static void generateGroupTournamentRandomGroups (Constats.GameType mGameType, Integer looseCount, ArrayList<PlayerObject> mPlayerList)
	{
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
