package jp.mayonnaise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Vector;

public class BedScreen extends GameScreen{

    static final float rArrowPointX = CAMERA_WIDTH - CAMERA_WIDTH / 20 - RightArrow.rArrow_WIDTH / 2;
    static final float rArrowPointY = CAMERA_HEIGHT / 80;

    static final float lArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float lArrowPointY = CAMERA_HEIGHT / 80;


    static final float mirrorPointX = 2.6f;
    static final float mirrorPointY = 2f;


    static final float bookPointX = 13.7f;
    static final float bookPointY = 4.25f;


    static final float deskPointX = 12.2f;
    static final float deskPointY = 3.2f;

    static final float phonePointX = 9.0f;
    static final float phonePointY = 3.2f;


    RightArrow rArrow;
    LeftArrow lArrow;

    Sprite lightOf;

    Sprite pcGamen;

    int swichState;

    String swichStateKey = "SWICHSTATE";

    public BedScreen(Escape01 game) {
        super(game);


        // 背景の準備
        Texture bgTexture = new Texture("bedscreen3.png");
        // TextureRegionで切り出す時の原点は左上
        mBg = new Sprite( new TextureRegion(bgTexture, 0, 0, 800, 450));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);

        // カメラ、ViewPortを生成、設定する
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);

        swichState = preferences.getInteger(swichStateKey,0);

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

        if (swichState == 1){
            lightOf.draw(mGame.batch);
        }

        if (nKeyItemState == 2) {
            pcGamen.draw(mGame.batch);
        }
        rArrow.draw(mGame.batch);
        lArrow.draw(mGame.batch);

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


        Texture textureLightOf = new Texture("light_of.png");
        lightOf = new Sprite(textureLightOf,0,0,800,450);
        lightOf.setSize(16,9);
        lightOf.setPosition(0,0);


        Texture texturePcGamen = new Texture("pc_gamen_2_s.png");
        pcGamen = new Sprite(texturePcGamen,0,0,55,25);
        pcGamen.setSize(1.1f,0.5f);
        pcGamen.setPosition(12.36f,4.64f);

    }


    private void update(){
        if(Gdx.input.justTouched()){
            super.pUpdate();
            //mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));
            Rectangle rectangleRightArrow = new Rectangle(rArrowPointX,rArrowPointY,RightArrow.rArrow_WIDTH,RightArrow.rArrow_HEIGHT);
            Rectangle rectangleLeftArrow = new Rectangle(lArrowPointX,lArrowPointY,LeftArrow.lArrow_WIDTH,LeftArrow.lArrow_HEIGHT);

            Rectangle rectangleBookScreen = new Rectangle(bookPointX,bookPointY,0.9f,0.7f);
            Rectangle rectangleMirrorScreen = new Rectangle(mirrorPointX,mirrorPointY,1.0f,3.7f);
            Rectangle rectangleDeskScreen = new Rectangle(deskPointX,deskPointY,2.5f,0.7f);
            Rectangle rectanglePhoneScreen = new Rectangle(phonePointX,phonePointY,0.5f,0.5f);


            if(rectangleRightArrow.contains(mTouchPoint.x,mTouchPoint.y)) {
                mGame.setScreen(new DoorScreen(mGame));
            }else if(rectangleLeftArrow.contains(mTouchPoint.x,mTouchPoint.y)){
                mGame.setScreen(new TvScreen(mGame));
            }else if (rectangleMirrorScreen.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                mGame.setScreen(new MirrorScreen(mGame));
            }else if (rectangleBookScreen.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0) {
                mGame.setScreen(new BookScreen(mGame));
            }else if (rectangleDeskScreen.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                mGame.setScreen(new DeskScreen(mGame));
            }else if (rectanglePhoneScreen.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                mGame.setScreen(new PhoneScreen(mGame));
            }
        }
    }
}
