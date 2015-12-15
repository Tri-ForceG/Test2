package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	public static final int WIDTH = 460;
	public static final int HEIGHT = 750;
	public static final String TITLE = "Dank Brd";
	Music Music;
	Sound Sound;
	Texture bg;
	
	@Override
	public void create () {
		Music = Gdx.audio.newMusic(Gdx.files.internal("ElginMusic.mp3")); // not the greatest naming
		Sound = Gdx.audio.newSound(Gdx.files.internal("Hitmarker.mp3"));
		Music.setLooping(true);
		Music.play();
		Music.setVolume(1.0f);
		setScreen(new Gravity(this));
	}

	@Override
	public void render () {
		super.render();

	}
}
