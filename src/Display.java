import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

public class Display extends javafx.scene.canvas.Canvas {

    private GraphicsContext gc;
    private Game game;

    public Display(Game game) {
        super();
        super.setWidth(600);
        super.setHeight(600);
        gc = super.getGraphicsContext2D();
        gc.setFont(Font.font("Verdana", 12));
        this.game = game;
        update();
    }

    public void update() {
        gc.clearRect(0, 0, super.getWidth(), super.getHeight());
        drawSquares();
        drawNumbers();
        drawSnakesAndLadders();
        drawPlayers();
    }

    private void drawSquares() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,600,600);
        gc.setFill(Color.LIGHTGRAY);
        for (int i = 0; i < 100; i++) {
            if ((i / 10) % 2 == 0) {
                if (i % 2 == 0) {
                    gc.fillRect(60 * i % 600, 540 - (i / 10) * 60, 60, 60);
                }
            } else {
                if (i % 2 == 1) {
                    gc.fillRect(60 * i % 600, 540 - (i / 10) * 60, 60, 60);
                }
            }
        }

    }

    private void drawNumbers() {
        gc.setFill(Color.BLACK);
        for (int i = 1; i <= 100; i++) {
            gc.fillText("" + (i), getCoordinates(i).getKey() - 30, getCoordinates(i).getValue() - 20);
        }
    }

    private void drawSnakesAndLadders() {
        gc.setStroke(Color.RED);
        int startx, starty, endx, endy;
        for (Integer snake : game.getBoard().getSnakes().keySet()) {
            startx = getCoordinates(snake).getKey();
            starty = getCoordinates(snake).getValue();
            endx = getCoordinates(game.getBoard().getSnakes().get(snake)).getKey();
            endy = getCoordinates(game.getBoard().getSnakes().get(snake)).getValue();
            gc.strokeLine(startx, starty, endx, endy);
        }

        gc.setStroke(Color.GREEN);
        for (Integer ladder : game.getBoard().getLadders().keySet()) {
            startx = getCoordinates(ladder).getKey();
            starty = getCoordinates(ladder).getValue();
            endx = getCoordinates(game.getBoard().getLadders().get(ladder)).getKey();
            endy = getCoordinates(game.getBoard().getLadders().get(ladder)).getValue();
            gc.strokeLine(startx, starty, endx, endy);
        }
    }

    private void drawPlayers() {
        for (int i = 0; i < game.getPlayers().length; i++) {
            int square = game.getPosition(i);
            int xCoordinate = getCoordinates(square).getKey();
            int yCoordinate = getCoordinates(square).getValue();
            xCoordinate = xCoordinate - 28 + ((i % 4) * 14);
            yCoordinate = yCoordinate + 16 - ((i / 4) * 14);
            gc.setFill(game.getPlayers()[i].getColor());
            gc.fillOval(xCoordinate, yCoordinate, 12, 12);
        }

    }

    private static Pair<Integer, Integer> getCoordinates(int square) {
        int xCoordinate, yCoordinate;
        square--;
        if (((square / 10) % 2) == 0) {
            xCoordinate = 30 + (60 * square % 600);
            yCoordinate = 570 - (square / 10) * 60;
        } else {
            xCoordinate = 570 - (60 * square % 600);
            yCoordinate = 570 - (square / 10) * 60;
        }
        return new Pair<>(xCoordinate, yCoordinate);
    }
}
