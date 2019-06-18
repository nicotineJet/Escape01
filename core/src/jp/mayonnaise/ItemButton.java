package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class ItemButton extends GameObject {
    public static final float ITEMBUTTON_WIDTH = 1.3f;
    public static final float ITEMBUTTON_HEIGHT = 1.3f;

    public ItemButton(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight){
        super(texture,srcX,srcY,srcWidth,srcHeight);
        setSize(ITEMBUTTON_WIDTH,ITEMBUTTON_HEIGHT);
    }
}
