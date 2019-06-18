package jp.mayonnaise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class DeskScreen extends GameScreen {

    static final float dArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float dArrowPointY = CAMERA_HEIGHT / 80;


    static final float drawerLOpenPointX = 0.7f;
    static final float drawerLOpenPointY =4.5f;
    static final float drawerROpenPointX = 8.5f;
    static final float drawerROpenPointY = 4.5f;

    static final float drawerLClosePointX = 0.7f;
    static final float drawerLClosePointY = 0.25f;
    static final float drawerRClosePointX = 8.5f;
    static final float drawerRClosePointY = 0.25f;


    static final float nKeyPointX = 12f;
    static final float nKeyPointY = 3.5f;

    static final float gakufuPointX = 2.5f;
    static final float gakufuPointY = 2.5f;


    DownArrow dArrow;


    Sprite drawerR;
    Sprite drawerL;
    Sprite deskOpenR;
    Sprite deskOpenL;

    Sprite nKey;

    Sprite gakufu;

    Sprite box;


    Texture textureDrawerClose;
    Texture textureDrawerOpen;

    Sound drawerOpen;
    Sound drawerClose;


    int drawerLState;
    int drawerRState;


    public DeskScreen(Escape01 game) {
        super(game);

        // 背景の準備
        Texture bgTexture = new Texture("desk_screen.png");
        // TextureRegionで切り出す時の原点は左上
        mBg = new Sprite( new TextureRegion(bgTexture, 0, 0, 800, 450));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);

        // カメラ、ViewPortを生成、設定する
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);

        //ScreenState = SCREEN_STATE_BED;
        mTouchPoint = new Vector3();


        drawerLState = 0;
        drawerRState = 0;


        drawerOpen = Gdx.audio.newSound(Gdx.files.internal("drawer-open1.mp3"));
        drawerClose = Gdx.audio.newSound(Gdx.files.internal("drawer-close1.mp3"));

        createStage();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        // カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mCamera.update();
        mGame.batch.setProjectionMatrix(mCamera.combined);

        mGame.batch.begin();

        // 原点は左下
        mBg.setPosition(mCamera.position.x - CAMERA_WIDTH / 2, mCamera.position.y - CAMERA_HEIGHT / 2);
        mBg.draw(mGame.batch);

        drawerL.draw(mGame.batch);
        drawerR.draw(mGame.batch);

        if (drawerRState == 1 && nKeyState == 0) {
            nKey.draw(mGame.batch);
        }

        if (drawerLState == 1 && gakufuState == 0){
            gakufu.draw(mGame.batch);
        }

        dArrow.draw(mGame.batch);

        /*if(penState == 0) {
            pen.draw(mGame.batch);
        }*/

        super.itemBar();


        itemButton.draw(mGame.batch);


        //box.draw(mGame.batch);

        mGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewPort.update(width, height);
    }

    public void createStage(){

        Texture dArrowTexture = new Texture("down_arrow.png");
        dArrow = new DownArrow(dArrowTexture,0,0,74,120);
        dArrow.setPosition(dArrowPointX,dArrowPointY);


        Texture textureNKey = new Texture("keyboard_n_item.png");
        nKey = new Sprite(textureNKey,0,0,80,80);
        nKey.setSize(0.6f,0.6f);
        nKey.setPosition(nKeyPointX,nKeyPointY);


        Texture textureGakufu = new Texture("gakufu_item.png");
        gakufu = new Sprite(textureGakufu,0,0,150,150);
        gakufu.setSize(3.0f,3.0f);
        gakufu.setPosition(gakufuPointX,gakufuPointY);


        textureDrawerClose = new Texture("desk_close.png");
        drawerL = new Sprite(textureDrawerClose,0,0,400,110);
        drawerL.setSize(8f,2.2f);
        drawerL.setPosition(0.25f,4.48f);

        drawerR = new Sprite(textureDrawerClose,0,0,400,110);
        drawerR.setSize(8f,2.2f);
        drawerR.setPosition(7.80f,4.48f);

        textureDrawerOpen = new Texture("desk_open.png");


        Texture boxTexture = new Texture("box.png");
        box = new Sprite(boxTexture,0,0,50,50);
        box.setSize(7.0f,2.0f);
        box.setPosition(8.5f,0.25f);
    }

    public void update(){
        if(Gdx.input.justTouched()) {
            super.pUpdate();
            //mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            Rectangle rectangleDownArrow = new Rectangle(dArrowPointX, dArrowPointY, DownArrow.dArrow_WIDTH, DownArrow.dArrow_HEIGHT);

            Rectangle rectangleDrawerLOpen = new Rectangle(drawerLOpenPointX,drawerLOpenPointY,7.0f,2.0f);
            Rectangle rectangleDrawerLClose = new Rectangle(drawerLClosePointX,drawerLClosePointY,7.0f,2.0f);

            Rectangle rectangleDrawerROpen = new Rectangle(drawerROpenPointX,drawerROpenPointY,7.0f,2.0f);
            Rectangle rectangleDrawerRClose = new Rectangle(drawerRClosePointX,drawerRClosePointY,7.0f,2.0f);

            Rectangle rectangleNKey = new Rectangle(nKeyPointX,nKeyPointY,0.6f,0.6f);

            Rectangle rectangleGakufu = new Rectangle(gakufuPointX,gakufuPointY,3.0f,3.0f);

            /*Rectangle rectanglePenItem = new Rectangle(penItemPointX, penItemPointY, PenItem.penItem_WIDTH, PenItem.penItem_HEIGHT);
            Rectangle rectangleItemButton = new Rectangle(itemButtonPointX, itemButtonPointY, ItemButton.ITEMBUTTON_WIDTH, ItemButton.ITEMBUTTON_HEIGHT);
            Rectangle rectangleTvRimoConItem = new Rectangle(tvRimoConItemPointX, tvRimoConItemPointY, 1.2f, 1.2f);*/

            //Rectangle rectangleKeyboard = new Rectangle(keyboardPointX, keyboardPointY, 7.0f, 2.5f);

            if (rectangleDownArrow.contains(mTouchPoint.x, mTouchPoint.y)) {
                mGame.setScreen(new BedScreen(mGame));
            }else if (rectangleDrawerLOpen.contains(mTouchPoint.x,mTouchPoint.y)){
                drawerOpen.play();
                drawerL = new Sprite(textureDrawerOpen,0,0,400,340);
                drawerL.setSize(8f,6.8f);
                drawerL.setPosition(0.25f,0.08f);
                drawerLState = 1;
            }else if (rectangleDrawerLClose.contains(mTouchPoint.x,mTouchPoint.y)){
                drawerClose.play();
                drawerL = new Sprite(textureDrawerClose,0,0,400,110);
                drawerL.setSize(8f,2.2f);
                drawerL.setPosition(0.25f,4.48f);
                drawerLState = 0;
            }else if (rectangleDrawerROpen.contains(mTouchPoint.x,mTouchPoint.y)){
                drawerOpen.play();
                drawerR = new Sprite(textureDrawerOpen,0,0,400,340);
                drawerR.setSize(8f,6.8f);
                drawerR.setPosition(7.8f,0.08f);
                drawerRState = 1;
            }else if (rectangleDrawerRClose.contains(mTouchPoint.x,mTouchPoint.y)){
                drawerClose.play();
                drawerR = new Sprite(textureDrawerClose,0,0,400,110);
                drawerR.setSize(8f,2.2f);
                drawerR.setPosition(7.8f,4.48f);
                drawerRState = 0;
            }else if (rectangleNKey.contains(mTouchPoint.x,mTouchPoint.y) && drawerRState == 1 && nKeyState == 0) {
                item.play();
                nKeyState = 1;
                preferences.putInteger("NKEYSTATE",1);
                preferences.flush();
                //nKey.setAlpha(0);
            }else if (rectangleGakufu.contains(mTouchPoint.x,mTouchPoint.y) && drawerLState == 1 && gakufuState == 0){
                item.play();
                gakufuState = 1;
                preferences.putInteger(gakufuStateKey,1);
                preferences.flush();
            }
            /*else if(rectangleItemButton.contains(mTouchPoint.x,mTouchPoint.y)) {
                super.updateItemBar();
            }*/
        }
    }
}
