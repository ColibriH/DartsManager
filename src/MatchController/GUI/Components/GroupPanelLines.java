package MatchController.GUI.Components;

import MatchController.Objects.GroupsTreeNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Prud on 10/31/2016.
 */

// TODO Refactor

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

		for (int k = mGroupsPanels.size () - 1; k > 0; k--)
		{
			ArrayList <GroupsTreeNode> a = mGroupsPanels.get (k);
			for (int i = 0; i < a.size (); i++)
			{
				GroupsTreeNode node = a.get (i);

				for (int j = 0; j < node.getChildrens ().size (); j++)
				{
					DisplayGroupPanel curLvlPanel = node.getChildrens ().get (j).getDisplayGroupPanel ();
					DisplayGroupPanel nextLvlPanel = node.getDisplayGroupPanel ();

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