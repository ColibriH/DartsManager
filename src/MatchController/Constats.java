package MatchController;

import java.io.File;

/**
 * Created by vladislavs on 12.09.2016..
 */
public class Constats
{
	public static final String PIC_FOLDER_PATH  = "pic_";

	private static final String PIC_FOLDER_FULL_PATH = System.getProperty("user.dir") + File.separator +
																				Constats.PIC_FOLDER_PATH +
																				File.separator;

	public static final String TABLE_PIC                    = PIC_FOLDER_FULL_PATH + "table_pic.jpeg";
	public static final String DELETE_PIC                   = PIC_FOLDER_FULL_PATH + "delete_pic.png";
	public static final String EDIT_PIC                     = PIC_FOLDER_FULL_PATH + "edit_pic.png";

	public static final String RIGHT_DART_PIC               = PIC_FOLDER_FULL_PATH + "dart_right.png";
	public static final String LEFT_DART_PIC                = PIC_FOLDER_FULL_PATH + "dart_left.png";

	public static final String MAIN_BOARD_PIC               = PIC_FOLDER_FULL_PATH + "board.png";
	public static final String TURN_ARROW_PIC               = PIC_FOLDER_FULL_PATH + "turn_arrow.png";
	public static final String DEFAULT_PLAYER_PIC_LEFT      = PIC_FOLDER_FULL_PATH + "def_player_left.png";
	public static final String DEFAULT_PLAYER_PIC_RIGHT     = PIC_FOLDER_FULL_PATH + "def_player_right.png";
	public static final String WINNER_PIC                   = PIC_FOLDER_FULL_PATH + "winner.png";

	public static final String DELETE_BTN_ID                = "Delete";
	public static final String EDIT_BTN_ID                  = "Edit";

	public static final String TEST                         = PIC_FOLDER_FULL_PATH + "test.gif";
}
