package suduko;

import java.util.*;


public class Sudoko {

    public final static int GRID_SIZE = 9;


    List<List<IntPair>> rowPaths = new ArrayList<>();
    List<List<IntPair>> colPaths = new ArrayList<>();
    List<List<IntPair>> segPaths = new ArrayList<>();

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
                segPaths.add(path);
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

            changed |= rowPaths.stream().map(path -> processPathReduce(path)).reduce(false, Boolean::logicalOr);
            changed |= colPaths.stream().map(path -> processPathReduce(path)).reduce(changed, Boolean::logicalOr);
            changed |= segPaths.stream().map(path -> processPathReduce(path)).reduce(changed, Boolean::logicalOr);

            changed |= segPaths.stream().map(path -> processPathOnlyOnePossiblePosition(path)).reduce(changed, Boolean::logicalOr);

            // for a segment are there lines

            // col 1
            // - is there a line of numbers
            // - yes - does it exist on the other cols
            // - no - we have a candidate


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

    private boolean processPathSegment() {

        for (int cx=0; cx<GRID_SIZE; cx+=3) {
            for (int cy=0; cy<GRID_SIZE; cy+=3) {


                for (int y=0; y <3; y++) {
                    int[] numbersAvailablePos1 = puzzle[cx][cy+y].getNumbersAvailable();
                    int[] numbersAvailablePos2 = puzzle[cx+1][cy+y].getNumbersAvailable();
                    int[] numbersAvailablePos3 = puzzle[cx+2][cy+y].getNumbersAvailable();

                    Set<Integer> dups = new HashSet();
                    for (int p1: numbersAvailablePos1) {
                        for (int p2: numbersAvailablePos2) {
                            for (int p3: numbersAvailablePos3) {
                                if (p1 == p2 || p1 == p3 )
                                    dups.add(new Integer(p1));
                                if (p2 == p1 || p2 == p3 )
                                    dups.add(new Integer(p2));
                                if (p3 == p1 || p3 == p2  )
                                    dups.add(new Integer(p3));
                            }
                        }
                    }

                    

                }

                for (int x=0; x <3; x++) {
                    int[] numbersAvailablePos1 = puzzle[cx+x][cy].getNumbersAvailable();
                    int[] numbersAvailablePos2 = puzzle[cx+x][cy+1].getNumbersAvailable();
                    int[] numbersAvailablePos3 = puzzle[cx+x][cy+2].getNumbersAvailable();

                    Set<Integer> dups = new HashSet();
                    for (int p1: numbersAvailablePos1) {
                        for (int p2: numbersAvailablePos2) {
                            for (int p3: numbersAvailablePos3) {
                                if (p1 == p2 || p1 == p3 )
                                    dups.add(new Integer(p1));
                                if (p2 == p1 || p2 == p3 )
                                    dups.add(new Integer(p2));
                                if (p3 == p1 || p3 == p2  )
                                    dups.add(new Integer(p3));
                            }
                        }
                    }



                }
            }
        }


        return false;
    }


    private int numberOccurancesInPath(int number, List<IntPair> path) {
        return (int) path.stream().filter( pair -> {
            return puzzle[pair.x][pair.y].isAvailable(number);
        }).count();
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






