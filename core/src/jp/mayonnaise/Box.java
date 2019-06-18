package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class Box extends GameObject {
    public static final float BOX_WIDTH = 0.8f;
    public static final float BOX_HEIGHT = 0.8f;

    public Box(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(BOX_WIDTH, BOX_HEIGHT);
    }
}
