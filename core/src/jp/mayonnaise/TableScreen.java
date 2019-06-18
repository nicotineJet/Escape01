package jp.mayonnaise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TableScreen extends GameScreen{

    static final float rArrowPointX = CAMERA_WIDTH - CAMERA_WIDTH / 20 - RightArrow.rArrow_WIDTH / 2;
    static final float rArrowPointY = CAMERA_HEIGHT / 80;

    static final float lArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float lArrowPointY = CAMERA_HEIGHT / 80;

    static final float calendarPointX = 2.0f;
    static final float calendarPointY = 4.8f;

    static final float tvZoomPointX = 12.5f;
    static final float tvZoomPointY = 1.5f;

    static final float tvKeyPointX = 13.76f;
    static final float tvKeyPointY = 2.05f;

    static final float cakePointX = 7.1f;
    static final float cakePointY = 4.2f;

    static final float tvScreenZoomPointX = 13.5f;
    static final float tvScreenZoomPointY = 4.0f;

    static final float grassTablePointX = 6.95f;
    static final float grassTablePointY = 3.9f;

    static final float deskLightPointX = 5.8f;
    static final float deskLightPointY = 3.5f;


    RightArrow rArrow;
    LeftArrow lArrow;

    Sprite tvKey;

    Sprite lightOf;

    Sprite grassCTable;
    Sprite grassWTable;

    Sprite deskLight;


    int tvKeyState;

    int swichState;

    String swichStateKey = "SWICHSTATE";



    public TableScreen(Escape01 game) {
        super(game);

        // 背景の準備
        Texture bgTexture = new Texture("tablescreen.png");
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
            deskLight.draw(mGame.batch);
        }


        if (grassCItemState == 2) {
            grassCTable.draw(mGame.batch);
        }
        if (grassWItemState == 2){
            grassWTable.draw(mGame.batch);
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

        grassCTable = new Sprite(textureGrassCItemOf,0,0,180,270);
        grassCTable.setSize(0.4f,0.6f);
        grassCTable.setPosition(grassTablePointX,grassTablePointY);

        grassWTable = new Sprite(textureGrassWItemOf,0,0,180,270);
        grassWTable.setSize(0.4f,0.6f);
        grassWTable.setPosition(grassTablePointX,grassTablePointY);

        Texture textureDeskLight = new Texture("light0.png");
        Texture textureDeskLightW = new Texture("light1.png");
        Texture textureDeskLightC = new Texture("light2.png");

        if (grassWItemState == 2){
            deskLight = new Sprite(textureDeskLightW,0,0,500,20);
        }else if (grassCItemState == 2){
            deskLight = new Sprite(textureDeskLightC,0,0,500,20);
        }else {
            deskLight = new Sprite(textureDeskLight,0,0,500,20);
        }
        deskLight.setSize(10f,0.4f);
        deskLight.setPosition(6f,4f);
    }

    private void update(){
        if(Gdx.input.justTouched()){
            super.pUpdate();
            Rectangle rectangleRightArrow = new Rectangle(rArrowPointX,rArrowPointY,RightArrow.rArrow_WIDTH,RightArrow.rArrow_HEIGHT);
            Rectangle rectangleLeftArrow = new Rectangle(lArrowPointX,lArrowPointY,LeftArrow.lArrow_WIDTH,LeftArrow.lArrow_HEIGHT);

            Rectangle rectangleCalendar = new Rectangle(calendarPointX,calendarPointY,1.9f,2.0f);

            Rectangle rectangleTvZoom = new Rectangle(tvZoomPointX,tvZoomPointY,3.8f,1.8f);

            Rectangle rectangleTvScreenZoom = new Rectangle(tvScreenZoomPointX,tvScreenZoomPointY,2.4f,1.3f);

            Rectangle rectangleCake = new Rectangle(cakePointX,cakePointY,0.8f,0.6f);

            Rectangle rectangleDeskLight = new Rectangle(deskLightPointX,deskLightPointY,0.8f,0.8f);


            if(rectangleRightArrow.contains(mTouchPoint.x,mTouchPoint.y)) {
                mGame.setScreen(new TvScreen(mGame));
            }else if(rectangleLeftArrow.contains(mTouchPoint.x,mTouchPoint.y)){
                mGame.setScreen(new DoorScreen(mGame));
            } else if(rectangleCalendar.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                mGame.setScreen(new CalendarScreen(mGame));
            }else if(rectangleTvZoom.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0) {
                preferences.putInteger("TVZOOMFROM", 1);
                preferences.flush();
                mGame.setScreen(new TvZoomScreen(mGame));
            }else if (rectangleTvScreenZoom.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                preferences.putInteger("TVZOOMFROM", 1);
                preferences.flush();
                mGame.setScreen(new TvScreenZoomScreen(mGame));
            }else if (rectangleCake.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                mGame.setScreen(new CakeScreen(mGame));
            }else if (rectangleDeskLight.contains(mTouchPoint.x,mTouchPoint.y) && swichState == 0){
                mGame.setScreen(new DeskLightScreen(mGame));
            }
        }
    }
}
