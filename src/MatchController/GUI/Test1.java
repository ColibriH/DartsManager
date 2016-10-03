package MatchController.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislavs on 03.10.2016..
 */
public class Test1
{
	private JFrame mJFrame;
	private JPanel mJPanel;
	private JPanel mBtnPanel;
	private JPanel mTablePanel;


	public static void main (String[] args)
	{
		new Test1 ();
	}


	public Test1 ()
	{
		initialization ();
	}


	private void addTablePanel (GridBagConstraints gbc)
	{
		mTablePanel = new JPanel ();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets  = new Insets (5, 0, 5, 0);

		mJPanel.add (mTablePanel, gbc);
	}


	private void addBtnPanel (GridBagConstraints mainGbc)
	{
		mBtnPanel = new JPanel ();
		mBtnPanel.setLayout (new GridBagLayout ());

		addComponentsToBtnPanel ();

		mainGbc.fill = GridBagConstraints.HORIZONTAL;
		mainGbc.gridx = 0;
		mainGbc.gridy = 0;
		mainGbc.insets  = new Insets (5, 0, 5, 0);

		mBtnPanel.setBackground (Color.red);


		mJPanel.add (mBtnPanel, mainGbc);
	}


	private void addComponentsToBtnPanel ()
	{
		GridBagConstraints panelGbc = new GridBagConstraints ();



		panelGbc.fill = GridBagConstraints.HORIZONTAL;
		panelGbc.gridx = 0;
		panelGbc.gridy = 0;
		panelGbc.insets  = new Insets (5, 0, 5, 0);

		mBtnPanel
	}


	private void initialization ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();
		mJPanel = new JPanel ();
		mJPanel.setLayout (new GridBagLayout ());

		addBtnPanel     (gbc);
		addTablePanel   (gbc);

		mJFrame =  new JFrame ();
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);
	}
}
