import processing.core.PApplet;

public class Snake {
    private int x, y, size;
    private double redColor, greenColor;
    private double xSpeed, ySpeed;

    public Snake(int x, int y, int size) {
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

        window.fill((int)redColor,(int)greenColor,0);
        window.ellipse(x,y,size,size);
        wallCollisions(window);
    }

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

    public void wallCollisions(PApplet window) {
        if (x + size/2 >= window.width){
            xSpeed = -xSpeed;
            this.x = window.width-size/2;
        }
        else if (x - size/2 <= 0) {
            xSpeed = -xSpeed;
            this.x = size/2;
        }
        if (y + size/2 >= window.height){
            ySpeed = -ySpeed;
            this.y = window.height-size/2;
        }
        if ( y - size/2 <= 0) {
            ySpeed = -ySpeed;
            this.y = size/2;
        }

        x += xSpeed;
        y += ySpeed;
    }

    public int get_x() {return this.x;}
    public int get_y() {return this.y;}
    public int get_size() {return this.size;}

    public double get_greencolor() {
        return this.greenColor;
    }

    public void set_xSpeed(int xspeed) {
        this.xSpeed = xspeed;
    }
    public void set_ySpeed(int yspeed) {
        this.ySpeed = yspeed;
    }
}
