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

public class TvZoomScreen extends GameScreen {

    static final float dArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float dArrowPointY = CAMERA_HEIGHT / 80;

    static final float tvKeyPointX = 6.75f;
    static final float tvKeyPonitY = 3.35f;

    static final float tvKeyTouchPointX = 8.1f;
    static final float tvKeyTouchPonitY = 3.2f;

    static final float tvRimoConPointX = 5.5f;
    static final float tvRimoConPointY = 5.1f;

    static final float grassCPointX = 3.0f;
    static final float grassCPointY = 2.4f;

    static final float grassWPointX = 8.5f;
    static final float grassWPointY = 2.4f;


    static final float boxPointX = 7.5f;
    static final float boxPointY = 4f;


    DownArrow dArrow;
    Box box;
    TvKey tvKey;
    Sprite tvDoorClose;
    Sprite tvDoorOpen;
    Sprite tvRimoCon;
    Sprite grassC;
    Sprite grassW;


    Sound open;
    Sound close;


    int tvKeyState;
    int tvZoomFrom;
    int tvDoorState;


    public TvZoomScreen(Escape01 game) {
        super(game);

        // 背景の準備
        Texture bgTexture = new Texture("tv_zoom_screen.png");
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
        tvZoomFrom = preferences.getInteger("TVZOOMFROM",0);


        open = Gdx.audio.newSound(Gdx.files.internal("door_d_open.mp3"));
        close = Gdx.audio.newSound(Gdx.files.internal("door_d_close.mp3"));


        createStage();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateTvZoom();

        // カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mCamera.update();
        mGame.batch.setProjectionMatrix(mCamera.combined);

        mGame.batch.begin();

        // 原点は左下
        mBg.setPosition(mCamera.position.x - CAMERA_WIDTH / 2, mCamera.position.y - CAMERA_HEIGHT / 2);
        mBg.draw(mGame.batch);


        /*if(penState == 0) {
            pen.draw(mGame.batch);
        }*/



        if (tvRimoConState == 0) {
            tvRimoCon.draw(mGame.batch);
        }

        if (grassCState == 0) {
            grassC.draw(mGame.batch);
        }
        if (grassWState == 0) {
            grassW.draw(mGame.batch);
        }

        dArrow.draw(mGame.batch);

        if (tvDoorState == 0) {
            tvDoorOpen.setAlpha(0);
            tvDoorClose.setAlpha(1);
            tvDoorClose.draw(mGame.batch);
        }else {
            tvDoorClose.setAlpha(0);
            tvDoorOpen.setAlpha(1);
            tvDoorOpen.draw(mGame.batch);
        }

        if (tvKeyState == 0) {
            tvKey.draw(mGame.batch);
        }


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


        Texture tvKeytexture = new Texture("tv_key.png");
        tvKey = new TvKey(tvKeytexture,0,0,125,85);
        tvKey.setPosition(tvKeyPointX,tvKeyPonitY);


        Texture tvDoorCloseTexture = new Texture("tv_door_close.png");
        tvDoorClose = new Sprite(tvDoorCloseTexture,0,0,710,275);
        tvDoorClose.setSize(14.3f,5.5f);
        tvDoorClose.setPosition(0.85f,1.83f);


        Texture tvDoorOpenTexture = new Texture("tv_door_open.png");
        tvDoorOpen = new Sprite(tvDoorOpenTexture,0,0,800,450);
        tvDoorOpen.setSize(16f,9f);
        tvDoorOpen.setPosition(0f,0f);


        Texture tvRimoConTexture = new Texture("tv_rimocon.png");
        tvRimoCon = new Sprite(tvRimoConTexture,0,0,80,95);
        tvRimoCon.setSize(1.6f,1.9f);
        tvRimoCon.setPosition(tvRimoConPointX,tvRimoConPointY);

        Texture textureGrassC = new Texture("grass_c.png");
        grassC = new Sprite(textureGrassC,0,0,180,270);
        grassC.setSize(1.3f,2.0f);
        grassC.setPosition(grassCPointX,grassCPointY);

        Texture textureGrassW = new Texture("grass_w.png");
        grassW = new Sprite(textureGrassW,0,0,180,270);
        grassW.setSize(1.3f,2.0f);
        grassW.setPosition(grassWPointX,grassWPointY);


        Texture boxTexture = new Texture("box.png");
        box = new Box(boxTexture,0,0,50,50);
        box.setPosition(boxPointX,boxPointY);
    }

