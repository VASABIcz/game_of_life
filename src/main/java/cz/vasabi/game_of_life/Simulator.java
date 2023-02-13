package cz.vasabi.game_of_life;

public class Simulator {
    boolean[][] grid;

    Simulator(int width, int height) {
        grid = new boolean[height][width];
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    public boolean[][] getSurroundingPixels(int x, int y) {
        var buf = new boolean[3][3];

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                var indexX = x + i;
                var indexY = y + j;

                if (indexX < 0) {
                    indexX = getWidth()-1;
                }
                else if (indexX >= getWidth()-1) {
                    indexX = 0;
                }

                if (indexY < 0) {
                    indexY = getHeight()-1;
                }
                else if (indexY >= getHeight()-1) {
                    indexY = 0;
                }

                buf[i+1][j+1] = grid[indexX][indexY];
            }
        }

        return buf;
    }


    static int[][] indexes = {{0,0}, {0,1}, {0,2}, {1, 0}, {1, 2}, {2, 0}, {2, 1}, {2, 2}};

    public void simulate() {
        var newFrame = new boolean[getWidth()][getHeight()];

        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                var res = getSurroundingPixels(i, j);

                var isAlive = res[1][1];
                var aliveNeigbors = 0;

                for (var index : indexes) {
                    if (res[index[0]][index[1]]) {
                        aliveNeigbors += 1;
                    }
                }

                if (!isAlive) {
                    if (aliveNeigbors == 3) {
                        newFrame[i][j] = true;
                    }
                    else {
                        newFrame[i][j] = grid[i][j];
                    }
                }
                else {
                    switch (aliveNeigbors) {
                        case 0, 1, 4, 5, 6, 7, 8 -> newFrame[i][j] = false;
                        case 2, 3 -> newFrame[i][j] = grid[i][j];
                    }
                }
            }
        }
        grid = newFrame;
    }

    public void printBoard() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                var value = grid[i][j];

                System.out.print(value ? 'X' : ' ');
            }
            System.out.println();
        }
    }
}
