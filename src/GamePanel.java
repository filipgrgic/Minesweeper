import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JFrame implements MouseListener {
    //SCREEN SETTINGS
    final int blockSize = 48;

    //COLORS
    final Color defaultColor = new Color(150, 150, 150);
    final Color revealedBackground = new Color(200, 200, 200);
    final Color bombedBackground = new Color(200, 50, 50);
    final Color otherBombColor = new Color(120, 50, 50);
    final Color flagBackground = new Color(50, 90, 180);
    final Color winColor = new Color(100, 150, 100);

    //ICONS
    final ImageIcon iconOne = new ImageIcon(".\\images\\Minesweeper_1.png");
    final Color colorTwo = new Color(50, 140, 100);
    final Color colorThree = new Color(180, 60, 60);
    final Color colorFour = new Color(150, 150, 80);
    final Color colorFive = new Color(150, 100, 100);
    final Color colorSix = new Color(120, 200, 140);
    final Color colorSeven = new Color(180, 100, 10);
    final Color colorEight = new Color(150, 150, 150);

    //SIZE OF THE FIELD
    private int col;
    private int row;
    private int screenWidth;
    private int screenHeight;
    private int numberOfBombs;
    private int notDiscoveredBombs;

    //WINDOW
    private JFrame frame = new JFrame();
    private JPanel button_panel = new JPanel();
    private JPanel bomb_panel = new JPanel();
    private JPanel win_panel = new JPanel();
    private JLabel bomb_label = new JLabel();
    private JLabel youWin = new JLabel();

    //GRID
    private JButton[][] buttons;
    private Grid grid;

    public GamePanel(int row, int col, int numberOfBombs) {
        this.row = row;
        this.col = col;
        this.screenWidth = blockSize * this.col;
        this.screenHeight = blockSize * this.row;
        this.numberOfBombs = this.notDiscoveredBombs = numberOfBombs;

        this.grid = new Grid(row, col, numberOfBombs);
        this.buttons = new JButton[row][col];

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(this.screenWidth, this.screenHeight);
        this.frame.setResizable(false);
        this.frame.setTitle("Minesweeper by pilp");
        this.frame.getContentPane().setBackground(new Color(50, 50, 50));
        this.frame.setLayout(new BorderLayout());

        this.frame.setVisible(true);
        this.frame.setLocationRelativeTo(null);

        this.button_panel.setLayout(new GridLayout(row, col));
        this.button_panel.setBackground(new Color(0, 0, 0));

        this.bomb_panel.setLayout(new BorderLayout());
        this.bomb_panel.setBounds(0, 0, this.screenWidth, 4 * blockSize);
        this.bomb_panel.setBackground(Color.WHITE);

        this.bomb_label.setForeground(Color.BLACK);
        this.bomb_label.setFont(new Font("Calibri", Font.BOLD, 32));
        this.bomb_label.setHorizontalAlignment(JLabel.CENTER);
        this.bomb_label.setText("Number of Bombs: " + numberOfBombs);
        this.bomb_label.setOpaque(true);

        this.bomb_panel.add(this.bomb_label);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.buttons[i][j] = new JButton();
                this.button_panel.add(this.buttons[i][j]);
                this.buttons[i][j].setFont(new Font("Calibri", Font.BOLD, blockSize / 2));
                this.buttons[i][j].setFocusable(false);
                this.buttons[i][j].addMouseListener(this);
                this.buttons[i][j].setBackground(defaultColor);
            }
        }

        this.frame.add(this.bomb_panel, BorderLayout.NORTH);
        this.frame.add(this.button_panel);
    }

    private void revealBorders(int row, int col) {
        int value = this.grid.getValue(row, col);
        this.buttons[row][col].setBackground(revealedBackground);
        if (value != 0) {
            this.buttons[row][col].setText("" + value);
            return;
        } else this.buttons[row][col].setText(" ");

        if (row - 1 >= 0) {
            if (this.buttons[row - 1][col].getText().isEmpty()) revealBorders(row - 1, col);

            if (col - 1 >= 0) {
                if (this.buttons[row - 1][col - 1].getText().isEmpty()) revealBorders(row - 1, col - 1);
            }

            if (col + 1 < this.col) {
                if (this.buttons[row - 1][col + 1].getText().isEmpty()) revealBorders(row - 1, col + 1);
            }
        }

        if (row + 1 < this.row) {
            if (this.buttons[row + 1][col].getText().isEmpty()) revealBorders(row + 1, col);

            if (col - 1 >= 0) {
                if (this.buttons[row + 1][col - 1].getText().isEmpty()) revealBorders(row + 1, col - 1);
            }

            if (col + 1 < this.col) {
                if (this.buttons[row + 1][col + 1].getText().isEmpty()) revealBorders(row + 1, col + 1);
            }
        }

        if (col - 1 >= 0) {
            if (this.buttons[row][col - 1].getText().isEmpty()) revealBorders(row, col - 1);
        }

        if (col + 1 < this.col) {
            if (this.buttons[row][col + 1].getText().isEmpty()) revealBorders(row, col + 1);
        }
    }

    private void revealAll() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int value = this.grid.getValue(i, j);
                if (this.buttons[i][j].getText().isEmpty() || this.buttons[i][j].getText().equals("F")) {
                    if (value == 9) {
                        this.buttons[i][j].setText("B");
                        this.buttons[i][j].setBackground(otherBombColor);
                    } else if (value == 0) {
                        this.buttons[i][j].setText(" ");
                        this.buttons[i][j].setBackground(defaultColor);
                    } else {
                        //setNumberColor(i, j, value);
                        this.buttons[i][j].setText("" + value);
                        this.buttons[i][j].setBackground(defaultColor);
                    }
                }
            }
        }
    }

    private void setNumberColor(int row, int col, int val) {
        switch (val) {
            case 1:
                buttons[row][col].setIcon(iconOne);
                buttons[row][col].setHorizontalAlignment(SwingConstants.CENTER);

            case 2:
                buttons[row][col].setForeground(colorTwo);

            case 3:
                buttons[row][col].setForeground(colorThree);

            case 4:
                buttons[row][col].setForeground(colorFour);

            case 5:
                buttons[row][col].setForeground(colorFive);

            case 6:
                buttons[row][col].setForeground(colorSix);

            case 7:
                buttons[row][col].setForeground(colorSeven);

            case 8:
                buttons[row][col].setForeground(colorEight);
        }
    }

    private void revealField(MouseEvent e) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (e.getSource() == this.buttons[i][j]) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (this.buttons[i][j].getText().isEmpty()) {
                            this.buttons[i][j].setText("F");
                            this.buttons[i][j].setBackground(flagBackground);

                            this.notDiscoveredBombs--;
                            this.bomb_panel.remove(bomb_label);

                            if (this.notDiscoveredBombs < 1) {
                                this.bomb_label.setText("Number of Bombs: 0");
                            } else this.bomb_label.setText("Number of Bombs: " + this.notDiscoveredBombs);

                            this.bomb_panel.add(bomb_label);

                        } else if (this.buttons[i][j].getText().equals("F")) {
                            this.buttons[i][j].setText("");
                            this.buttons[i][j].setBackground(defaultColor);

                            this.notDiscoveredBombs++;

                            if (this.notDiscoveredBombs < 1) {
                                this.bomb_label.setText("Number of Bombs: 0");
                            } else if (this.notDiscoveredBombs >= this.numberOfBombs) {
                                this.bomb_label.setText("Number of Bombs: " + this.numberOfBombs);
                            } else this.bomb_label.setText("Number of Bombs: " + this.notDiscoveredBombs);
                        }
                    } else if (this.buttons[i][j].getText().isEmpty()) {
                        int value = this.grid.getValue(i, j);
                        if (value == 9) {
                            this.buttons[i][j].setText("B");
                            this.revealAll();
                            this.buttons[i][j].setBackground(bombedBackground);
                            return;
                        } else if (value == 0) {
                            this.revealBorders(i, j);
                        } else {
                            //setNumberColor(i, j, value);
                            this.buttons[i][j].setText("" + value);
                            this.buttons[i][j].setBackground(revealedBackground);
                        }
                    }
                }
            }
        }

        this.checkWin();
    }

    private void checkWin() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (this.buttons[i][j].getText().isEmpty()) {
                    return;
                }

                if (this.buttons[i][j].getText().equals("F")) {
                    if (grid.getValue(i, j) != 9) {
                        return;
                    }
                }
            }
        }

        this.win_panel.setBackground(winColor);

        this.youWin.setBackground(winColor);
        this.youWin.setForeground(Color.WHITE);
        this.youWin.setFont(new Font("Calibri", Font.BOLD, 100));
        this.youWin.setHorizontalAlignment(JLabel.CENTER);
        this.youWin.setText("GEWONNEN!");
        this.youWin.setOpaque(true);

        this.win_panel.add(youWin);
        this.frame.remove(this.button_panel);
        this.frame.remove(this.bomb_panel);
        this.frame.add(this.win_panel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        revealField(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        revealField(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        revealField(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
