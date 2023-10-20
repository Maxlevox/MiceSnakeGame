import processing.core.PApplet;

import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    ArrayList<Mice> mouseList;
    Player player;
    ArrayList<Snake> snakeList;
    public void settings() {
        size(800, 600);   // set the window size

    }

    public void setup() {
        player = new Player(300,300,20);
        mouseList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Mice mouse = new Mice((int)(Math.random()*550+50),(int)(Math.random()*550+50),18);
            mouseList.add(mouse);
        }

        snakeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Snake snake = new Snake((int)(Math.random()*550+50), (int)(Math.random()*550+50), 30);
//            for (int j = 0; j < mouseList.size(); j++) {
//               if (mouseList.get(j).get_x() == snake.get_x())
//            }
            snakeList.add(snake);
        }
    }

    public void draw() {
        background(255);    // paint screen white
        fill(0, 255, 0);          // load green paint color

        for (Mice mouse : mouseList) {
            mouse.draw(this);
        }

        for (Snake snake: snakeList) {
            snake.draw(this);
        }

        player.draw(this);
    }

    public void keyPressed(){
       player.handleKeyPress(key);
    }
    public void keyReleased(){
        player.handleKeyReleased(key);
    }
        public static void main (String[]args){
            PApplet.main("Game");
        }
}
