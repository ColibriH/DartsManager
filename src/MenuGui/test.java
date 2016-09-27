package MenuGui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislavs on 26.09.2016..
 */
public class test
{
	private JPanel mJPanel;
	private JFrame mJFrame;

	public static void main (String[] args)
	{
		new test ();
	}


	public test ()
	{
		mJFrame = new JFrame ("test");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);

		mJPanel.setLayout (new FlowLayout ());

		JPanel mJPanel = new JPanel ();
		mJPanel.setLayout (new FlowLayout ());
		mJPanel.setPreferredSize (new Dimension (200, 100));
		mJPanel.setBackground (Color.BLUE);

		JLayeredPane mJLayeredPane = new JLayeredPane ();
		mJLayeredPane.setOpaque (true);
		mJLayeredPane.setBackground (new Color (255, 255, 0 ,0));
		mJLayeredPane.setPreferredSize(new Dimension(80, 50));
		mJLayeredPane.setBorder(BorderFactory.createLineBorder(Color.green));

		JPanel mCornerIconsPanel = new JPanel (new BorderLayout ());
		mCornerIconsPanel.setBackground (new Color (255, 255, 0, 0));
		mCornerIconsPanel.add (new JLabel ("bg"));
		mCornerIconsPanel.setBounds (0, 0, 20, 20);


		JPanel mBtnBgPanel = new JPanel (new BorderLayout ());
		mBtnBgPanel.setBackground (new Color (255, 255, 0, 0));
		mBtnBgPanel.setBounds (10, 10, 60, 30);
		mBtnBgPanel.add (new JLabel ("btnbg"));


		JPanel mBtnPanel = new JPanel ();
		mBtnPanel.setLayout (new BorderLayout ());
		mBtnPanel.setBackground (Color.green);
		mBtnPanel.setBounds (10, 10, 60, 30);


		mJLayeredPane.add (mCornerIconsPanel, 0);
		mJLayeredPane.add (mBtnPanel, 1);
		mJLayeredPane.add (mBtnBgPanel, 2);


		mJPanel.add (mJLayeredPane);
		mJLayeredPane.setBorder(BorderFactory.createLineBorder(Color.green));

		this.mJPanel.add (mJPanel);
		mJFrame.pack ();
		mJFrame.setVisible (true);
	}
}
