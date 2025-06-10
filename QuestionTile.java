import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class QuestionTile {
    public final int amount;
    public Question question;
    public ImageIcon icon;
    public JLabel label = new JLabel();
    public int x;
    public int y;
    public int category;
    public boolean clicked;

    public QuestionTile(String icon, int amount, int category, int x, int y) {
        URL imageUrl = QuestionTile.class.getResource("/Tiles/" + icon + ".png");
        if (imageUrl != null) {
            this.icon = new ImageIcon(imageUrl);
        } else {
            System.out.println("Error: Image not found - " + icon + ".png");
            this.icon = new ImageIcon();
        }

        this.amount = amount;
        this.category = category;
        this.x = x;
        this.y = y;

        label.setBounds(x, y, this.icon.getIconWidth(), this.icon.getIconHeight());
        label.setIcon(this.icon);
        clicked = false;
    }

    public void scale(double amount) {
        Image img = this.icon.getImage();
        int newWidth = (int) Math.round(this.icon.getIconWidth() * amount);
        int newHeight = (int) Math.round(this.icon.getIconHeight() * amount);
        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        this.icon = new ImageIcon(scaledImg);
        this.label.setIcon(this.icon);
        this.label.setBounds(this.x, this.y, newWidth, newHeight);
    }

    public void addQuestion() {
        question = new Question(category, amount, 1);
    }

    public boolean inBounds(int x, int y) {
        Rectangle bounds = label.getBounds();
        boolean inside = bounds.contains(x, y);

        if (!clicked && inside) {
            //System.out.println("Clicked tile: $" + amount + " at (" + bounds.x + "," + bounds.y + ")");
            clicked = true;
        }

        return inside;
    }
}