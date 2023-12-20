

import processing.core.PApplet;
import processing.core.PImage;

public class Snake {
    private int xPos, yPos, size;
    private double redColor, greenColor;
    private double xSpeed, ySpeed;
    private PImage snakeImg;
    private boolean enraged;



    public Snake(PApplet window, int x, int y, int size) {
        snakeImg = window.loadImage("Snake.png");
        snakeImg.resize(50, 50);
        this.xPos = x;
        this.yPos = y;
        this.size = size;
        redColor = 0;
        greenColor = 255;
        enraged = false;

        xSpeed = Math.random()*4 + 1;
        if (xSpeed > 0.5) xSpeed = -xSpeed;

        ySpeed = Math.random() *4 + 1;
        if (ySpeed > 0.5) ySpeed = -ySpeed;

    }

    public void draw(PApplet window, Player player){


        window.image(this.snakeImg, xPos, yPos);
        movement(window, player);
    }
    // trying to get color to change
    public boolean changeColor(boolean hasMouseAndCollided){
        redColor += 0.3;
        greenColor -= 0.3;

        if(hasMouseAndCollided){
            redColor = 0;
            greenColor = 255;
            return false;
        }
        return false;

    }

    public void movement(PApplet window, Player player) {
        if (xPos + size >= window.width){
            xSpeed = -xSpeed;
            this.xPos = window.width-size;
        }
        else if (xPos <= 0) {
            xSpeed = -xSpeed;
            this.xPos = 0;
        }
        if (yPos + size >= window.height){
            ySpeed = -ySpeed;
            this.yPos = window.height-size;
        }
        if ( yPos <= 0) {
            ySpeed = -ySpeed;
            this.yPos = 0;
        }

        if(!enraged) {
            xPos += xSpeed;
            yPos += ySpeed;
        } else{
            if(player.get_x() > this.xPos){
                this.xPos += 5;
            } else{
                this.xPos -= 5;
            }

            if(player.get_y() > this.yPos){
                this.yPos += 5;
            } else{
                this.yPos -= 5;
            }
        }
    }



    public int get_x() {return this.xPos;}
    public int get_y() {return this.yPos;}
    public int get_size() {return this.size;}

    public double get_greenColor() {
        return this.greenColor;
    }
    public double get_redColor() { return this.redColor; }

    public void set_xSpeed(int xspeed) {
        this.xSpeed = xspeed;
    }
    public void set_ySpeed(int yspeed) {
        this.ySpeed = yspeed;
    }
    public boolean isEnraged(){
        return enraged;
    }
    public void set_enraged(){
        enraged = true;
    }
}