package byow.Core;

import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Engine {
    public static class point{
        int x;
        int y;
        public point(int xin,int yin){
            x=xin;
            y=yin;
        }
    }

    public static class room {
        int x; //lowerleft corner x
        int y; //lowerleft corner y
        int xSize; //horizontal size
        int ySize; //vertical size
        List<point> wall = new ArrayList<>();
        List<point> space = new ArrayList<>();

        public room(int xin, int yin, int xSizein, int ySizein) {
            x = xin;
            y = yin;
            xSize = xSizein;
            ySize = ySizein;
            //fill the up and down side wall
            for (int i = x; i < (x + xSize); i++) {
                wall.add(new point(i, y));
                wall.add(new point(i, y + ySize - 1));
            }
            //fill the left and right size wall
            for (int i = y + 1; i < (y + ySize - 1); i++) {
                wall.add(new point(x, i));
                wall.add(new point(x + xSize - 1, i));
            }
            //fill the space
            for (int i = x + 1; i < (x + xSize - 1); i++) {
                for (int j = y + 1; j < (y + ySize - 1); j++) {
                    space.add(new point(i, j));
                }
            }
        }
    }

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private Random RANDOM;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        //fill with empty tiles
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }

        long seed=parseInput(input);
        //if the input string is not legitimate, return null
        if (seed==-1)
            return finalWorldFrame;
        RANDOM = new Random(seed);

        //generate the world
       room r1;
       int xin,yin,xSizein,ySizein;
       for (int i=0;i<20;i++) {
           xin = RANDOM.nextInt(WIDTH - 2) ;
           yin = RANDOM.nextInt(HEIGHT - 2) ;
           xSizein = RANDOM.nextInt(WIDTH / 4) + 3;
           ySizein = RANDOM.nextInt(HEIGHT / 4) + 3;
           System.out.println("xin:"+xin+" yin:"+yin+" xSize:"+xSizein+" ySize"+ySizein);
           if (((xin + xSizein) < WIDTH) && ((yin + ySizein) < HEIGHT)) {
               r1 = new room(xin, yin, xSizein, ySizein);
               for (point pt : r1.wall) {
                   if (!finalWorldFrame[pt.x][pt.y].equals(Tileset.FLOOR))
                       finalWorldFrame[pt.x][pt.y] = Tileset.WALL;
               }
               for (point pt : r1.space) {
                   finalWorldFrame[pt.x][pt.y] = Tileset.FLOOR;
               }
           }
       }
        return finalWorldFrame;
    }

    //interpret the input string
    private long parseInput(String input){
        StringInputDevice A = new StringInputDevice(input);
        String B = "";
        char c;
        long seed;

        //if the input is empty, return null
        if (!A.possibleNextInput())
            return -1;
        //if the input is not start with "N", return null
        if (A.getNextKey() != 'N')
            return -1;
        //concatent the numbers between S and N
        while (A.possibleNextInput()) {
            c = A.getNextKey();
            if (c != 'S') {
                B = B + c;
            }
        }
        //convert the string to number
        seed = Long.parseLong(B);
        return seed;
    }

    public static void main(String[] args) {
        Engine A=new Engine();
        TETile[][] world=A.interactWithInputString("N6998651S");
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        // draws the world to the screen
        ter.renderFrame(world);
    }
}
