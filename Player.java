import javax.swing.*;
import java.awt.*;

public class Player {
    public final String name;
    public int money;
    public boolean isTurn;
    public JLabel label = new JLabel();

    public Player(String name, int y) {
        this.name = name;
        money = 0;
        isTurn = false;
        this.label.setFont(FontLoader.loadJeopardyFont(75f));
        this.label.setBounds(1300, y, 900, 90);
        this.label.setForeground(new Color(255, 255, 255));
        this.label.setHorizontalAlignment(SwingConstants.LEFT);
        this.label.setVerticalAlignment(SwingConstants.TOP);
        this.label.setText(name + "  $0");

    }

    public void win(int amount) {
        money += amount;
        this.label.setText(name + "  $" + money);
    }

    public void lose(int amount) {
        money -= amount;
        this.label.setText(name + "  $" + money);
    }
}