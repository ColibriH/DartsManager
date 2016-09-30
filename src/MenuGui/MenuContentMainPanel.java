package MenuGui;

import MatchController.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislavs on 30.09.2016..
 */
public class MenuContentMainPanel extends JPanel
{
	Image bg = ImageLoader.getImage (Constats.MENU_BG_PIC);

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, bg.getWidth(null), bg.getHeight(null), this);
	}
}
