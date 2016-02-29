package ca.etsmtl.pfe.ui.levelselect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LevelSelectButton extends Button {

    private Label label;
    private Image image;

    public LevelSelectButton(Label label, Image image, ButtonStyle style) {
        super(style);
        this.label = label;
        this.image = image;
        this.stack(image,label);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(getClickListener().isVisualPressed()){
            label.getStyle().fontColor = Color.RED;
        }
        else{
            label.getStyle().fontColor = Color.WHITE;
        }
        super.draw(batch, parentAlpha);
    }
}
