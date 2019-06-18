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

public class DoorScreen extends GameScreen {

    static final float rArrowPointX = CAMERA_WIDTH - CAMERA_WIDTH / 20 - RightArrow.rArrow_WIDTH / 2;
    static final float rArrowPointY = CAMERA_HEIGHT / 80;

    static final float lArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float lArrowPointY = CAMERA_HEIGHT / 80;

    static final float dartsPointX = 12.95f;
    static final float dartsPointY = 5.15f;


    static final float swichPointX = 5f;
    static final float swichPointY = 4.5f;

    static final float passPointX = 8f;
    static final float passPointY = 5.2f;


    static final float goalPointX = 8.2f;
    static final float goalPointY = 4.4f;

    RightArrow rArrow;
    LeftArrow lArrow;

    Sprite swich;

    Sprite lightOf;

    Sprite pass;

    Sprite box;

    Texture textureSwichOn;
    Texture textureSwichOf;


    Sound soundSwich;
    Sound open;
    Sound close;


    int swichState;

    int passState;

    String swichStateKey = "SWICHSTATE";

    public DoorScreen(Escape01 game) {
        super(game);

        // 背景の準備
        Texture bgTexture = new Texture("doorscreen.png");
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

        swichState = preferences.getInteger(swichStateKey,0);

        passState = preferences.getInteger("PASSSTATE",0);


        soundSwich = Gdx.audio.newSound(Gdx.files.internal("swich.mp3"));
        open = Gdx.audio.newSound(Gdx.files.internal("door-open1.mp3"));
        close = Gdx.audio.newSound(Gdx.files.internal("keyclose.mp3"));

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
        //mBg.setPosition(mCamera.position.x - CAMERA_WIDTH / 2, mCamera.position.y - CAMERA_HEIGHT / 2);
        mBg.draw(mGame.batch);

        pass.draw(mGame.batch);

        if (swichState == 1){
            lightOf.draw(mGame.batch);
        }

        rArrow.draw(mGame.batch);
        lArrow.draw(mGame.batch);


        swich.draw(mGame.batch);

        super.itemBar();

        itemButton.draw(mGame.batch);

        mGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewPort.update(width, height);
    }

    private void createStage(){
        Texture rArrowTexture = new Texture("right_arrow.png");
        rArrow = new RightArrow(rArrowTexture,0,0,120,74);
        rArrow.setPosition(rArrowPointX,rArrowPointY);


        Texture lArrowTexture = new Texture("left_arrow.png");
        lArrow = new LeftArrow(lArrowTexture,0,0,120,74);
        lArrow.setPosition(lArrowPointX,lArrowPointY);

        textureSwichOn = new Texture("swich_on.png");
        textureSwichOf = new Texture("swich_of.png");

        Texture textureLightOf = new Texture("light_of.png");
        lightOf = new Sprite(textureLightOf,0,0,800,450);
        lightOf.setSize(16,9);
        lightOf.setPosition(0,0);

        if (swichState == 0) {
            swich = new Sprite(textureSwichOn, 0, 0, 35, 50);
        }else {
            swich = new Sprite(textureSwichOf,0,0,35,50);
        }
        swich.setSize(0.7f,1f);
        swich.setPosition(swichPointX,swichPointY);


        Texture texturePass = new Texture("pass.png");
        pass = new Sprite(texturePass,0,0,250,430);
        pass.setSize(0.34f,0.6f);
        pass.setPosition(passPointX,passPointY);
    }


    private void update(){
        if(Gdx.input.justTouched()) {
            super.pUpdate();
            Rectangle rectangleRightArrow = new Rectangle(rArrowPointX, rArrowPointY, RightArrow.rArrow_WIDTH, RightArrow.rArrow_HEIGHT);
            Rectangle rectangleLeftArrow = new Rectangle(lArrowPointX, lArrowPointY, LeftArrow.lArrow_WIDTH, LeftArrow.lArrow_HEIGHT);

            Rectangle rectangleDarts = new Rectangle(dartsPointX,dartsPointY,1.15f,1.15f);

            Rectangle rectangleSwich = new Rectangle(swichPointX,swichPointY,0.7f,1.0f);

            Rectangle rectanglePass = new Rectangle(passPointX,passPointY,0.34f,0.6f);

            Rectangle rectangleGoal = new Rectangle(goalPointX,goalPointY,0.4f,0.4f);

            if (rectangleRightArrow.contains(mTouchPoint.x, mTouchPoint.y)) {
                mGame.setScreen(new TableScreen(mGame));
            } else if (rectangleLeftArrow.contains(mTouchPoint.x, mTouchPoint.y)) {
                mGame.setScreen(new BedScreen(mGame));
            } else if (rectangleDarts.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                mGame.setScreen(new DartsScreen(mGame));
            }else if (rectanglePass.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0) {
                mGame.setScreen(new PassScreen(mGame));
            }else if (rectangleSwich.contains(mTouchPoint.x,mTouchPoint.y)){
                soundSwich.play();
                if (swichState == 0){
                    preferences.putInteger(swichStateKey,1);
                    preferences.flush();
                    swichState = 1;
                    swich = new Sprite(textureSwichOf,0,0,35,50);
                    swich.setSize(0.7f,1.0f);
                    swich.setPosition(swichPointX,swichPointY);
                }else if (swichState == 1){
                    preferences.putInteger(swichStateKey,0);
                    preferences.flush();
                    swichState = 0;
                    swich = new Sprite(textureSwichOn,0,0,35,50);
                    swich.setSize(0.7f,1.0f);
                    swich.setPosition(swichPointX,swichPointY);
                }
            }else if (rectangleGoal.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                if (passState == 1) {
                    open.play();
                    mGame.setScreen(new GameOverScreen(mGame));
                }else {
                    close.play();
                }
            }
        }
    }
}
