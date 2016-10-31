package MatchController.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Prud on 10/31/2016.
 */

public class GroupPanelLines extends JPanel
{
    private HashMap <Integer, ArrayList<DisplayGroupPanel>> mGroupsPanels;

    public GroupPanelLines(HashMap <Integer, ArrayList<DisplayGroupPanel>> groupsPanels)
    {
        if (groupsPanels != null)
            mGroupsPanels = new HashMap <> (groupsPanels);
    }

    @Override
    public Dimension getPreferredSize()
    {
        int size = 10 * 30;
        return new Dimension(size, size);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        if (mGroupsPanels == null || mGroupsPanels.size() == 0)
            return;

        for (int k = 1; k < mGroupsPanels.size() + 1; k++)
        {
            if (k + 2 > mGroupsPanels.size())
                break;

            ArrayList<DisplayGroupPanel> a = mGroupsPanels.get(k);
            ArrayList<DisplayGroupPanel> b = mGroupsPanels.get(k + 1);

            int j = 0;
            for (int i = 0; i < a.size(); i++)
            {
                if (i + 1 > a.size() - 1)
                    break;

                Point a1p1 = new Point(a.get(i).getX() + a.get(i).getWidth(), a.get(i).getY() + (a.get(i).getHeight() / 2));
                Point a1p2 = new Point(b.get(j).getX() + b.get(j).getWidth() / 2, a.get(i).getY() + (a.get(i).getHeight() / 2));
                Point a1p3 = new Point(b.get(j).getX() + b.get(j).getWidth() / 2, b.get(j).getY());

                Point a2p1 = new Point(a.get(i + 1).getX() + a.get(i + 1).getWidth(), a.get(i + 1).getY() + (a.get(i + 1).getHeight() / 2));
                Point a2p2 = new Point(b.get(j).getX() + b.get(j).getWidth() / 2, a.get(i + 1).getY() + (a.get(i + 1).getHeight() / 2));
                Point a2p3 = new Point(b.get(j).getX() + b.get(j).getWidth() / 2, b.get(j).getY() + b.get(j).getHeight());

                g.drawLine(a1p1.x, a1p1.y, a1p2.x, a1p2.y);
                g.drawLine(a1p2.x, a1p2.y, a1p3.x, a1p3.y);

                g.drawLine(a2p1.x, a2p1.y, a2p2.x, a2p2.y);
                g.drawLine(a2p2.x, a2p2.y, a2p3.x, a2p3.y);

                j++;
                i++;
            }
        }
    }
}