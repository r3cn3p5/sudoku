package suduko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ApplicationEuler {


    public static void main(String[] args) {

        int puzzle[][] = new int[9][9];

        int sumOfPuzzles = 0;

        // Open stream
        Class clazz = ApplicationEuler.class;
        InputStream inputStream = clazz.getResourceAsStream("/p096_sudoku.txt");
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Grid")) {
                    System.out.println(line);
                    lineNumber = 0;
                    continue;
                }

                for (int c=0; c<9; c++) {
                    puzzle[lineNumber][c] = Integer.valueOf(line.substring(c,c+1));
                }

                lineNumber++;

                if (lineNumber == 9) {
                    Sudoku s = new Sudoku(puzzle);

                    if (s.solve()) {
                        int solvedPuzzle[][] = s.get();
                        sumOfPuzzles += solvedPuzzle[0][0] * 100;
                        sumOfPuzzles += solvedPuzzle[0][1] * 10;
                        sumOfPuzzles += solvedPuzzle[0][2];

                        s.printGrid();
                    }

                }
            }
        } catch (IOException ex) {
            System.out.println("Ooops-"+ex.getMessage());
        }

        System.out.println("Sum = " + sumOfPuzzles);

    }


}
