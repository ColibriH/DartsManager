package GroupsController;

import MatchController.Gui.Components.GroupTournamentTableGroupPanel;
import MatchController.Objects.GroupPlayerObject;
import MatchController.Objects.PlayerObject;
import Tools.GroupGenerator;

import java.util.*;

// TODO BIG REFACTOR

public class GroupTournamentGroupsController
{
	private HashMap <PlayerObject, ArrayList <PlayerObject>>        mMatchGroups;
	private HashMap <Integer, ArrayList <GroupPlayerObject>>        mGameGroups;

	public GroupTournamentGroupsController (HashMap <PlayerObject, ArrayList <PlayerObject>> matchGroups)
	{
		mGameGroups = new HashMap <> ();
		mMatchGroups = matchGroups;

		// Done twice!
		mGameGroups.putAll (GroupGenerator.generateGroupTournamentRandomGroups (mGameGroups.size (), generatePlayingGroups ()));
		mGameGroups.putAll (GroupGenerator.generateGroupTournamentRandomGroups (mGameGroups.size (), generatePlayingGroups ()));

		linkGroupsWithPlayersAndCreatePanelObject ();
	}


	private void linkGroupsWithPlayersAndCreatePanelObject ()
	{
		for (int i = 0; i < mGameGroups.size (); i++)
		{
			GroupTournamentTableGroupPanel gp = new GroupTournamentTableGroupPanel (mGameGroups.get (i));
			GroupPlayerObject fGroup = mGameGroups.get (i).get (0);
			GroupPlayerObject sGroup = mGameGroups.get (i).get (1);

			fGroup.setGroupTournamentGroupPanel (gp);
			sGroup.setGroupTournamentGroupPanel (gp);
		}
	}


	private ArrayList <GroupPlayerObject> generatePlayingGroups ()
	{
		ArrayList <GroupPlayerObject> playingGroups = new ArrayList <> ();
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

			playingGroups.add (new GroupPlayerObject (pair.getKey (), playerOpponent));
		}

		return playingGroups;
	}


	private PlayerObject tryToGetPlayerOpponent (ArrayList <GroupPlayerObject> playingGroups, ArrayList <PlayerObject> opponentVariation, Integer pId)
	{
		while (opponentVariation.size () != 0)
		{
			Random rand = new Random ();
			int n = rand.nextInt (opponentVariation.size ());
			PlayerObject possibleOpponent = opponentVariation.get (n);

			if (playingGroups.size () == 0)
				return possibleOpponent;

			boolean isUnique = true;
			for (GroupPlayerObject playersInOneGroup : playingGroups)
			{
				if ((playersInOneGroup.getSecondPlayer ().getId ().equals (possibleOpponent.getId ())) || (playersInOneGroup.getSecondPlayer ().getId ().equals (pId)))
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


	public HashMap <Integer, ArrayList <GroupPlayerObject>> getGameGroups ()
	{
		return mGameGroups;
	}
}
