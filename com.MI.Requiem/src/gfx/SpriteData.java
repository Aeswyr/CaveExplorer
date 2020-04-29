package gfx;

import runtime.Light;

public class SpriteData {

	public static final int TYPE_ENTITY = Sprite.createLightData(1, Light.NONE);
	public static final int TYPE_GUI_COMPONENT = Sprite.createLightData(108, Light.IGNORE);
	public static final int TYPE_INVENTORY_ITEM = Sprite.createLightData(1010, Light.IGNORE);
	public static final int TYPE_GUI_FOREGROUND_SHAPE = Sprite.createLightData(109, Light.IGNORE);
	public static final int TYPE_GUI_BACKGROUND_SHAPE = Sprite.createLightData(107, Light.IGNORE);
	public static final int TYPE_GUI_ITEM_SHAPE = Sprite.createLightData(1011, Light.IGNORE);
	public static final int TYPE_ITEM_DROP = Sprite.createLightData(1, Light.NONE);
	public static final int TYPE_FLOOR = Sprite.createLightData(0, Light.NONE);
	public static final int TYPE_WALL = Sprite.createLightData(0, Light.NONE);
	public static final int TYPE_CEILING = Sprite.createLightData(2, Light.DIM);
	public static final int TYPE_TEXT = Sprite.createLightData(1012, Light.IGNORE);

}
