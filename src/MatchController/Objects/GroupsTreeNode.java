package MatchController.Objects;

import MatchController.Gui.Components.TournamentTableGroupPanel;

import java.util.ArrayList;

public class GroupsTreeNode
{
	private ArrayList <GroupsTreeNode>  mChildrens;
	private ArrayList <PlayerObject>    mPlayer;
	private TournamentTableGroupPanel   mTournamentTableGroupPanel;
	private GroupsTreeNode              mParent;


	public GroupsTreeNode (TournamentTableGroupPanel tournamentTableGroupPanel)
	{
		mPlayer = new ArrayList <> ();
		mChildrens                  = new ArrayList <> ();
		mTournamentTableGroupPanel  = tournamentTableGroupPanel;
	}


	public GroupsTreeNode (TournamentTableGroupPanel tournamentTableGroupPanel, ArrayList <PlayerObject> playersInGroup)
	{
		this (tournamentTableGroupPanel);
		mPlayer = new ArrayList <> (playersInGroup);
	}


	public void addChildren (GroupsTreeNode childrenNodes)
	{
		mChildrens.add (childrenNodes);
	}


	public void setParent (GroupsTreeNode parentNode)
	{
		mParent = parentNode;
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


	public ArrayList <PlayerObject> getPlayer ()
	{
		return mPlayer;
	}


	public void setPlayerObjects (ArrayList <PlayerObject> player)
	{
		mPlayer = player;
	}
}
