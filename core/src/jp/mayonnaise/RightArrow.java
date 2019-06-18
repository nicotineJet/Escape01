package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class RightArrow extends GameObject {
    public static final float rArrow_WIDTH = 1.7f;
    public static final float rArrow_HEIGHT = 1.3f;

    public RightArrow(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(rArrow_WIDTH, rArrow_HEIGHT);
    }
}
