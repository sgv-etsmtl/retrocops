package ca.etsmtl.pfe.gameobjects.items;


import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.pathfinding.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Pistole extends Item {

    public Pistole(int totalAmmo,int currentClipAmmo, int maxAmmoByClip) {
        super(true, false, totalAmmo, currentClipAmmo, maxAmmoByClip);
    }

    @Override
    public void attack(BaseCharacter characterToAttack) {
        characterToAttack.characterGetIt(1);
    }

    @Override
    public void attackTile(Node tileToAttack) {
        throw new NotImplementedException();
    }
}
