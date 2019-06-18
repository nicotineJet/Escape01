package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class TvKeyNumber extends GameObject {
    public static final float NUMBER_WIDTH = 1.0f;
    public static final float NUMBER_HEIGHT = 2.9f;

    public TvKeyNumber(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(NUMBER_WIDTH, NUMBER_HEIGHT);
    }
}
