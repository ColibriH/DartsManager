package GameController.GUI;

import MatchController.Objects.PlayerObject;
import MatchController.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislavs on 07.09.2016..
 */
public class GameDisplayGuiForm
{
	private JFrame          mJFrame;
	private JPanel          mJPanel;

	private JTextField      mFirstPlayerName;
	private JTextField      mFirstPlayerLeg;
	private JTextField      mFirstPlayerScore;
	private JLabel          mFPlayerLabel;

	private JTextField      mSecondPlayerName;
	private JTextField      mSecondPlayerLeg;
	private JTextField      mSecondPlayerScore;
	private JLabel          mSPlayerLabel;

	private JLabel          mMainImage;
	private JLabel          fPlayerImage;
	private JLabel          sPlayerImage;
	private JLabel          mMPlayerArrow;
	private JLabel          mOpponentPlayerArrow;

	private PlayerObject    mPlayer;
	private PlayerObject    mPlayerOpponent;



	public GameDisplayGuiForm (PlayerObject firstPlayer, PlayerObject secondPlayer)
	{
		mPlayer         = firstPlayer;
		mPlayerOpponent = secondPlayer;

		formInitialization ();
		iconsInitialization ();
		playerTableInitialization ();
	}


	private void formInitialization ()
	{
		mJFrame = new JFrame ("GameDisplayGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.setUndecorated(true);
		mJFrame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
		mJFrame.pack ();
		mJFrame.setVisible (true);

		setMJFrameLocation ();
	}


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void iconsInitialization ()
	{
		initializePlayerTurnArrows ();

		setIconToLabel (new ImageIcon (ImageLoader.getImage   (Constats.MAIN_BOARD_PIC)), mMainImage);
		setIconToLabel (new ImageIcon (ImageLoader.getImage   (Constats.DEFAULT_PLAYER_PIC_RIGHT)), sPlayerImage);
		setIconToLabel (new ImageIcon (ImageLoader.getImage   (Constats.DEFAULT_PLAYER_PIC_LEFT)), fPlayerImage);
	}


	private void initializePlayerTurnArrows ()
	{
		ImageIcon arrow = new ImageIcon (ImageLoader.getImage   (Constats.TURN_ARROW_PIC));

		setIconToLabel (arrow, mOpponentPlayerArrow);
		setIconToLabel (arrow, mMPlayerArrow);

		mOpponentPlayerArrow    .setVisible (false);
		mMPlayerArrow           .setVisible (false);

		mPlayer         .setTurnArrow (mMPlayerArrow);
		mPlayerOpponent .setTurnArrow (mOpponentPlayerArrow);
	}


	private void setIconToLabel (ImageIcon imageIcon, JLabel label)
	{
		if (imageIcon != null)
			label.setIcon (imageIcon);
	}


	private void playerTableInitialization ()
	{
		mSPlayerLabel.setText       (mPlayer.mName);
		mFirstPlayerName.setText    (mPlayer.mName);
		mFirstPlayerLeg.setText     (String.valueOf (mPlayer.mLeg));
		mFirstPlayerScore.setText   (String.valueOf (mPlayer.mScore));

		mFPlayerLabel.setText       (mPlayerOpponent.mName);
		mSecondPlayerName.setText   (mPlayerOpponent.mName);
		mSecondPlayerLeg.setText    (String.valueOf (mPlayerOpponent.mLeg));
		mSecondPlayerScore.setText  (String.valueOf (mPlayerOpponent.mScore));
	}


	public void updatePlayerData ()
	{
		mFirstPlayerScore.setText (String.valueOf (mPlayer.mScore));
		mSecondPlayerScore.setText (String.valueOf (mPlayerOpponent.mScore));
	}


	public void updateGameLegData ()
	{
		mFirstPlayerLeg.setText     (String.valueOf (mPlayer.mLeg));
		mFirstPlayerScore.setText   (String.valueOf (mPlayer.mScore));

		mSecondPlayerLeg.setText    (String.valueOf (mPlayerOpponent.mLeg));
		mSecondPlayerScore.setText  (String.valueOf (mPlayerOpponent.mScore));
	}


	public Dimension getSize ()
	{
		return mJFrame.getSize ();
	}


	public Point getLocation ()
	{
		return mJFrame.getLocation ();
	}


	public void destroy ()
	{
		mJFrame.setVisible (false);
		mJFrame.dispose ();
	}
}
