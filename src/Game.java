import processing.core.PApplet;

import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    private ArrayList<Mice> mouseList;
    private Player player;
    private ArrayList<Snake> snakeList;
    private int frames;
    private boolean lost;
    public void settings() {
        size(800, 600);   // set the window size

    }

    public void setup() {
        lost = false;
        frames = 0;


        try {
            prevHighScore = readFile("score/highscore");
        } catch (IOException e) {
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

    public void draw() {
        if (!lost) frames++;
        background(255);    // paint screen white
        fill(0, 255, 0);          // load green paint color

        player.draw(this);

        for (Mice mouse : mouseList) {
            // changing the direction of mouse if it collides or is close to snake
            for (int j = 0; j < snakeList.size(); j++) {
                if ( mouse.colliding(snakeList.get(j)) ) {
                    mouse.flip_Xspeed();
                    mouse.flip_Yspeed();
                }
            }
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
        for (int i = 0; i < snakeList.size(); i++) {
            if (player.collidingWithSnake(snakeList.get(i)) && player.hasMouse) {
                snakeList.get(i).changeColor(true);
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
            if (    snakeList.get(i).get_greencolor() <= 0) {
                lost = true;
            }
        }
        // snakes are red (hungry) and you lose because they eat you
        time = (int)(frames / 60.0);
        if(lost) {
            System.out.println(time);

            background(100);

            // HAVE TO MAKE PLAYER STOP MOVING
            // creating rectangle in the middle that says " you lose"
            fill(0, 255, 0);
            textSize(30);
            player.x = 99999;
            text("You Lost. You have lasted. " + time + " seconds", 160, 250);
            text("High score: " + prevHighScore + " seconds",160,300);
            text("Your score: " + time + " seconds",160,400);

            try {
                if (time == 0) {
                    System.out.println("TIME IS ZERO");
                }
                saveData(time, "score/highscore", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        textSize(35);
        fill(0);
       text("Time: " + time,20,40);
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
}
