public class Grid {
    private int gridCol;
    private int gridRow;
    private int numberOfBombs;
    private int[][] grid;

    public Grid() {
        this.gridRow = 16;
        this.gridCol = 16;
        this.numberOfBombs = 40;
        this.grid = new int[this.gridRow][this.gridCol];
        this.placeBombs();
    }

    public Grid(int row, int col, int bombs) {
        this.gridRow = row;
        this.gridCol = col;
        this.numberOfBombs = bombs;
        this.grid = new int[this.gridRow][this.gridCol];
        this.placeBombs();
    }

    private void placeBombs() {
        int bombs = this.numberOfBombs;
        while (bombs > 0) {
            int row = (int) (Math.random() * 100.0);
            int col = (int) (Math.random() * 100.0);

            row %= this.gridRow;
            col %= this.gridCol;

            if (this.grid[row][col] != 9) {
                this.grid[row][col] = 9;
                bombs--;
            }
        }

        for (int i = 0; i < this.gridRow; i++) {
            for (int j = 0; j < this.gridCol; j++) {
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

            if (col + 1 < this.gridCol) {
                if (this.grid[row - 1][col + 1] == 9) {
                    count++;
                }
            }
        }

        if (row + 1 < this.gridRow) {
            if (this.grid[row + 1][col] == 9) {
                count++;
            }

            if (col - 1 >= 0) {
                if (this.grid[row + 1][col - 1] == 9) {
                    count++;
                }
            }

            if (col + 1 < this.gridCol) {
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

        if (col + 1 < this.gridCol) {
            if (this.grid[row][col + 1] == 9) {
                count++;
            }
        }

        return count;
    }

    public int getValue(int i, int j) {
        return this.grid[i][j];
    }
}
