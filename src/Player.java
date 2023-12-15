import processing.core.PApplet;
import processing.core.PImage;

public class Player {
    private int x, y,size;
    private boolean alive;
    private boolean hasMouse;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private PImage playerImg;


    public Player(PApplet window, int x, int y, int size){
        playerImg = window.loadImage("thekid.png");
        playerImg.resize(50,50);
        this.size = size;
        this.x = x;
        this.y = y;
        alive = true;
        hasMouse = false;
    }

    public void draw(PApplet window){

        window.image(this.playerImg, x, y);

        if(up){
            y-=5;
        }
        if(down){
            y+=5;
        }
        if(left){
            x-=5;
        }
        if(right){
            x+=5;
        }
        wallCollision(window);
    }

    public boolean collidingWithMouse(Mice mouse) {
            return (float) (this.size) / 2 + (float) mouse.get_size() / 2 >= distance(this.get_x(), this.get_y(), mouse.get_x(), mouse.get_y());
    }

    public boolean collidingWithSnake(Snake snake) {
        return (float)(this.size)/2 + (float)snake.get_size()/2 >= distance(this.get_x(), this.get_y(), snake.get_x(), snake.get_y());
    }
    public double distance(float x1, float y1, int x2, int y2) {
        float distanceX = x1 - x2;
        float distanceY = y1 - y2;
        return Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
    }

    public void handleKeyPress(char key){
        if(key == 'w'){
            up = true;
        }
        if(key == 's'){
            down = true;
        }
        if(key == 'a'){
            left = true;
        }
        if(key == 'd'){
            right = true;
        }
    }
    public void handleKeyReleased(char key){
        if(key == 'w'){
            up = false;
        }
        if(key == 's'){
            down = false;
        }
        if(key == 'a'){
            left = false;
        }
        if(key == 'd'){
            right = false;
        }

    }

    public void wallCollision(PApplet window){
        if(x + size > window.width){
            x = window.width - size;
        }
        if(x <= 0){
            x = 0;
        }

        if(y <= 0){
            y = 0;
        }
        if(y + size> window.height){
            y = window.height-size;
        }
    }
    public boolean isAlive(){
        return alive;
    }

    public boolean doYouHaveMouse(){
        return hasMouse;
    }
    public void setHasMouse(boolean gotMouse){
        hasMouse = gotMouse;
    }

    public int get_x() {return this.x;}
    public int get_y() {return this.y;}
    public int get_size(){return this.size;}

    public double getSpeed() {
        return 5;
    }

    public void set_x(int newX) {this.x = newX;}
}
