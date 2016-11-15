package Constants;

import java.io.File;

// TODO Refactor replace based on forms

/**
 * Created by vladislavs on 12.09.2016..
 */
public class Constats
{
	public static final int MAIN_WIDTH            = 620;
	public static final int MAIN_HEIGHT           = 800;

	public static final String PIC_FOLDER_PATH      = "pic_";
	public static final String FONT_FOLDER_PATH     = "font_";

	public static final String FONT_FOLDER_FULL_PATH = System.getProperty("user.dir") + File.separator +
																				Constats.FONT_FOLDER_PATH +
																				File.separator;

	private static final String PIC_FOLDER_FULL_PATH = System.getProperty("user.dir") + File.separator +
																				Constats.PIC_FOLDER_PATH +
																				File.separator;

	public static final String DELETE_BTN_ID                = "Delete";
	public static final String EDIT_BTN_ID                  = "Edit";

	public static final String DELETE_PIC                   = PIC_FOLDER_FULL_PATH  + "chalk_remove.jpg";
	public static final String EDIT_PIC                     = PIC_FOLDER_FULL_PATH  + "chalk_edit.jpg";

	public static final String RIGHT_DART_PIC               = PIC_FOLDER_FULL_PATH  + "dart_right.png";
	public static final String LEFT_DART_PIC                = PIC_FOLDER_FULL_PATH  + "dart_left.png";
	public static final String MAIN_BOARD_PIC               = PIC_FOLDER_FULL_PATH  + "board.png";
	public static final String TURN_ARROW_PIC               = PIC_FOLDER_FULL_PATH  + "turn_arrow.png";
	public static final String DEFAULT_PLAYER_PIC_LEFT      = PIC_FOLDER_FULL_PATH  + "def_player_left.png";
	public static final String DEFAULT_PLAYER_PIC_RIGHT     = PIC_FOLDER_FULL_PATH  + "def_player_right.png";
	public static final String WINNER_PIC                   = PIC_FOLDER_FULL_PATH  + "winner.png";
	public static final String BTN_TEXTURE_PIC              = PIC_FOLDER_FULL_PATH  + "chalk_borders.png";
	public static final String BTN_FILLER_PIC               = PIC_FOLDER_FULL_PATH  + "chalk_filler.png";
	public static final String BTN_TEXTURE_PIC_150x25       = PIC_FOLDER_FULL_PATH  + "chalk_borders_150x25.png";
	public static final String BTN_FILLER_PIC_150x25        = PIC_FOLDER_FULL_PATH  + "chalk_filler_150x25.png";

	public static final String MENU_BG_PIC                  = PIC_FOLDER_FULL_PATH  + "menuBg.jpg";
	public static final String OPEN_BOARD_PIC               = PIC_FOLDER_FULL_PATH  + "matchmenu-bg.jpg";
	public static final String RIGHT_BOARD_PIC              = PIC_FOLDER_FULL_PATH  + "right_board.png";
	public static final String CHALK_BOARD                  = PIC_FOLDER_FULL_PATH  + "chalk_board.jpg";

	public static final String CHALK_FONT1                  = FONT_FOLDER_FULL_PATH + "erasdust.ttf";
	public static final String CHALK_FONT2                  = FONT_FOLDER_FULL_PATH + "Eraser.ttf";
	public static final String SCRATHC_FONT                 = FONT_FOLDER_FULL_PATH + "nailscratch.ttf";
}
