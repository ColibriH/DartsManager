package Constants;

import java.io.File;

public class Constats
{
	//=============================================Variables===========================================================
	public static final int MAIN_WIDTH                      = 620;
	public static final int MAIN_HEIGHT                     = 800;

	public static enum GameType
	{
		Tournament,
		GroupTournament
	}

	private static final String PIC_FOLDER_PATH             = "pic_";
	private static final String FONT_FOLDER_PATH            = "font_";

	private static final String FONT_FOLDER_FULL_PATH       = System.getProperty("user.dir") + File.separator + Constats.FONT_FOLDER_PATH + File.separator;
	private static final String PIC_FOLDER_FULL_PATH        = System.getProperty("user.dir") + File.separator + Constats.PIC_FOLDER_PATH + File.separator;

	public static final String DELETE_BTN_ID                = "Delete";
	public static final String EDIT_BTN_ID                  = "Edit";


	//=============================================ImagePaths===========================================================
	public static final String DEFAULT_PIC                  = PIC_FOLDER_FULL_PATH  + "p_m.pnh";

	//==================================================================================================================
	//=============================================Menu=================================================================
	//==================================================================================================================
	public static final String BTN_TEXTURE_PIC              = PIC_FOLDER_FULL_PATH  + "c_b.png";
	public static final String BTN_FILLER_PIC               = PIC_FOLDER_FULL_PATH  + "c_f.png";
	public static final String BTN_TEXTURE_PIC_150x25       = PIC_FOLDER_FULL_PATH  + "c_b_150x25.png";
	public static final String BTN_FILLER_PIC_150x25        = PIC_FOLDER_FULL_PATH  + "c_f_150x25.png";
	public static final String MENU_BG_PIC                  = PIC_FOLDER_FULL_PATH  + "m_b.jpg";

	//==================================================================================================================
	//=============================================GameDisplayFrame=====================================================
	//==================================================================================================================

	public static final String GAME_MAIN_BOARD_PIC          = PIC_FOLDER_FULL_PATH  + "g_b.png";
	public static final String TURN_ARROW_PIC               = PIC_FOLDER_FULL_PATH  + "t_a.png";
	public static final String DEFAULT_PLAYER_PIC_LEFT      = PIC_FOLDER_FULL_PATH  + "d_p_l.png";
	public static final String DEFAULT_PLAYER_PIC_RIGHT     = PIC_FOLDER_FULL_PATH  + "d_p_r.png";

	//==================================================================================================================
	//=============================================PlayersRegistration==================================================
	//==================================================================================================================
	public static final String TABLE_DELETE_PIC             = PIC_FOLDER_FULL_PATH  + "c_r.jpg";
	public static final String TABLE_EDIT_PIC               = PIC_FOLDER_FULL_PATH  + "c_e.jpg";
	public static final String PL_OPEN_BOARD_PIC            = PIC_FOLDER_FULL_PATH  + "p_r_bg.jpg";
	public static final String TABLE_BOARD_PIC              = PIC_FOLDER_FULL_PATH  + "r_b.png";

	//==================================================================================================================
	//=============================================TournamentTable======================================================
	//==================================================================================================================
	public static final String RIGHT_DART_PIC               = PIC_FOLDER_FULL_PATH  + "d_r.png";
	public static final String LEFT_DART_PIC                = PIC_FOLDER_FULL_PATH  + "d_l.png";
	public static final String TOURNAMENT_TABLE_BG          = PIC_FOLDER_FULL_PATH  + "c_b.jpg";

	//==================================================================================================================
	//=============================================WinnerFrame==========================================================
	//==================================================================================================================
	public static final String WINNER_PIC                   = PIC_FOLDER_FULL_PATH  + "winner.png";


	//=============================================FontPaths============================================================
	public static final String CHALK_FONT1                  = FONT_FOLDER_FULL_PATH + "erasdust.ttf";
	public static final String CHALK_FONT2                  = FONT_FOLDER_FULL_PATH + "Eraser.ttf";
	public static final String SCRATHC_FONT                 = FONT_FOLDER_FULL_PATH + "nailscratch.ttf";
}
