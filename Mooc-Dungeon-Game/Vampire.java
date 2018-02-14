package dungeon;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Aaron on 2/8/2018.
 */
public class Vampire {
    private int positionX, positionY, xBound, yBound;

    public Vampire(int xBound, int yBound) {
        this.xBound = xBound;
        this.yBound = yBound;
        this.generateRandomPosition(xBound, yBound);
    }

    public Integer[] getCurrentPosition() {
        return new Integer[] {positionX, positionY};
    }

    public boolean move(String direction, int amount) {
        boolean validMove = false;
        if(direction.equals("s") && (positionY + amount) < yBound) {
            positionY += amount;
            validMove = true;
        } else if(direction.equals("w") && (positionY - amount) >= 0) {
            positionY -= amount;
            validMove = true;
        } else if(direction.equals("a") && (positionX - amount) >= 0) {
            positionX -= amount;
            validMove = true;
        } else if(direction.equals("d") && (positionX + amount) < xBound) {
            positionX += amount;
            validMove = true;
        }
        return validMove;
    }

    public void setPosition(Integer[] position) {
        positionX = position[0];
        positionY = position[1];
    }

    public void generateRandomPosition(int xBound, int yBound) {
        positionX = ThreadLocalRandom.current().nextInt(1, xBound);
        positionY = ThreadLocalRandom.current().nextInt(1, yBound);
    }

    public String toString() {
        return "v " + positionY + " " + positionX;
    }

}
