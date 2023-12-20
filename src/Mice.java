import processing.core.PApplet;
import processing.core.PImage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Mice {
    private int xPos, yLoc, size;
   private double xSpeed, ySpeed;
   private boolean isCaught;
   private PImage mouseImg;


    public Mice(PApplet window, int x, int y, int size){
        mouseImg = window.loadImage("newMouse.png");
        mouseImg.resize(45,30);
        this.xPos = x;
        this.yLoc = y;
        this.size = size;
        isCaught = false;

        xSpeed = Math.random() * 4 + 1;
        if(Math.random() > 0.5){
            xSpeed = -xSpeed;
        }

        ySpeed = Math.random() * 4 + 1;
        if(Math.random() > 0.5){
            ySpeed = -ySpeed;
        }
    }

    public void draw(PApplet window, Mice mouse, ArrayList<Snake> snakeList){

        // changing the direction of mouse if it collides or is close to snake
        MouseSnakeCollision(mouse, snakeList);

        window.image(this.mouseImg, xPos, yLoc);
        wallCollision(window);
    }

    public void wallCollision(PApplet window){
        if(xPos + mouseImg.width >= window.width){
            xSpeed = -xSpeed;
            xPos = window.width - mouseImg.width;
        }
        if(xPos <= 0){
            xSpeed = -xSpeed;
            xPos = 0;
        }

        if(yLoc <= 0){
            ySpeed = -ySpeed;
            yLoc = 0;
        }
        if(yLoc + mouseImg.height >= window.height){
            ySpeed = -ySpeed;
            yLoc = window.height - mouseImg.height;
        }

        xPos += xSpeed;
        yLoc += ySpeed;

    }

    private void MouseSnakeCollision(Mice mouse, ArrayList<Snake> listOfSnakes) {
        for (int j = 0; j < listOfSnakes.size(); j++) {
            if ( mouse.colliding(listOfSnakes.get(j)) ) {
                mouse.flip_Xspeed();
                mouse.flip_Yspeed();
            }
        }
    }

    public void doActions(Player player, PApplet window, ArrayList<Snake> snakeList) {
        // player catching or colliding with mouse
        if(player.collidingWithMouse(this) && !player.doYouHaveMouse()){
            player.setHasMouse(true);
            this.setCaught(true);
        }

        if (this.isCaught()) {
            // making mouse follow
            this.set_x(player.get_x());
            this.set_y(player.get_y());
        }

        this.draw(window,this, snakeList);
    }


    public boolean colliding(Snake snake){
        return Math.abs((double)((this.size)/2) + (double)(snake.get_size()/2)) >= distance(this.get_x(), this.get_y(), snake.get_x(), snake.get_y());
    }

    public void setCaught(boolean shouldFollow) {
        isCaught = shouldFollow;
    }
    public boolean isCaught(){
        return isCaught;
    }

    public double distance(int miceX, int miceY, int snakeX, int snakeY) {
        int distanceX = miceX - snakeX;
        int distanceY = miceY - snakeY;
        return Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
    }

    public int get_x() {return this.xPos;}
    public void set_x(int newX) {this.xPos = newX;}
    public void set_y(int newY) {this.yLoc = newY;}
    public int get_y() {return this.yLoc;}
    public int get_size() {return this.size;}
    public double get_Xspeed() {return this.xSpeed;}
    public double get_Yspeed() {return this.ySpeed;}
    public void set_Xspeed(int xspeed) {this.xSpeed = xspeed;}

    public void flip_Xspeed() {this.xSpeed = -this.xSpeed;}
    public void flip_Yspeed() {this.ySpeed = -this.ySpeed;}

    public void set_Yspeed(int speed) {this.ySpeed = speed;}
}
