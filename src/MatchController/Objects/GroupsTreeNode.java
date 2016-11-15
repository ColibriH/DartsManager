package MatchController.Objects;

import MatchController.Gui.Components.TournamentTableGroupPanel;

import java.util.ArrayList;

/**
 * Created by vladislavs on 03.11.2016..
 */
public class GroupsTreeNode
{
	private ArrayList <GroupsTreeNode> mChildrens;
	private ArrayList <PlayerObject> mPlayerObjects;
	private TournamentTableGroupPanel mTournamentTableGroupPanel;
	private GroupsTreeNode mParent;


	public GroupsTreeNode (TournamentTableGroupPanel group)
	{
		mPlayerObjects      = new ArrayList <> ();
		mChildrens          = new ArrayList <> ();
		mTournamentTableGroupPanel = group;
	}


	public GroupsTreeNode (TournamentTableGroupPanel group, ArrayList <PlayerObject> playersInGroup)
	{
		this (group);
		mPlayerObjects = new ArrayList <> (playersInGroup);
	}


	public void addChildren (GroupsTreeNode children)
	{
		mChildrens.add (children);
	}


	public void setParent (GroupsTreeNode parent)
	{
		mParent = parent;
	}


	public TournamentTableGroupPanel getDisplayGroupPanel ()
	{
		return mTournamentTableGroupPanel;
	}


	public GroupsTreeNode getParent ()
	{
		return mParent;
	}


	public ArrayList <GroupsTreeNode> getChildrens ()
	{
		return mChildrens;
	}


	public ArrayList <PlayerObject> getPlayerObjects ()
	{
		return mPlayerObjects;
	}


	public void setPlayerObjects (ArrayList <PlayerObject> mPlayerObjects)
	{
		this.mPlayerObjects = mPlayerObjects;
	}
}
