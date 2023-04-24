package com.app.states;

import com.app.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverState extends State {

    private Texture background;

    private Stage stage;
    public GameOverState(GameStateManager gsm, int finalScore) {
        super(gsm);
        background = new Texture("background.png");

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Label label = new Label("GAME OVER :(", skin);
        label.setFontScale(7.0f, 7.0f);
        label.setPosition(240, Constants.APP_HEIGHT * 8 / 10);

        Label scoreLabel = new Label("SCORE : " + finalScore, skin);
        scoreLabel.setFontScale(5.0f, 5.0f);
        scoreLabel.setPosition(270, label.getY() - 200);


        TextButton tryAgainButton = new TextButton("TRY AGAIN", skin);
        tryAgainButton.setSize(500, 150);
        tryAgainButton.getLabel().setFontScale(5.0f, 5.0f);
        tryAgainButton.setPosition(270, scoreLabel.getY() - 200);
        tryAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tryAgain();
            }
        });

        TextButton mainMenuButton = new TextButton("MAIN MENU", skin);
        mainMenuButton.setSize(500, 150);
        mainMenuButton.getLabel().setFontScale(5.0f, 5.0f);
        mainMenuButton.setPosition(270, tryAgainButton.getY() - 175);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToMainMenu();
            }
        });

        ScreenViewport vp = new ScreenViewport(camera);
        stage = new Stage(vp);
        stage.addActor(label);
        stage.addActor(scoreLabel);
        stage.addActor(tryAgainButton);
        stage.addActor(mainMenuButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

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

    private void tryAgain() {
        gsm.set(new PlayState(gsm));
    }

    private void goToMainMenu() {
        gsm.set(new MenuState(gsm));
    }
}
