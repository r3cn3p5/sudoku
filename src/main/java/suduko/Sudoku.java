package suduko;

import java.util.*;


public class Sudoku {

    public final static int GRID_SIZE = 9;


    List<List<IntPair>> rowPaths = new ArrayList<>();
    List<List<IntPair>> colPaths = new ArrayList<>();
    List<List<IntPair>> segPaths = new ArrayList<>();

    SudokoNumber[][] puzzle = new SudokoNumber[9][9];

    public Sudoku() {

        reset();

        // rows
        for (int y=0; y<GRID_SIZE; y++) {

            List<IntPair> path = new ArrayList();
            for (int x=0; x<GRID_SIZE; x++) {
                path.add(new IntPair(x,y));
            }
            rowPaths.add(path);
        }

        // columns
        for (int x=0; x<GRID_SIZE; x++) {

            List<IntPair> path = new ArrayList();
            for (int y=0; y<GRID_SIZE; y++) {
                path.add(new IntPair(x,y));
            }
            colPaths.add(path);
        }

        // segments
        for (int y=0; y<GRID_SIZE; y=y+3) {
            for (int x = 0; x < GRID_SIZE; x = x + 3) {

                List<IntPair> path = new ArrayList();
                for (int ix = 0; ix < 3; ix++) {
                    for (int iy = 0; iy < 3; iy++) {
                        path.add(new IntPair(x+ix,y+iy));
                    }
                }
                segPaths.add(path);
            }
        }

    }

    public void reset() {
        for (int x=0; x<GRID_SIZE; x++)
            for (int y=0; y<GRID_SIZE; y++)
                puzzle[x][y] = new SudokoNumber();

    }

    public boolean isComplete() {
        for (int x=0; x<GRID_SIZE; x++)
            for (int y=0; y<GRID_SIZE; y++)
                 if (!puzzle[x][y].isSingle())
                     return false;

        return true;
    }

    public void setCell(int x, int y, int number) {
        puzzle[x][y].removeAllExcept(number);
    }


    public boolean process() {

        boolean changed = true;

        // for each paths
        while(changed) {

            changed = false;

            changed |= rowPaths.stream().map(path -> processPathReduce(path)).reduce(false, Boolean::logicalOr);
            changed |= colPaths.stream().map(path -> processPathReduce(path)).reduce(changed, Boolean::logicalOr);
            changed |= segPaths.stream().map(path -> processPathReduce(path)).reduce(changed, Boolean::logicalOr);

            changed |= segPaths.stream().map(path -> processPathOnlyOnePossiblePosition(path)).reduce(changed, Boolean::logicalOr);

            changed |= processRowOrColumnOnly();

        }

        return changed;
    }



    private boolean processPathReduce(List<IntPair> path) {

        return path.stream().map(pair -> {


            int number = puzzle[pair.x][pair.y].getSingle();

            if (number!=0) {

                return path.stream().map( innerPair -> {

                    boolean changed = false;
                    if (innerPair.x != pair.x || innerPair.y != pair.y) {

                        if (puzzle[innerPair.x][innerPair.y].isAvailable(number)) {
                            puzzle[innerPair.x][innerPair.y].remove(number);
                            changed = true;
                        }
                    }
                    return changed;
                }).reduce(Boolean::logicalOr ).get();
            }

            return false;

        }).reduce(Boolean::logicalOr ).get();

    }

    private boolean processPathOnlyOnePossiblePosition(List<IntPair> path) {

        return path.stream().map(pair -> {
            int numbersAvailable[] = puzzle[pair.x][pair.y].getNumbersAvailable();

            // for each number
            if (numbersAvailable.length > 1) {
                for (int i = 0; i < numbersAvailable.length; i++) {

                    if (numberOccurancesInPath(numbersAvailable[i], path) == 1) {
                        puzzle[pair.x][pair.y].removeAllExcept(numbersAvailable[i]);
                        return true;
                    }
                }
            }
            return false;
        }).reduce(Boolean::logicalOr ).get();

    }

    private boolean processRowOrColumnOnly() {

        boolean changed = false;
        for (int n=1; n<=9; n++) {

            for (int xs=0; xs < 3; xs++) {
                for (int ys=0; ys < 3; ys++) {

                    for (int r = 0; r < 3; r++) {

                        if (existsOnRow(xs,ys,r,n)) {

                            boolean candidate = true;
                            for (int ri = 0; ri < 3; ri++) {
                                if (ri != r && existsOnRow(xs,ys,ri,n))
                                    candidate = false;
                            }

                            if (candidate) {
                                //
                                for (int xsi=0; xsi <3; xsi++) {
                                    if (xs != xsi) {
                                        changed |= removeNumberOnRow(xsi,ys,r,n);
                                    }
                                }
                            }
                        }
                    }

                    for (int c = 0; c < 3; c++) {

                        if (existsOnRow(xs,ys,c,n)) {

                            boolean candidate = true;
                            for (int ci = 0; ci < 3; ci++) {
                                if (ci != c && existsOnColumn(xs,ys,ci,n))
                                    candidate = false;
                            }

                            if (candidate) {
                                //
                                for (int ysi=0; ysi <3; ysi++) {
                                    if (ys != ysi) {
                                        changed |= removeNumberOnColumn(xs,ysi,c,n);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        return changed;
    }


    private int numberOccurancesInPath(int number, List<IntPair> path) {
        return (int) path.stream().filter( pair -> {
            return puzzle[pair.x][pair.y].isAvailable(number);
        }).count();
    }

    private boolean existsOnRow(int segx, int segy, int row, int number ) {
        for (int c = 0; c < 3; c++) {
            if (puzzle[(segx * 3) + c][(segy * 3) + row].isAvailable(number))
                return true;
        }

        return false;
    }

    private boolean removeNumberOnRow(int segx, int segy, int row, int number ) {
        boolean changed = false;
        for (int c = 0; c < 3; c++) {
            changed |= puzzle[(segx * 3) + c][(segy * 3) + row].remove(number);
        }
        return changed;
    }

    private boolean existsOnColumn(int segx, int segy, int column, int number ) {
        for (int r = 0; r < 3; r++) {
            if (puzzle[(segx * 3) + column][(segy * 3) + r].isAvailable(number))
                return true;
        }

        return false;
    }

    private boolean removeNumberOnColumn(int segx, int segy, int column, int number ) {
        boolean changed = false;
        for (int r = 0; r < 3; r++) {
            changed |= puzzle[(segx * 3) + column][(segy * 3) + r].remove(number);
        }
        return changed;
    }

    public void printGrid() {

        System.out.println("+-----+-----+-----+");
        for (int y=0; y<GRID_SIZE; y++) {

            System.out.print("|");
            for (int x=0; x<GRID_SIZE; x++) {
                System.out.print(puzzle[x][y].printSingle());

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






