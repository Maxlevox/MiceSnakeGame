import processing.core.PApplet;

import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    private ArrayList<Mice> mouseList;

    private Player player;
    private ArrayList<Snake> snakeList;
    boolean gameOver;
    public void settings() {
        size(800, 600);   // set the window size

    }

    public void setup() {
        player = new Player(300,300,20);
        mouseList = new ArrayList<>();
        gameOver = false;
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
            for (int j = 0; j < snakeList.size(); j++) {
                if ( mouse.colliding(snakeList.get(j)) ) {
                    mouse.flip_Xspeed();
                    mouse.flip_Yspeed();
                }
            }
            if(player.collidingWithMouse(mouse) && !player.hasMouse){
                player.setHasMouse(true);
                mouse.setCaught(true);
            }
            if(mouse.isCaught()){
                mouse.set_x(player.get_x());
                mouse.set_y(player.get_y());
            }
            mouse.draw(this);
        }

        for (Snake snake: snakeList) {
            snake.draw(this);
            if(snake.changeColor(false)){
                background(100);
            }
        }

        for (int i = 0; i < snakeList.size(); i++) {
            if (player.collidingWithSnake(snakeList.get(i)) && player.hasMouse) {
                snakeList.get(i).draw(this);
                if(snakeList.get(i).changeColor(player.hasMouse)){
                    gameOver = true;
                }
                player.hasMouse = false;

                for(Mice mouse : mouseList){
                    if(mouse.isCaught()){
                        mouse.setCaught(false);
                        mouse.set_x((int)(Math.random()*550+50));
                        mouse.set_y((int)(Math.random()*550+50));
                    }
                }
            }
        }
        if(gameOver){
            background(100);
            fill(255,0,0);
            textSize(70);
            text("YOU LOST!",240,300);
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
