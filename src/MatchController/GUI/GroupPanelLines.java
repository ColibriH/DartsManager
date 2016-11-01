package MatchController.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Prud on 10/31/2016.
 */

public class GroupPanelLines extends JPanel
{
	private HashMap <Integer, ArrayList <DisplayGroupPanel>> mGroupsPanels;


	public GroupPanelLines (HashMap <Integer, ArrayList <DisplayGroupPanel>> groupsPanels)
	{
		if (groupsPanels != null)
			mGroupsPanels = new HashMap <> (groupsPanels);
	}


	@Override
	public Dimension getPreferredSize ()
	{
		int size = 10 * 30;
		return new Dimension (size, size);
	}


	@Override
	public void paintComponent (Graphics g)
	{
		if (mGroupsPanels == null || mGroupsPanels.size () == 0)
			return;

		HashMap <Integer, ArrayList <DisplayGroupPanel>> groupsPanels = cloneHashMap ();//new HashMap <> (mGroupsPanels);

		boolean firstOdd = false;

		for (int k = 1; k < groupsPanels.size () + 1; k++)
		{
			if (k + 1 > groupsPanels.size ())
				break;

			ArrayList <DisplayGroupPanel> a = groupsPanels.get (k);
			ArrayList <DisplayGroupPanel> b = groupsPanels.get (k + 1);

			int j = 0;
			for (int i = 0; i < a.size (); i++)
			{
				ArrayList<DisplayGroupPanel> t = null;
				DisplayGroupPanel curLvlPanel;
				DisplayGroupPanel curLvlPanel2;
				DisplayGroupPanel nextLvlPanel;

				if (i + 1 > a.size () - 1)
				{
					if (firstOdd && k != groupsPanels.size ())
					{
						curLvlPanel     = a.get (i);
						t = findPrev (groupsPanels);
						curLvlPanel2    = t.get (0);

						if (curLvlPanel2 == null)
							break;

						nextLvlPanel    = b.get (j);
					}
					else
					{
						firstOdd = true;
						break;
					}
				}
				else
				{
					curLvlPanel     = a.get (i);
					curLvlPanel2    = a.get (i + 1);
					nextLvlPanel    = b.get (j);
				}

				Point a1p1 = new Point (curLvlPanel.getX ()     + curLvlPanel.getWidth (),      curLvlPanel.getY ()  +  (curLvlPanel.getHeight () / 2));
				Point a1p2 = new Point (nextLvlPanel.getX ()    + nextLvlPanel.getWidth () / 2, curLvlPanel.getY ()  +  (curLvlPanel.getHeight () / 2));
				Point a1p3 = new Point (nextLvlPanel.getX ()    + nextLvlPanel.getWidth () / 2, nextLvlPanel.getY ());

				Point a2p1 = new Point (curLvlPanel2.getX ()    + curLvlPanel2.getWidth (),     curLvlPanel2.getY () +  (curLvlPanel2.getHeight () / 2));
				Point a2p2 = new Point (nextLvlPanel.getX ()    + nextLvlPanel.getWidth () / 2, curLvlPanel2.getY () +  (curLvlPanel2.getHeight () / 2));
				Point a2p3 = new Point (nextLvlPanel.getX ()    + nextLvlPanel.getWidth () / 2, nextLvlPanel.getY () +   nextLvlPanel.getHeight ());

				g.drawLine (a1p1.x, a1p1.y, a1p2.x, a1p2.y);
				g.drawLine (a1p2.x, a1p2.y, a1p3.x, a1p3.y);

				g.drawLine (a2p1.x, a2p1.y, a2p2.x, a2p2.y);
				g.drawLine (a2p2.x, a2p2.y, a2p3.x, a2p3.y);

				a.set (i, null);

				j++;
				i++;

				if (i < a.size ())
				{
					a.set (i, null);
				}
				else
				{
					if (t != null)
						t.remove (0);
				}
			}

			Iterator <DisplayGroupPanel> i = a.iterator ();
			while (i.hasNext ())
			{
				if (i.next () == null)
					i.remove ();
			}
		}
	}


	private ArrayList <DisplayGroupPanel> findPrev (HashMap <Integer, ArrayList <DisplayGroupPanel>> groupsPanels)
	{
		Iterator it = groupsPanels.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			ArrayList <DisplayGroupPanel> a = (ArrayList <DisplayGroupPanel>)pair.getValue ();

			if (a.size () != 0)
				return a;
		}

		return null;
	}


	private HashMap <Integer, ArrayList <DisplayGroupPanel>> cloneHashMap ()
	{
		HashMap <Integer, ArrayList <DisplayGroupPanel>> tmp = new HashMap <> ();
		Iterator it = mGroupsPanels.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			ArrayList <DisplayGroupPanel> a = new ArrayList <> ((ArrayList <DisplayGroupPanel>)pair.getValue ());

			tmp.put (new Integer ((Integer)pair.getKey ()), a);
		}

		return tmp;
	}
}