package BaseAbstractClasses;

import MatchController.MatchController;

import javax.swing.*;
import java.awt.*;

public abstract class DartsGuiFormBase
{
	protected abstract void initializeComponents    ();
	protected abstract void addComponentsListener   ();
	protected abstract void buildMainPanel          ();

	private         JFrame          mJFrame;
	private         JPanel          mJPanel;
	private         boolean         mIsMainFrameResizable;
	private final   MatchController mMatchController;


	public DartsGuiFormBase (MatchController matchController)
	{
		mMatchController        = matchController;
		mJFrame                 = new JFrame ();
		mJPanel                 = new JPanel ();
		mIsMainFrameResizable   = false;

		initializeComponents ();
		addComponentsListener ();
		buildMainFrame ();
	}


	public DartsGuiFormBase ()
	{
		this (null);
	}


	protected void addComponentToPanel (JComponent parent, Component child, int xPos, int yPos, Insets insets, int iPady, double weightX, double weighty, int gridWidth, Integer anchor, GridBagConstraints gbc, Integer fill)
	{
		if (fill != null)
			gbc.fill    = fill;

		gbc.gridx       = xPos;
		gbc.gridy       = yPos;
		gbc.insets      = insets;
		gbc.ipady       = iPady;
		gbc.weightx     = weightX;
		gbc.weighty     = weighty;
		gbc.gridwidth   = gridWidth;

		if (anchor != null)
			gbc.anchor  = anchor;

		parent.add (child, gbc);
	}


	protected JFrame getMainJFrame ()
	{
		return mJFrame;
	}


	protected JPanel getMainJPanel ()
	{
		return mJPanel;
	}


	protected void setMainJPanel (JPanel panel)
	{
		mJPanel = panel;
	}


	protected void setMainFrameResizable (boolean isResizable)
	{
		mIsMainFrameResizable = isResizable;
		mJFrame.setResizable (mIsMainFrameResizable);
	}


	private void setMainJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void buildMainFrame ()
	{
		setMainJFrameLocation ();
		buildMainPanel ();
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.setContentPane (mJPanel);
		mJFrame.setResizable (mIsMainFrameResizable);
		mJFrame.pack ();
		mJFrame.setVisible (true);
	}


	public void setVisibility (boolean visibilityFlag)
	{
		mJFrame.setVisible (visibilityFlag);
	}


	public boolean isMainFrameResizable ()
	{
		return mIsMainFrameResizable;
	}


	public MatchController getMatchController ()
	{
		return mMatchController;
	}


	public void destroy ()
	{
		mJFrame.dispose ();
	}
}
