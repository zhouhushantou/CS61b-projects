package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private int size;
    private int nColTile,nRowTile;
    private int WIDTH;
    private int HEIGHT;
    private TETile[][] world;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    //constructor
    public HexWorld(int size){
       this.size=size;
       nRowTile=size*2;
       nColTile=size+2*(size-1);
       WIDTH=5*nColTile+size;
       HEIGHT=5*nRowTile;
        world = new TETile[WIDTH][HEIGHT];
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        //fill with empty tiles
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int k=0;
        for (int x=0;x<(WIDTH/2-2*size);x=x+nColTile-size+1) {
            for (int y = k*size; y < HEIGHT-k*size; y = y + nRowTile) {
                addHexagon(2 * nColTile + 1+x, y);
                addHexagon(2 * nColTile  + 1-x, y);
            }
            k++;
        }

        // draws the world to the screen
        ter.renderFrame(world);
    }
    public void addHexagon(int xStart, int yStart){
        TETile temp=randomTile();
        int k=0;
        for (int y=yStart;y<yStart+size;y++){
            for (int x=(xStart+size-1-k);x<(xStart+2*size-1+k);x++){
                world[x][y]=temp;
                world[x][yStart+nRowTile-k-1]=temp;
            }
            k++;
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.GRASS;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.WATER;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {
        HexWorld A=new HexWorld(6);
    }
}
