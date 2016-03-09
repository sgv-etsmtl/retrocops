package ca.etsmtl.pfe.gameobjects.items;


import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.pathfinding.Node;

public abstract class Item {
    protected boolean isReloadable;
    protected boolean isThrowable;
    protected BaseCharacter owner;
    protected int totalAmmo;
    protected int maxAmmoByClip;
    protected int currentClipAmmo;

    public Item(boolean isReloadable, boolean isThrowable, int totalAmmo, int currentClipAmmo, int maxAmmoByClip) {
        this.isReloadable = isReloadable;
        this.isThrowable = isThrowable;
        this.totalAmmo = totalAmmo;
        this.maxAmmoByClip = maxAmmoByClip;
        this.currentClipAmmo = currentClipAmmo;
    }

    public boolean isReloadable() {
        return isReloadable;
    }

    public void setIsReloadable(boolean isReloadable) {
        this.isReloadable = isReloadable;
    }

    public boolean isThrowable() {
        return isThrowable;
    }

    public void setIsThrowable(boolean isThrowable) {
        this.isThrowable = isThrowable;
    }

    public BaseCharacter getOwner() {
        return owner;
    }

    public void setOwner(BaseCharacter owner) {
        this.owner = owner;
    }

    public int getTotalAmmo() {
        return totalAmmo;
    }

    public void setTotalAmmo(int totalAmmo) {
        this.totalAmmo = totalAmmo;
    }

    public int getMaxAmmoByClip() {
        return maxAmmoByClip;
    }

    public void setMaxAmmoByClip(int maxAmmoByClip) {
        this.maxAmmoByClip = maxAmmoByClip;
    }

    public int getCurrentClipAmmo() {
        return currentClipAmmo;
    }

    public void setCurrentClipAmmo(int currentClipAmmo) {
        this.currentClipAmmo = currentClipAmmo;
    }

    public abstract void attack(BaseCharacter characterToAttack);

    public abstract void attackTile(Node tileToAttack);

    public void reload(){
        if(isReloadable && currentClipAmmo < maxAmmoByClip){
            totalAmmo -= maxAmmoByClip - currentClipAmmo;
            currentClipAmmo = maxAmmoByClip;
        }
    }
}
