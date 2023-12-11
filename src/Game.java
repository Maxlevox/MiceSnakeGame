import processing.core.PApplet;

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
    public void settings() {
        size(800, 600);   // set the window size

    }

    public void setup() {
        time = 0;
        lost = false;
        frames = 0;

        try {
            prevHighScore = readFile("score/highscore");
            if (prevHighScore.trim().equals("")) prevHighScore = "0";
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        player = new Player(300,300,25);
        mouseList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Mice mouse = new Mice((int)(Math.random()*550+50),(int)(Math.random()*550+50),18);
            mouseList.add(mouse);
        }

        snakeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Snake snake = new Snake((int)(Math.random()*550+50), (int)(Math.random()*550+50), 30);
            snakeList.add(snake);
        }

    }

    public void draw(){
        if (!lost) frames++; // continue to add frames (for time) if player has not lost yet
        background(255);    // paint screen white
        fill(0, 255, 0);          // load green paint color

        player.draw(this);

        for (Mice mouse : mouseList) {
            // changing the direction of mouse if it collides or is close to snake
            MouseSnakeCollision(mouse, snakeList);

            // player catching or colliding with mouse
            if(player.collidingWithMouse(mouse) && !player.hasMouse){
                player.setHasMouse(true);
                mouse.setCaught(true);
            }

            if (mouse.isCaught()) {
                // making mouse follow
                mouse.set_x(player.get_x());
                mouse.set_y(player.get_y());
            }

            mouse.draw(this);
        }

        // changing snake color to more reddish color to show they are hungrier
        for (Snake snake: snakeList) {
            snake.changeColor(false);
            snake.draw(this);
        }

        // feeding the closest snake by colliding with it
        feedSnake(snakeList);

        // snakes are red (hungry) and you lose because they eat you --> checks if you loose
        isLost(lost, frames);

        textSize(35);
        fill(0);
        text("Time: " + time,20,40);
    }

    private void MouseSnakeCollision(Mice mouse, ArrayList<Snake> listOfSnakes) {
        for (int j = 0; j < listOfSnakes.size(); j++) {
            if ( mouse.colliding(listOfSnakes.get(j)) ) {
                mouse.flip_Xspeed();
                mouse.flip_Yspeed();
            }
        }
    }

    private void feedSnake(ArrayList<Snake> listOfSnakes) {
        for (int i = 0; i < listOfSnakes.size(); i++) {
            if (player.collidingWithSnake(listOfSnakes.get(i)) && player.hasMouse) {
                listOfSnakes.get(i).changeColor(true);
                player.hasMouse = false;
                // finding the mouse that was eaten and "making" new mouse
                for(Mice mouse : mouseList){
                    if(mouse.isCaught()){
                        mouse.setCaught(false);
                        mouse.set_x((int)(Math.random()*550+50));
                        mouse.set_y((int)(Math.random()*550+50));
                    }
                }
            }
            if (listOfSnakes.get(i).get_greencolor() <= 0) {
                this.lost = true;
            }
        }
    }

    private void isLost(boolean lost, int frames) {
        time = (int)(frames / 60.0);
        if(lost) {
            System.out.println(time);

            background(100);

            fill(0, 255, 0);
            textSize(30);
            player.x = 99999;
            text("You Lost. You have lasted. " + time + " seconds", 160, 250);
            text("High score: " + prevHighScore.trim() + " seconds",160,300);
            text("Press 'r' to restart.",160,400);

            try {
                if (time == 0) {
                    System.out.println("TIME IS ZERO");
                }
                saveData(time, "score/highscore", false, prevHighScore);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveData(int data, String filepath, boolean append, String HighScore) throws IOException {
        System.out.println("about to save data: " + data);
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
