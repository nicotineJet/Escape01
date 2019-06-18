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

public class MirrorScreen extends GameScreen {

    static final float dArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float dArrowPointY = CAMERA_HEIGHT / 80;

    static final float mirrorPointX = 5f;
    static final float mirrorPointY = 0f;


    static final float mirrorButtonPointX = 7.1f;
    static final float mirrorButtonPointY = 3.1f;


    static final float boxPointX = 5f;
    static final float boxPointY = 0f;


    DownArrow dArrow;


    Sprite mirror;
    Texture textureMirrors[] = new Texture[3];

    Sprite mirrorButton;
    Texture textureMirrorButtons[] = new Texture[4];

    Box box;

    public Sound crack;
    public Sound button;


    int mirrorState;
    int mirrorButtonState;


    public MirrorScreen(Escape01 game) {
        super(game);
        // 背景の準備
        Texture bgTexture = new Texture("mirror_screen.png");
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

        mirrorState = preferences.getInteger("MIRRORSTATE",0);

        mirrorButtonState = preferences.getInteger("MIRRORBUTTONSTATE",0);

        crack = Gdx.audio.newSound(Gdx.files.internal("glass-crack1.mp3"));
        button = Gdx.audio.newSound(Gdx.files.internal("button71.mp3"));

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

        dArrow.draw(mGame.batch);

        /*if(penState == 0) {
            pen.draw(mGame.batch);
        }*/

        super.itemBar();


        itemButton.draw(mGame.batch);

        mirrorButton.draw(mGame.batch);

        mirror.draw(mGame.batch);



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


        for (int i = 0;i < 3;i ++){
            textureMirrors[i] = new Texture("mirror" + i + ".png");
        }
        mirror = new Sprite(textureMirrors[mirrorState],0,0,800,450);
        mirror.setSize(16,9);
        mirror.setPosition(0,0);


        for (int i = 0;i < 4;i ++){
            textureMirrorButtons[i] = new Texture("push_button" + i + ".png");
        }
        mirrorButton = new Sprite(textureMirrorButtons[mirrorButtonState],0,0,100,100);
        mirrorButton.setSize(2,2);
        mirrorButton.setPosition(mirrorButtonPointX,mirrorButtonPointY);


        Texture boxTexture = new Texture("box.png");
        box = new Box(boxTexture,0,0,50,50);
        box.setPosition(boxPointX,boxPointY);
    }

    public void update(){
        if(Gdx.input.justTouched()) {
            super.pUpdate();
            //mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            Rectangle rectangleDownArrow = new Rectangle(dArrowPointX, dArrowPointY, DownArrow.dArrow_WIDTH, DownArrow.dArrow_HEIGHT);

            Rectangle rectangleMirror = new Rectangle(mirrorPointX, mirrorPointY, 7f, 7.8f);

            Rectangle rectangleMirrorButton = new Rectangle(mirrorButtonPointX, mirrorButtonPointY, 2f, 2f);

            /*Rectangle rectanglePenItem = new Rectangle(penItemPointX, penItemPointY, PenItem.penItem_WIDTH, PenItem.penItem_HEIGHT);
            Rectangle rectangleItemButton = new Rectangle(itemButtonPointX, itemButtonPointY, ItemButton.ITEMBUTTON_WIDTH, ItemButton.ITEMBUTTON_HEIGHT);
            Rectangle rectangleTvRimoConItem = new Rectangle(tvRimoConItemPointX, tvRimoConItemPointY, 1.2f, 1.2f);*/

            if (rectangleDownArrow.contains(mTouchPoint.x, mTouchPoint.y)) {
                mGame.setScreen(new BedScreen(mGame));
            } /*else if(rectangleItemButton.contains(mTouchPoint.x,mTouchPoint.y)) {
                super.updateItemBar();
            }*/
            if (rectangleMirrorButton.contains(mTouchPoint.x,mTouchPoint.y)){
                if (mirrorState > 1){
                    updateMirrorButton();
                }
            }
            if (rectangleMirror.contains(mTouchPoint.x,mTouchPoint.y)){
                if (mirrorState < 2){
                    updateMirror();
                }
            }
        }
    }

    public void updateMirror(){
        crack.play();
        mirrorState ++;
        preferences.putInteger("MIRRORSTATE",mirrorState);
        preferences.flush();
        mirror = new Sprite(textureMirrors[mirrorState],0,0,800,450);
        mirror.setSize(16,9);
        mirror.setPosition(0,0);
    }

    public void updateMirrorButton(){
        if (mirrorButtonState < 3){
            button.play();
            mirrorButtonState ++;
            preferences.putInteger("MIRRORBUTTONSTATE",mirrorButtonState);
            preferences.flush();
            mirrorButton = new Sprite(textureMirrorButtons[mirrorButtonState],0,0,100,100);
            mirrorButton.setSize(2,2);
            mirrorButton.setPosition(mirrorButtonPointX,mirrorButtonPointY);
        }
    }
}
