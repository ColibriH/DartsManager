package MatchController.Objects;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by vladislavs on 06.09.2016..
 */
public class NewPlayerArrayList extends ArrayList
{
	public static ArrayList <NewPlayerObject> getNewPlayerArrayList (ArrayList<Vector> newPlayerArrayList)
	{
		ArrayList <NewPlayerObject> newPlayerObjects = new ArrayList <NewPlayerObject> ();

		for (Vector newPlayer : newPlayerArrayList)
			newPlayerObjects.add (new NewPlayerObject (newPlayer.get (0).toString (), newPlayer.get (1).toString ()));

		return newPlayerObjects;
	}
}
