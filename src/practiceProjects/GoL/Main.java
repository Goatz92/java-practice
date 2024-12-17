package practiceProjects.GoL;

import java.util.Random;
import java.util.Scanner;

/**
 * Conway's Game of Life
 * In Java
 * Program outputs only on console in
 * Print blocks of stars and dots.
 * User inputs number of Rows, Columns, Generations and Max alive cells
 * for the first generation printed. Then applies the rules and continues
 * until maximum generations are printed.
 *
 */
public class GameOfLife {

    static Scanner in = new Scanner(System.in);
    static Random rand = new Random();

    public static void main(String[] args) {

        int rows = 0;
        int columns = 0;

        //User input for number of rows and columns of the grid
        System.out.println("Please input the number of Rows to be printed");
        rows = in.nextInt();
        System.out.println("Please input the number of Columns to be printed");
        columns = in.nextInt();

        //grid initialization
        int[][] grid = new int[rows][columns];
        
        //Randomizes cell quantity and position in the first grid generation
        initGridRandom(grid, rows, columns, rand);

        //Prints Current gen of grid gen < x times
        //Then generates nextGen of grid.
        System.out.println("Please input the number of Generations to be printed");
        int numberOfGenerations = in.nextInt();
        for (int gen = 0; gen < numberOfGenerations; gen++) {
            System.out.println("Generation: " + (gen + 1));
            printGrid(grid);
            nextGen(grid, rows, columns);
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
     *
     * @param grid The first and all the next updated version of the grid
     */
    public static void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] == 1 ? "* " : ". ");
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
     * @param grid The initial grid to be populated
     * @param rows The rows of the initial grid
     * @param columns The columns of the initial grid
     * @param rand Object of the Random class
     */

    public static void initGridRandom(int[][] grid, int rows, int columns, Random rand) {

        System.out.println("Please input the number of Max Alive Cells to be printed in the first generation");
        int maxAliveCells = in.nextInt();
        int aliveCount = 0;

        while (aliveCount != maxAliveCells) {
            int row = rand.nextInt(0, rows);
            int col = rand.nextInt(0, columns);
            if (grid[(row)][(col)] == 0) {
                grid[row][col] = 1;
                aliveCount++;
            }
        }
    }

    /**
     * Generates the next generation/state of the grid
     * Rules:
     * 1) Any live cell with fewer than two live neighbours dies, as if by underpopulation.
     * 2) Any live cell with two or three live neighbours lives on to the next generation.
     * 3) Any live cell with more than three live neighbours dies, as if by overpopulation.
     * 4) Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
     * Rule 1) and 2) are merged into one rule
     *
     * @param grid    The initial generation grid
     * @param rows    The rows of the initial grid (input by the user)
     * @param columns The columns of the initial grid (input by the user)
     */
    public static void nextGen(int[][] grid, int rows, int columns) {

        int aliveCount;
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