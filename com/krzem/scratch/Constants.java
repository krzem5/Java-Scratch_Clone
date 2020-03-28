package com.krzem.scratch;



import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.lang.Exception;



public class Constants{
	public static final int DISPLAY_ID=0;
	public static final GraphicsDevice SCREEN=GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[DISPLAY_ID];
	public static final Rectangle WINDOW_SIZE=SCREEN.getDefaultConfiguration().getBounds();
	public static final int MAX_FPS=60;
	public static final boolean GENERATE_LOG_FILES=false;

	public static final Color APP_BG_COLOR=new Color(20,145,230);

	public static final Color EDITOR_BG_COLOR=new Color(240,240,240);
	public static final int EDITOR_BG_MARKER_SIZE=1;
	public static final int EDITOR_BG_MARKER_DIST=25;
	public static final Color EDITOR_BG_MARKER_COLOR=new Color(140,140,140);
	public static final Color EDITOR_BORDER_COLOR=new Color(0,115,200);
	public static final int EDITOR_BORDER_SIZE=20;

	public static final String ASSETS_PATH="./com/krzem/scratch_clone/assets/";
	public static final String BLOCK_CLASS_PATH="./com/krzem/scratch_clone/blocks";
	public static final String LOG_PATH="./com/krzem/scratch_clone/log";

	public static final String BLOCK_GROUP_TEX_PATH="group/";
	public static final String FONT_TEX_PATH="font/";
	public static final String BLOCK_TEX_PATH="block/";
	public static final String CONNECTOR_TEX_PATH="connector/";

	public static final String FONT_CHARS=" .0123456789abcdefghijklmnopqrstuvwxyz";
	public static final String FONT_UNKNOWN_CHAR="?";
	public static final String FONT_UNKNOWN_CHAR_NAME="unknown";
	public static final String FONT_SPACE_CHAR=" ";
	public static final String FONT_SPACE_CHAR_NAME="space";
	public static final int FONT_CHAR_SPACE=1;

	public static final int SCROLLBAR_SIZE=20;
	public static final int SCROLLBAR_MARGIN=40;
	public static final Color SCROLLBAR_COLOR=new Color(65,65,65);
	public static final Color SCROLLBAR_DISABLED_COLOR=new Color(65,65,65,200);
	public static final Color SCROLLBAR_DRAGGING_COLOR=new Color(105,105,105);
	public static final Color SCROLLBAR_BACKGROUND_COLOR=new Color(120,120,120,100);
	public static final int SCROLLBAR_SCROLL_WHEEL_STEP_SIZE=50;

	public static final int CONNECTOR_GAP_SIZE=3;
	public static final int CONNECTOR_CODE_BLOCK_INNER_OFFSET=6;

	public static final int BLOCK_GROUP_PANEL_WIDTH=400;
	public static final Color BLOCK_GROUP_PANEL_BG_COLOR=new Color(250,250,250);
	public static final Color BLOCK_GROUP_PANEL_BORDER_COLOR=new Color(120,120,120);
	public static final int BLOCK_GROUP_PANEL_BORDER=6;
	public static final String BLOCK_GROUP_PANEL_DEFAULT_GROUP_NAME="default";
	public static final int BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH=100;
	public static final Color BLOCK_GROUP_PANEL_GROUP_PANEL_BG_COLOR=new Color(240,240,240);
	public static final int BLOCK_GROUP_PANEL_GROUP_PANEL_BORDER=3;
	public static final Color BLOCK_GROUP_PANEL_GROUP_PANEL_BORDER_COLOR=new Color(120,120,120);
	public static final int BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_DIST=20;
	public static final int BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE=50;
	public static final int BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_BORDER=5;
	public static final Color BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_BORDER_COLOR=new Color(200,200,200);
	public static final Color BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_DEFAULT_BG_COLOR=new Color(0,0,0);
	public static final int BLOCK_GROUP_PANEL_BLOCK_PADDING_X=10;
	public static final int BLOCK_GROUP_PANEL_BLOCK_PADDING_Y=10;
	public static final int BLOCK_GROUP_PANEL_BLOCK_MIN_DIST=20;
	public static final int BLOCK_GROUP_PANEL_BLOCK_MAX_DIST=45;
	public static final int BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_BEFORE_DIST=60;
	public static final int BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_AFTER_DIST=35;
	public static final int BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_FONT_SIZE=20;
	public static final Color BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_FONT_COLOR=new Color(85,95,105);
	public static final String BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_FONT_NAME="Heveltica";
	public static final int BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_FONT_MODIFIER=Font.BOLD;

	public static final int BLOCK_DEFAULT_WIDTH=100;
	public static final int BLOCK_DEFAULT_HEIGHT=30;
	public static final int BLOCK_CONTROL_DEFAULT_WIDTH=200;
	public static final int BLOCK_CONTROL_DEFAULT_HEIGHT=75;
	public static final Color BLOCK_TEXT_COLOR=new Color(250,250,250);
	public static final int BLOCK_TEXT_SIZE=15;
	public static final int BLOCK_NAME_OFF_X=6;
	public static final int BLOCK_NAME_OFF_Y=16;
	public static final int BLOCK_POP_OUT_X_OFF=20;
	public static final int BLOCK_POP_OUT_Y_OFF=20;
	public static final Color BLOCK_SHADOW_COLOR=new Color(200,200,200,200);
}