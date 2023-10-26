import processing.core.PApplet;

public class Mice {
    private int x, y, size;
    double xSpeed, ySpeed;

    public Mice(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;

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
        window.fill(128,128,128);
        window.ellipse(x,y,size,size);

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
        return (double)((this.size)/2) + (double)(snake.get_size()/2) <= distance(this.get_x(), this.get_y(), snake.get_x(), snake.get_y());
    }

    public double distance(int miceX, int miceY, int snakeX, int snakeY) {
        int distanceX = miceX - snakeX;
        int distanceY = miceY - snakeY;
        return Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
    }

    public int get_x() {return this.x;}
    public int get_y() {return this.y;}
    public int get_size() {return this.size;}

}
