package jp.mayonnaise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Rectangle;



public class StartScreen extends ScreenAdapter {
    static final float CAMERA_WIDTH = 16;
    static final float CAMERA_HEIGHT = 9;

    static final float stButtonPointX = 5f;
    static final float stButtonPointY = 3.5f;

    static final float ngButtonPointX = 5f;
    static final float ngButtonPointY = 2f;

    private Escape01 mGame;
    Sprite mBg;
    OrthographicCamera mCamera;
    FitViewport mViewPort;
    BitmapFont mFont;


    Sprite stButton;
    Sprite ngButton;

    Rectangle rcStButton;
    Rectangle rcNgButton;

    Vector3 mTouchPoint;

    Preferences preferences;


    public StartScreen(Escape01 game){

        mGame = game;

        mTouchPoint = new Vector3();

        preferences = Gdx.app.getPreferences("jp.mayonnaise");


        // 背景の準備
        Texture bgTexture = new Texture("start_screen.png");
        mBg = new Sprite( new TextureRegion(bgTexture, 0, 0, 800, 450));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);

        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);



        Texture txStButton = new Texture("start_button.png");
        stButton = new Sprite(txStButton,0,0,270,70);
        stButton.setSize(5.4f,1.4f);
        stButton.setPosition(stButtonPointX,stButtonPointY);

        Texture txNgButton = new Texture("newgame_button1.png");
        ngButton = new Sprite(txNgButton,0,0,270,70);
        ngButton.setSize(5.4f,1.4f);
        ngButton.setPosition(ngButtonPointX,ngButtonPointY);

        rcStButton = new Rectangle(stButtonPointX,stButtonPointY,5.4f,1.4f);
        rcNgButton = new Rectangle(ngButtonPointX,ngButtonPointY,5.4f,1.4f);

    }

    @Override
    public void render(float delta) {
        // 描画する
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mCamera.update();
        mGame.batch.setProjectionMatrix(mCamera.combined);

        mGame.batch.begin();

        mBg.draw(mGame.batch);

        stButton.draw(mGame.batch);
        ngButton.draw(mGame.batch);

        mGame.batch.end();


        if (Gdx.input.justTouched()) {
            mViewPort.unproject(mTouchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));


            if (rcStButton.contains(mTouchPoint.x,mTouchPoint.y)) {
                mGame.setScreen(new BedScreen(mGame));
            }else if (rcNgButton.contains(mTouchPoint.x,mTouchPoint.y)){
                preferences.clear();
                preferences.flush();
                mGame.setScreen(new BedScreen(mGame));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        mViewPort.update(width, height);
    }
}
