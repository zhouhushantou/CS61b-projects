package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40,seed);
        game.startGame();

    }

    public MemoryGame(int width, int height,int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        rand=new Random(seed);
       // System.out.println(generateRandomString(5));
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String result = "";
        for (int i = 0; i < n; i++)
            result += CHARACTERS[rand.nextInt(25)];
        return result;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.width/2,this.height-2,"Watch!");
        StdDraw.text(4,this.height-2,"round: "+Integer.toString(round));
        StdDraw.line(0,this.height-3,this.width,this.height-3);
        StdDraw.text(this.width-4,this.height-2,s);
        StdDraw.show();

    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame("test");
            StdDraw.text(this.width / 2, this.height / 2, "" + letters.charAt(i));
            StdDraw.show();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String result="";
        int i=0;
        while(i<n) {
            if (StdDraw.hasNextKeyTyped()) {
                result+=StdDraw.nextKeyTyped();
                i++;
            }
        }
        return result;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        String rdm=generateRandomString(5);
        flashSequence(rdm);

        drawFrame("Input");
        StdDraw.show();
        String result=solicitNCharsInput(5);
        StdDraw.text(this.width/2,this.height/2,result);
        StdDraw.show();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        String t1;
        if (result.equals(rdm))
            t1="Pass";
        else
            t1="Fail";
        drawFrame("Input");
        StdDraw.show();
        StdDraw.text(this.width/2,this.height/2,t1);
        StdDraw.show();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        round++;
        //TODO: Establish Engine loop
    }

}
