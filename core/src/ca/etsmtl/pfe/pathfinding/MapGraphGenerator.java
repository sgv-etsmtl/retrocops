package ca.etsmtl.pfe.pathfinding;

import com.badlogic.gdx.utils.Array;

import ca.etsmtl.pfe.gameworld.GameMap;

public class MapGraphGenerator {

        /*
       CODE EMPRUNTÉ :
       Les lignes suivantes sont basées sur une classe
       provenant du site :
          //https://github.com/LetsMakeAnIndieGame/PhysicsShmup/blob/master/core/src/com/mygdx/game/pathfinding/GraphGenerator.java
       J'ai pris la seul méthode de la classe et je l'ai améliorer. Cette méthode sert à génerer le graph de connection
       de la map qui dit pour chaque tuiles vers qu'elle tuiles on peut aller.
    */
    public MapGraph generateGraph(GameMap map) {
        int mapWidth = map.getNumberOfTileWidth();
        int mapHeight = map.getNumberOfTileHeigth();
        int mapTilePixelWidth = (int) map.getMapTilePixelWidth();
        int mapTilePixelHeigth = (int) map.getMapTilePixelHeigth();
        Node nodes [] = new Node [mapWidth * mapHeight];
        int index;
        int up;
        int down;
        int left;
        int rigth;
        if (mapWidth != 0 && mapHeight != 0){
            nodes[0] = new Node(0,0,0,mapTilePixelWidth,mapTilePixelHeigth);
        }

        for (int tileY = 0; tileY < mapHeight; ++tileY) {

            for (int tileX = 0; tileX < mapWidth; ++tileX) {
                index = mapWidth * tileY + tileX;
                up = index + mapWidth;
                down = index - mapWidth;
                left = index - 1;
                rigth = index + 1;
                Node current = nodes[index];
                if(tileY < mapHeight - 1){
                    nodes[up] = new Node(up,tileX,tileY+1,mapTilePixelWidth,mapTilePixelHeigth);
                }
                if(tileY == 0 && rigth % mapWidth != 0) {
                    nodes[rigth] = new Node(rigth,tileX+1,tileY,mapTilePixelWidth,mapTilePixelHeigth);
                }

                if (!map.isCellBlocked(tileX, tileY)) {
                    if (tileY != mapHeight - 1 &&  !map.isCellBlocked(tileX, tileY + 1)) {
                        current.createConnection(nodes[up]);
                    }
                    if (tileY != 0 && !map.isCellBlocked(tileX, tileY - 1)) {
                        current.createConnection(nodes[down]);
                    }
                    if (tileX != 0 && !map.isCellBlocked(tileX - 1, tileY)) {
                        current.createConnection(nodes[left]);
                    }
                    if (tileX != mapWidth - 1 && !map.isCellBlocked(tileX + 1, tileY)) {
                        current.createConnection(nodes[rigth]);
                    }

                }
            }
        }
        return new MapGraph(new Array<Node>(nodes));
    }
    /* FIN DU CODE EMPRUNTÉ */
}
