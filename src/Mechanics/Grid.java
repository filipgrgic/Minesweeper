package Mechanics;

public class Grid {
    final int gridCol = 16;
    final int gridRow = 16;
    final int numberOfBombs = 40;
    private int[][] grid;

    public static void main(String[] args) {
        Grid test = new Grid();
        test.placeBombs();
        System.out.println(test);
    }

    public Grid() {
        this.grid = new int[gridRow][gridCol];
    }

    private void placeBombs() {
        int bombs = numberOfBombs;
        while (bombs > 0) {
            int row = (int) (Math.random() * 100.0);
            int col = (int) (Math.random() * 100.0);

            row %= gridRow;
            col %= gridCol;

            if (this.grid[row][col] != 9) {
                this.grid[row][col] = 9;
                bombs--;
            }
        }

        for (int i = 0; i < gridRow; i++) {
            for (int j = 0; j < gridCol; j++) {
                if (this.grid[i][j] != 9) {
                    this.grid[i][j] = this.countNeighbours(i, j);
                }
            }
        }
    }

    private int countNeighbours(int row, int col) {
        int count = 0;

        if (row - 1 >= 0) {
            if (this.grid[row - 1][col] == 9) {
                count++;
            }

            if (col - 1 >= 0) {
                if (this.grid[row - 1][col - 1] == 9) {
                    count++;
                }
            }

            if (col + 1 < gridCol) {
                if (this.grid[row - 1][col + 1] == 9) {
                    count++;
                }
            }
        }

        if (row + 1 < gridRow) {
            if (this.grid[row + 1][col] == 9) {
                count++;
            }

            if (col - 1 >= 0) {
                if (this.grid[row + 1][col - 1] == 9) {
                    count++;
                }
            }

            if (col + 1 < gridCol) {
                if (this.grid[row + 1][col + 1] == 9) {
                    count++;
                }
            }
        }

        if (col - 1 >= 0) {
            if (this.grid[row][col - 1] == 9) {
                count++;
            }
        }

        if (col + 1 < gridCol) {
            if (this.grid[row][col + 1] == 9) {
                count++;
            }
        }

        return count;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < gridRow; i++) {
            for (int j = 0; j < gridCol; j++) {
                s.append(this.grid[i][j]).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
