package suduko;

import java.util.*;


public class Sudoku {


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


    public boolean solve(int[][] lastPuzzle, int cx, int cy) {

        int puzzle[][] = new int[9][9];
        for (int r=0; r<9; r++) {
            for (int c=0; c<9; c++) {
                puzzle[r][c] = lastPuzzle[r][c];
            }

        }

        if (cx>=9) {
            cx = 0;
            cy++;

            if (cy >= 9) {
                printGrid(puzzle);
                return true;
            }
        }

            // not valid return false
        if (isError(puzzle))
            return false;

        if (puzzle[cy][cx] != 0) {
            return solve(puzzle, cx+1, cy);
        } else {
            for (int n = 1; n <= 9; n++) {

                // set next number
                puzzle[cy][cx] = n;

                // move next location
                if (solve(puzzle, cx+1, cy)) {
                    return true;
                }

            }
            return false;
        }

        // Should never get here
    }


    public void printGrid(int[][] puzzle) {

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






