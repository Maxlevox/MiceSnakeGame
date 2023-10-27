import processing.core.PApplet;

import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    private ArrayList<Mice> mouseList;
    private Mice gottenMouse;
    private Snake fedSnake;
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

        System.out.println(player.hasMouse);
        for (Mice mouse : mouseList) {
            if(player.collidingWithMouse(mouse) && !player.hasMouse){
                player.setHasMouse(true);
                mouse.setCaught(true);
            }
            if(mouse.isCaught()){
                mouse.set_x(player.get_x());
                mouse.set_y(player.get_y());
                System.out.println("asfasdf");
            }
            mouse.draw(this);
        }

        for (Snake snake: snakeList) {
            snake.changeColor(player.doYouHaveMouse());
            snake.draw(this);
        }

        for (int i = 0; i < mouseList.size(); i++) {
            for (int j = 0; j < snakeList.size(); j++) {
                if ( mouseList.get(i).colliding(snakeList.get(j)) ) {
                    mouseList.get(i).set_Xspeed();
                    mouseList.get(i).set_Yspeed();
                }
            }
        }



        for (int i = 0; i < snakeList.size(); i++) {
            if (player.collidingWithSnake(snakeList.get(i))) {
                fedSnake = snakeList.get(i);
                fedSnake.changeColor(true);
                player.hasMouse = false;
                mouseList.remove(gottenMouse);
                gottenMouse = null;
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
