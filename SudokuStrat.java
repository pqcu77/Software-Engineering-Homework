import java.util.*;

public class SudokuStrat {
    public static List<Integer>[][] LRC(int[][] grid) {
        List<Integer>[][] cands = possibleNumbers(grid);
        for (int n = 1; n <= 9; n++) {
            // Check rows
            for (int r = 0; r < 9; r++) {
                List<int[]> cells = new ArrayList<>();
                for (int c = 0; c < 9; c++) {
                    if (grid[r][c] == 0 && cands[r][c].contains(n)) {
                        cells.add(new int[]{r, c});
                    }
                }
                // If only one cell in this row can contain this number
                if (cells.size() == 1) {
                    int row = cells.get(0)[0];
                    int col = cells.get(0)[1];
                    cands[row][col] = new ArrayList<>(Collections.singletonList(n));
                }
            }

            // Check columns
            for (int c = 0; c < 9; c++) {
                List<int[]> cells = new ArrayList<>();
                for (int r = 0; r < 9; r++) {
                    if (grid[r][c] == 0 && cands[r][c].contains(n)) {
                        cells.add(new int[]{r, c});
                    }
                }
                if (cells.size() == 1) {
                    int row = cells.get(0)[0];
                    int col = cells.get(0)[1];
                    cands[row][col] = new ArrayList<>(Collections.singletonList(n));
                }
            }

            // Check blocks
            for (int br = 0; br < 3; br++) {
                for (int bc = 0; bc < 3; bc++) {
                    List<int[]> cells = new ArrayList<>();
                    for (int r = 0; r < 3; r++) {
                        for (int c = 0; c < 3; c++) {
                            int row = br * 3 + r;
                            int col = bc * 3 + c;
                            if (grid[row][col] == 0 && cands[row][col].contains(n)) {
                                cells.add(new int[]{row, col});
                            }
                        }
                    }
                    if (cells.size() == 1) {
                        int row = cells.get(0)[0];
                        int col = cells.get(0)[1];
                        cands[row][col] = new ArrayList<>(Collections.singletonList(n));
                    }
                }
            }
        }
        return cands;
    }

    public static List<Integer>[][] possibleNumbers(int[][] grid) {
        List<Integer>[][] cands = new List[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] != 0) {
                    cands[r][c] = new ArrayList<>(Collections.singletonList(grid[r][c]));
                } else {
                    cands[r][c] = new ArrayList<>();
                    for (int n = 1; n <= 9; n++) {
                        cands[r][c].add(n);
                    }

                    for (int i = 0; i < 9; i++) {
                        if (grid[r][i] != 0) {
                            cands[r][c].remove(Integer.valueOf(grid[r][i]));
                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        if (grid[i][c] != 0) {
                            cands[r][c].remove(Integer.valueOf(grid[i][c]));
                        }
                    }
                    int br = (r / 3) * 3;
                    int bc = (c / 3) * 3;
                    for (int i = br; i < br + 3; i++) {
                        for (int j = bc; j < bc + 3; j++) {
                            if (grid[i][j] != 0) {
                                cands[r][c].remove(Integer.valueOf(grid[i][j]));
                            }
                        }
                    }
                }
            }
        }
        return cands;
    }

    public static void main(String[] args) {
        // 例子：
        int[][] grid = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        System.out.println("Possible Number Inference:");
        List<Integer>[][] cands = possibleNumbers(grid);
        print(cands);
        System.out.println("After LRC:");
        List<Integer>[][] cands2 = LRC(grid);
        print(cands2);
    }

    public static void print(List<Integer>[][] cands) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print("Cell[" + r + "][" + c + "]: ");
                System.out.println(cands[r][c]);
            }
        }
    }

    
}