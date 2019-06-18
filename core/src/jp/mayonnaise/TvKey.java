package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class TvKey extends GameObject {
    public static final float TVKEY_WIDTH = 2.5f;
    public static final float TVKEY_HEIGHT = 1.7f;

    public TvKey(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(TVKEY_WIDTH, TVKEY_HEIGHT);
    }
}
