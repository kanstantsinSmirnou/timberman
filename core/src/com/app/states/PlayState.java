package com.app.states;

import static java.lang.Math.random;

import com.app.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.Menu;
import java.util.Random;
import java.util.Vector;

public class PlayState extends State {
    private Texture background;
    private Texture tree;
    private Texture rightBranch;
    private TextureRegion leftBranch;
    private int score;

    private Label scoreLabel;
    private Stage stage;
    private int branchCount;
    enum BRANCH_TYPE {
        LEFT,
        RIGHT,
        NONE
    }

    Vector < BRANCH_TYPE > branches;
    public PlayState(GameStateManager gsm) {
        super(gsm);

        background = new Texture("background.png");
        tree = new Texture("tree.png");
        rightBranch = new Texture("branch.png");
        leftBranch = new TextureRegion(rightBranch);
        leftBranch.flip(true, false);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        scoreLabel = new Label(String.valueOf(score), skin);
        scoreLabel.setFontScale(7.0f, 7.0f);
        scoreLabel.setPosition(Constants.APP_WIDTH / 2 - 65, Constants.APP_HEIGHT * 85 / 100);

        ScreenViewport vp = new ScreenViewport(camera);
        stage = new Stage(vp);
        stage.addActor(scoreLabel);

        branches = new Vector<>();
        branchCount = 15;
        generateBranches();
        score = 0;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            branches.add(nextBranch());
            if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
                if (branches.get(0) == BRANCH_TYPE.LEFT) {
                    gameOver();
                    return;
                }
            } else {
                if (branches.get(0) == BRANCH_TYPE.RIGHT) {
                    gameOver();
                    return;
                }
            }
            ++score;
            scoreLabel.setText(score);
            branches.remove(0);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        sb.draw(tree, (Constants.APP_WIDTH / 2) - (tree.getWidth() / 2), 100, tree.getWidth(), Constants.APP_HEIGHT - 100);
        for (int i = 0; i < branchCount; ++i) {
            if (branches.get(i) == BRANCH_TYPE.LEFT) {
                sb.draw(leftBranch, (Constants.APP_WIDTH / 2) - (tree.getWidth() / 2) - rightBranch.getWidth(), 140 + i * 200);
            } else if (branches.get(i) == BRANCH_TYPE.RIGHT) {
                sb.draw(rightBranch, (Constants.APP_WIDTH / 2) + (tree.getWidth() / 2), 140 + i * 200);
            }
        }
        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        tree.dispose();
        stage.dispose();
    }

    private BRANCH_TYPE nextBranch() {
        if (branches.lastElement() != BRANCH_TYPE.NONE) {
            return BRANCH_TYPE.NONE;
        }
        int type = new Random().nextInt(11);
        if (type == 0) {
            return BRANCH_TYPE.NONE;
        } else
            if (type % 2 == 0) {
            return BRANCH_TYPE.LEFT;
        } else {
            return BRANCH_TYPE.RIGHT;
        }
    }
    private void generateBranches() {
        branches.add(BRANCH_TYPE.NONE);
        branches.add(BRANCH_TYPE.NONE);
        for (int i = 2; i < branchCount; ++i) {
            branches.add(nextBranch());
        }
    }

    private void gameOver() {
        gsm.set(new GameOverState(gsm, score));
    }
}
