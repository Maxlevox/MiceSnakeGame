import processing.core.PApplet;
import processing.core.PImage;

public class Player {
    private int xPos, yPos,size;
    private boolean is_alive;
    private boolean hasMouse;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private PImage playerImg;


    public Player(PApplet window, int xPos, int yPos, int size){
        playerImg = window.loadImage("thekid.png");
        playerImg.resize(50,50);
        this.size = size;
        this.xPos = xPos;
        this.yPos = yPos;
        is_alive = true;
        hasMouse = false;
    }

    public void draw(PApplet window){

        window.image(this.playerImg, xPos, yPos);

        if(up){
            yPos-=5;
        }
        if(down){
            yPos+=5;
        }
        if(left){
            xPos-=5;
        }
        if(right){
            xPos+=5;
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

    public boolean collidingWithEnragedSnake(Snake snake) {
        return snake.isEnraged() && (float)(this.size)/2 + (float)snake.get_size()/2 >= distance(this.get_x(), this.get_y(), snake.get_x(), snake.get_y());
    }

    public void wallCollision(PApplet window){
        if(xPos + size > window.width){
            xPos = window.width - size;
        }
        if(xPos <= 0){
            xPos = 0;
        }

        if(yPos <= 0){
            yPos = 0;
        }
        if(yPos + size> window.height){
            yPos = window.height-size;
        }
    }
    public boolean isAlive(){
        return is_alive;
    }

    public boolean doYouHaveMouse(){
        return hasMouse;
    }
    public void setHasMouse(boolean gotMouse){
        hasMouse = gotMouse;
    }

    public int get_x() {return this.xPos;}
    public int get_y() {return this.yPos;}
    public int get_size(){return this.size;}

    public double getSpeed() {
        return 5;
    }

    public void set_x(int newX) {this.xPos = newX;}
}
