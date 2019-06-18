package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class Pen extends GameObject {
    public static final float pen_WIDTH = 1.3f;
    public static final float pen_HEIGHT = 1.3f;

    public static final int PEN_EXIST = 0;
    public static final int PEN_NONE = 1;

    int mState;

    public Pen(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(pen_WIDTH, pen_HEIGHT);
        mState = PEN_EXIST;
    }

    public void get(){
        mState = PEN_NONE;
        setAlpha(0);
    }

    public void set(){
        mState = PEN_EXIST;
        setAlpha(1);
    }
}
