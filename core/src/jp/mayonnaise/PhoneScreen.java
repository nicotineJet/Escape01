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
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PhoneScreen extends GameScreen {
    static final float STRCAMERA_WIDTH = 512;
    static final float STRCAMERA_HEIGHT = 288;



    static final float dArrowPointX = CAMERA_WIDTH / 20 - LeftArrow.lArrow_WIDTH / 2;
    static final float dArrowPointY = CAMERA_HEIGHT / 80;

    static final float newsBoxPointsX[] = {1.1f,1.86f,2.62f,3.4f};
    static final float newsBoxPointY = 3.7f;

    static final float tPhoneKeyPointsX[] = {2.3f,3.0f,1.6f,2.3f,3.0f,2.3f};
    static final float tPhoneKeyPointsY[] = {2.6f,2.6f,1.9f,1.9f,1.9f,1.15f};

    static final float qPhoneKeyPointsX[] = {1.6f,3.0f};
    static final float qPhoneKeyPointY = 1.15f;

    static final float phoneKeyOkPointX = 3.7f;
    static final float phoneKeyOkPointY = 1.15f;


    static final float phoneSkipPointX = 0.97f;
    static final float phoneSkipPointY = 0.9f;


    static final String tPhoneKeys[][] = {{"A","B","C"},{"D","E","F"},{"G","H","I"},{"J","K","L"},{"M","N","O"},{"T","U","V"}};
    static final String qPhoneKeys[][] = {{"P","Q","R","S"},{"W","X","Y","Z"}};

    OrthographicCamera mStrCamera;
    FitViewport mStrViewPort;


    DownArrow dArrow;

    Sprite newsBoxs[] = new Sprite[4];

    Sprite phoneSkip;

    Sprite phoneEnd;

    Sprite box;

    Texture textureNewsBoxOf;
    Texture textureNewsBoxOn;


    Sound button;
    Sound ok;
    Sound ng;


    int newsBoxStates[] = new int[4];

    int tPhoneKeyStates[] = new int[6];
    int qPhoneKeyStates[] = new int[2];

    int choiceNewsBoxState;

    int choiceNewsBo;

    int phoneState;

    String strNewsBoxs[] = new String[4];


    FreeTypeFontGenerator fontGenerator;
    BitmapFont bitmapFont;




    public PhoneScreen(Escape01 game) {
        super(game);

        // 背景の準備
        Texture bgTexture = new Texture("phone_screen.png");
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
            newsBoxStates[i] = 0;
            strNewsBoxs[i] = "";
        }
        for (int i = 0;i < 6;i ++){
            tPhoneKeyStates[i] = 0;
        }
        for (int i = 0;i < 2;i ++){
            qPhoneKeyStates[i] = 0;
        }

        phoneState = preferences.getInteger("PHONESTATE",0);



        FileHandle file = Gdx.files.internal("NotoSerifCJKjp-Black.otf");
        fontGenerator = new FreeTypeFontGenerator(file);

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 15;
        param.color = Color.BLACK;

        bitmapFont = fontGenerator.generateFont(param);
        //bitmapFont.getData().setScale(0.5f);


        button = Gdx.audio.newSound(Gdx.files.internal("pass.mp3"));
        ok = Gdx.audio.newSound(Gdx.files.internal("button71.mp3"));
        ng = Gdx.audio.newSound(Gdx.files.internal("button56.mp3"));


        createStage();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateCalendar();

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
        if (phoneState == 1) {
            phoneSkip.draw(mGame.batch);
        }else if (phoneState == 2){
            phoneEnd.draw(mGame.batch);
        }


        if (phoneState != 2) {
            for (int i = 0; i < 4; i++) {
                newsBoxs[i].draw(mGame.batch);
            }
        }

        super.itemBar();

        itemButton.draw(mGame.batch);

        //box.draw(mGame.batch);


        mGame.batch.end();



        mStrCamera.update();
        mGame.batch.setProjectionMatrix(mStrCamera.combined);

        mGame.batch.begin();
        for (int i = 0;i < 4;i ++) {
            bitmapFont.draw(mGame.batch, strNewsBoxs[i], newsBoxPointsX[i] * 32 + 5, newsBoxPointY * 32 + 17);
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

        textureNewsBoxOf = new Texture("pass_news_of.png");
        for (int i = 0;i < 4;i ++){
            newsBoxs[i] = new Sprite(textureNewsBoxOf,0,0,37,35);
            newsBoxs[i].setSize(0.74f,0.7f);
            newsBoxs[i].setPosition(newsBoxPointsX[i],newsBoxPointY);
        }

        textureNewsBoxOn = new Texture("pass_news_on.png");

        Texture texturePhoneSkip = new Texture("phone_screen2.png");
        phoneSkip = new Sprite(texturePhoneSkip,165,260);
        phoneSkip.setSize(3.3f,5.2f);
        phoneSkip.setPosition(phoneSkipPointX,phoneSkipPointY);

        Texture texturePhoneEnd = new Texture("phone_screen3.png");
        phoneEnd = new Sprite(texturePhoneEnd,165,260);
        phoneEnd.setSize(3.3f,5.2f);
        phoneEnd.setPosition(phoneSkipPointX,phoneSkipPointY);


        Texture boxTexture = new Texture("box.png");
        box = new Sprite(boxTexture,0,0,50,50);
        box.setSize(0.50f,1.20f);
        box.setPosition(3.7f,1.15f);
    }

    public void updateCalendar(){
        if(Gdx.input.justTouched()) {
            super.pUpdate();
            //mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            Rectangle rectangleDownArrow = new Rectangle(dArrowPointX, dArrowPointY, DownArrow.dArrow_WIDTH, DownArrow.dArrow_HEIGHT);

            Rectangle rectangleNewsBoxs[] = new Rectangle[4];
            for (int i = 0;i < 4;i ++){
                rectangleNewsBoxs[i] = new Rectangle(newsBoxPointsX[i],newsBoxPointY,0.74f,0.7f);
            }

            Rectangle rectangleTPhoneKeys[] = new Rectangle[6];
            for (int i = 0;i < 6;i ++){
                rectangleTPhoneKeys[i] = new Rectangle(tPhoneKeyPointsX[i],tPhoneKeyPointsY[i],0.6f,0.6f);
            }

            Rectangle rectangleQPhoneKeys[] = new Rectangle[2];
            for (int i = 0;i < 2;i ++){
                rectangleQPhoneKeys[i] = new Rectangle(qPhoneKeyPointsX[i],qPhoneKeyPointY,0.6f,0.6f);
            }

            Rectangle rectanglePhoneKeyOk = new Rectangle(phoneKeyOkPointX,phoneKeyOkPointY,0.5f,1.2f);



            if (rectangleDownArrow.contains(mTouchPoint.x, mTouchPoint.y)) {
                mGame.setScreen(new BedScreen(mGame));
            }

            for (int i = 0;i < 4;i ++){
                if (rectangleNewsBoxs[i].contains(mTouchPoint.x,mTouchPoint.y) && phoneState != 2){
                    if (newsBoxStates[i] == 0){
                        for (int num = 0;num < 6;num ++){
                            tPhoneKeyStates[num] = 0;
                        }
                        qPhoneKeyStates[0] = 0;
                        qPhoneKeyStates[1] = 0;
                        newsBoxOf();
                        newsBoxStates[i] = 1;
                        newsBoxs[i] = new Sprite(textureNewsBoxOn,0,0,37,35);
                        newsBoxs[i].setSize(0.74f,0.7f);
                        newsBoxs[i].setPosition(newsBoxPointsX[i],newsBoxPointY);
                        choiceNewsBoxState = i + 1;
                    }else if (newsBoxStates[i] == 1){
                        newsBoxOf();
                        choiceNewsBoxState = 0;
                    }
                }
            }

            for (int i = 0;i < 6;i ++){
                if (rectangleTPhoneKeys[i].contains(mTouchPoint.x,mTouchPoint.y) && choiceNewsBoxState != 0 && phoneState != 2){
                    button.play();
                    for (int num = 0;num < 6;num ++){
                        if (num != i){
                            tPhoneKeyStates[num] = 0;
                        }
                    }
                    qPhoneKeyStates[0] = 0;
                    qPhoneKeyStates[1] = 0;
                    strNewsBoxs[choiceNewsBoxState - 1] = tPhoneKeys[i][tPhoneKeyStates[i]];
                    if (tPhoneKeyStates[i] < 2){
                        tPhoneKeyStates[i] ++;
                    }else if (tPhoneKeyStates[i] > 1){
                        tPhoneKeyStates[i] = 0;
                    }
                }
            }

            for (int i = 0;i < 2;i ++){
                if (rectangleQPhoneKeys[i].contains(mTouchPoint.x,mTouchPoint.y) && choiceNewsBoxState != 0 && phoneState != 2){
                    button.play();
                    for (int num = 0;num < 6;num ++){
                        tPhoneKeyStates[num] = 0;
                    }
                    for (int num = 0;num < 2;num ++){
                        if (num != i) {
                            qPhoneKeyStates[num] = 0;
                        }
                    }
                    strNewsBoxs[choiceNewsBoxState - 1] = qPhoneKeys[i][qPhoneKeyStates[i]];
                    if (qPhoneKeyStates[i] < 3){
                        qPhoneKeyStates[i] ++;
                    }else if (qPhoneKeyStates[i] > 2){
                        qPhoneKeyStates[i] = 0;
                    }
                }
            }
            if (rectanglePhoneKeyOk.contains(mTouchPoint.x,mTouchPoint.y)){
                if (phoneState == 0){
                    if (strNewsBoxs[0] == "N" && strNewsBoxs[1] == "E" && strNewsBoxs[2] == "W" && strNewsBoxs[3] == "S"){
                        ok.play();
                        preferences.putInteger("PHONESTATE",1);
                        preferences.flush();
                        phoneState = 1;
                        newsBoxOf();
                        for (int i = 0;i < 4;i ++){
                            strNewsBoxs[i] = "";
                        }
                        for (int i = 0;i < 6;i ++){
                            tPhoneKeyStates[i] = 0;
                        }
                        for (int i = 0;i < 2;i ++){
                            qPhoneKeyStates[i] = 0;
                        }
                    }else {
                        ng.play();
                    }
                }if (phoneState == 1){
                    if (strNewsBoxs[0] == "S" && strNewsBoxs[1] == "K" && strNewsBoxs[2] == "I" && strNewsBoxs[3] == "P"){
                        ok.play();
                        preferences.putInteger("PHONESTATE",2);
                        preferences.flush();
                        phoneState = 2;
                        for (int i = 0;i < 4;i ++){
                            strNewsBoxs[i] = "";
                        }
                    }else {
                        ng.play();
                    }
                }
            }
        }
    }

    public void newsBoxOf(){
        for (int i = 0;i < 4;i ++){
            newsBoxStates[i] = 0;
            newsBoxs[i] = new Sprite(textureNewsBoxOf,0,0,37,35);
            newsBoxs[i].setSize(0.74f,0.7f);
            newsBoxs[i].setPosition(newsBoxPointsX[i],newsBoxPointY);
        }
    }
}
