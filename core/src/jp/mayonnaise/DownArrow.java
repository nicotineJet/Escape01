package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class DownArrow extends GameObject {
    public static final float dArrow_WIDTH = 1.3f;
    public static final float dArrow_HEIGHT = 1.7f;

    public DownArrow(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(dArrow_WIDTH, dArrow_HEIGHT);
    }
}
