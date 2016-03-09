package ca.etsmtl.pfe.helper;


import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import ca.etsmtl.pfe.gameworld.GameWorld;


public class GestureHandler implements GestureDetector.GestureListener {
    private GameWorld gameWorld;
    private Vector2 dragFirstPos;
    private float originalDistanceZoom;
    private float baseDistance;
    private float originalZoom;

    private static final float MAX_ZOOM_OUT = 3;
    private static final float MAX_ZOOM_IN = 0.3f;

    public GestureHandler(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        dragFirstPos = new Vector2();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        dragFirstPos.x = x;
        dragFirstPos.y = y;
        gameWorld.setLastScreenPositionClick(x, y);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        gameWorld.handleTapScreen(x,y);
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if(!gameWorld.isPositionPixelInMenu(dragFirstPos.x,dragFirstPos.y)) {
            gameWorld.translateCamera(-deltaX, deltaY);
            return true;
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    /*
       CODE EMPRUNTÉ :
       Les lignes suivantes sont basées sur une classe
       provenant du site :
          https://openclassrooms.com/forum/sujet/android-zoom-avec-gesturedetector-de-libgdx
       dans la réponse de berthommie rC Le 28 août 2014 à 23:27:56
       J'ai pris les lignes suivantes qui servent à calculer le nouveau facteur du zoom de la caméra après
       la gesture zoom que l'utilisateur a faite et on s'assure de ne pas dépasser 2 facteurs de zoom
       soit MAX_ZOOM_OUT et MAX_ZOOM_IN
    */
    public boolean zoom(float initialDistance, float distance) {

        if(originalDistanceZoom != initialDistance){
            originalDistanceZoom = initialDistance;
            baseDistance = initialDistance;
            originalZoom = gameWorld.getCameraZoom();
        }
        float ratio = baseDistance/distance;
        float newZoom = originalZoom*ratio;

        if (newZoom >= MAX_ZOOM_OUT) {
            gameWorld.setCameraZoom(MAX_ZOOM_OUT);
            originalZoom = MAX_ZOOM_OUT;
            baseDistance = distance;
        } else if (newZoom <= MAX_ZOOM_IN) {
            gameWorld.setCameraZoom(MAX_ZOOM_IN);
            originalZoom = MAX_ZOOM_IN;
            baseDistance = distance;
        } else {
            gameWorld.setCameraZoom(newZoom);
        }
        return true;
    }
    /* FIN DU CODE EMPRUNTÉ */
    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
