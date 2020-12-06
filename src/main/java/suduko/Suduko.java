package suduko;

import java.util.ArrayList;
import java.util.List;


public class Suduko {

    public final static int GRID_SIZE = 9;


    List<List> rowColumnPaths  = new ArrayList<>();
    List<List> segmentPaths  = new ArrayList<>();



    SudukoNumber[][] puzzle = new SudukoNumber[9][9];

    public Suduko() {

        for (int x=0; x<GRID_SIZE; x++)
            for (int y=0; y<GRID_SIZE; y++)
               puzzle[x][y] = new SudukoNumber();


        // rows
        for (int y=0; y<GRID_SIZE; y++) {

            List<IntPair> path = new ArrayList();
            for (int x=0; x<GRID_SIZE; x++) {
                path.add(new IntPair(x,y));
            }
            rowColumnPaths.add(path);
        }

        // columns
        for (int x=0; x<GRID_SIZE; x++) {

            List<IntPair> path = new ArrayList();
            for (int y=0; y<GRID_SIZE; y++) {
                path.add(new IntPair(x,y));
            }
            rowColumnPaths.add(path);
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

            changed |= rowColumnPaths.stream().map(path -> processPathReducePossibilities(path)).reduce(changed, Boolean::logicalOr);
            changed |= segmentPaths.stream().map(path -> processPathReducePossibilities(path)).reduce(changed, Boolean::logicalOr);
            changed |= segmentPaths.stream().map(path -> processPathOnlySingle(path)).reduce(changed, Boolean::logicalOr);
            changed |= processReserveSingle();

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

    private boolean processPathOnlySingle(List<IntPair> path) {

        List<Integer> tallyList =  new ArrayList<>();

        int[] tallyArray = new int[9];
        for (int i=0; i<tallyArray.length; i++)
            tallyArray[i] = 0;

        for (IntPair p: path) {
            for (int r : puzzle[p.x][p.y].getNumbersAvailable())
                tallyArray[r-1]++;
        }

        boolean changed = false;

        for (int i = 0; i<9; i++) {

            if (tallyArray[i] == 1) {

                for (IntPair p: path) {

                    if (puzzle[p.x][p.y].isAvailable(i+1)) {

                        if (puzzle[p.x][p.y].availableCount() > 1)
                            changed = true;

                        puzzle[p.x][p.y].removeAllExcept(i+1);
                    }
                }
            }
        }

        return changed;
    }

    private boolean processReserveSingle() {

        boolean changed = false;


        int [] part = new int[3];



        // segments
        for (int y=0; y<GRID_SIZE; y=y+3) {
            for (int x = 0; x < GRID_SIZE; x = x + 3) {

                int[] candidates = new int[0];

                for (int p = 0; p < part.length; p++)
                    part[p] = 0;

                // x's
                for (int iy=0; iy<3; iy++ ) {
                    for (int ix=0; ix<3; ix++) {

                        if (puzzle[x+ix][y+iy].availableCount() > 1) {
                            part[iy]++;
                            candidates = puzzle[x+ix][y+iy].getNumbersAvailable();
                        }
                    }
                }

                int count = 0;
                for (int p = 0; p < part.length; p++)
                    if (part[p] > 0)
                        count++;

                if (count==1) {

                     for (int p = 0; p < part.length; p++) {
                         if (part[p] > 0 && part[p] == candidates.length) {

                             for (int ix = 0; ix < 9; ix++ ) {

                                if (ix < x || ix > x+3) {

                                    for (int n = 0; n < candidates.length; n++) {
                                        changed |= puzzle[ix][y + p].remove(candidates[n]);
                                    }
                                }
                             }
                         }
                     }
                 }

                for (int p = 0; p < part.length; p++)
                    part[p] = 0;

                // y's
                for (int ix=0; ix<3; ix++ ) {
                    for (int iy=0; iy<3; iy++) {

                        if (puzzle[x+ix][y+iy].availableCount() > 1) {
                            part[ix]++;
                            candidates = puzzle[x+ix][y+iy].getNumbersAvailable();
                        }
                    }
                }

                count = 0;
                for (int p = 0; p < part.length; p++)
                    if (part[p] > 0)
                        count++;

                if (count==1) {

                    for (int p = 0; p < part.length; p++) {
                        if (part[p] > 0 && part[p] == candidates.length) {

                            for (int iy = 0; iy < 9; iy++ ) {

                                if (iy < y || iy > y+3) {

                                    for (int n = 0; n < candidates.length; n++) {
                                        changed |= puzzle[x + p][iy].remove(candidates[n]);
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






