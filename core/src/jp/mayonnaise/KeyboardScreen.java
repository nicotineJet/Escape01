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

public class KeyboardScreen extends GameScreen {

    static final float dArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float dArrowPointY = CAMERA_HEIGHT / 80;

    static final float keyNkeyPointX = 7.53f;
    static final float keyNkeyPointY = 4.45f;

    static final float boxPointX = 7.5f;
    static final float boxPointY = 4.5f;

    Box box;
    //Pen pen;
    DownArrow dArrow;
    Sprite keyNkey;

    Texture textureNKey;


    public KeyboardScreen(Escape01 game) {
        super(game);

        // 背景の準備
        Texture bgTexture = new Texture("keyboard0.png");
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

        /*if(penState == 0) {
            pen.draw(mGame.batch);
        }*/

        super.itemBar();

        if (nKeyItemState == 2) {
            keyNkey.draw(mGame.batch);
        }

        dArrow.draw(mGame.batch);


        itemButton.draw(mGame.batch);

        //box.draw(mGame.batch);

        mGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewPort.update(width, height);
    }

    private void createStage(){
        Texture dArrowTexture = new Texture("down_arrow.png");
        dArrow = new DownArrow(dArrowTexture,0,0,74,120);
        dArrow.setPosition(dArrowPointX,dArrowPointY);

        textureNKey = new Texture("nkey.png");
        keyNkey = new Sprite(textureNKey,0,0,75,75);
        keyNkey.setSize(1.5f,1.5f);
        keyNkey.setPosition(keyNkeyPointX,keyNkeyPointY);


        Texture boxTexture = new Texture("box.png");
        box = new Box(boxTexture,0,0,50,50);
        box.setPosition(boxPointX,boxPointY);

    }

    /*private void update(float delta){
        switch (ScreenState){
            case SCREEN_STATE_BED:
                updateBed();
                break;
            case SCREEN_STATE_TV:
                updateTv();
                break;
            case SCREEN_STATE_DOOR:
                updateDoor();
        }
    }*/

    private void update(){
        if(Gdx.input.justTouched()){
            super.pUpdate();
            //mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));
            Rectangle rectangleDownArrow = new Rectangle(dArrowPointX, dArrowPointY, DownArrow.dArrow_WIDTH, DownArrow.dArrow_HEIGHT);

            Rectangle rectangleKeyNKey = new Rectangle(keyNkeyPointX,keyNkeyPointY,1.5f,1.5f);

            /*Rectangle rectanglePenItem = new Rectangle(penItemPointX,penItemPointY,PenItem.penItem_WIDTH,PenItem.penItem_HEIGHT);
            Rectangle rectangleItemButton = new Rectangle(itemButtonPointX,itemButtonPointY,ItemButton.ITEMBUTTON_WIDTH,ItemButton.ITEMBUTTON_HEIGHT);
            Rectangle rectangleTvRimoConItem = new Rectangle(tvRimoConItemPointX,tvRimoConItemPointY,1.2f,1.2f);*/


            if (rectangleDownArrow.contains(mTouchPoint.x,mTouchPoint.y)){
                mGame.setScreen(new BookScreen(mGame));
            }/*else if(rectangleItemButton.contains(mTouchPoint.x,mTouchPoint.y)){
                super.updateItemBar();
            }*/else if (rectangleKeyNKey.contains(mTouchPoint.x,mTouchPoint.y) && nKeyItemState == 1){
                item.play();
                preferences.putInteger(nKeyItemStateKey,2);
                preferences.flush();
                nKeyItemState = 2;
            }
        }
    }

    private void updateTv(){

    }

    private void updateDoor(){

    }
}
