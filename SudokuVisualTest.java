import java.util.*;

public class SudokuVisualTest {
    public static void main(String[] args) {
        // 测试各种数独谜题
        testVisual(createEasyPuzzle(), "Easy Puzzle");
        testVisual(createMediumPuzzle(), "Medium Puzzle");
        testVisual(createHardPuzzle(), "Hard Puzzle");
        testVisual(createExtremelyHardPuzzle(), "Extremely Hard Puzzle");
    }
    
    private static void testVisual(int[][] grid, String testName) {
        System.out.println("===== " + testName + " =====");
        
        // 打印原始网格
        System.out.println("Original Grid:");
        printGrid(grid);
        
        // 从Possible Number获取候选值
        List<Integer>[][] possibleCands = SudokuStrat.possibleNumbers(grid);
        
        // 从Last Remaining Cell获取候选值
        List<Integer>[][] lastCands = SudokuStrat.LRC(grid);
        
        // 统计每种方法解决的单元格数量
        int possibleSolved = countSolved(grid, possibleCands);
        int lastSolved = countSolved(grid, lastCands);
        
        System.out.println("\nPossible Number solved " + possibleSolved + " cells.");
        System.out.println("Last Remaining Cell solved " + lastSolved + " additional cells.");
        
        // 打印显示已解决单元格的网格
        System.out.println("\nAfter applying strategies (solved cells marked):");
        printSolvedGrid(grid, lastCands);
        
        // 打印所有未解决单元格及其候选值
        System.out.println("\nComplete list of cell candidates:");
        printAllCandidates(grid, lastCands);
        
        System.out.println("\n");
    }
    
    private static void printGrid(int[][] grid) {
        System.out.println("┌───────┬───────┬───────┐");
        for (int i = 0; i < 9; i++) {
            System.out.print("│ ");
            for (int j = 0; j < 9; j++) {
                System.out.print(grid[i][j] == 0 ? "." : grid[i][j]);
                if (j % 3 == 2) System.out.print(" │ ");
                else System.out.print(" ");
            }
            System.out.println();
            if (i % 3 == 2 && i < 8) 
                System.out.println("├───────┼───────┼───────┤");
        }
        System.out.println("└───────┴───────┴───────┘");
    }
    
    private static void printSolvedGrid(int[][] grid, List<Integer>[][] cands) {
        System.out.println("┌───────┬───────┬───────┐");
        for (int i = 0; i < 9; i++) {
            System.out.print("│ ");
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    System.out.print(grid[i][j] + " ");
                } else if (cands[i][j].size() == 1) {
                    // 使用固定宽度显示已解决的单元格
                    System.out.print("*" + cands[i][j].get(0) + "");
                } else {
                    System.out.print(". ");
                }
                
                if (j % 3 == 2) System.out.print("│ ");
            }
            System.out.println();
            if (i % 3 == 2 && i < 8) 
                System.out.println("├───────┼───────┼───────┤");
        }
        System.out.println("└───────┴───────┴───────┘");
        System.out.println("注: *表示策略推断出的数字");
    }
    
    // 新方法：打印所有未解决单元格的候选值
    private static void printAllCandidates(int[][] grid, List<Integer>[][] cands) {
        int count = 0;
        
        // 按行列顺序打印每个未解决单元格的候选值
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // 只打印未填充且有多个候选值的单元格
                if (grid[i][j] == 0 && cands[i][j].size() > 1) {
                    System.out.println("Cell[" + i + "][" + j + "]: " + cands[i][j]);
                    count++;
                }
            }
        }
        
        if (count == 0) {
            System.out.println("没有未解决的单元格或所有单元格都已确定唯一值。");
        } else {
            System.out.println("总共有 " + count + " 个未解决的单元格。");
        }
    }
    
    // 此方法保留用于打印样本（前5个）候选值，如果需要的话
    private static void printSampleCandidates(int[][] grid, List<Integer>[][] cands) {
        // 打印一些有趣单元格的候选值
        int count = 0;
        for (int i = 0; i < 9 && count < 5; i++) {
            for (int j = 0; j < 9 && count < 5; j++) {
                if (grid[i][j] == 0 && cands[i][j].size() > 1) {
                    System.out.println("Cell[" + i + "][" + j + "]: " + cands[i][j]);
                    count++;
                }
            }
        }
    }
    
    private static int countSolved(int[][] grid, List<Integer>[][] cands) {
        int solved = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0 && cands[i][j].size() == 1) {
                    solved++;
                }
            }
        }
        return solved;
    }
    
    // 测试案例生成器
    private static int[][] createEasyPuzzle() {
        return new int[][] {
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
    }
    
    private static int[][] createMediumPuzzle() {
        return new int[][] {
            {0, 2, 0, 6, 0, 8, 0, 0, 0},
            {5, 8, 0, 0, 0, 9, 7, 0, 0},
            {0, 0, 0, 0, 4, 0, 0, 0, 0},
            {3, 7, 0, 0, 0, 0, 5, 0, 0},
            {6, 0, 0, 0, 0, 0, 0, 0, 4},
            {0, 0, 8, 0, 0, 0, 0, 1, 3},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 0, 9, 8, 0, 0, 0, 3, 6},
            {0, 0, 0, 3, 0, 6, 0, 9, 0}
        };
    }
    
    private static int[][] createHardPuzzle() {
        return new int[][] {
            {8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 6, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 9, 0, 2, 0, 0},
            {0, 5, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 4, 5, 7, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 3, 0},
            {0, 0, 1, 0, 0, 0, 0, 6, 8},
            {0, 0, 8, 5, 0, 0, 0, 1, 0},
            {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
    }
    
    private static int[][] createExtremelyHardPuzzle() {
        return new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 3, 0, 8, 5},
            {0, 0, 1, 0, 2, 0, 0, 0, 0},
            {0, 0, 0, 5, 0, 7, 0, 0, 0},
            {0, 0, 4, 0, 0, 0, 1, 0, 0},
            {0, 9, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 7, 3},
            {0, 0, 2, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 4, 0, 0, 0, 9}
        };
    }
}