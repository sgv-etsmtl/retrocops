package ca.etsmtl.pfe.ui.gamemenu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.gameobjects.items.Item;
import ca.etsmtl.pfe.gameworld.GameWorld;

public class MenuClickListenerHandler extends ClickListener {

    private ImageButton switchButton;
    private ItemMenuButton itemButton;
    private TextButton useButton, overwatchButton, optionsButton;
    private GameWorld gameWorld;

    public MenuClickListenerHandler() {
    }

    public MenuClickListenerHandler(ImageButton switchButton, ItemMenuButton itemButton,
                                    TextButton useButton, TextButton overwatchButton,
                                    TextButton optionsButton,
                                    GameWorld gameWorld) {
        super();
        this.switchButton = switchButton;
        this.itemButton = itemButton;
        this.useButton = useButton;
        this.overwatchButton = overwatchButton;
        this.optionsButton = optionsButton;
        this.gameWorld = gameWorld;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public ImageButton getSwitchButton() {
        return switchButton;
    }

    public void setSwitchButton(ImageButton switchButton) {
        this.switchButton = switchButton;
    }

    public ItemMenuButton getItemButton() {
        return itemButton;
    }

    public void setItemButton(ItemMenuButton itemButton) {
        this.itemButton = itemButton;
    }

    public TextButton getUseButton() {
        return useButton;
    }

    public void setUseButton(TextButton useButton) {
        this.useButton = useButton;
    }

    public TextButton getOverwatchButton() {
        return overwatchButton;
    }

    public void setOverwatchButton(TextButton overwatchButton) {
        this.overwatchButton = overwatchButton;
    }

    public TextButton getOptionsButton() {
        return optionsButton;
    }

    public void setOptionsButton(TextButton optionsButton) {
        this.optionsButton = optionsButton;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if(event.getListenerActor() == switchButton) {
            handleSwitchCharacterClick();

        } else if(event.getListenerActor() == itemButton) {
            handleItemClick();

        } else if(event.getListenerActor() == useButton) {
            handleUseClick();

        }else if(event.getListenerActor() == overwatchButton) {
            handleOverwatchClick();

        }else if(event.getListenerActor() == optionsButton) {
            handleOptionClick();
        }
    }

    private void handleSwitchCharacterClick(){
        if(gameWorld != null){
            gameWorld.changeSelectedCharacter();
        }
    }

    private void handleItemClick(){
        if(gameWorld.getSelectedCharacter() != null &&
                gameWorld.getGameWorldState() == GameWorld.GameWorldState.waitingForAction){

            gameWorld.getSelectedCharacter().reloadItem();
            Item currentItemUse = gameWorld.getSelectedCharacter().getItemCharacter();
            itemButton.updateInfoAmmo(currentItemUse.getTotalAmmo(),
                    currentItemUse.getCurrentClipAmmo(), currentItemUse.getMaxAmmoByClip());
        }
    }

    private void handleUseClick(){
        if(gameWorld.getGameWorldState() == GameWorld.GameWorldState.waitingForAction){
            getGameWorld().setGameWorldState(GameWorld.GameWorldState.usingItem);
            this.useButton.setText("CANCEL");
        }
        else if(gameWorld.getGameWorldState() == GameWorld.GameWorldState.usingItem){
            getGameWorld().setGameWorldState(GameWorld.GameWorldState.waitingForAction);
            this.useButton.setText("USE");
        }
    }

    private void handleOverwatchClick(){
        if(gameWorld != null && gameWorld.getSelectedCharacter() != null
                && gameWorld.getSelectedCharacter().getBaseCharacterState() == BaseCharacter.BaseCharacterState.waiting
                && gameWorld.getGameWorldState() == GameWorld.GameWorldState.waitingForAction) {

            gameWorld.getSelectedCharacter().setBaseCharacterState(BaseCharacter.BaseCharacterState.overwatch);
            Gdx.app.log("info", "OW BUTTON PRESSED");
        }
    }

    private void handleOptionClick(){

    }
}
