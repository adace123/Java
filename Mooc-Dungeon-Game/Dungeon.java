package dungeon;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Aaron on 2/8/2018.
 */
public class Dungeon {
    private final int length, height, vampiresCount, moves;
    private final boolean vampiresMove;
    private final Player player;
    private final String[][] gameMap;
    private final int[][] gameSituation;
    private final String playerCharacter = "@";
    private final String vampireCharacter = "v";
    private final String[] directions = {"w", "s", "a", "d"};
    private final Scanner playerInput = new Scanner(System.in);
    private final Map<Vampire, Integer[]> vampires = new ConcurrentHashMap<Vampire, Integer[]>();

    public Dungeon(int length, int height, int vampiresCount, int moves, boolean vampiresMove) {
        this.length = length;
        this.height = height;
        this.vampiresCount = vampiresCount;
        this.moves = moves;
        this.vampiresMove = vampiresMove;
        player = new Player(moves, length, height);
        gameMap = new String[length][height];
        gameSituation = new int[length][height];
        this.initMap();
    }

    private void initMap() {
        gameMap[0][0] = playerCharacter;
        for(int i = 0; i < gameMap.length; i++) {
            for(int j = 0; j < gameMap[i].length; j++) {
                if(gameMap[i][j] == null) {
                    gameMap[i][j] = ".";
                }
            }
        }
        initVampires();
    }

    private String randomDirection() {
        return directions[ThreadLocalRandom.current().nextInt(4)];
    }

    private int randomAmount() {
        return ThreadLocalRandom.current().nextInt(length);
    }

    private void moveVampires() {
        for(Vampire vampire : vampires.keySet()) {
            Integer[] oldPosition = vampire.getCurrentPosition();
            while(!vampire.move(randomDirection(), randomAmount())) {}
            Integer[] newPosition = vampire.getCurrentPosition();
            if(!characterAlreadyAtPosition(newPosition)) {
                clearSpace(oldPosition);
                gameMap[newPosition[0]][newPosition[1]] = vampireCharacter;
                vampires.put(vampire, newPosition);
            }
        }
    }

    private void clearSpace(Integer[] position) {
        gameMap[position[0]][position[1]] = ".";
    }

    private boolean characterAlreadyAtPosition(Integer[] position) {
        return !gameMap[position[0]][position[1]].equals(".");
    }

    private void updatePlayerPosition(String direction) {
        Integer[] currentPlayerPosition = player.getCurrentPosition();
        if(player.move(direction, 1)) {
            clearSpace(currentPlayerPosition);
            currentPlayerPosition = player.getCurrentPosition();
            gameMap[currentPlayerPosition[1]][currentPlayerPosition[0]] = playerCharacter;
            checkForHit();

        } else {
            player.setPosition(currentPlayerPosition);
        }
    }

    private void initVampires() {
        vampires.clear();
        for(int i = 0; i < vampiresCount; i++) {
            Vampire vampire = new Vampire(length, height);
            while(characterAlreadyAtPosition(vampire.getCurrentPosition())) {
                int x = ThreadLocalRandom.current().nextInt(length);
                int y = ThreadLocalRandom.current().nextInt(height);
                vampire.setPosition(new Integer[] {x, y});
            }
            Integer[] vampirePosition = vampire.getCurrentPosition();
            vampires.put(vampire, vampirePosition);
            gameMap[vampirePosition[0]][vampirePosition[1]] = vampireCharacter;
        }
    }

    private void checkForHit() {
        Integer[] currentPlayerPosition = player.getCurrentPosition();
        for(Vampire vampire : vampires.keySet()) {
            Integer[] currentPosition = vampire.getCurrentPosition();
            if(currentPosition[1] == currentPlayerPosition[0] && currentPosition[0] == currentPlayerPosition[1]) {
                attackVampire(vampire);
            }
        }
    }

    private void printSituation() {
        Integer[] playerPosition = player.getCurrentPosition();
        System.out.println(player.getLampCharge() + "\n");
        System.out.println(playerCharacter + " " + playerPosition[0] + " " + playerPosition[1]);
        for(Integer[] vampirePositions: vampires.values()) {
            System.out.println(vampireCharacter + " " + vampirePositions[1] + " " + vampirePositions[0]);
        }
        System.out.println();
    }

    private void printMap() {
        for(int i = 0; i < gameMap.length; i++) {
            for(int j = 0; j < gameMap[i].length; j++) {
                System.out.print(gameMap[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }


    private void attackVampire(Vampire vampire) {
        Integer[] vampirePosition = vampire.getCurrentPosition();
        gameMap[vampirePosition[0]][vampirePosition[1]] = playerCharacter;
        vampires.remove(vampire);
    }

    public void run() {
        while(player.getLampCharge() > 0 && vampires.size() > 0) {
            printSituation();
            printMap();
            String[] directions = playerInput.nextLine().toLowerCase().split("");
            for(String direction: directions) {
               updatePlayerPosition(direction);
            }
            if(vampiresMove) {
                moveVampires();
            }
            int currentCharge = player.getLampCharge();
            if((currentCharge - 1) >= 0) {
                player.setLampCharge(currentCharge - 1);
            }
        }
        if(player.getLampCharge() == 0 && vampires.size() > 0) {
            System.out.println("YOU LOSE");
        } else System.out.println("YOU WIN");
    }
}
