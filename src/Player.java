import processing.core.PApplet;

public class Player {
    int x, y,size;
    boolean alive;

    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;


    public Player(int x, int y, int size){
        this.size = size;
       this.x = x;
       this.y = y;
        alive = true;
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
}
