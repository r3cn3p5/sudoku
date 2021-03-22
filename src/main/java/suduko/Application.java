package suduko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Application {


    public static void main(String[] args) {

        Sudoku s = new Sudoku();


        int puzzle[][] = new int[9][9];

        // Open stream
        Class clazz = Application.class;
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
                    int number = Integer.valueOf(line.substring(c,c+1));
                    if (number!=0)
                        puzzle[c][lineNumber] = number;
                }

                lineNumber++;

                if (lineNumber == 9) {
                    s.process();
                    if (!s)
                        s.printGrid();
                    System.out.println("Is this complete " + s.isComplete());

                }
            }
        } catch (IOException ex) {
            System.out.println("Ooops-"+ex.getMessage());
        }


    }

    private static void game1(Sudoku s) {
        s.setCell(3,1,2);
        s.setCell(3,2,1);
        s.setCell(4,1,9);
        s.setCell(5,0,6);
        s.setCell(5,1,8);

        s.setCell(6,2,5);
        s.setCell(8,0,4);

        s.setCell(0,5,2);
        s.setCell(1,3,6);
        s.setCell(1,4,9);
        s.setCell(1,5,1);
        s.setCell(2,3,3);

        s.setCell(6,3,1);
        s.setCell(6,4,8);
        s.setCell(8,4,5);
        s.setCell(8,5,7);

        s.setCell(0,8,5);
        s.setCell(2,6,9);

        s.setCell(3,6,5);
        s.setCell(4,6,8);
        s.setCell(4,8,2);
        s.setCell(5,8,7);

        s.setCell(7,8,4);
        s.setCell(8,7,6);
    }

    private static void game2(Sudoku s) {
        s.setCell(0,2,8);
        s.setCell(1,2,4);
        s.setCell(2,2,1);

        s.setCell(3,1,9);
        s.setCell(4,2,7);

        s.setCell(6,1,8);
        s.setCell(6,2,9);
        s.setCell(8,1,7);

        s.setCell(0,3,1);
        s.setCell(1,5,2);
        s.setCell(2,4,3);
        s.setCell(2,5,5);

        s.setCell(3,4,6);
        s.setCell(3,5,3);
        s.setCell(5,3,4);
        s.setCell(5,4,7);

        s.setCell(6,3,7);
        s.setCell(6,4,2);
        s.setCell(7,3,9);
        s.setCell(8,5,6);

        s.setCell(0,7,3);
        s.setCell(2,6,2);
        s.setCell(2,7,9);

        s.setCell(4,6,5);
        s.setCell(5,7,1);

        s.setCell(6,6,4);
        s.setCell(7,6,7);
        s.setCell(8,6,9);

    }
}
