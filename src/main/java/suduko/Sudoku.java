package suduko;

import java.util.*;


public class Sudoku {


    private boolean isError(int[][] puzzle) {

        // row check
        for (int r=0; r<9; r++) {
            for (int n = 1; n <= 9; n++) {
                int counter=0;
                for (int c=0; c<9; c++) {
                    if (n==puzzle[c][r])
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
                    if (n==puzzle[c][r])
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
                        for (int r=0; r<3; c++) {
                            if (n==puzzle[(sc*3) + c][(sr*3) + r])
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


    private boolean solve(int[][] puzzle, int cx, int cy) {

        // not valid return false
        if (isError(puzzle))
            return false;

        if (puzzle[cx][cy] != 0) {

            // move next location
            cx++;
            if (cx==9) {
                cx=0;
                cy++;

                if (cy==9) {
                    return true;
                }
            }

            if (solve(puzzle, cx, cy))
                return true;

        } else {
            for (int n = 1; n <= 9; n++) {

                // set next number
                puzzle[cx][cy] = n;

                // move next location
                cx++;
                if (cx==9) {
                    cx=0;
                    cy++;

                    if (cy==9) {
                        return true;
                    }
                }

                if (solve(puzzle, cx, cy))
                    return true;

            }
        }

        return true;
    }


    public void printGrid(int[][] puzzle) {

        System.out.println("+-----+-----+-----+");
        for (int y=0; y<6; y++) {

            System.out.print("|");
            for (int x=0; x<9; x++) {
                System.out.print(puzzle[x][y]);

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






