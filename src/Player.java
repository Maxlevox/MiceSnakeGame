import processing.core.PApplet;

public class Player {
    int x, y,size;
    boolean alive;

    public Player(int x, int y, int size){
        this.size = size;
       this.x = x;
       this.y = y;
        alive = true;
    }

    public void draw(PApplet window){
        window.fill(0,0,255);
        window.ellipse(x,y,size,size);
    }

    public void handleKeyPress(PApplet window, char key){
        //if()
    }

    public boolean isAlive(){
        return alive;
    }
}
