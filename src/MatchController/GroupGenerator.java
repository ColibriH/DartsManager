package MatchController;

import MatchController.Objects.NewPlayerObject;

import java.util.*;

/**
 * Created by vladislavs on 06.09.2016..
 */
public class GroupGenerator
{

	public static HashMap <String, String []> generateRandomGroups (ArrayList <NewPlayerObject> mPlayerList)
	{
		// TODO GROUP OF ONE PERSON HANDLE????
		int maxPlayerInGroup = 2;
		int groupCount = mPlayerList.size () / maxPlayerInGroup;       // Check with 9 or 7 or 5... (even numbers)

		HashMap <String, String []> generatedGroupMap = initializeGroupMap (groupCount);

		// TODO use linkedarraylist in seperate method

		List <Integer> arr = new LinkedList <> ();

		for (int i = 0; i < mPlayerList.size (); i++)
			arr.add (i);

		Collections.shuffle(arr);

		// TODO Bad Code Reafactor!! may use iterator
		for (int i = 1; i < groupCount + 1; i++)
		{
			String [] playersGroupValue = generatedGroupMap.get (i);

			playersGroupValue [0] = mPlayerList.get (arr.get (0)).mId;
			playersGroupValue [1] = mPlayerList.get (arr.get (1)).mId;

			arr.remove (0);
			arr.remove (0);
		}

		return generatedGroupMap;
	}


	private static HashMap <String, String []> initializeGroupMap (int groupCount)
	{
		HashMap groupMap = new HashMap <String, String []> ();

		for (int i = 1; i < groupCount + 1; i++)
		{
			String [] blankData = {"", ""};
			groupMap.put (i, blankData);
		}

		return groupMap;
	}
}
