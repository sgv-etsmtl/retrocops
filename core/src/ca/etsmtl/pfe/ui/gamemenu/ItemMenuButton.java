package ca.etsmtl.pfe.ui.gamemenu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;

import ca.etsmtl.pfe.helper.AssetLoader;

public class ItemMenuButton extends Button {

    private Label.LabelStyle itemMenuButtonLabelStyle;
    private Label totalAmmoLabel;
    private Label currentAmmoInfoLabel;
    private Image currentImage;
    private Image imageUp;
    private Image imageDown;
    private boolean cliked;
    private boolean isPress;

    public ItemMenuButton(String totalAmmo, String currentAmmoClip, String maxAmmoByClip,
                          ButtonStyle style, Image imageUp, Image imageDown) {
        super(style);
        this.itemMenuButtonLabelStyle = new Label.LabelStyle();
        this.itemMenuButtonLabelStyle.font = AssetLoader.ammoFont;

        this.totalAmmoLabel = new Label(totalAmmo, itemMenuButtonLabelStyle);
        this.totalAmmoLabel.setAlignment(Align.bottomLeft);
        this.currentAmmoInfoLabel = new Label(currentAmmoClip + "/" + maxAmmoByClip,
                itemMenuButtonLabelStyle);
        this.currentAmmoInfoLabel.setAlignment(Align.bottomRight);
        this.imageUp = imageUp;
        this.imageDown = imageDown;
        this.currentImage = imageUp;
        cliked = false;
        isPress = false;
        this.stack(this.currentImage, this.totalAmmoLabel, this.currentAmmoInfoLabel);
    }

    public void updateInfoAmmo(int totalAmmo, int currentAmmoClip, int maxAmmoByClip){
        this.totalAmmoLabel.setText(String.valueOf(totalAmmo));
        this.currentAmmoInfoLabel.setText(currentAmmoClip + "/" + maxAmmoByClip);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(getClickListener().isVisualPressed()){
            this.currentImage = this.imageDown;
            cliked = true;
        }
        else{
            this.currentImage = this.imageUp;
            cliked = false;
        }
        if(isPress != cliked){
            isPress = cliked;
            this.getCells().get(this.getCells().size - 1).clearActor();
            this.stack(this.currentImage, this.totalAmmoLabel, this.currentAmmoInfoLabel);
            Gdx.graphics.requestRendering();
        }
        super.draw(batch, parentAlpha);
    }
}
