package MatchController.Gui.Components;

import MatchController.Objects.GroupsTreeNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupPanelLines extends JPanel
{
	private HashMap <Integer, ArrayList <GroupsTreeNode>> mGroupsPanels;


	public GroupPanelLines (HashMap <Integer, ArrayList <GroupsTreeNode>> groupsPanels)
	{
		if (groupsPanels != null)
			mGroupsPanels = new HashMap <> (groupsPanels);
	}


	@Override
	public void paintComponent (Graphics g)
	{
		if (mGroupsPanels == null || mGroupsPanels.size () == 0)
			return;

		g.setColor (Color.white);

		for (int k = mGroupsPanels.size () - 1; k > 0; k--)
		{
			ArrayList <GroupsTreeNode> a = mGroupsPanels.get (k);
			for (GroupsTreeNode node : a)
			{
				for (int j = 0; j < node.getChildrens ().size (); j++)
				{
					TournamentTableGroupPanel curLvlPanel = node.getChildrens ().get (j).getDisplayGroupPanel ();
					TournamentTableGroupPanel nextLvlPanel = node.getDisplayGroupPanel ();

					Point a1p1 = new Point (curLvlPanel.getX () + curLvlPanel.getWidth (), curLvlPanel.getY () + (curLvlPanel.getHeight () / 2));
					Point a1p2 = new Point (nextLvlPanel.getX () + nextLvlPanel.getWidth () / 2, curLvlPanel.getY () + (curLvlPanel.getHeight () / 2));
					Point a1p3 = new Point (nextLvlPanel.getX () + nextLvlPanel.getWidth () / 2, nextLvlPanel.getY ());

					if (j % 2 != 0)
						a1p3 = new Point (nextLvlPanel.getX () + nextLvlPanel.getWidth () / 2, nextLvlPanel.getY () + nextLvlPanel.getHeight ());

					g.drawLine (a1p1.x, a1p1.y, a1p2.x, a1p2.y);
					g.drawLine (a1p2.x, a1p2.y, a1p3.x, a1p3.y);
				}
			}
		}
	}
}