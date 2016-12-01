package Tools;

import MatchController.Objects.GroupPlayerObject;
import MatchController.Objects.PlayerObject;

import java.util.*;

// Comment - I regret for this shit code!!!

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
		ArrayList <ArrayList <GroupPlayerObject>> randomGroupsArray = getRepeatedPlayerGroupsAtSeparatePositions (mPlayingGroups);

		if (randomGroupsArray.size () != 0)
			ifExistRepeatedPlayersAddToDifferentGroup (randomGroupsArray, mPlayingGroups);

		while (mPlayingGroups.size () != 0)
		{
			int randomNumber = new Random ().nextInt (mPlayingGroups.size ());
			GroupPlayerObject groupToAdd = mPlayingGroups.get (randomNumber);
			addGroupToArray (randomGroupsArray, groupToAdd);
			mPlayingGroups.remove (groupToAdd);
		}

		return convertGroupArrayToGroupMap (randomGroupsArray, startPos);
	}


	private static void ifExistRepeatedPlayersAddToDifferentGroup (ArrayList <ArrayList<GroupPlayerObject>> randomGroupsArray, ArrayList <GroupPlayerObject> mPlayingGroups)
	{

		for (ArrayList<GroupPlayerObject> randomGroups : randomGroupsArray)
		{
			for (Iterator <GroupPlayerObject> it = mPlayingGroups.iterator (); it.hasNext (); )
			{
				GroupPlayerObject group = it.next ();
				if (randomGroups.get (0).getFirstPlayer ().equals (group.getFirstPlayer ()) || randomGroups.get (0).getSecondPlayer ().equals (group.getSecondPlayer ()))
				{
					getFreeCorrectGroup (randomGroupsArray, randomGroups).add (group);
					it.remove ();
				}
			}
		}
	}


	private static ArrayList<GroupPlayerObject> getFreeCorrectGroup (ArrayList <ArrayList <GroupPlayerObject>> randomGroupsArray, ArrayList <GroupPlayerObject> randomGroups)
	{
		ArrayList <ArrayList <GroupPlayerObject>> localRandomGroupsArray = new ArrayList <> (randomGroupsArray);
		localRandomGroupsArray.remove (randomGroups);

		int index = 0;
		if (localRandomGroupsArray.size () > 1)
			index = new Random ().nextInt (localRandomGroupsArray.size ());

		return localRandomGroupsArray.get (index);
	}


	private static HashMap <Integer, ArrayList <GroupPlayerObject>> convertGroupArrayToGroupMap (ArrayList <ArrayList <GroupPlayerObject>> randomGroupsArray, int startPos)
	{
		HashMap <Integer, ArrayList <GroupPlayerObject>> returnArray = new HashMap <> ();
		for (ArrayList <GroupPlayerObject> groupArray : randomGroupsArray)
		{
			returnArray.put (startPos, groupArray);
			startPos++;
		}

		return returnArray;
	}


	private static void addGroupToArray (ArrayList <ArrayList <GroupPlayerObject>> randomGroupsArray, GroupPlayerObject group)
	{
		int maxGroupCount = 2;
		int trys = 0;

		if (randomGroupsArray.size () == 0)
		{
			randomGroupsArray.add (new ArrayList <GroupPlayerObject> ()
			{{
				add (group);
			}});
			return;
		}

		for (ArrayList <GroupPlayerObject> groupArray : randomGroupsArray)
		{
			if (groupArray.size () != maxGroupCount)
			{
				if (GroupPlayersIsDifferent (groupArray.get (0), group))
				{
					groupArray.add (group);
					return;
				}
			}

			if (trys >= randomGroupsArray.size () - 1)
			{
				randomGroupsArray.add (new ArrayList <GroupPlayerObject> (){{add (group);}});
				return;
			}

			trys++;
		}
	}


	private static ArrayList <GroupPlayerObject> getRepeatedPlayerGroups (ArrayList <GroupPlayerObject> playingGroups)
	{
		ArrayList <GroupPlayerObject> returnArray = new ArrayList <> ();

		for (int i = 0; i < playingGroups.size (); i++)
		{
			PlayerObject firstPlayer = playingGroups.get (i).getFirstPlayer ();
			PlayerObject secondPlayer = playingGroups.get (i).getSecondPlayer ();

			GroupPlayerObject repGroup = isPlayerRepeatedReturnGroup (playingGroups, firstPlayer, i);

			if (repGroup == null)
				repGroup = isPlayerRepeatedReturnGroup (playingGroups, secondPlayer, i);

			if (repGroup != null)
			{
				returnArray.add (playingGroups.get (i));
				returnArray.add (repGroup);
				playingGroups.remove (repGroup);
				playingGroups.remove (i);
				i = 0;
			}
		}

		return returnArray;
	}


	private static ArrayList <ArrayList <GroupPlayerObject>> getRepeatedPlayerGroupsAtSeparatePositions (ArrayList <GroupPlayerObject> playingGroups)
	{
		ArrayList <ArrayList <GroupPlayerObject>> returnArray = new ArrayList <> ();

		for (GroupPlayerObject group : getRepeatedPlayerGroups (playingGroups))
			returnArray.add (new ArrayList <GroupPlayerObject> (){{add (group);}});

		return returnArray;
	}


	private static GroupPlayerObject isPlayerRepeatedReturnGroup (ArrayList<GroupPlayerObject> playingGroups, PlayerObject player, int startPos)
	{
		if (startPos + 1 >= playingGroups.size ())
			return null;

		for (int i = startPos + 1; i < playingGroups.size (); i++)
		{
			PlayerObject firstPlayer = playingGroups.get (i).getFirstPlayer ();
			PlayerObject secondPlayer = playingGroups.get (i).getSecondPlayer ();

			if (player.equals (firstPlayer) || player.equals (secondPlayer))
				return playingGroups.get (i);
		}

		return null;
	}


	private static boolean GroupPlayersIsDifferent (GroupPlayerObject groupPlayerObject, GroupPlayerObject playerObject)
	{
		if (groupPlayerObject.getSecondPlayer () != playerObject.getSecondPlayer ())
			if (groupPlayerObject.getFirstPlayer () != playerObject.getFirstPlayer ())
				return true;

		return false;
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
