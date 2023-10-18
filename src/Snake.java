import processing.core.PApplet;

public class Snake {
    private int x, y, size;
    private int redColor, greenColor;
    double xSpeed, ySpeed;

    public Snake(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;

        xSpeed = Math.random()*4 + 1;
        if (xSpeed > 0.5) xSpeed = -xSpeed;

        ySpeed = Math.random() *4 + 1;
        if (ySpeed > 0.5) ySpeed = -ySpeed;

    }

    public void draw(PApplet window){
        window.fill(0,255,0);
        window.ellipse(x,y,size,size);
        wallCollisions(window);
    }

    public void wallCollisions(PApplet window) {
        if (x + size/2 >= 800){
            xSpeed = -xSpeed;
            this.x = 800-size/2;
        }
        else if (x - size/2 <= 0) {
            xSpeed = -xSpeed;
            this.x = size/2;
        }
        if (y + size/2 >= 800){
            ySpeed = -ySpeed;
            this.y = 800-size/2;
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
}
