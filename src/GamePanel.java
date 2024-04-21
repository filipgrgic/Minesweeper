import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JFrame implements MouseListener {
    //SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 16;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    final Color defaultColor = new Color(150, 150, 150);
    final Color revealedBackground = new Color(200, 200, 200);
    final Color bombedBackground = new Color(200, 50, 50);
    final Color otherBombColor = new Color(120, 50, 50);
    final Color flagBackground = new Color(50, 90, 180);
    final Color winColor = new Color(100, 150, 100);

    private JFrame frame = new JFrame();
    private JPanel button_panel = new JPanel();

    private JPanel winPanel = new JPanel();
    private JButton[][] buttons = new JButton[maxScreenRow][maxScreenCol];
    private Grid grid = new Grid();

    public GamePanel() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(screenWidth, screenHeight);
        this.frame.setResizable(false);
        this.frame.setTitle("Minesweeper by pilp");
        this.frame.getContentPane().setBackground(new Color(50, 50, 50));
        this.frame.setLayout(new BorderLayout());

        this.frame.setVisible(true);
        this.frame.setLocationRelativeTo(null);

        this.button_panel.setLayout(new GridLayout(maxScreenRow, maxScreenCol));
        this.button_panel.setBackground(new Color(0, 0, 0));

        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
                this.buttons[i][j] = new JButton();
                this.button_panel.add(this.buttons[i][j]);
                this.buttons[i][j].setFont(new Font("Calibri", Font.BOLD, tileSize / 2));
                this.buttons[i][j].setFocusable(false);
                this.buttons[i][j].addMouseListener(this);
                this.buttons[i][j].setBackground(defaultColor);
            }
        }

        winPanel.setBackground(winColor);

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

            if (col + 1 < maxScreenCol) {
                if (this.buttons[row - 1][col + 1].getText().isEmpty()) revealBorders(row - 1, col + 1);
            }
        }

        if (row + 1 < maxScreenRow) {
            if (this.buttons[row + 1][col].getText().isEmpty()) revealBorders(row + 1, col);

            if (col - 1 >= 0) {
                if (this.buttons[row + 1][col - 1].getText().isEmpty()) revealBorders(row + 1, col - 1);
            }

            if (col + 1 < maxScreenCol) {
                if (this.buttons[row + 1][col + 1].getText().isEmpty()) revealBorders(row + 1, col + 1);
            }
        }

        if (col - 1 >= 0) {
            if (this.buttons[row][col - 1].getText().isEmpty()) revealBorders(row, col - 1);
        }

        if (col + 1 < maxScreenCol) {
            if (this.buttons[row][col + 1].getText().isEmpty()) revealBorders(row, col + 1);
        }
    }

    private void revealAll() {
        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
                int value = this.grid.getValue(i, j);
                if (this.buttons[i][j].getText().isEmpty() || this.buttons[i][j].getText().equals("F")) {
                    if (value == 9) {
                        this.buttons[i][j].setText("B");
                        this.buttons[i][j].setBackground(otherBombColor);
                    } else if (value == 0) {
                        this.buttons[i][j].setText(" ");
                        this.buttons[i][j].setBackground(defaultColor);
                    } else {
                        this.buttons[i][j].setText("" + value);
                        this.buttons[i][j].setBackground(defaultColor);
                    }
                }
            }
        }
    }

    private void revealField(MouseEvent e) {
        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
                if (e.getSource() == this.buttons[i][j]) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (this.buttons[i][j].getText().isEmpty()) {
                            this.buttons[i][j].setText("F");
                            this.buttons[i][j].setBackground(flagBackground);
                        } else if (this.buttons[i][j].getText().equals("F")) {
                            this.buttons[i][j].setText("");
                            this.buttons[i][j].setBackground(defaultColor);
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
        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
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

        this.frame.remove(button_panel);
        this.frame.add(this.winPanel);
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
