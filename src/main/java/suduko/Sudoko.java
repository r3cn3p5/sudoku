package suduko;

import java.util.ArrayList;
import java.util.List;


public class Sudoko {

    public final static int GRID_SIZE = 9;


    List<List<IntPair>> rowPaths = new ArrayList<>();
    List<List<IntPair>> colPaths = new ArrayList<>();
    List<List<IntPair>> segmentPaths  = new ArrayList<>();

    SudokoNumber[][] puzzle = new SudokoNumber[9][9];

    public Sudoko() {

        for (int x=0; x<GRID_SIZE; x++)
            for (int y=0; y<GRID_SIZE; y++)
               puzzle[x][y] = new SudokoNumber();


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
                segmentPaths.add(path);
            }
        }



    }

    public void setCell(int x, int y, int number) {

        puzzle[x][y].removeAllExcept(number);

    }


    public boolean process() {

        boolean changed = true;

        // for each paths
        while(changed) {

            changed = false;

            changed |= rowPaths.stream().map(path -> processPathReducePossibilities(path)).reduce(changed, Boolean::logicalOr);
            changed |= colPaths.stream().map(path -> processPathReducePossibilities(path)).reduce(changed, Boolean::logicalOr);
            changed |= segmentPaths.stream().map(path -> processPathReducePossibilities(path)).reduce(changed, Boolean::logicalOr);
        }

        return changed;
    }

    private boolean processPathReducePossibilities(List<IntPair> path) {

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

    public void printGrid() {



        for (int y=0; y<GRID_SIZE; y++) {
            for (int x=0; x<GRID_SIZE; x++)
                System.out.print(puzzle[x][y].printSingle() + " ");
            System.out.println();
        }
        System.out.println();
        System.out.flush();
    }

}






