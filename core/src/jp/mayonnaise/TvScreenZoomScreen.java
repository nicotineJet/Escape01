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

public class TvScreenZoomScreen extends GameScreen {
    static final float dArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float dArrowPointY = CAMERA_HEIGHT / 80;

    //static final float tvKeyPointX = 6.75f;
    //static final float tvKeyPonitY = 3.35f;

    //static final float tvKeyTouchPointX = 8.1f;
    //static final float tvKeyTouchPonitY = 3.2f;

    //static final float tvRimoConPointX = 5.5f;
    //static final float tvRimoConPointY = 5.1f;

    static final float tvScreenPointX = 1.5f;
    static final float tvScreenPointY = 1.7f;

    static final float boxPointX = 1.5f;
    static final float boxPointY = 1.7f;


    DownArrow dArrow;
    Box box;
    TvKey tvKey;
    //Sprite tvDoorClose;
    //Sprite tvDoorOpen;
    //Sprite tvRimoCon;

    Sprite tv1;
    Sprite tv2;

    Sound tv;

    //Pen pen;

    //int tvKeyState;
    int tvZoomFrom;
    //int tvDoorState;

    int tvScreenState;


    public TvScreenZoomScreen(Escape01 game) {
        super(game);

        // 背景の準備
        Texture bgTexture = new Texture("tvscreen_zoom_screen.png");
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

        tvZoomFrom = preferences.getInteger("TVZOOMFROM",0);

        tvScreenState = preferences.getInteger("TVSCREENSTATE",1);

        tv = Gdx.audio.newSound(Gdx.files.internal("cursor2.mp3"));


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

        if (tvScreenState == 1){
            tv1.draw(mGame.batch);
        }else {
            tv2.draw(mGame.batch);
        }


        super.itemBar();


        itemButton.draw(mGame.batch);




        /*if (tvRimoConState == 0) {
            tvRimoCon.draw(mGame.batch);
        }*/

        dArrow.draw(mGame.batch);

        /*if (tvDoorState == 0) {
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
        }*/



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


        Texture textureTv1 = new Texture("tv1.png");
        tv1 = new Sprite(textureTv1,800,450);
        tv1.setSize(16,9);
        tv1.setPosition(0,0);


        Texture textureTv2 = new Texture("tv2.png");
        tv2 = new Sprite(textureTv2,800,450);
        tv2.setSize(16,9);
        tv2.setPosition(0,0);


        Texture boxTexture = new Texture("box.png");
        box = new Box(boxTexture,0,0,50,50);
        box.setPosition(boxPointX,boxPointY);
    }

    public void updateTvZoom(){
        if(Gdx.input.justTouched()) {
            super.pUpdate();
            //mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            Rectangle rectangleDownArrow = new Rectangle(dArrowPointX, dArrowPointY, DownArrow.dArrow_WIDTH, DownArrow.dArrow_HEIGHT);

            Rectangle rectangleTvScreen = new Rectangle(tvScreenPointX,tvScreenPointY,13.0f,5.5f);

            /*Rectangle rectangleTvKey = new Rectangle(tvKeyTouchPointX,tvKeyTouchPonitY,1.3f,1.0f);
            Rectangle rectangleTvRimoCon = new Rectangle(tvRimoConPointX,tvRimoConPointY,1.6f,1.9f);
            Rectangle rectangleTvDoorClose = new Rectangle(boxPointX,boxPointY,Box.BOX_WIDTH,Box.BOX_HEIGHT);*/

            /*Rectangle rectanglePenItem = new Rectangle(penItemPointX,penItemPointY,PenItem.penItem_WIDTH,PenItem.penItem_HEIGHT);
            Rectangle rectangleItemButton = new Rectangle(itemButtonPointX,itemButtonPointY,ItemButton.ITEMBUTTON_WIDTH,ItemButton.ITEMBUTTON_HEIGHT);
            Rectangle rectangleTvRimoConItem = new Rectangle(tvRimoConItemPointX,tvRimoConItemPointY,1.2f,1.2f);*/


            if (rectangleDownArrow.contains(mTouchPoint.x,mTouchPoint.y)){
                if (tvZoomFrom == 0){
                    mGame.setScreen(new TvScreen(mGame));
                }else {
                    mGame.setScreen(new TableScreen(mGame));
                }
            }/*else if(rectangleItemButton.contains(mTouchPoint.x,mTouchPoint.y)) {
                super.updateItemBar();
            }*/else if (rectangleTvScreen.contains(mTouchPoint.x,mTouchPoint.y) && tvRimoConItemState == 1){
                tv.play();
                if (tvScreenState == 1){
                    preferences.putInteger("TVSCREENSTATE",2);
                    preferences.flush();
                    tvScreenState = 2;
                    tv1.setAlpha(0);
                    tv2.setAlpha(1);
                }else {
                    preferences.putInteger("TVSCREENSTATE",1);
                    preferences.flush();
                    tvScreenState = 1;
                    tv2.setAlpha(0);
                    tv1.setAlpha(1);
                }
            }

        }
    }
}
