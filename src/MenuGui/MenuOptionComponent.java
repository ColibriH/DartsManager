package MenuGui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by vladislavs on 26.09.2016..
 */
public class MenuOptionComponent extends JPanel
{
	private JLayeredPane mLayeredPane;
	private JPanel mBGPanel;
	private JPanel mBtnPanel;

	public MenuOptionComponent ()
	{

		setLayout (new BorderLayout ());
		setPreferredSize (new Dimension (200, 100));
		setBackground (Color.BLUE);

		mLayeredPane = new JLayeredPane ();
		//mLayeredPane.setLayout (new BorderLayout ());
		mLayeredPane.setPreferredSize (new Dimension (200, 100));
		//mLayeredPane.setBounds (0, 0, 200, 100);
		mLayeredPane.setBackground (Color.RED);
		mLayeredPane.setVisible (true);

//		mBGPanel = new JPanel ();
//		mBtnPanel = new JPanel ();
//
//		mBGPanel.setLayout (new GridLayout ());
//		mBGPanel.setPreferredSize (new Dimension (20, 20));
//		mBGPanel.setBackground (Color.black);
//		mBGPanel.setBorder (new BevelBorder (BevelBorder.LOWERED));
//
//		mBGPanel.setLayout (new GridLayout ());
//		mBtnPanel.setBackground (Color.RED);
//		mBtnPanel.setBorder (new BevelBorder (BevelBorder.LOWERED));

		//mLayeredPane.add (mBGPanel, 0, 0);
		//mLayeredPane.add (mBGPanel, 1, 0);



		add (mLayeredPane, BorderLayout.EAST);
		//revalidate();
		//repaint();
	}
}
