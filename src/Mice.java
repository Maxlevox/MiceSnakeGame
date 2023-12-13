import com.sun.javafx.scene.control.GlobalMenuAdapter;
import processing.core.PApplet;
import processing.core.PImage;

public class Mice {
    private int x, y, size;
   private double xSpeed, ySpeed;
   private boolean isCaught;
   private PImage mouseImg;


    public Mice(PApplet window, int x, int y, int size){
        mouseImg = window.loadImage("newMouse.png");
        mouseImg.resize(70,70);
        this.x = x;
        this.y = y;
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

    public void draw(PApplet window){
        window.image(this.mouseImg, x, y);
        wallCollision(window);
    }

    public void wallCollision(PApplet window){
        if(x + size/2 > window.width){
            xSpeed = -xSpeed;
            x = window.width - size/2;
        }
        if(x < size/2){
            xSpeed = -xSpeed;
            x = size/2;
        }

        if(y < size/2){
            ySpeed = -ySpeed;
            y = size/2;
        }
        if(y > window.height - size/2){
            ySpeed = -ySpeed;
            y = window.height - size/2;
        }

        x += xSpeed;
        y += ySpeed;


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

    public int get_x() {return this.x;}
    public void set_x(int newX) {this.x = newX;}
    public void set_y(int newY) {this.y = newY;}
    public int get_y() {return this.y;}
    public int get_size() {return this.size;}
    public double get_Xspeed() {return this.xSpeed;}
    public double get_Yspeed() {return this.ySpeed;}
    public void set_Xspeed(int xspeed) {this.xSpeed = xspeed;}

    public void flip_Xspeed() {this.xSpeed = -this.xSpeed;}
    public void flip_Yspeed() {this.ySpeed = -this.ySpeed;}

    public void set_Yspeed(int speed) {this.ySpeed = speed;}
}
