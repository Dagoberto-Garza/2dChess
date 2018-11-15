package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.states.GameState;
import com.game.states.GameStateManager;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	GameStateManager gsm;
	public final static int HIGHT = 768;
	public final static int WIDTH = 768;
	public final static float ft=1f/60f;
	public static final Vector2 screen = new Vector2(WIDTH,HIGHT);
	public static final Vector2 mousePos = new Vector2(-1,-1);


	public static BitmapFont font;

	@Override
	public void create () {
		Gdx.graphics.setTitle("Chess");
		Gdx.graphics.setWindowedMode(WIDTH,HIGHT);
		font =new BitmapFont();
		batch = new SpriteBatch();
		gsm= new GameStateManager();
		gsm.push(new GameState());

	}

	@Override
	public void render () {
		gsm.update(Gdx.graphics.getDeltaTime());
		mousePos.set(Gdx.input.getX(),screen.y- Gdx.input.getY());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {

	}
}
