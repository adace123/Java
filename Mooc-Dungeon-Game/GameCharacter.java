package dungeon;

/**
 * Created by Aaron on 2/11/2018.
 */
public interface GameCharacter {
    boolean move(String direction, int amount);
    void setPosition(Integer[] position);
}
