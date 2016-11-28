package GroupsController;

import MatchController.Objects.PlayerObject;
import Tools.GroupGenerator;

import java.util.*;

/**
 * Created by vladislavs on 23.11.2016..
 */
public class GroupTournamentGroupsController
{
	private HashMap <PlayerObject, ArrayList <PlayerObject>>        mMatchGroups;
	private HashMap <Integer, ArrayList <ArrayList <PlayerObject>>> mGameGroups;

	public GroupTournamentGroupsController (HashMap <PlayerObject, ArrayList <PlayerObject>> matchGroups)
	{
		mGameGroups = new HashMap <> ();
				mMatchGroups = matchGroups;

		// Done twice!
		mGameGroups.putAll (GroupGenerator.generateGroupTournamentRandomGroups (mGameGroups.size (), generatePlayingGroups ()));
		mGameGroups.putAll (GroupGenerator.generateGroupTournamentRandomGroups (mGameGroups.size (), generatePlayingGroups ()));
	}


	private ArrayList <ArrayList <PlayerObject>> generatePlayingGroups ()
	{
		ArrayList <ArrayList <PlayerObject>> playingGroups = new ArrayList <> ();
		HashMap <PlayerObject, ArrayList <PlayerObject>> matchGroups = hashMapDeepCopy (mMatchGroups);

		matchGroups = sortHashMapByKey (matchGroups);
		Iterator iterator  = matchGroups.entrySet ().iterator ();

		while (iterator.hasNext ())
		{
			Map.Entry <PlayerObject, ArrayList <PlayerObject>> pair = (Map.Entry <PlayerObject, ArrayList <PlayerObject>>) iterator.next ();
			ArrayList <PlayerObject> playerGroupVariations = pair.getValue ();

			PlayerObject playerOpponent = tryToGetPlayerOpponent (playingGroups, playerGroupVariations, pair.getKey ().getId ());

			if (playerOpponent == null)
				continue;

			playingGroups.add (new ArrayList <PlayerObject> ()
			{
				{
					add (pair.getKey ());
					add (playerOpponent);
				}
			});
		}

		return playingGroups;
	}


	private PlayerObject tryToGetPlayerOpponent (ArrayList <ArrayList <PlayerObject>> playingGroups, ArrayList <PlayerObject> opponentVariation, Integer pId)
	{
		while (opponentVariation.size () != 0)
		{
			Random rand = new Random ();
			int n = rand.nextInt (opponentVariation.size ());
			PlayerObject possibleOpponent = opponentVariation.get (n);

			if (playingGroups.size () == 0)
				return possibleOpponent;

			boolean isUnique = true;
			for (ArrayList <PlayerObject> playersInOneGroup : playingGroups)
			{
				if ((playersInOneGroup.get (1).getId ().equals (possibleOpponent.getId ())) || (playersInOneGroup.get (1).getId ().equals (pId)))
				{
					isUnique = false;
					break;
				}
			}

			if (! isUnique)
				opponentVariation.remove (n);
			else
				return possibleOpponent;
		}

		return null;
	}


	private HashMap <PlayerObject, ArrayList <PlayerObject>> hashMapDeepCopy (HashMap <PlayerObject, ArrayList <PlayerObject>> map)
	{
		HashMap <PlayerObject, ArrayList <PlayerObject>> copy = new HashMap <> ();
		Iterator iterator  = map.entrySet ().iterator ();

		while (iterator.hasNext ())
		{
			Map.Entry <PlayerObject, ArrayList <PlayerObject>> pair = (Map.Entry <PlayerObject, ArrayList <PlayerObject>>) iterator.next ();
			copy.put (new PlayerObject (pair.getKey ()), new ArrayList <> (pair.getValue ()));
		}

		return copy;
	}


	private static HashMap <PlayerObject, ArrayList <PlayerObject>> sortHashMapByKey (HashMap <PlayerObject, ArrayList <PlayerObject>> map)
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
}
