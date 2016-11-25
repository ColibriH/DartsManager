package GroupsController;

import MatchController.Objects.PlayerObject;

import java.util.*;

/**
 * Created by vladislavs on 23.11.2016..
 */
public class GroupTournamentGroupsController
{
	private HashMap <PlayerObject, ArrayList <PlayerObject>> mMatchGroups;
	private ArrayList <ArrayList<PlayerObject>> mPlayingGroups;

	public GroupTournamentGroupsController (HashMap <PlayerObject, ArrayList <PlayerObject>> matchGroups)
	{
		mMatchGroups = matchGroups;
		generatePlayingGroups ();
	}

	public void generatePlayingGroups ()
	{
		HashMap <PlayerObject, ArrayList <PlayerObject>> matchGroups = new HashMap <> (mMatchGroups);// TODO May be can use better way than create copy of it
		Random rand = new Random ();
		Iterator iterator = matchGroups.entrySet ().iterator ();

		while (iterator.hasNext ())
		{
			Map.Entry <PlayerObject, ArrayList <PlayerObject>> pair = (<PlayerObject, ArrayList <PlayerObject>>) iterator.next ();
		}

		int groupsCount = matchGroups.size () / 2;
		for (int i = 0; i < groupsCount; i++)
		{
			int  n = rand.nextInt(matchGroups.);
		}
	}
}
