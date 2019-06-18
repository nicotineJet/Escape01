package jp.mayonnaise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PassScreen extends GameScreen {
    static final float STRCAMERA_WIDTH = 512;
    static final float STRCAMERA_HEIGHT = 288;



    static final float dArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float dArrowPointY = CAMERA_HEIGHT / 80;

    static final float passBoxPointsX[] = {5.6f,6.6f,7.6f,8.6f};
    static final float passBoxPointY = 6.6f;

    static final float passKeyPointsX[] = {5.4f,6.9f,8.4f,5.4f,6.9f,8.4f,5.4f,6.9f,8.4f};
    static final float passKeyPointsY[] = {3.6f,3.6f,3.6f,2.1f,2.1f,2.1f,0.6f,0.6f,0.6f};

    static final float passOkPointX = 7f;
    static final float passOkPointY = 5.2f;

    OrthographicCamera mStrCamera;
    FitViewport mStrViewPort;


    DownArrow dArrow;

    Sprite passBoxs[] = new Sprite[4];

    Sprite box;


    public Sound button;
    public Sound open;
    public Sound close;


    Texture texturePassBoxOf;
    Texture texturePassBoxOn;


    int passBoxStates[] = new int[4];
    int choicePassBoxState;

    int passState;


    String strPassBoxs[] = new String[4];

    FreeTypeFontGenerator fontGenerator;
    BitmapFont bitmapFont;

    public PassScreen(Escape01 game) {
        super(game);

        // 背景の準備
        Texture bgTexture = new Texture("pass_door.png");
        // TextureRegionで切り出す時の原点は左上
        mBg = new Sprite( new TextureRegion(bgTexture, 0, 0, 800, 450));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);

        // カメラ、ViewPortを生成、設定する
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);

        mStrCamera = new OrthographicCamera();
        mStrCamera.setToOrtho(false,STRCAMERA_WIDTH,STRCAMERA_HEIGHT);
        mStrViewPort = new FitViewport(STRCAMERA_WIDTH,STRCAMERA_HEIGHT,mStrCamera);

        for (int i = 0;i < 4;i ++){
            passBoxStates[i] = 0;
            strPassBoxs[i] = "";
        }

        choicePassBoxState = 0;
        passState = preferences.getInteger("PASSSTATE",0);



        FileHandle file = Gdx.files.internal("NotoSerifCJKjp-Black.otf");
        fontGenerator = new FreeTypeFontGenerator(file);

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 25;
        param.color = Color.BLACK;

        bitmapFont = fontGenerator.generateFont(param);


        button = Gdx.audio.newSound(Gdx.files.internal("pass.mp3"));
        open = Gdx.audio.newSound(Gdx.files.internal("keyopen.mp3"));
        close = Gdx.audio.newSound(Gdx.files.internal("button56.mp3"));


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

        for (int i = 0; i < 4; i++) {
            passBoxs[i].draw(mGame.batch);
        }

        /*if(penState == 0) {
            pen.draw(mGame.batch);
        }*/

        super.itemBar();


        itemButton.draw(mGame.batch);


        //box.draw(mGame.batch);

        mGame.batch.end();

        mStrCamera.update();
        mGame.batch.setProjectionMatrix(mStrCamera.combined);

        mGame.batch.begin();
        for (int i = 0;i < 4;i ++) {
            bitmapFont.draw(mGame.batch, strPassBoxs[i], passBoxPointsX[i] * 32 + 9, passBoxPointY * 32 + 25);
        }
        mGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewPort.update(width, height);
        mStrViewPort.update(width,height);
    }

    public void createStage(){

        Texture dArrowTexture = new Texture("down_arrow.png");
        dArrow = new DownArrow(dArrowTexture,0,0,74,120);
        dArrow.setPosition(dArrowPointX,dArrowPointY);

        texturePassBoxOf = new Texture("pass_news_of.png");
        for (int i = 0;i < 4;i ++){
            passBoxs[i] = new Sprite(texturePassBoxOf,0,0,37,35);
            passBoxs[i].setSize(1f,1f);
            passBoxs[i].setPosition(passBoxPointsX[i],passBoxPointY);
        }

        texturePassBoxOn = new Texture("pass_news_on.png");

        Texture textureBox = new Texture("box.png");
        box = new Sprite(textureBox,0,0,50,50);
        box.setSize(1f,1f);
        box.setPosition(7.5f,4f);


    }

    public void update(){
        if(Gdx.input.justTouched()) {
            super.pUpdate();
            //mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            Rectangle rectangleDownArrow = new Rectangle(dArrowPointX, dArrowPointY, DownArrow.dArrow_WIDTH, DownArrow.dArrow_HEIGHT);
            Rectangle rectanglePassBoxs[] = new Rectangle[4];
            for (int i = 0;i < 4;i ++){
                rectanglePassBoxs[i] = new Rectangle(passBoxPointsX[i],passBoxPointY,1f,1f);
            }
            Rectangle rectanglePassKeys[] = new Rectangle[9];
            for (int i = 0;i < 9;i ++){
                rectanglePassKeys[i] = new Rectangle(passKeyPointsX[i],passKeyPointsY[i],1.4f,1.4f);
            }
            Rectangle rectanglePassOk = new Rectangle(passOkPointX,passOkPointY,1f,1f);


            if (rectangleDownArrow.contains(mTouchPoint.x,mTouchPoint.y)){
                mGame.setScreen(new DoorScreen(mGame));
            }
            for (int i = 0;i < 4;i ++ ){
                if (rectanglePassBoxs[i].contains(mTouchPoint.x,mTouchPoint.y) && passState != 1){
                    if (passBoxStates[i] == 0){
                        passBoxOf();
                        passBoxStates[i] = 1;
                        passBoxs[i] = new Sprite(texturePassBoxOn,0,0,37,35);
                        passBoxs[i].setSize(1f,1f);
                        passBoxs[i].setPosition(passBoxPointsX[i],passBoxPointY);
                        choicePassBoxState = i + 1;
                    }else if (passBoxStates[i] == 1){
                        passBoxOf();
                        choicePassBoxState = 0;
                    }
                }
            }

            for (int i = 0;i < 9;i ++){
                if (rectanglePassKeys[i].contains(mTouchPoint.x,mTouchPoint.y) && choicePassBoxState != 0 && passState != 1){
                    button.play();
                    strPassBoxs[choicePassBoxState - 1] = String.valueOf(i + 1);
                }
            }

            if (rectanglePassOk.contains(mTouchPoint.x,mTouchPoint.y) && passState != 1){
                if (strPassBoxs[0] == "3" && strPassBoxs[1] == "9" && strPassBoxs[2] == "7" && strPassBoxs[3] == "3"){
                    open.play();
                    preferences.putInteger("PASSSTATE",1);
                    preferences.flush();
                    passState = 1;
                }else {
                    close.play();
                }
            }
        }
    }

    public void passBoxOf(){
        for (int i = 0;i < 4;i ++){
            passBoxStates[i] = 0;
            passBoxs[i] = new Sprite(texturePassBoxOf,0,0,37,35);
            passBoxs[i].setSize(1f,1f);
            passBoxs[i].setPosition(passBoxPointsX[i],passBoxPointY);
        }
    }
}
