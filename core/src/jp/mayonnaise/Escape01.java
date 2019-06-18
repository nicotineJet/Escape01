package jp.mayonnaise;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.Preferences;

public class Escape01 extends Game {
	public SpriteBatch batch;
	public ActivityRequestHandler mRequestHandler;


	public Escape01(ActivityRequestHandler requestHandler) {
		super();
		mRequestHandler = requestHandler;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();


		setScreen(new StartScreen(this));
	}

}
