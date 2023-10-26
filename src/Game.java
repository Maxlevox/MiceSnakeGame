import processing.core.PApplet;

import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    private ArrayList<Mice> mouseList;
    private Mice gottenMouse;
    private Player player;
    private ArrayList<Snake> snakeList;
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
            snakeList.add(snake);
        }

    }

    public void draw() {
        background(255);    // paint screen white
        fill(0, 255, 0);          // load green paint color

        player.draw(this);

        for (Mice mouse : mouseList) {
            mouse.draw(this);
            if(player.collidingWithMouse(mouse)){
                player.setHasMouse(true);
            }
        }

        for (Snake snake: snakeList) {
            snake.changeColor(player.doYouHaveMouse());
            snake.draw(this);
        }

        for (int i = 0; i < mouseList.size(); i++) {
            for (int j = 0; j < snakeList.size(); j++) {
                if ( mouseList.get(i).colliding(snakeList.get(j)) ) {
                    mouseList.get(i).xSpeed = -mouseList.get(i).xSpeed;
                    mouseList.get(i).ySpeed = -mouseList.get(i).xSpeed;
                }
            }
        }

        for (int i = 0; i < mouseList.size(); i++) {
            if ( player.collidingWithMouse(mouseList.get(i)) ) {
                player.setHasMouse(true);
                gottenMouse = mouseList.get(i);
                // System.out.println("mouse got coughtt");
                // Not sure how to make the mouse follow the player. This is an idea.
                gottenMouse.set_x(player.get_x() - 18);
                gottenMouse.set_y(player.get_y() - 18);
                i = mouseList.size();

            }
        }
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
