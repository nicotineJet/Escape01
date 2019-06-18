package jp.mayonnaise;

import com.badlogic.gdx.graphics.Texture;

public class NewGameButton extends GameObject {
        public static final float NEWGAMEBUTTON_WIDTH = 1.7f;
        public static final float NEWGAMEBUTTON_HEIGHT = 1.3f;

        public NewGameButton(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
            super(texture, srcX, srcY, srcWidth, srcHeight);
            setSize(NEWGAMEBUTTON_WIDTH, NEWGAMEBUTTON_HEIGHT);
        }
}
