/*
*   Created By Phillip Sztachera
*   On 06/05/2025
*   Finished 06/??/2025
*
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

public class Jeopardy {
    final static String PATH = System.getProperty("user.dir");
    static JFrame window = new JFrame();
    static JFrame pCreation = new JFrame();
    static JLayeredPane layeredPane = window.getLayeredPane();
    static JLabel categories = new JLabel();
    static JButton questionNext = new JButton("Continue");

    static int mouseX;
    static int mouseY;
    static int numPlayers;
    static int createPlayerNum;
    static int currentQuestion;
    static int catQuestion;
    static int amountQuestion;

    static boolean playerCreation;
    static boolean question;
    static boolean qChoose;


    static QuestionTile[][] board = new QuestionTile[6][8];
    static ArrayList<Player> players = new ArrayList<Player>();
    static ArrayList<JButton> questionWin = new ArrayList<JButton>();
    static ArrayList<JButton> questionLose = new ArrayList<JButton>();
    static ArrayList<JLabel> playerNumber = new ArrayList<JLabel>();

    public static void main(String[] args) {
        init();
        createPlayers();
        update();
    }

    private static void createBoard() {
        int posX = 0;
        for(int i = 0; i < board.length; i++) {
            int posY = 115;
            int amount = 200;
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new QuestionTile("" + amount, amount, i + 1, posX, posY);
                board[i][j].scale(0.20);
                board[i][j].addQuestion();
                window.revalidate();
                window.repaint();

                if(j < 5)
                    amount += 200;
                else
                    amount += 400;

                window.add(board[i][j].label);

                posY += board[i][j].label.getHeight() + 10;
            }
            posX += board[i][0].label.getWidth() + 10;
        }
    }

    private static void update() {
        window.revalidate();
        window.repaint();

        if(pCreation.isActive()) {
            pCreation.revalidate();
            pCreation.repaint();
        }
    }

    private static void init() {
        window.setLayout(null);
        window.setSize(2000, 1200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(new Color(19, 14, 90));
        window.setTitle("Calculus Jeopardy");
        URL iconURL = Jeopardy.class.getResource("/Tiles/Jeopardy.jpg");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            window.setIconImage(icon.getImage());
        } else {
            System.out.println("Error: Icon image not found.");
        }
        URL iconURL2 = Jeopardy.class.getResource("/Tiles/header.png");
        if (iconURL2 != null) {
            ImageIcon icon2 = new ImageIcon(iconURL2);
            categories.setIcon(icon2);
        } else {
            System.out.println("Error: Icon image not found.");
        }

        categories.setBounds(0, 0, 1260, 115);
        window.add(categories);

        //new Color(51, 41, 162) //lighter blue
        question = false;
        questionNext.setBounds(1500,1050, 450, 100);
        questionNext.addActionListener(next -> pickQuestion());
        questionNext.setFont(FontLoader.loadJeopardyFont(90f));

        addMouseTracking();
        addMouseClickListener();
        createBoard();
        update();
    }

    private static void createPlayers() {
        playerCreation = true;
        numPlayers = 4;
        int width = 2000;
        createPlayerNum = 1;

        ArrayList<String> teamNames = new ArrayList<>();

        JLabel title = new JLabel("How many teams are there?");
        JLabel playerSel = new JLabel();
        JButton plus = new JButton("+");
        JButton minus = new JButton("-");
        JButton continueBtn = new JButton("Continue");
        JTextField teamName = new JTextField();

        pCreation.setLayout(null);
        pCreation.setSize(width, 1200);
        pCreation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pCreation.getContentPane().setBackground(new Color(19, 14, 90));
        pCreation.setTitle("Calculus Jeopardy ");

        title.setForeground(new Color(255, 255, 255));
        title.setBounds(0, 30, width, 200);
        title.setFont(FontLoader.loadJeopardyFont(150f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);

        playerSel.setText("" + numPlayers);
        playerSel.setForeground(new Color(255, 255, 255));
        playerSel.setBounds(0, 230, width, 200);
        playerSel.setFont(FontLoader.loadJeopardyFont(150f));
        playerSel.setHorizontalAlignment(SwingConstants.CENTER);
        playerSel.setVerticalAlignment(SwingConstants.CENTER);

        plus.setBounds(1100, 230, 200, 200);
        plus.setFont(FontLoader.loadJeopardyFont(200f));
        plus.setHorizontalAlignment(SwingConstants.CENTER);
        plus.setVerticalAlignment(SwingConstants.CENTER);
        plus.setBorder(null);
        plus.setFocusPainted(false);

        minus.setBounds(700, 230, 200, 200);
        minus.setFont(FontLoader.loadJeopardyFont(200f));
        minus.setHorizontalAlignment(SwingConstants.CENTER);
        minus.setVerticalAlignment(SwingConstants.CENTER);
        minus.setBorder(null);
        minus.setFocusPainted(false);

        continueBtn.setFont(FontLoader.loadJeopardyFont(100f));
        continueBtn.setBounds(750, 500, 500, 200);
        continueBtn.setBorder(null);
        continueBtn.setFocusPainted(false);

        teamName.setFont(FontLoader.loadJeopardyFont(200f));
        teamName.setBounds(0, 230, 1980, 200);
        teamName.setBackground(new Color(19, 14, 90));
        teamName.setForeground(new Color(255, 255, 255));
        teamName.setHorizontalAlignment(SwingConstants.CENTER);

        pCreation.add(playerSel);
        pCreation.add(title);
        pCreation.add(plus);
        pCreation.add(minus);
        pCreation.add(continueBtn);

        pCreation.setVisible(true);
        
        //thank you stack overflow for showing me this
        //add to player cound
        plus.addActionListener(addPlayerNum -> {
            if(numPlayers < 6) {
                numPlayers++;
                playerSel.setText("" + numPlayers);
                update();
            }
        });

        //remove from player count
        minus.addActionListener(removePlayerNum -> {
            if(numPlayers > 2){
                numPlayers--;
                playerSel.setText("" + numPlayers);
                update();
            }
        });

        //create the players and start game
        continueBtn.addActionListener(Continue -> {
            //removes selection number and buttons on the first continue
            if(createPlayerNum == 1) {
                pCreation.remove(playerSel);
                pCreation.remove(plus);
                pCreation.remove(minus);
                pCreation.add(teamName);
                update();
            }
            //takes the input of the text filed for all subsequent continues
            else{
                teamNames.add(teamName.getText());
                teamName.setText("");
            }
            //limits continues to amount of players then starts the game
            if((createPlayerNum) > numPlayers) {
                pCreation.dispose();
                window.setVisible(true);
                playerCreation = false;
                qChoose = true;
                addPlayers(teamNames);
            }
            title.setText("Team " + createPlayerNum + " name");

            createPlayerNum++;
        });
    }


    private static void addPlayers(ArrayList<String> names) {
        int pos = 183;
        int posBtn = 0;
        for(int i = 0; i < names.size(); i++){
            Player player = new Player(names.get(i), pos);
            players.add(player);
            window.add(players.get(i).label);
            pos += 115;

            JButton winButton = new JButton("+");
            JButton loseButton = new JButton("-");
            JLabel playerNum = new JLabel("Team " + (i + 1));

            winButton.setBounds(posBtn, 1100, 65, 50);
            posBtn += 60;
            winButton.setFont(FontLoader.loadJeopardyFont(35f));
            winButton.addActionListener(e -> {
                player.win(currentQuestion);
                //System.out.println(player.name + " $" + player.money);
                qAnswer(catQuestion, amountQuestion);
            });
            questionWin.add(winButton);

            posBtn += 20;
            playerNum.setForeground(new Color(255, 255, 255));
            playerNum.setFont(FontLoader.loadJeopardyFont(40f));
            playerNum.setBounds(posBtn, 1100, 200, 50);
            posBtn += 160;

            playerNumber.add(playerNum);

            loseButton.setBounds(posBtn, 1100, 50, 50);
            posBtn += 60;
            loseButton.setFont(FontLoader.loadJeopardyFont(40f));
            loseButton.addActionListener(e -> {
                player.lose(currentQuestion);
                //System.out.println(player.name + " $" + player.money);
                qAnswer(catQuestion, amountQuestion);
            });
            questionLose.add(loseButton);

        }
    }

    private static void qAnswer(int cat, int amount) {
        for (int i = 0; i < questionWin.size(); i++) {
            layeredPane.remove(questionWin.get(i));
            layeredPane.remove(questionLose.get(i));
            layeredPane.remove(playerNumber.get(i));
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                layeredPane.remove(board[i][j].question.label);
            }
        }

        if(!question) {
            layeredPane.remove(questionNext);
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    layeredPane.remove(board[i][j].question.answer);
                    layeredPane.remove(board[i][j].question.label);
                }
            }
            qChoose = true;
            update();
        }

        qChoose = true;
        update();
    }

    private static void pickQuestion() {
        if(question) {
            layeredPane.add(board[catQuestion][amountQuestion].question.answer);
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    layeredPane.remove(board[i][j].question.label);
                }
            }
            for(int k = 0; k < players.size(); k++) {
                layeredPane.add(questionWin.get(k), JLayeredPane.POPUP_LAYER);
                layeredPane.add(questionLose.get(k), JLayeredPane.POPUP_LAYER);
                layeredPane.add(playerNumber.get(k), JLayeredPane.POPUP_LAYER);
            }
            question = false;
            update();
            layeredPane.remove(questionNext);
        }
    }

    private static void addMouseTracking() {
        window.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent mouse) {
                Point point = SwingUtilities.convertPoint(window, mouse.getPoint(), window.getContentPane());
                mouseX = point.x;
                mouseY = point.y;
            }
        });
    }

    private static void addMouseClickListener() {
        window.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouse) {
                //Whoever decided that the mouse and everything else are to have two different coordinate systems is going receive a strongly worded letter
                //That's three hours of my life I'm not getting back
                Point point = SwingUtilities.convertPoint(window, mouse.getPoint(), window.getContentPane());
                mouseX = point.x;
                mouseY = point.y;

                //System.out.println("Click at " + mouseX + ", " + mouseY);
                //I'm sorry for this monstrosity
                if(qChoose) {
                    qChoose = false;
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board[i].length; j++) {
                            if (board[i][j].inBounds(mouseX, mouseY)) {
                                window.remove(board[i][j].label);
                                layeredPane.add(board[i][j].question.label, 0);
                                currentQuestion = board[i][j].amount;
                                catQuestion = i;
                                amountQuestion = j;
                                layeredPane.add(questionNext, JLayeredPane.POPUP_LAYER);
                                question = true;
                                update();
                            }
                        }
                    }
                }
            }
        });
    }
}