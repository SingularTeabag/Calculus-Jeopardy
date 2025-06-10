import javax.swing.*;
import java.net.URL;

public class Question {
    public JLabel label = new JLabel();
    public int category;
    public int worth;
    public boolean visible;
    private ImageIcon icon;
    private ImageIcon answerIcon;
    public JLabel answer = new JLabel();

    public Question(int category, int worth, int ran) {
        this.category = category;
        this.worth = worth;
        this.visible = false;

        URL questionUrl = Question.class.getResource("/Questions/" + category + "/" + worth + "/" + ran + ".png");
        URL answerUrl = Question.class.getResource("/Answers/" + category + "/" + worth + "/" + ran + ".png");

        if (questionUrl != null) {
            this.icon = new ImageIcon(questionUrl);
        } else {
            System.out.println("Error: Question image not found - " + category + "/" + worth + "/" + ran + ".png");
            this.icon = new ImageIcon();
        }

        if (answerUrl != null) {
            this.answerIcon = new ImageIcon(answerUrl);
        } else {
            System.out.println("Error: Answer image not found - " + category + "/" + worth + "/" + ran + ".png");
            this.answerIcon = new ImageIcon();
        }

        this.label.setIcon(icon);
        this.label.setBounds(0, 0, 2000, 1200);
        this.answer.setIcon(answerIcon);
        this.answer.setBounds(0, 0, 2000, 1200);
    }
}