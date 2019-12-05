import javafx.scene.paint.Color;

public class Player {

    private String name;
    private javafx.scene.paint.Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
