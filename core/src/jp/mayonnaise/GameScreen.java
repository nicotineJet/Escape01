package jp.mayonnaise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends ScreenAdapter {
    static final float CAMERA_WIDTH = 16;
    static final float CAMERA_HEIGHT = 9;


    static final float itemBarPointX = 0;
    static final float itemBarPointY = CAMERA_HEIGHT - ItemBar.ITEMBAR_HEIGHT;

    static final float gakufuItemPointX = 0.5f;
    static final float gakufuItemPointY = itemBarPointY + 0.2f;

    static final float tvRimoConItemPointX = gakufuItemPointX + 1.7f;
    static final float tvRimoConItemPointY = gakufuItemPointY;

    static final float nKeyItemPointX = tvRimoConItemPointX + 1.7f;
    static final float nKeyItemPointY = gakufuItemPointY;

    static final float grassCItemPointX = nKeyItemPointX + 1.7f;
    static final float grassCItemPointY = gakufuItemPointY;

    static final float grassWItemPointX = grassCItemPointX + 1.7f;
    static final float grassWItemPointY = gakufuItemPointY;

    static final float itemButtonPointX = CAMERA_WIDTH - ItemButton.ITEMBUTTON_WIDTH * 1.5f;
    static final float itemButtonPointY = CAMERA_HEIGHT - ItemBar.ITEMBAR_HEIGHT / 2 - ItemButton.ITEMBUTTON_HEIGHT / 2;

    static final float hintPointX = 0.5f;
    static final float hintPointY = itemBarPointY - 1.5f;

    Escape01 mGame;


    Sprite mBg;
    OrthographicCamera mCamera;
    FitViewport mViewPort;


    ItemBar itemBar;

    //PenItem penItem;
    Sprite gakufuItem;
    Sprite tvRimoConItem;
    Sprite grassCItem;
    Sprite grassWItem;
    Sprite nKeyItem;

    Sprite gakufuScreen;

    Sprite hint;

    ItemButton itemButton;

    Texture textureGakufuItemOf;
    Texture textureTvRimoConItemOf;
    Texture textureNKeyOf;
    Texture textureGrassCItemOf;
    Texture textureGrassWItemOf;

    Texture textureGakufuItemOn;
    Texture textureTvRimoConItemOn;
    Texture textureNKeyItemOn;
    Texture textureGrassCItemOn;
    Texture textureGrassWItemOn;


    Sound item;
    Sound paper;


    Vector3 mTouchPoint;

    //int penState;
    int itemBarState;
    int tvRimoConState;
    int grassCState;
    int grassWState;
    int nKeyState;
    int gakufuState;

    int gakufuItemState;
    int tvRimoConItemState;
    int grassCItemState;
    int grassWItemState;
    int nKeyItemState;

    int gakufuScreenState;


    //static final String penStateKey = "PEN";
    static final String itemBarStateKey = "ITEMBARSTATE";
    static final String tvRimoConStateKey = "TVRIMOCONSTATE";
    static final String grassCStateKey = "GRASSCSTATE";
    static final String grassWStateKey = "GRASSWSTATE";
    static final String nKeyStateKey = "NKEYSTATE";
    static final String gakufuStateKey = "GAKUFUSTATE";


    static final String gakufuItemStateKey = "GAKUFUITEMSTATE";
    static final String tvRimoConItemStateKey = "TVRIMOCONITEMSTATE";
    static final String grassCItemStateKey = "GRASSCITEMSTATE";
    static final String grassWItemStateKey = "GRASSWITEMSTATE";
    static final String nKeyItemStateKey = "NKEYITEMSTATE";

    static final String gakufuScreenStateKey = "GAKUFUSCREENSTATE";


    Preferences preferences;


    public GameScreen (Escape01 game){
        mGame = game;

        mTouchPoint = new Vector3();

        preferences = Gdx.app.getPreferences("jp.mayonnaise");
        //penState = preferences.getInteger(penStateKey,0);
        tvRimoConState = preferences.getInteger(tvRimoConStateKey,0);
        grassCState = preferences.getInteger(grassCStateKey,0);
        grassWState = preferences.getInteger(grassWStateKey,0);
        nKeyState = preferences.getInteger(nKeyStateKey,0);
        gakufuState = preferences.getInteger(gakufuStateKey,0);

        gakufuItemState = preferences.getInteger(gakufuItemStateKey,0);
        tvRimoConItemState = preferences.getInteger(tvRimoConItemStateKey,0);
        grassCItemState = preferences.getInteger(grassCItemStateKey,0);
        grassWItemState = preferences.getInteger(grassWItemStateKey,0);
        nKeyItemState = preferences.getInteger(nKeyItemStateKey,0);

        gakufuScreenState = preferences.getInteger(gakufuScreenStateKey,0);

        itemBarState = preferences.getInteger(itemBarStateKey,0);


        item = Gdx.audio.newSound(Gdx.files.internal("button70.mp3"));
        paper = Gdx.audio.newSound(Gdx.files.internal("paper-take2.mp3"));


        Texture itemBarTexture = new Texture("itembar.png");
        itemBar = new ItemBar(itemBarTexture,0,0,800,60);
        itemBar.setPosition(itemBarPointX,itemBarPointY);


        Texture itemButtonTexture = new Texture("itembutton.png");
        itemButton = new ItemButton(itemButtonTexture,0,0,60,60);
        itemButton.setPosition(itemButtonPointX,itemButtonPointY);


        /*Texture penItemTexture = new Texture("pen_item.png");
        penItem = new PenItem(penItemTexture,0,0,50,50);
        penItem.setPosition(penItemPointX,penItemPointY);*/

        Texture textureGakufuScreen = new Texture("gakufu_screen.png");
        gakufuScreen = new Sprite(textureGakufuScreen,0,0,600,338);
        gakufuScreen.setSize(12,6.76f);
        gakufuScreen.setPosition(2f,1.12f);


        textureGakufuItemOf = new Texture("gakufu_item.png");
        if (gakufuItemState == 0){
            gakufuItem = new Sprite(textureGakufuItemOf,0,0,150,150);
            gakufuItem.setSize(1.2f,1.2f);
            gakufuItem.setPosition(gakufuItemPointX,gakufuItemPointY);
        }


        textureTvRimoConItemOf = new Texture("tv_rimocon.png");
        if (tvRimoConItemState == 0) {
            tvRimoConItem = new Sprite(textureTvRimoConItemOf, 0, 0, 80, 95);
            tvRimoConItem.setSize(1.2f, 1.2f);
            tvRimoConItem.setPosition(tvRimoConItemPointX, tvRimoConItemPointY);
        }

        textureNKeyOf = new Texture("keyboard_n_item.png");
        if (nKeyItemState == 0) {
            nKeyItem = new Sprite(textureNKeyOf, 0, 0, 80, 80);
            nKeyItem.setSize(1.2f, 1.2f);
            nKeyItem.setPosition(nKeyItemPointX, nKeyItemPointY);
        }

        textureGrassCItemOf = new Texture("grass_c.png");
        if (grassCItemState == 0) {
            grassCItem = new Sprite(textureGrassCItemOf, 0, 0, 180, 270);
            grassCItem.setSize(1.2f, 1.2f);
            grassCItem.setPosition(grassCItemPointX, grassCItemPointY);
        }

        textureGrassWItemOf = new Texture("grass_w.png");
        if (grassWItemState == 0) {
            grassWItem = new Sprite(textureGrassWItemOf, 0, 0, 180, 270);
            grassWItem.setSize(1.2f, 1.2f);
            grassWItem.setPosition(grassWItemPointX, grassWItemPointY);
        }


        textureGakufuItemOn = new Texture("gakufu_item_on.png");
        if (gakufuItemState == 1){
            gakufuItem = new Sprite(textureGakufuItemOn,0,0,150,150);
            gakufuItem.setSize(1.2f,1.2f);
            gakufuItem.setPosition(gakufuItemPointX,gakufuItemPointY);
        }


        textureTvRimoConItemOn = new Texture("tv_rimocon_on.png");
        if (tvRimoConItemState == 1){
            tvRimoConItem = new Sprite(textureTvRimoConItemOn,0,0,80,95);
            tvRimoConItem.setSize(1.2f,1.2f);
            tvRimoConItem.setPosition(tvRimoConItemPointX,tvRimoConItemPointY);
        }

        textureNKeyItemOn = new Texture("keyboard_n_item_on.png");
        if (nKeyItemState == 1){
            nKeyItem = new Sprite(textureNKeyItemOn,0,0,80,80);
            nKeyItem.setSize(1.2f,1.2f);
            nKeyItem.setPosition(nKeyItemPointX,nKeyItemPointY);
        }


        textureGrassCItemOn = new Texture("grass_c_on.png");
        if (grassCItemState == 1) {
            grassCItem = new Sprite(textureGrassCItemOn, 0, 0, 180, 270);
            grassCItem.setSize(1.2f, 1.2f);
            grassCItem.setPosition(grassCItemPointX, grassCItemPointY);
        }

        textureGrassWItemOn = new Texture("grass_w_on.png");
        if (grassWItemState == 1) {
            grassWItem = new Sprite(textureGrassWItemOn, 0, 0, 180, 270);
            grassWItem.setSize(1.2f, 1.2f);
            grassWItem.setPosition(grassWItemPointX, grassWItemPointY);
        }


        Texture txHint = new Texture("hint_ic.png");
        hint = new Sprite(txHint,0,0,150,150);
        hint.setSize(1.5f,1.5f);
        hint.setPosition(hintPointX,hintPointY);

    }

    public void itemBar(){
        if (itemBarState == 1) {
            itemBar.draw(mGame.batch);
            if (gakufuState == 1){
                gakufuItem.draw(mGame.batch);
            }
            /*if (penState == 1){
                penItem.draw(mGame.batch);
            }*/
            if (tvRimoConState == 1){
                tvRimoConItem.draw(mGame.batch);
            }
            if (nKeyState == 1 && nKeyItemState != 2){
                nKeyItem.draw(mGame.batch);
            }
            if (grassCState == 1 && grassCItemState != 2){
                grassCItem.draw(mGame.batch);
            }
            if (grassWState == 1 && grassWItemState != 2){
                grassWItem.draw(mGame.batch);
            }
        }
        if (gakufuScreenState == 1){
            gakufuScreen.draw(mGame.batch);
        }
        hint.draw(mGame.batch);
    }

    public void pUpdate(){
        mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));

        Rectangle rectangleGakufuClose = new Rectangle(0,0,800,450);

        //Rectangle rectanglePenItem = new Rectangle(penItemPointX,penItemPointY,PenItem.penItem_WIDTH,PenItem.penItem_HEIGHT);
        Rectangle rectangleItemButton = new Rectangle(itemButtonPointX,itemButtonPointY,ItemButton.ITEMBUTTON_WIDTH,ItemButton.ITEMBUTTON_HEIGHT);

        Rectangle rectangleGakufuItem = new Rectangle(gakufuItemPointX,gakufuItemPointY,1.2f,1.2f);
        Rectangle rectangleTvRimoConItem = new Rectangle(tvRimoConItemPointX,tvRimoConItemPointY,1.2f,1.2f);
        Rectangle rectangleNKeyItem = new Rectangle(nKeyItemPointX,nKeyItemPointY,1.2f,1.2f);
        Rectangle rectangleGrassCItem = new Rectangle(grassCItemPointX,grassCItemPointY,1.2f,1.2f);
        Rectangle rectangleGrassWItem = new Rectangle(grassWItemPointX,grassWItemPointY,1.2f,1.2f);

        Rectangle rectangleHint = new Rectangle(hintPointX,hintPointY,1.5f,1.5f);


        if (rectangleGakufuClose.contains(mTouchPoint.x,mTouchPoint.y) && gakufuScreenState == 1){
            preferences.putInteger(gakufuScreenStateKey,0);
            preferences.flush();
            gakufuScreenState = 0;
            gakufuItemOf();
        }

        if (rectangleItemButton.contains(mTouchPoint.x,mTouchPoint.y)){
            updateItemBar();
        }
        if (rectangleGakufuItem.contains(mTouchPoint.x,mTouchPoint.y) && itemBarState == 1 && gakufuState == 1){
            paper.play();
            updateGakufuItem();
        }
        if (rectangleTvRimoConItem.contains(mTouchPoint.x,mTouchPoint.y) && itemBarState == 1 && tvRimoConState == 1){
            updateTvRimoConItem();
        }
        if (rectangleNKeyItem.contains(mTouchPoint.x,mTouchPoint.y) && itemBarState == 1 && nKeyState == 1){
            updateNKeyItem();
        }
        if (rectangleGrassCItem.contains(mTouchPoint.x,mTouchPoint.y) && itemBarState == 1 && grassCState == 1){
            updateGrassCItem();
        }
        if (rectangleGrassWItem.contains(mTouchPoint.x,mTouchPoint.y) && itemBarState == 1 && grassWState == 1){
            updateGrassWItem();
        }

        if (rectangleHint.contains(mTouchPoint.x,mTouchPoint.y)){
            mGame.setScreen(new HintScreen(mGame));
        }

    }

    public void updateGakufuItem(){
        if (gakufuItemState == 0){
            if (tvRimoConItemState == 1){
                tvRimoConItemOf();
            }
            if (nKeyItemState == 1){
                nKeyItemOf();
            }
            if (grassCItemState == 1){
                grassCItemOf();
            }
            if (grassWItemState == 1){
                grassWItemOf();
            }
            preferences.putInteger(gakufuItemStateKey,1);
            preferences.flush();
            gakufuItemState = 1;
            gakufuItem = new Sprite(textureGakufuItemOn,0,0,150, 150);
            gakufuItem.setSize(1.2f,1.2f);
            gakufuItem.setPosition(gakufuItemPointX,gakufuItemPointY);

            preferences.putInteger(gakufuScreenStateKey,1);
            preferences.flush();
            gakufuScreenState = 1;
        }else {
            gakufuItemOf();
        }
    }


    public void gakufuItemOf(){
        preferences.putInteger(gakufuItemStateKey,0);
        preferences.flush();
        gakufuItemState = 0;
        gakufuItem = new Sprite(textureGakufuItemOf,0,0,150,150);
        gakufuItem.setSize(1.2f,1.2f);
        gakufuItem.setPosition(gakufuItemPointX,gakufuItemPointY);
    }



    public void updateTvRimoConItem(){
        if (tvRimoConItemState == 0){
            if (gakufuItemState == 1){
                gakufuItemOf();
            }
            if (nKeyItemState == 1) {
                nKeyItemOf();
            }
            if (grassCItemState == 1){
                grassCItemOf();
            }
            if (grassWItemState == 1){
                grassWItemOf();
            }
            preferences.putInteger(tvRimoConItemStateKey,1);
            preferences.flush();
            tvRimoConItemState = 1;
            tvRimoConItem = new Sprite(textureTvRimoConItemOn,0,0,80,95);
            tvRimoConItem.setSize(1.2f,1.2f);
            tvRimoConItem.setPosition(tvRimoConItemPointX,tvRimoConItemPointY);
        }else {
            tvRimoConItemOf();
        }
    }

    public void tvRimoConItemOf(){
        preferences.putInteger(tvRimoConItemStateKey,0);
        preferences.flush();
        tvRimoConItemState = 0;
        tvRimoConItem = new Sprite(textureTvRimoConItemOf,0,0,80,95);
        tvRimoConItem.setSize(1.2f,1.2f);
        tvRimoConItem.setPosition(tvRimoConItemPointX,tvRimoConItemPointY);
    }


    public void updateNKeyItem(){
        if (nKeyItemState == 0){
            if (gakufuItemState == 1){
                gakufuItemOf();
            }
            if (tvRimoConItemState == 1) {
                tvRimoConItemOf();
            }
            if (grassCItemState == 1){
                grassCItemOf();
            }
            if (grassWItemState == 1){
                grassWItemOf();
            }
            preferences.putInteger(nKeyItemStateKey,1);
            preferences.flush();
            nKeyItemState = 1;
            nKeyItem = new Sprite(textureNKeyItemOn,0,0,80,80);
            nKeyItem.setSize(1.2f,1.2f);
            nKeyItem.setPosition(nKeyItemPointX,nKeyItemPointY);
        }else if (nKeyItemState == 1){
            nKeyItemOf();
        }
    }

    public void nKeyItemOf(){
        preferences.putInteger(nKeyItemStateKey,0);
        preferences.flush();
        nKeyItemState = 0;
        nKeyItem = new Sprite(textureNKeyOf,0,0,80,80);
        nKeyItem.setSize(1.2f,1.2f);
        nKeyItem.setPosition(nKeyItemPointX,nKeyItemPointY);
    }


    public void updateGrassCItem(){
        if (grassCItemState == 0){
            if (gakufuItemState == 1){
                gakufuItemOf();
            }
            if (tvRimoConItemState == 1) {
                tvRimoConItemOf();
            }
            if (nKeyItemState == 1){
                nKeyItemOf();
            }
            if (grassWItemState == 1){
                grassWItemOf();
            }
            preferences.putInteger(grassCItemStateKey,1);
            preferences.flush();
            grassCItemState = 1;
            grassCItem = new Sprite(textureGrassCItemOn, 0, 0, 180, 270);
            grassCItem.setSize(1.2f, 1.2f);
            grassCItem.setPosition(grassCItemPointX, grassCItemPointY);
        }else if (grassCItemState == 1){
            grassCItemOf();
        }
    }

    public void grassCItemOf(){
        preferences.putInteger(grassCItemStateKey,0);
        preferences.flush();
        grassCItemState = 0;
        grassCItem = new Sprite(textureGrassCItemOf, 0, 0, 180, 270);
        grassCItem.setSize(1.2f, 1.2f);
        grassCItem.setPosition(grassCItemPointX, grassCItemPointY);
    }


    public void updateGrassWItem(){
        if (grassWItemState == 0){
            if (gakufuItemState == 1){
                gakufuItemOf();
            }
            if (tvRimoConItemState == 1) {
                tvRimoConItemOf();
            }
            if (nKeyItemState == 1){
                nKeyItemOf();
            }
            if (grassCItemState == 1){
                grassCItemOf();
            }
            preferences.putInteger(grassWItemStateKey,1);
            preferences.flush();
            grassWItemState = 1;
            grassWItem = new Sprite(textureGrassWItemOn, 0, 0, 180, 270);
            grassWItem.setSize(1.2f, 1.2f);
            grassWItem.setPosition(grassWItemPointX, grassWItemPointY);
        }else if (grassWItemState == 1){
            grassWItemOf();
        }
    }

    public void grassWItemOf(){
        preferences.putInteger(grassWItemStateKey,0);
        preferences.flush();
        grassWItemState = 0;
        grassWItem = new Sprite(textureGrassWItemOf, 0, 0, 180, 270);
        grassWItem.setSize(1.2f, 1.2f);
        grassWItem.setPosition(grassWItemPointX, grassWItemPointY);
    }




    public void updateItemBar(){
        if(itemBarState == 0){
            itemBarState = 1;
            preferences.putInteger(itemBarStateKey,1);
            preferences.flush();
            itemBar.open();
            /*if (penState == 1){
                penItem.open();
            }*/
            if (tvRimoConState == 1){
                tvRimoConItem.setAlpha(1);
            }
            if (nKeyState == 1 && nKeyItemState != 2){
                nKeyItem.setAlpha(1);
            }
        }else {
            itemBarState = 0;
            preferences.putInteger(itemBarStateKey,0);
            preferences.flush();
            gakufuItemOf();
            tvRimoConItemOf();
            if (nKeyItemState != 2) {
                nKeyItemOf();
            }
            if (grassCItemState != 2){
                grassCItemOf();
            }
            if (grassWItemState != 2){
                grassWItemOf();
            }
            itemBar.close();
            //penItem.close();
            tvRimoConItem.setAlpha(0);
            if (nKeyItemState != 2) {
                nKeyItem.setAlpha(0);
            }
        }
    }
}
