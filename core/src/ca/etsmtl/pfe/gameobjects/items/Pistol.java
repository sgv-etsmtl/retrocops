package ca.etsmtl.pfe.gameobjects.items;


import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.pathfinding.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Pistol extends Item {

    public Pistol(int totalAmmo, int currentClipAmmo, int maxAmmoByClip) {
        super(true, false, totalAmmo, currentClipAmmo, maxAmmoByClip);
    }

    @Override
    public boolean attack(BaseCharacter characterToAttack) {
        if(currentClipAmmo > 0) {
            AssetLoader.getGunSoundRandom().play();
            currentClipAmmo -= 1;
            characterToAttack.characterGetHit(1);
        }
        return true;
    }

    @Override
    public boolean attackTile(Node tileToAttack) {
        throw new NotImplementedException();
    }
}
