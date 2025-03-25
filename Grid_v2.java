import java.util.Random;
import java.util.Scanner;

class Grid_v2 {
    private String[][] layout;
    private int size;
    private int currentLevel;
    // private final int MAX_LEVEL = 10;
    private Scanner scan = new Scanner(System.in);
    private Random random = new Random();

    public Grid_v2(int size) {
        this.size = size;
        this.layout = new String[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                this.layout[i][j] = " ";
            }
        }
        this.currentLevel = 5;
    }

    public void startGame() {
        String userInput = "y";
        do { 
            this.createPuzzle();
            this.displayGrid();
            
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (layout[i][j].equals(" ")) {
                        System.out.print("Enter value for row " + (i + 1) + " and column " + (j + 1) + " (1-" + size + "): ");
                        int value = scan.nextInt();
                        if (value >= 1 && value <= size) {
                            layout[i][j] = String.valueOf(value);
                        } else {
                            System.out.println("Invalid value! Must be between 1 and " + size);
                            j--;
                        }
                        this.displayGrid();
                    }
                }
            }
            
            if (isValidSudoku()) {
                System.out.println("Congratulations! You won!");
            } else {
                System.out.println("Sorry, you lost. The solution is not a valid Sudoku.");
            }
            
            System.out.print("Do you want to continue(y/n): ");
            scan.nextLine();
            userInput = scan.nextLine().toLowerCase();
        } while (userInput.equals("y"));
        scan.close();
    }

    public void createPuzzle() {
        generate_pattern();
        removeNumbers();
    }

    public void displayGrid() {
        int digits = calculateDigits();
        System.out.println("\nSudoku Puzzle:");
        for(int i = 0; i < 2*size+1; i++) {
            if(i % 2 == 0) {
                for(int j = 0; j < 2*size+1; j++) {
                    if(j % 2 == 0) {
                        System.out.print("+");
                    } else {
                        String s = "-";
                        System.out.print(s.repeat(digits + 2));
                    }
                }
            } else {
                for(int j = 0; j < 2*size+1; j++) {
                    if(j % 2 == 0) {
                        System.out.print("|");
                    } else {
                        int row = (i-1)/2;
                        int col = (j-1)/2;
                        String value = layout[row][col];
                        String ans = String.format("%" + digits + "s", value);
                        System.out.print(" " + ans + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    private void generate_pattern() {
        int[][] arr2 = new int[size][size];
        generate(arr2, 0, 0);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                layout[i][j] = String.valueOf(arr2[i][j]);
            }
        }
    }

    private boolean generate(int[][] arr2, int row, int col) {
        if (row == size) {
            return true;
        }
        if (col == size) {
            return generate(arr2, row + 1, 0);
        }

        for (int i = 0; i < size; i++) {
            int num = random.nextInt(size) + 1;
            if (isValid(arr2, num, row, col)) {
                arr2[row][col] = num;
                if (generate(arr2, row, col + 1)) {
                    return true;
                }
                arr2[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isValid(int[][] arr, int num, int row, int col) {
        for (int i = 0; i < size; i++) {
            if (arr[row][i] == num) {
                return false;
            }
        }
        for (int i = 0; i < size; i++) {
            if (arr[i][col] == num) {
                return false;
            }
        }
        return true;
    }

    private void removeNumbers() {
        int cellsToRemove = (size * size) / 3;
        boolean[][] removed = new boolean[size][size];
        
        for (int i = 0; i < cellsToRemove; ) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            if (!removed[row][col]) {
                layout[row][col] = " ";
                removed[row][col] = true;
                i++;
            }
        }
    }

    private int calculateDigits() {
        return String.valueOf(size).length();
    }

    private boolean isValidSudoku() {
        for (int i = 0; i < size; i++) {
            boolean[] rowCheck = new boolean[size];
            boolean[] colCheck = new boolean[size];
            for (int j = 0; j < size; j++) {
                int rowNum = Integer.parseInt(layout[i][j]);
                int colNum = Integer.parseInt(layout[j][i]);
                if (rowNum < 1 || rowNum > size || colNum < 1 || colNum > size) {
                    return false;
                }
                int rowIndex = rowNum - 1;
                int colIndex = colNum - 1;
                if (rowCheck[rowIndex] || colCheck[colIndex]) {
                    return false;
                }
                rowCheck[rowIndex] = true;
                colCheck[colIndex] = true;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Starting Sudoku Game...");
        Grid_v2 game = new Grid_v2(5);
        game.startGame();
        System.out.println("\nBye!! Bye!!\n");
    }
}