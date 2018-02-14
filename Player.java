package dungeon;

/**
 * Created by Aaron on 2/8/2018.
 */
public class Player implements GameCharacter {
    private int positionX, positionY, lampCharge, xBound, yBound;

    public Player(int lampCharge, int xBound, int yBound) {
        this.lampCharge = lampCharge;
        this.xBound = xBound;
        this.yBound = yBound;
    }

    public Integer[] getCurrentPosition() {
        return new Integer[] {positionX, positionY};
    }

    public boolean move(String direction, int amount) {
        if(amount > 1 || amount < 1)
            return false;
        boolean validMove = false;
        if(direction.equals("s") && (positionY + 1) < yBound) {
            positionY += 1;
            validMove = true;
        } else if(direction.equals("w") && (positionY - 1) >= 0) {
            positionY -= 1;
            validMove = true;
        } else if(direction.equals("a") && (positionX - 1) >= 0) {
            positionX -= 1;
            validMove = true;
        } else if(direction.equals("d") && (positionX + 1) < xBound) {
            positionX += 1;
            validMove = true;
        }

        return validMove;
    }

    public void setPosition(Integer[] position) {
        positionX = position[0];
        positionY = position[1];
    }

    public int getLampCharge() {
        return lampCharge;
    }

    public void setLampCharge(int lampCharge) {
        this.lampCharge = lampCharge;
    }

    public String toString() {
        return "@ " + positionY + " " + positionX;
    }
}

