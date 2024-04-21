import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel implements ActionListener {
    //SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 16;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    final Color revealedBackground = new Color(200, 200, 200);
    final Color bombedBackground = new Color(200, 50, 50);

    private JFrame frame = new JFrame();
    private JPanel button_panel = new JPanel();
    private JButton[][] buttons = new JButton[maxScreenRow][maxScreenCol];
    private Grid grid = new Grid();

    public GamePanel() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setResizable(false);
        frame.setTitle("Minesweeper by pilp");
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        button_panel.setLayout(new GridLayout(maxScreenRow, maxScreenCol));
        button_panel.setBackground(new Color(0, 0, 0));

        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
                buttons[i][j] = new JButton();
                button_panel.add(buttons[i][j]);
                buttons[i][j].setFont(new Font("Calibri", Font.BOLD, tileSize / 2));
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(this);
                buttons[i][j].setBackground(new Color(150, 150, 150));
            }
        }
        frame.add(button_panel);
    }

    private void revealBorders(int row, int col) {
        int value = this.grid.getValue(row, col);
        buttons[row][col].setText("" + value);
        buttons[row][col].setBackground(revealedBackground);
        if (value != 0) {
            return;
        }

        if (row - 1 >= 0) {
            if (buttons[row - 1][col].getText().isEmpty()) revealBorders(row - 1, col);

            if (col - 1 >= 0) {
                if (buttons[row - 1][col - 1].getText().isEmpty()) revealBorders(row - 1, col - 1);
            }

            if (col + 1 < maxScreenCol) {
                if (buttons[row - 1][col + 1].getText().isEmpty()) revealBorders(row - 1, col + 1);
            }
        }

        if (row + 1 < maxScreenRow) {
            if (buttons[row + 1][col].getText().isEmpty()) revealBorders(row + 1, col);

            if (col - 1 >= 0) {
                if (buttons[row + 1][col - 1].getText().isEmpty()) revealBorders(row + 1, col - 1);
            }

            if (col + 1 < maxScreenCol) {
                if (buttons[row + 1][col + 1].getText().isEmpty()) revealBorders(row + 1, col + 1);
            }
        }

        if (col - 1 >= 0) {
            if (buttons[row][col - 1].getText().isEmpty()) revealBorders(row, col - 1);
        }

        if (col + 1 < maxScreenCol) {
            if (buttons[row][col + 1].getText().isEmpty()) revealBorders(row, col + 1);
        }
    }

    private void revealAll() {
        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    int value = this.grid.getValue(i, j);
                    if (value == 9) {
                        buttons[i][j].setText("B");
                        buttons[i][j].setBackground(bombedBackground);
                    } else {
                        buttons[i][j].setText("" + value);
                        buttons[i][j].setBackground(revealedBackground);
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
                if (e.getSource() == buttons[i][j]) {
                    if (buttons[i][j].getText().isEmpty()) {
                        int value = this.grid.getValue(i, j);
                        if (value == 9) {
                            this.revealAll();
                        } else if (value == 0) {
                            this.revealBorders(i, j);
                        } else {
                            buttons[i][j].setText("" + value);
                            buttons[i][j].setBackground(revealedBackground);
                        }
                    }
                }
            }
        }
    }
}
