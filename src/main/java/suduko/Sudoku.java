package suduko;

import java.util.*;


public class Sudoku {

    private int[][] puzzle;

    public Sudoku(int[][] puzzle) {
        this.puzzle = puzzle;
    }

    public boolean solve() {
        return solve(puzzle, 0, 0);
    }

    public int[][] get() {
        return puzzle;
    }

    private boolean solve(int[][] lastPuzzle, int cx, int cy) {

        // not valid return false
        if (isError(lastPuzzle))
            return false;

        if (cx>=9) {
            cx = 0;
            cy++;

            if (cy >= 9) {
                clonePuzzle(lastPuzzle,this.puzzle);
                return true;
            }
        }

        // Clone
        int localPuzzle[][] = new int[9][9];
        clonePuzzle(lastPuzzle,localPuzzle);

        if (localPuzzle[cy][cx] != 0) {
            return solve(localPuzzle, cx+1, cy);
        } else {
            for (int n = 1; n <= 9; n++) {

                // set next number
                localPuzzle[cy][cx] = n;

                // move next location
                if (solve(localPuzzle, cx+1, cy))
                    return true;
            }
            return false;
        }

        // Should never get here
    }


    private boolean isError(int[][] puzzle) {

        // row check
        for (int r=0; r<9; r++) {
            for (int n = 1; n <= 9; n++) {
                int counter=0;
                for (int c=0; c<9; c++) {
                    if (n==puzzle[r][c])
                        counter++;
                }
                if (counter > 1)
                    return true;
            }
        }
        // col check
        for (int c=0; c<9; c++) {
            for (int n = 1; n <= 9; n++) {
                int counter=0;
                for (int r=0; r<9; r++) {
                    if (n==puzzle[r][c])
                        counter++;
                }
                if (counter > 1)
                    return true;
            }
        }

        // seg check
        for (int sc=0; sc<3; sc++) {
            for (int sr=0; sr<3; sr++) {
                for (int n = 1; n <= 9; n++) {
                    int counter=0;
                    for (int c=0; c<3; c++) {
                        for (int r=0; r<3; r++) {
                            if (n==puzzle[(sr*3) + r][(sc*3) + c])
                                counter++;
                        }
                    }
                    if (counter > 1)
                        return true;
                }
            }
        }

        return false;
    }

    private void clonePuzzle(int[][] source, int[][] target) {
        for (int r=0; r<9; r++) {
            for (int c = 0; c < 9; c++) {
                target[r][c] = source[r][c];
            }
        }
    }

    public void printGrid() {

        System.out.println("+-----+-----+-----+");
        for (int y=0; y<9; y++) {

            System.out.print("|");
            for (int x=0; x<9; x++) {
                System.out.print(puzzle[y][x]);

                if ((x+1) % 3 == 0)
                    System.out.print("|");
                else
                    System.out.print(" ");


            }
            System.out.println();

            if ((y+1) % 3 ==0)
                System.out.println("+-----+-----+-----+");
        }
        System.out.println();
        System.out.flush();
    }

}






