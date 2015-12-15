package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Admin on 12/14/2015.
 */
public class Gravity implements Screen, InputProcessor {
    Sound Sound;
    World world;
    BodyDef bdef;
    FixtureDef fdef;
    Sprite[] spBird = new Sprite[4];
    TextureAtlas taBird;
    Box2DDebugRenderer b2dr;
    OrthographicCamera camera;

    float elapsedTime;
    Animation aPlayer;
    SpriteBatch batch;
    Body floor;
    Body ceiling;
    Body player;
    Texture bg;


    public Gravity(Game game) {
        Sound = Gdx.audio.newSound(Gdx.files.internal("Hitmarker.mp3"));
        b2dr = new Box2DDebugRenderer();
        batch = new SpriteBatch();

        taBird = new TextureAtlas(Gdx.files.internal("Bird.txt"));

        for (int i = 0; i < 4; i++) {
            spBird[i] = new Sprite(taBird.findRegion("frame_" + i));
        }

            world = new World(new Vector2(0, -98f), true);
            world.setContactListener(new ContactListener() {
                @Override
                public void beginContact(Contact contact) {

                }

                @Override
                public void endContact(Contact contact) {

                }

                @Override
                public void preSolve(Contact contact, Manifold oldManifold) {

                }

                @Override
                public void postSolve(Contact contact, ContactImpulse impulse) {

                }
            });
            createPlayer();
            createFloor();
            createCeiling();

            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            aPlayer = new Animation(1 / 8f, spBird);
        }

        private void createPlayer() {
        bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();

        bdef.position.set(0,100);
        bdef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bdef);

        shape.setAsBox(spBird[0].getWidth(), spBird[0].getHeight() / 2); // sets the outside hit box around the animation
        fdef = new FixtureDef();
        fdef.shape = shape;
        player.setSleepingAllowed(false);
        player.createFixture(fdef);
        player.setGravityScale(1);
    }
    private void createCeiling() {
        bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();

        bdef.position.set(0,750);
        bdef.type = BodyDef.BodyType.StaticBody;
        ceiling = world.createBody(bdef);

        shape.setAsBox(Gdx.graphics.getWidth(), 1);
        fdef = new FixtureDef();
        fdef.shape = shape;
        ceiling.setSleepingAllowed(false);
        ceiling.createFixture(fdef);
        ceiling.setGravityScale(0);
    }

    private void createFloor() {
        bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();

        bdef.position.set(0,3);
        bdef.type = BodyDef.BodyType.StaticBody;
        floor = world.createBody(bdef);

        shape.setAsBox(Gdx.graphics.getWidth(), 1);
        fdef = new FixtureDef();
        fdef.shape = shape;
        floor.setSleepingAllowed(false);
        floor.createFixture(fdef);
        floor.setGravityScale(0);

    }




    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.applyForceToCenter(0, 200000, true);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(100, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 6, 2);
        b2dr.render(world, camera.combined);
        //batch.draw(bg, 0, 0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        batch.begin();
        batch.draw(aPlayer.getKeyFrame(elapsedTime, true), player.getPosition().x, player.getPosition().y - spBird[0].getHeight() / 2);
        if(Gdx.input.justTouched())
            Sound.play();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
