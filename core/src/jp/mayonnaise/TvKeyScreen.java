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

import java.util.ArrayList;
import java.util.List;

public class TvKeyScreen extends GameScreen {

    static final float dArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float dArrowPointY = CAMERA_HEIGHT / 80;

    static final float tvKeyNumberPointsX[] = {4.7f,6.55f,8.4f,10.25f};
    static final float tvKeyNumberPointY = 2.8f;


    static final float keyPointX = 11.8f;
    static final float keyPointY = 3.5f;


    DownArrow dArrow;
    TvKeyNumber tvKeyNumbers[] = new TvKeyNumber[4];


    int tvKeyNumberStates[] = new int[4];


    Texture tvKeyNumberTextures[] = new Texture[10];
    Rectangle rectangleTvKeyNumbers[] = new Rectangle[4];


    public Sound keyOpen;
    public Sound keyClose;
    public Sound key;

    Box box;


    public TvKeyScreen(Escape01 game) {
        super(game);

        // 背景の準備
        Texture bgTexture = new Texture("tv_key_screen.png");
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


        for (int i = 0;i <= 3;i ++) {
            tvKeyNumberStates[i] = preferences.getInteger("TVKEYNUMBER" + i, 0);
        }

        keyOpen = Gdx.audio.newSound(Gdx.files.internal("keyopen.mp3"));
        keyClose = Gdx.audio.newSound(Gdx.files.internal("keyclose.mp3"));
        key = Gdx.audio.newSound(Gdx.files.internal("switch01.mp3"));

        createStage();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateTvKey();

        // カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mCamera.update();
        mGame.batch.setProjectionMatrix(mCamera.combined);

        mGame.batch.begin();

        // 原点は左下
        mBg.setPosition(mCamera.position.x - CAMERA_WIDTH / 2, mCamera.position.y - CAMERA_HEIGHT / 2);
        mBg.draw(mGame.batch);

        dArrow.draw(mGame.batch);

        /*if(penState == 0) {
            pen.draw(mGame.batch);
        }*/

        super.itemBar();


        itemButton.draw(mGame.batch);

        for (int i = 0;i <= 3;i ++) {
            tvKeyNumbers[i].draw(mGame.batch);
        }

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


        for (int i = 0;i <= 9;i++) {
            tvKeyNumberTextures[i] = new Texture("tv_keynumber" + i + ".png");
        }
        for (int i = 0;i <= 3;i ++) {
            tvKeyNumbers[i] = new TvKeyNumber(tvKeyNumberTextures[tvKeyNumberStates[i]], 0, 0, 40, 150);
            tvKeyNumbers[i].setPosition(tvKeyNumberPointsX[i], tvKeyNumberPointY);
        }

        /*Texture boxTexture = new Texture("box.png");
        box = new Box(boxTexture,0,0,50,50);
        box.setPosition(boxPointX,boxPointY);*/
    }

    public void updateTvKey(){
        if(Gdx.input.justTouched()) {
            super.pUpdate();
            //mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            Rectangle rectangleDownArrow = new Rectangle(dArrowPointX, dArrowPointY, DownArrow.dArrow_WIDTH, DownArrow.dArrow_HEIGHT);

            for (int i = 0;i <= 3;i ++) {
                rectangleTvKeyNumbers[i] = new Rectangle(tvKeyNumberPointsX[i], tvKeyNumberPointY, TvKeyNumber.NUMBER_WIDTH, TvKeyNumber.NUMBER_HEIGHT);
            }
            Rectangle rectangleKey = new Rectangle(keyPointX,keyPointY,2.0f,1.3f);

            /*Rectangle rectanglePenItem = new Rectangle(penItemPointX,penItemPointY,PenItem.penItem_WIDTH,PenItem.penItem_HEIGHT);
            Rectangle rectangleItemButton = new Rectangle(itemButtonPointX,itemButtonPointY,ItemButton.ITEMBUTTON_WIDTH,ItemButton.ITEMBUTTON_HEIGHT);*/


            if (rectangleDownArrow.contains(mTouchPoint.x,mTouchPoint.y)){
                mGame.setScreen(new TvZoomScreen(mGame));
            }else if (rectangleKey.contains(mTouchPoint.x,mTouchPoint.y)){
                if (tvKeyNumberStates[0] == 6 && tvKeyNumberStates[1] == 1 && tvKeyNumberStates[2] == 4 && tvKeyNumberStates[3] == 5) {
                    keyOpen.play();
                    preferences.putInteger("TVKEYSTATE",1);
                    preferences.flush();
                    mGame.setScreen(new TvZoomScreen(mGame));
                }else {
                    keyClose.play();
                }
            }/*else if(rectangleItemButton.contains(mTouchPoint.x,mTouchPoint.y)) {
                super.updateItemBar();
            }*/

            for (int i = 0;i <= 3;i ++) {
                if (rectangleTvKeyNumbers[i].contains(mTouchPoint.x, mTouchPoint.y)) {
                    updateTvKeyNumber(i);
                    key.play();
                }
            }
        }
    }

    public void updateTvKeyNumber(int i){
        if (tvKeyNumberStates[i] < 9) {
            tvKeyNumberStates[i] ++;
            tvKeyNumbers[i] = new TvKeyNumber(tvKeyNumberTextures[tvKeyNumberStates[i]], 0, 0, 40, 150);
            tvKeyNumbers[i].setPosition(tvKeyNumberPointsX[i], tvKeyNumberPointY);
            preferences.putInteger("TVKEYNUMBER" + i,tvKeyNumberStates[i]);
            preferences.flush();
        }else {
            tvKeyNumberStates[i] = 0;
            tvKeyNumbers[i] = new TvKeyNumber(tvKeyNumberTextures[tvKeyNumberStates[i]], 0, 0, 40, 150);
            tvKeyNumbers[i].setPosition(tvKeyNumberPointsX[i], tvKeyNumberPointY);
            preferences.putInteger("TVKEYNUMBER" + i,tvKeyNumberStates[i]);
            preferences.flush();
        }
    }
}
