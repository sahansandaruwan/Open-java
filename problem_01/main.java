import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== GRID SENSOR ANOMALY DETECTOR ===");

        // Step 1: Read and validate the grid
        int[][] grid = readAndValidateGrid(scanner);

        // Step 2: Find all local minima
        int[] minima = findLocalMinima(grid);

        // Step 3: Evaluate grid status
        String status = evaluateGridStatus(grid, minima);

        // Step 4: Display Results
        printResults(grid, minima, status);

        scanner.close();
    }


    public static int[][] readAndValidateGrid(Scanner scanner) {
        int rows = 0;
        int cols = 0;

        // Validation Loop: Keep prompting until valid inputs are given
        while (rows < 3 || cols < 3) {
            System.out.print("Enter grid rows (minimum 3): ");
            rows = scanner.nextInt();
            System.out.print("Enter grid columns (minimum 3): ");
            cols = scanner.nextInt();

            if (rows < 3 || cols < 3) {
                System.out.println(">> Error: Dimensions must be at least 3x3. Try again!\n");
            }
        }

        int[][] grid = new int[rows][cols];
        System.out.println("\nEnter sensor values (between -100 and 100):");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Sensor [" + i + "][" + j + "]: ");
                grid[i][j] = scanner.nextInt();
            }
        }

        return grid;
    }

    /**
     * Helper Method: Checks if a cell at (r, c) is strictly less than its existing neighbors.
     */
    public static boolean isLocalMinimum(int[][] grid, int r, int c) {
        int val = grid[r][c];
        int rows = grid.length;
        int cols = grid[0].length;

        // Check Up Neighbor (if it exists)
        if (r > 0 && val >= grid[r - 1][c]) {
            return false;
        }
        // Check Down Neighbor (if it exists)
        if (r < rows - 1 && val >= grid[r + 1][c]) {
            return false;
        }
        // Check Left Neighbor (if it exists)
        if (c > 0 && val >= grid[r][c - 1]) {
            return false;
        }
        // Check Right Neighbor (if it exists)
        if (c < cols - 1 && val >= grid[r][c + 1]) {
            return false;
        }

        return true; // If it was strictly smaller than all neighbors present
    }

    /**
     * Finds all local minima in the grid and returns them as a 1D array.
     */
    public static int[] findLocalMinima(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Pass 1: Count how many local minima exist
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isLocalMinimum(grid, i, j)) {
                    count++;
                }
            }
        }

        // Pass 2: Fill the array with the local minima values
        int[] minima = new int[count];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isLocalMinimum(grid, i, j)) {
                    minima[index] = grid[i][j];
                    index++;
                }
            }
        }

        return minima;
    }

    /**
     * Calculates grid average and determines status (CRITICAL, WARNING, STABLE).
     */
    public static String evaluateGridStatus(int[][] grid, int[] minima) {
        double sum = 0;
        int totalCells = grid.length * grid[0].length;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                sum += grid[i][j];
            }
        }

        double average = sum / totalCells;

        // Status Decision Logic
        if (minima.length > 3 && average < 0) {
            return "CRITICAL";
        } else if (minima.length > 0 || average < 20) {
            return "WARNING";
        } else {
            return "STABLE";
        }
    }

    /**
     * Formats and prints the final program report.
     */
    public static void printResults(int[][] grid, int[] minima, String status) {
        System.out.println("\n----------------------------------");
        System.out.println("         SYSTEM REPORT            ");
        System.out.println("----------------------------------");

        System.out.println("Sensor Grid Data:");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.printf("%5d ", grid[i][j]);
            }
            System.out.println();
        }

        System.out.print("\nDetected Local Minima: [ ");
        for (int i = 0; i < minima.length; i++) {
            System.out.print(minima[i] + (i < minima.length - 1 ? ", " : " "));
        }
        System.out.println("]");

        System.out.println("System Status: " + status);
        System.out.println("----------------------------------");
    }
}