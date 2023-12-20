import processing.core.PApplet;
import processing.core.PImage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    private ArrayList<Mice> mouseList;
    private Player player;
    private ArrayList<Snake> snakeList;
    private int frames;
    private boolean lost;
    private int time;
    private String prevHighScore;
    PImage mouseImg;
    PImage playerImg;
    public void settings() {
        size(800, 600);   // set the window size

    }

    public void setup() {
        time = 0;
        lost = false;
        frames = 0;
        mouseImg = loadImage("newMouse.png");

        mouseImg.resize(70,70);
        playerImg = loadImage("thekid.png");
        playerImg.resize(50,50);

        // reading file and saving that information of high score
        try {
            prevHighScore = readFile("score/highscore");
            if (prevHighScore.trim().equals("")) prevHighScore = "0";
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        player = new Player(this, 300,300,50);
        mouseList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Mice mouse = new Mice(this, (int)(Math.random()*550+50),(int)(Math.random()*550+50),30);
            mouseList.add(mouse);
        }

        snakeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Snake snake = new Snake(this, (int)(Math.random()*550+50), (int)(Math.random()*550+50), 50);
            snakeList.add(snake);
        }

    }

    public void draw(){
        if (!lost) frames++; // continue to add frames (for time) if player has not lost yet
        background(255);    // paint screen white
        fill(0, 255, 0);          // load green paint color
        tint(255);

        player.draw(this);

        for (Mice mouse : mouseList) {
            mouse.doActions(player, this, snakeList);
        }

        // changing snake color to more reddish color to show they are hungrier
        for (Snake snake: snakeList) {
            snake.changeColor(false);
            snake.draw(this, player);
        }
        // adding one more snake every 30 seconds to increase difficulty
        if (frames/60.0 % 30 == 0) addSnake(snakeList);

        // feeding the closest snake by colliding with it
        feedSnake(snakeList);

        // snakes are red (hungry) and you lose because they eat you --> checks if you loose
        isLost(lost, frames);

        textSize(35);
        fill(0);
        text("Time: " + time,20,40);
    }

    private void addSnake(ArrayList<Snake> snakeList) {
        Snake snake = new Snake(this, (int)(Math.random()*550+50), (int)(Math.random()*550+50), 50);
        snakeList.add(snake);
    }

    private void feedSnake(ArrayList<Snake> listOfSnakes) {
        for (int i = 0; i < listOfSnakes.size(); i++) {
            if (player.collidingWithEnragedSnake(snakeList.get(i))) {lost = true;}
            if (player.collidingWithSnake(listOfSnakes.get(i)) && player.doYouHaveMouse()) {
                listOfSnakes.get(i).changeColor(true);
                player.setHasMouse(false);

                // finding the mouse that was eaten and "making" new mouse
                for(Mice mouse : mouseList){
                    if(mouse.isCaught()){
                        mouse.setCaught(false);
                        mouse.set_x((int)(Math.random()*550+50));
                        mouse.set_y((int)(Math.random()*550+50));
                    }
                }
            }
            if (listOfSnakes.get(i).get_greenColor() <= 0) {
                listOfSnakes.get(i).set_enraged();
            }
        }
    }

    private void isLost(boolean lost, int frames) {
        time = (int)(frames / 60.0);
        if(lost) {

            background(100);

            fill(0, 255, 0);
            textSize(30);
            player.set_x(999999);
            text("You Lost. You have lasted. " + time + " seconds", 160, 250);
            text("High score: " + prevHighScore.trim() + " seconds",160,300);
            text("Press 'r' to restart.",160,400);

            try {
                saveData(time, "score/highscore", false, prevHighScore);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void saveData(int data, String filepath, boolean append, String HighScore) throws IOException {
        try (FileWriter f = new FileWriter(filepath, append);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);) {

            if(Integer.parseInt(HighScore.trim()) < data) {
                writer.println(data);
            }
            else writer.println(HighScore);

        } catch (IOException error ) {
            System.err.println("There was a problem writing to the file: " + filepath);
            error.printStackTrace();
        }
    }

    public void keyPressed(){
       player.handleKeyPress(key);
    }
    public void keyReleased() {
        player.handleKeyReleased(key);
        if (key == 'r') {
            setup();
        }
        if(key == 'p'){
            lost = true;
        }


    }
    public static void main (String[]args){
            PApplet.main("Game");
        }
    public static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
