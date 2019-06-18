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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameOverScreen extends ScreenAdapter {
    static final float CAMERA_WIDTH = 16;
    static final float CAMERA_HEIGHT = 9;

    private Escape01 mGame;
    Sprite mBg;
    OrthographicCamera mCamera;
    FitViewport mViewPort;


    Vector3 mTouchPoint;

    Preferences preferences;


    public GameOverScreen(Escape01 game){

        mGame = game;

        if (mGame.mRequestHandler != null) { // ←追加する
            mGame.mRequestHandler.showAds(true); // ←追加する
        } // ←追加する

        mTouchPoint = new Vector3();

        preferences = Gdx.app.getPreferences("jp.mayonnaise");


        // 背景の準備
        Texture bgTexture = new Texture("gameover_screen.png");
        mBg = new Sprite( new TextureRegion(bgTexture, 0, 0, 800, 450));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);

        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);


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

        mGame.batch.end();


        if (Gdx.input.justTouched()) {
            if (mGame.mRequestHandler != null) { // ←追加する
                mGame.mRequestHandler.showAds(false); // ←追加する
            } // ←追加する
            preferences.clear();
            preferences.flush();
            mGame.setScreen(new StartScreen(mGame));
        }
    }

    @Override
    public void resize(int width, int height) {
        mViewPort.update(width, height);
    }
}
