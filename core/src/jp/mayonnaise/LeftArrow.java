package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class LeftArrow extends GameObject{
    public static final float lArrow_WIDTH = 1.7f;
    public static final float lArrow_HEIGHT = 1.3f;

    public LeftArrow(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(lArrow_WIDTH, lArrow_HEIGHT);
    }
}
