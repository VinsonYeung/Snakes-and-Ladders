import java.util.*;

public class Board {

    // Mapping of snakes which move player positions down from the key to the value
    private Map<Integer, Integer> snakes;
    // Mapping of ladders which move player positions up from the key to the value
    private Map<Integer, Integer> ladders;
    // List of keys for snake and ladder maps
    private List<Integer> endPoints;

    public Board(int snakes, int ladders) {
        endPoints = new ArrayList<>();
        this.snakes = generateSnakes(snakes);
        this.ladders = generateLadders(ladders);
    }

    public Map<Integer, Integer> getSnakes() {
        return snakes;
    }

    public Map<Integer, Integer> getLadders() {
        return ladders;
    }

    public List<Integer> getEndPoints() {
        return endPoints;
    }

    private Map<Integer, Integer> generateSnakes(int snakes) {
        ArrayList<Integer> heads = new ArrayList<>();
        for (int i = 0; i < snakes ; i++) {
            int head;
            while(true) {
                head = (int) Math.ceil(Math.random() * 100);
                if (head > 10 && head != 100 && !endPoints.contains(head)) {
                    heads.add(head);
                    endPoints.add(head);
                    break;
                }
            }
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : heads) {
            map.put(i,findLowerRowInt(i));
        }
        return map;
    }

    private Map<Integer, Integer> generateLadders(int ladders) {
        ArrayList<Integer> bases = new ArrayList<>();
        for (int i = 0; i < ladders ; i++) {
            int base;
            while(true) {
                base = (int) Math.ceil(Math.random() * 100);
                if (base < 91 && base != 1 && !endPoints.contains(base)) {
                    bases.add(base);
                    endPoints.add(base);
                    break;
                }
            }
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : bases) {
            map.put(i,findHigherRowInt(i));
        }
        return map;
    }

    private static int findRow(int square) {
        return (square - 1) / 10;
    }

    private int findLowerRowInt(int square) {
        int tail;
        while (true) {
            tail = (int) Math.ceil(Math.random() * 100);
            if (findRow(tail) < findRow(square) && !endPoints.contains(tail)) {
                endPoints.add(tail);
                return tail;
            }
        }
    }

    private int findHigherRowInt(int square) {
        int top;
        while (true) {
            top = (int) Math.ceil(Math.random() * 100);
            if (findRow(top) > findRow(square) && !endPoints.contains(top)) {
                endPoints.add(top);
                return top;
            }
        }
    }
}
