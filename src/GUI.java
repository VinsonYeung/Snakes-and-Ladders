import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends javafx.application.Application{

    private enum Phase {
        ROLL, MOVE, EXTRA_MOVE, SWITCH, FINISH
    }

    private Stage gui;
    private Display display;
    private TextArea text;
    private Game game;
    private Player activePlayer;
    private Phase phase;
    private int roll;

    @Override
    public void start(Stage primaryStage) {
        gui = primaryStage;
        gui.setTitle("Snakes & Ladders");
        game = new Game();

        GridPane grid = new GridPane();

        display = new Display(game);
        display.setOnMouseClicked(event -> runGame());
        grid.add(display,0,0);

        text = new TextArea();
        grid.add(text,1,0);
        text.setPrefSize(240, 600);
        text.setEditable(false);

        Scene scene = new Scene(grid);
        gui.setScene(scene);
        gui.show();
        activePlayer = game.getPlayers()[0];
        phase = Phase.ROLL;
        runGame();
    }

    private void runGame() {
        switch (phase) {
            case ROLL:
                roll = Game.rollDice();
                text.appendText(activePlayer.getName() + " rolled a " + roll + "\n");
                phase = Phase.MOVE;
                break;
            case MOVE:
                makeMove();
                phase = Phase.EXTRA_MOVE;
                checkNextPhase();
                break;
            case EXTRA_MOVE:
                checkExtraMovement();
                phase = Phase.SWITCH;
                checkNextPhase();
                break;
            case SWITCH:
                switchPlayer();
                phase = Phase.ROLL;
                runGame();
                break;
            case FINISH:
                break;
        }
    }

    private void checkNextPhase() {
        if (game.getPosition(activePlayer) == 100) {
            text.appendText(activePlayer.getName() + " wins!");
            display.update();
            phase = Phase.FINISH;
            return;
        }

        if (game.getBoard().getSnakes().containsKey(game.getPosition(activePlayer)) ||
                game.getBoard().getLadders().containsKey(game.getPosition(activePlayer))) {
            phase = Phase.EXTRA_MOVE;
        } else {
            phase = Phase.SWITCH;
        }
    }

    private void makeMove() {
        if (game.getPosition(activePlayer) + roll > 100) {
            // Bounce back at final square
            game.moveTo(activePlayer, (100 - (game.getPosition(activePlayer) + roll) % 100));
            display.update();
            text.appendText(activePlayer.getName() + " hit the edge and moved back to " + game.getPosition(activePlayer) + "\n");
        } else {
            game.moveTo(activePlayer, game.getPosition(activePlayer) + roll);
            display.update();
            text.appendText(activePlayer.getName() + " has moved to " + game.getPosition(activePlayer) + "\n");
        }
    }

    private void checkExtraMovement() {
        while (true) {
            // Snakes or ladders
            if (game.getBoard().getSnakes().containsKey(game.getPosition(activePlayer))) {
                text.appendText(activePlayer.getName() + " has slipped on a snake at " + game.getPosition(activePlayer) + "\n");
                game.moveTo(activePlayer, game.getBoard().getSnakes().get(game.getPosition(activePlayer)));
                display.update();
                text.appendText("and landed at " + game.getPosition(activePlayer) + "\n");
            } else if (game.getBoard().getLadders().containsKey(game.getPosition(activePlayer))) {
                text.appendText(activePlayer.getName() + " has found a ladder at " + game.getPosition(activePlayer) + "\n");
                game.moveTo(activePlayer, game.getBoard().getLadders().get(game.getPosition(activePlayer)));
                display.update();
                text.appendText("and has climbed up to " + game.getPosition(activePlayer) + "\n");
            } else {  // No snakes or ladders
                break;
            }
        }
    }

    private void switchPlayer() {
        for (int i = 0; i < game.getPlayers().length; i++) {
            if (game.getPlayers()[i] == activePlayer) {
                // If cycle is at the end
                if (i == game.getPlayers().length - 1) {
                    activePlayer = game.getPlayers()[0];
                    break;
                } else {
                    activePlayer = game.getPlayers()[i + 1];
                    break;
                }
            }
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
