package TournamentGameController.GUI;

import MatchController.Objects.PlayerObject;
import Constants.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class TournamentGameDisplayGuiForm
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


	public TournamentGameDisplayGuiForm (PlayerObject firstPlayer, PlayerObject secondPlayer)
	{
		mPlayer         = firstPlayer;
		mPlayerOpponent = secondPlayer;
		initializeForm ();
		initializeIcons ();
		initializePlayerTable ();
	}


	private void initializeForm ()
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


	private void initializeIcons ()
	{
		initializePlayerTurnArrows ();

		setIconToLabel (new ImageIcon (ImageLoader.getImage   (Constats.GAME_MAIN_BOARD_PIC)),      mMainImage);
		setIconToLabel (new ImageIcon (ImageLoader.getImage   (Constats.DEFAULT_PLAYER_PIC_RIGHT)), sPlayerImage);
		setIconToLabel (new ImageIcon (ImageLoader.getImage   (Constats.DEFAULT_PLAYER_PIC_LEFT)),  fPlayerImage);
	}


	private void initializePlayerTurnArrows ()
	{
		ImageIcon arrow = new ImageIcon (ImageLoader.getImage   (Constats.TURN_ARROW_PIC));

		setIconToLabel (arrow, mOpponentPlayerArrow);
		setIconToLabel (arrow, mMPlayerArrow);

		mPlayer.setTurnArrow            (mMPlayerArrow);
		mMPlayerArrow.setVisible        (false);
		mPlayerOpponent.setTurnArrow    (mOpponentPlayerArrow);
		mOpponentPlayerArrow.setVisible (false);
	}


	private void setIconToLabel (ImageIcon imageIcon, JLabel label)
	{
		if (imageIcon != null)
			label.setIcon (imageIcon);
	}


	private void initializePlayerTable ()
	{
		mSPlayerLabel.setText       (mPlayer.getName ());
		mFirstPlayerName.setText    (mPlayer.getName ());
		mFirstPlayerLeg.setText     (String.valueOf (mPlayer.getLeg ()));
		mFirstPlayerScore.setText   (String.valueOf (mPlayer.getScore ()));
		mFPlayerLabel.setText       (mPlayerOpponent.getName ());
		mSecondPlayerName.setText   (mPlayerOpponent.getName ());
		mSecondPlayerLeg.setText    (String.valueOf (mPlayerOpponent.getLeg ()));
		mSecondPlayerScore.setText  (String.valueOf (mPlayerOpponent.getScore ()));
	}


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	public void updatePlayerData ()
	{
		mFirstPlayerScore.setText (String.valueOf (mPlayer.getScore ()));
		mSecondPlayerScore.setText (String.valueOf (mPlayerOpponent.getScore ()));
	}


	public void updateGameLegData ()
	{
		mFirstPlayerLeg.setText     (String.valueOf (mPlayer.getLeg ()));
		mFirstPlayerScore.setText   (String.valueOf (mPlayer.getScore ()));

		mSecondPlayerLeg.setText    (String.valueOf (mPlayerOpponent.getLeg ()));
		mSecondPlayerScore.setText  (String.valueOf (mPlayerOpponent.getScore ()));
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
