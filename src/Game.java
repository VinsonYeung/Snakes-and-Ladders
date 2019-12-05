import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private Player[] players;
    private Player activePlayer;
    private Board board;
    private Map<Player, Integer> mapping;

    public Game() {
        players = new Player[6];
        players[0] = new Player("Red", Color.RED);
        players[1] = new Player("Blue", Color.BLUE);
        players[2] = new Player("Green", Color.GREEN);
        players[3] = new Player("Yellow", Color.YELLOW);
        players[4] = new Player("Violet", Color.BLUEVIOLET);
        players[5] = new Player("Azure", Color.AZURE);
        board = new Board(7,7);
        mapping = new HashMap<>();
        for (Player p : players) {
            mapping.put(p,1);
        }
    }

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Integer getPosition(int player) {
        return mapping.get(players[player]);
    }

    public Integer getPosition(Player player) {
        return mapping.get(player);
    }

    public static int rollDice() {
        int dice1 = (int) Math.ceil(Math.random() * 6);
        int dice2 = (int) Math.ceil(Math.random() * 6);
        return dice1 + dice2;
    }

    private static Player[] rollTurns(Player[] players) {
        Player[] newTurns = new Player[players.length];
        int position = 0;

        Map<Player, Integer> roll = new HashMap<>();
        for (Player p : players) {
            roll.put(p, rollDice());
        }
        // Check each players roll from 12 to 2
        for (int i = 12; i >= 2; i--) {
            List<Player> bracket = new ArrayList<>();
            for (Player p : players) {
                if (roll.get(p) == i) {
                    bracket.add(p);
                }
            }
            if (bracket.size() == 1) {
                newTurns[position++] = bracket.get(0);
            } else if (bracket.size() > 1) {
                // Convert list to array before using recursive
                Player[] sublist = new Player[bracket.size()];
                Player[] reordered = rollTurns(bracket.toArray(sublist));
                for (Player p : reordered) {
                    newTurns[position++] = p;
                }
            }
        }
        return newTurns;
    }

    public void moveTo(Player player, int square) {
        mapping.put(player, square);
    }
}
