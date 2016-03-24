package ca.etsmtl.pfe.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class GameLog {

    private ArrayList<String> messages;
    private final int LOG_SIZE = 5;
    private final String DEFAULT_MAP_OBJECTIVE = "Eliminate all hostile presence.";

    public GameLog(){
        this.messages = new ArrayList<String>(LOG_SIZE);
        for(int i = 0; i < LOG_SIZE; i++) {
            messages.add("");
        }
        messages.set(0, DEFAULT_MAP_OBJECTIVE);
    }

    public void clearLog() {
        for (int i=0; i< messages.size(); i++) {
            messages.set(i, "");

        }
        messages.set(0, DEFAULT_MAP_OBJECTIVE);
    }

    public void addMessage(String message) {

        for(int i = LOG_SIZE-1; i > 0; i--) {
            messages.set(i, messages.get(i-1));
        }
        messages.set(0, message);

    }

    public void drawGameLog(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont writeFont) {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.9f);
        shapeRenderer.rect(1280, 0, 600, 450);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batcher.begin();
        for (int i = 0; i < this.messages.size(); i++) {
            writeFont.draw(batcher, this.messages.get(i), 1300, (i + 1) * 80);

        }
        batcher.end();
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }
}
