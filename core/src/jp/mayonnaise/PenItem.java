package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class PenItem extends GameObject {
    public static final float penItem_WIDTH = 1.2f;
    public static final float penItem_HEIGHT = 1.2f;

    public static final int PENITEM_EXIST = 0;
    public static final int PENITEM_NONE = 1;

    int mState;

    public PenItem(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(penItem_WIDTH, penItem_HEIGHT);
        mState = PENITEM_EXIST;
    }

    public void close(){
        mState = PENITEM_NONE;
        setAlpha(0);
    }

    public void open() {
        mState = PENITEM_EXIST;
        setAlpha(1);
    }
}
