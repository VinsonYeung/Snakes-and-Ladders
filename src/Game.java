import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Game {

    private Player[] players;
    private Player activePlayer;
    private Board board;
    private Map<Player, Integer> mapping;

    Game() {
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

    Board getBoard() {
        return board;
    }

    Player[] getPlayers() {
        return players;
    }

    Integer getPosition(int player) {
        return mapping.get(players[player]);
    }

    Integer getPosition(Player player) {
        return mapping.get(player);
    }

    static int rollDice() {
        return (int) Math.ceil(Math.random() * 6);
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

    void moveTo(Player player, int square) {
        mapping.put(player, square);
    }
}
