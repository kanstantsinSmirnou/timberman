package com.app.states;

import com.app.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuState extends State {

    private Texture background;
    private Stage stage;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("background.png");

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        TextButton playButton = new TextButton("PLAY", skin);
        Label label = new Label("TIMBERMAN", skin);
        label.setFontScale(7.0f, 7.0f);
        label.setPosition(240, Constants.APP_HEIGHT * 8 / 10);

        playButton.setSize(500, 150);
        playButton.getLabel().setFontScale(5.0f, 5.0f);
        playButton.setPosition(290, label.getY() - 250);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                play();
            }
        });

        TextButton recordsButton = new TextButton("RECORDS", skin);
        recordsButton.setSize(500, 150);
        recordsButton.getLabel().setFontScale(5.0f, 5.0f);
        recordsButton.setPosition(290, playButton.getY() - 175);

        ScreenViewport vp = new ScreenViewport(camera);
        stage = new Stage(vp);
        stage.addActor(label);
        stage.addActor(playButton);
        stage.addActor(recordsButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {}

    @Override
    public void update(float dt) {}

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }

    void play() {
        gsm.set(new PlayState(gsm));
    }
}