    public void updateTvZoom(){
        if(Gdx.input.justTouched()) {
            super.pUpdate();
            //mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            Rectangle rectangleDownArrow = new Rectangle(dArrowPointX, dArrowPointY, DownArrow.dArrow_WIDTH, DownArrow.dArrow_HEIGHT);

            Rectangle rectangleTvKey = new Rectangle(tvKeyTouchPointX,tvKeyTouchPonitY,1.3f,1.0f);
            Rectangle rectangleTvRimoCon = new Rectangle(tvRimoConPointX,tvRimoConPointY,1.6f,1.9f);
            Rectangle rectangleGrassC = new Rectangle(grassCPointX,grassCPointY,1.3f,2.0f);
            Rectangle rectangleGrassW = new Rectangle(grassWPointX,grassWPointY,1.3f,2.0f);
            Rectangle rectangleTvDoorClose = new Rectangle(boxPointX,boxPointY,Box.BOX_WIDTH,Box.BOX_HEIGHT);

            /*Rectangle rectanglePenItem = new Rectangle(penItemPointX,penItemPointY,PenItem.penItem_WIDTH,PenItem.penItem_HEIGHT);
            Rectangle rectangleItemButton = new Rectangle(itemButtonPointX,itemButtonPointY,ItemButton.ITEMBUTTON_WIDTH,ItemButton.ITEMBUTTON_HEIGHT);
            Rectangle rectangleTvRimoConItem = new Rectangle(tvRimoConItemPointX,tvRimoConItemPointY,1.2f,1.2f);*/


            if (rectangleDownArrow.contains(mTouchPoint.x,mTouchPoint.y)){
                if (tvZoomFrom == 0){
                    mGame.setScreen(new TvScreen(mGame));
                }else {
                    mGame.setScreen(new TableScreen(mGame));
                }
            }else if (rectangleTvKey.contains(mTouchPoint.x,mTouchPoint.y) && tvKeyState == 0){
                mGame.setScreen(new TvKeyScreen(mGame));
            }else if (rectangleTvDoorClose.contains(mTouchPoint.x,mTouchPoint.y) && tvKeyState == 1 && tvDoorState == 0){
                open.play();
                tvDoorState = 1;
            }else if (rectangleTvDoorClose.contains(mTouchPoint.x,mTouchPoint.y) && tvKeyState == 1 && tvDoorState == 1){
                close.play();
                tvDoorState = 0;
            }else if (rectangleTvRimoCon.contains(mTouchPoint.x,mTouchPoint.y) && tvDoorState == 1 && tvRimoConState == 0){
                item.play();
                preferences.putInteger("TVRIMOCONSTATE",1);
                preferences.flush();
                tvRimoConState = 1;
                tvRimoCon.setAlpha(0);
            }else if (rectangleGrassC.contains(mTouchPoint.x,mTouchPoint.y) && tvDoorState == 1 && grassCState == 0){
                item.play();
                preferences.putInteger(grassCStateKey,1);
                preferences.flush();
                grassCState = 1;
            }else if (rectangleGrassW.contains(mTouchPoint.x,mTouchPoint.y) && tvDoorState == 1 && grassWState == 0){
                item.play();
                preferences.putInteger(grassWStateKey,1);
                preferences.flush();
                grassWState = 1;
            }


            /*else if(rectangleItemButton.contains(mTouchPoint.x,mTouchPoint.y)) {
                super.updateItemBar();
            }*/

        }
    }
}
