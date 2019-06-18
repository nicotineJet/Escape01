package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class ItemBar extends GameObject {
    public static final float ITEMBAR_WIDTH = BedScreen.CAMERA_WIDTH;
    public static final float ITEMBAR_HEIGHT = 1.5f;

    public ItemBar(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(ITEMBAR_WIDTH, ITEMBAR_HEIGHT);
    }

    public void open(){
        setAlpha(1);
    }

    public void close(){
        setAlpha(0);
    }
}
