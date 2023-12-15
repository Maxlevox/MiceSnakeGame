import processing.core.PApplet;
import processing.core.PImage;

public class Snake {
    private int x, y, size;
    private double redColor, greenColor;
    private double xSpeed, ySpeed;
    private PImage snakeImg;



    public Snake(PApplet window, int x, int y, int size) {
        snakeImg = window.loadImage("Snake.png");
        snakeImg.resize(50, 50);
        this.x = x;
        this.y = y;
        this.size = size;
        redColor = 0;
        greenColor = 255;

        xSpeed = Math.random()*4 + 1;
        if (xSpeed > 0.5) xSpeed = -xSpeed;

        ySpeed = Math.random() *4 + 1;
        if (ySpeed > 0.5) ySpeed = -ySpeed;

    }

    public void draw(PApplet window){
        window.image(this.snakeImg, x, y);

        wallCollisions(window);
    }

    // trying to get color to change
    public boolean changeColor(PApplet window, boolean hasMouseAndCollided){
        redColor += 0.3;
        greenColor -= 0.3;

        if(hasMouseAndCollided){
            redColor = 0;
            greenColor = 255;
            return false;
        }
        return false;

    }

    public void wallCollisions(PApplet window) {
        if (x + size >= window.width){
            xSpeed = -xSpeed;
            this.x = window.width-size;
        }
        else if (x <= 0) {
            xSpeed = -xSpeed;
            this.x = 0;
        }
        if (y + size >= window.height){
            ySpeed = -ySpeed;
            this.y = window.height-size;
        }
        if ( y <= 0) {
            ySpeed = -ySpeed;
            this.y = 0;
        }

        x += xSpeed;
        y += ySpeed;
    }



    public int get_x() {return this.x;}
    public int get_y() {return this.y;}
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
}
