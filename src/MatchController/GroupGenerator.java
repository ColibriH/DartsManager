package MatchController;

import MatchController.Objects.PlayerObject;

import java.util.*;

/**
 * Created by vladislavs on 06.09.2016..
 */
public class GroupGenerator
{

	public static HashMap <Integer, Integer []> generateRandomGroups (ArrayList <PlayerObject> mPlayerList)
	{
		// TODO GROUP OF ONE PERSON HANDLE????
		int maxPlayerInGroup = 2;
		int groupCount = mPlayerList.size () / maxPlayerInGroup;       // Check with 9 or 7 or 5... (even numbers)

		HashMap <Integer, Integer []> generatedGroupMap = initializeGroupMap (groupCount);

		// TODO use linkedarraylist in seperate method

		List <Integer> arr = new LinkedList <> ();

		for (int i = 0; i < mPlayerList.size (); i++)
			arr.add (i);

		Collections.shuffle(arr);

		// TODO Bad Code Reafactor!! may use iterator
		for (int i = 1; i < groupCount + 1; i++)
		{
			Integer [] playersGroupValue = generatedGroupMap.get (i);

			playersGroupValue [0] = mPlayerList.get (arr.get (0)).mId;
			playersGroupValue [1] = mPlayerList.get (arr.get (1)).mId;

			arr.remove (0);
			arr.remove (0);
		}

		return generatedGroupMap;
	}


	private static HashMap <Integer, Integer []> initializeGroupMap (int groupCount)
	{
		HashMap groupMap = new HashMap <Integer, Integer []> ();

		for (int i = 1; i < groupCount + 1; i++)
		{
			Integer [] blankData = {-1, -1};
			groupMap.put (i, blankData);    // TODO check warning
		}

		return groupMap;
	}
}
