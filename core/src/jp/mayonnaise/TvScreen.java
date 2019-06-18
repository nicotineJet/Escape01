package jp.mayonnaise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TvScreen extends GameScreen{

    static final float rArrowPointX = CAMERA_WIDTH - CAMERA_WIDTH / 20 - RightArrow.rArrow_WIDTH / 2;
    static final float rArrowPointY = CAMERA_HEIGHT / 80;

    static final float lArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float lArrowPointY = CAMERA_HEIGHT / 80;


    static final float tvKeyPointX = 2.1f;
    static final float tvKeyPointY = 2.2f;


    static final float tvZoomPointX = 0.9f;
    static final float tvZoomPointY = 1.9f;


    static final float tvScreenZoomPointX = 0.9f;
    static final float tvScreenZoomPointY = 3.9f;


    static final float toranpuScreenPointX = 0.1f;
    static final float toranpuScreenPointY = 2.1f;


    RightArrow rArrow;
    LeftArrow lArrow;
    Sprite tvKey;

    Sprite lightOf;

    Sprite lightCircle;
    Sprite lightCircleT;


    int tvKeyState;

    int swichState;

    String swichStateKey = "SWICHSTATE";


    public TvScreen(Escape01 game) {
        super(game);

        // 背景の準備
        Texture bgTexture = new Texture("tvscreen.png");
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


        tvKeyState = preferences.getInteger("TVKEYSTATE",0);

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

        if (tvKeyState == 0) {
            tvKey.draw(mGame.batch);
        }

        if (swichState == 1){
            lightOf.draw(mGame.batch);
            if (grassWItemState == 2){
                lightCircleT.draw(mGame.batch);
            }else if (grassCItemState != 2){
                lightCircle.draw(mGame.batch);
            }
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

        Texture tvKeytexture = new Texture("tablescreen_tv_key.png");
        tvKey = new Sprite(tvKeytexture,0,0,100,65);
        tvKey.setSize(0.7f,0.7f);
        tvKey.setPosition(tvKeyPointX,tvKeyPointY);

        Texture textureLightOf = new Texture("light_of.png");
        lightOf = new Sprite(textureLightOf,0,0,800,450);
        lightOf.setSize(16,9);
        lightOf.setPosition(0,0);

        Texture textureLightCircle = new Texture("light_circle.png");
        lightCircle = new Sprite(textureLightCircle,0,0,40,40);
        lightCircle.setSize(0.8f,0.8f);
        lightCircle.setPosition(7f,4.5f);

        Texture textureLightCircleT = new Texture("light_circle_3.png");
        lightCircleT = new Sprite(textureLightCircleT,0,0,40,40);
        lightCircleT.setSize(0.8f,0.8f);
        lightCircleT.setPosition(8f,5f);

    }

    private void update(){
        if(Gdx.input.justTouched()){
            super.pUpdate();
            Rectangle rectangleRightArrow = new Rectangle(rArrowPointX,rArrowPointY,RightArrow.rArrow_WIDTH,RightArrow.rArrow_HEIGHT);
            Rectangle rectangleLeftArrow = new Rectangle(lArrowPointX,lArrowPointY,LeftArrow.lArrow_WIDTH,LeftArrow.lArrow_HEIGHT);

            Rectangle rectangleTvZoom = new Rectangle(tvZoomPointX,tvZoomPointY,2.9f,1.7f);

            Rectangle rectangleTvScreenZoom = new Rectangle(tvScreenZoomPointX,tvScreenZoomPointY,1.85f,1.3f);

            Rectangle rectangleToranpuScreen = new Rectangle(toranpuScreenPointX,toranpuScreenPointY,0.8f,1.0f);

            if(rectangleRightArrow.contains(mTouchPoint.x,mTouchPoint.y)) {
                mGame.setScreen(new BedScreen(mGame));
            }else if(rectangleLeftArrow.contains(mTouchPoint.x,mTouchPoint.y)){
                mGame.setScreen(new TableScreen(mGame));
            }else if (rectangleToranpuScreen.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                mGame.setScreen(new ToranpuScreen(mGame));
            }else if (rectangleTvZoom.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                preferences.putInteger("TVZOOMFROM",0);
                preferences.flush();
                mGame.setScreen(new TvZoomScreen(mGame));
            }else if (rectangleTvScreenZoom.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                preferences.putInteger("TVZOOMFROM",0);
                preferences.flush();
                mGame.setScreen(new TvScreenZoomScreen(mGame));
            }
        }
    }
}
