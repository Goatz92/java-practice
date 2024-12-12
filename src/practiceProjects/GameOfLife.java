package practiceProjects;

import java.util.Random;

/**
 * Attempt to making Conway's Game of Life
 * In Java
 * Program outputs only on console in
 * print blocks of stars and dots
 *
 */
public class GameOfLife {

    public static void main(String[] args) {

        final int ROWS = 10;
        final int COLUMNS = 10;

//      Init a grid. Dimensions can be changed by changing values of final int ROWS/COLUMNS
        int[][] grid = new int[ROWS][COLUMNS];
        //Randomizing grid's contents
        Random rand = new Random();

        initGridRandom(grid, ROWS, COLUMNS, rand);

        //Prints Current gen of grid gen < x times
        //Then generates nextGen of grid
        for (int gen = 0; gen < 50; gen++) {
            System.out.println("Generation: " + (gen + 1));
            printGrid(grid);
            nextGen(grid, ROWS, COLUMNS);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prints the grid in "*" and "." format
     * Used for 1st and all concurrent Generations
     * @param grid The first or the last updated version of the grid
     */
    public static void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] == 1 ? "*" : ".");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Initializes the grid by randomly setting the values of each cell
     * to 1 or 0.
     * Has a limit to how many cells can be alive in the grid
     * at first initialization equal to the MAX_ALIVE_CELLS final int
     *
     * @param grid The initial grid to be populated
     * @param rows The rows of the initial grid
     * @param columns The columns of the initial grid
     * @param rand Object of the Random class
     * @return Returns the randomly filled grid
     */

    /*
    TODO The method now populates the grid in a linear way. This means the result can be that the first 3 rows are
         randomly populated and the rest empty (especially when grid has large dimensions)
         -Find a way to randomly populate the grid in it's entirety
     */
    public static int[][] initGridRandom(int[][] grid, int rows, int columns, Random rand) {
        // Calculate the maximum number of alive cells (half the grid size)
        final int MAX_ALIVE_CELLS = 30;
        int aliveCount = 0;

        // Randomly populate the grid (randomly set cells to 1 or 0)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // Only set cell to alive (1) if we haven't reached the max limit
                if (aliveCount < MAX_ALIVE_CELLS && rand.nextBoolean()) {
                    grid[i][j] = 1;
                    aliveCount++;  // Increment the alive cell counter
                } else {
                    grid[i][j] = 0;
                }
            }
        }
        return grid;
    }

    /**
     * Generates the next generation/state of the grid
     * Rules:
     * 1) Any live cell with fewer than two live neighbours dies, as if by underpopulation.
     * 2) Any live cell with two or three live neighbours lives on to the next generation.
     * 3) Any live cell with more than three live neighbours dies, as if by overpopulation.
     * 4) Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
     *
     * @param grid    The initial generation grid
     * @param rows    The rows of the initial grid
     * @param columns The columns of the initial grid
     */
    public static void nextGen(int[][] grid, int rows, int columns) {

        int aliveCount = 0;
        int[][] nextState = new int[rows][columns];

        //Grid traversal through every cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                aliveCount = 0;
                //Checks how many alive neighbours the cell has
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if ((i + k >= 0 && i + k < rows) && (j + l >= 0 && j + l < columns)) {
                            aliveCount += grid[i + k][j + l];
                        }
                    }
                }
                //The loop counts the initial cell itself
                //So we have to subtract it from aliveCount
                //todo: Find a way to not count the initial cell
                aliveCount -= grid[i][j];

                //Apply Game of life Rules

                //Cell has less than two neighbours and dies
                if (grid[i][j] == 1) {
                    //Rule 1: Cell dies due to underpopulation or overpopulation
                    if (aliveCount < 2 || aliveCount > 3) {
                        nextState[i][j] = 0;
                        //Cell stays alive because it has exactly 3 neighbours
                    } else {
                        nextState[i][j] = 1;
                    }
                } else {
                    //Rule 2: Dead cell is born if it has exactly 3 live neighbors
                    if (aliveCount == 3) {
                        nextState[i][j] = 1;
                    }
                }
            }
        }
        //Update grid state
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = nextState[i][j];
            }
        }
    }
}

/*

*/
