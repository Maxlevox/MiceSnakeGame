import processing.core.PApplet;

public class Player {
    int x, y,size;
    boolean alive;
    boolean hasMouse;

    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;


    public Player(int x, int y, int size){
        this.size = size;
        this.x = x;
        this.y = y;
        alive = true;
        hasMouse = false;
    }

    public void draw(PApplet window){
        window.fill(0,0,255);
        window.ellipse(x,y,size,size);

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
        if (hasMouse) {
            return (float) (this.size) / 2 + (float) mouse.get_size() / 2 >= distance(this.get_x(), this.get_y(), mouse.get_x(), mouse.get_y());
        }
        return false;
    }

    public boolean collidingWithSnake(Snake snake) {
        return (float)(this.size)/2 + (float)snake.get_size()/2 >= distance(this.get_x(), this.get_y(), snake.get_x(), snake.get_y());
    }
    public double distance(float x1, float y1, int x2, int y2) {
        float distanceX = x1 - x2;
        // System.out.println("bullshit ass ");
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
        if(x + size/2 > window.width){
            x = window.width - size/2;
        }
        if(x < size/2){
            x = size/2;
        }

        if(y < size/2){
            y = size/2;
        }
        if(y > window.height - size/2){
            y = window.height - size/2;
        }
    }
    public boolean isAlive(){
        return alive;
    }

    public boolean doYouHaveMouse(){
        return hasMouse;
    }
    public void setHasMouse(boolean gotMouse){
        if(gotMouse) {
            hasMouse = true;
        } else{
            hasMouse = false;
        }
    }
    public int get_x() {return this.x;}
    public int get_y() {return this.y;}
    public int get_size(){return this.size;}

    public double getSpeed() {
        return 5;
    }
}
