package MenuGui;

import MatchController.MatchController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vladislavs on 23.09.2016..
 */

// TODO refactor

public class MenuGuiForm
{
	private JFrame mJFrame;
	private JPanel mJPanel;
	private JButton tournamentButton;
	private JButton exitButton;
	private JButton optionsButton;
	private JButton eachVsEachButton;
	private JPanel mCtrBtnPanel;


	public MenuGuiForm ()
	{
		frameInitialization ();
		formComponentsModifications ();
	}


	private void formComponentsModifications ()
	{
		exitButton.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				System.exit (1);
			}
		});

		tournamentButton.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				new MatchController ();
				destroy ();
			}
		});
	}


	private void frameInitialization ()
	{
		mJFrame = new JFrame ("GameManagerGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);
		mJPanel.setLayout (new BorderLayout ());

		setMJFrameLocation ();
		setStyle ();
	}


	private void setStyle ()
	{
		createLayerPane ();
	}


	private void createLayerPane ()
	{
		JLayeredPane layeredPane = new JLayeredPane ();
		layeredPane.setOpaque (true);
		layeredPane.setBackground (Color.RED);
		layeredPane.setPreferredSize(new Dimension(800, 600));
		layeredPane.setBorder(BorderFactory.createLineBorder(Color.green));


		mJPanel.add (layeredPane, BorderLayout.CENTER);
	}


	private void destroy ()
	{
		mJFrame.dispose ();
	}


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}
}
