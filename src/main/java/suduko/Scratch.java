package suduko;

import java.util.ArrayList;
import java.util.List;

public class Scratch {

//    private boolean processPathOnlySingle(List<IntPair> path) {
//
//        List<Integer> tallyList =  new ArrayList<>();
//
//        int[] tallyArray = new int[9];
//        for (int i=0; i<tallyArray.length; i++)
//            tallyArray[i] = 0;
//
//        for (IntPair p: path) {
//            for (int r : puzzle[p.x][p.y].getNumbersAvailable())
//                tallyArray[r-1]++;
//        }
//
//        boolean changed = false;
//
//        for (int i = 0; i<9; i++) {
//
//            if (tallyArray[i] == 1) {
//
//                for (IntPair p: path) {
//
//                    if (puzzle[p.x][p.y].isAvailable(i+1)) {
//
//                        if (puzzle[p.x][p.y].availableCount() > 1)
//                            changed = true;
//
//                        puzzle[p.x][p.y].removeAllExcept(i+1);
//                    }
//                }
//            }
//        }
//
//        return changed;
//    }
//
//    private boolean processReserveSingle() {
//
//        boolean changed = false;
//
//
//        int [] part = new int[3];
//
//
//
//        // segments
//        for (int y=0; y<GRID_SIZE; y=y+3) {
//            for (int x = 0; x < GRID_SIZE; x = x + 3) {
//
//                int[] candidates = new int[0];
//
//                for (int p = 0; p < part.length; p++)
//                    part[p] = 0;
//
//                // x's
//                for (int iy=0; iy<3; iy++ ) {
//                    for (int ix=0; ix<3; ix++) {
//
//                        if (puzzle[x+ix][y+iy].availableCount() > 1) {
//                            part[iy]++;
//                            candidates = puzzle[x+ix][y+iy].getNumbersAvailable();
//                        }
//                    }
//                }
//
//                int count = 0;
//                for (int p = 0; p < part.length; p++)
//                    if (part[p] > 0)
//                        count++;
//
//                if (count==1) {
//
//                    for (int p = 0; p < part.length; p++) {
//                        if (part[p] > 0 && part[p] == candidates.length) {
//
//                            for (int ix = 0; ix < 9; ix++ ) {
//
//                                if (ix < x || ix > x+3) {
//
//                                    for (int n = 0; n < candidates.length; n++) {
//                                        changed |= puzzle[ix][y + p].remove(candidates[n]);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                for (int p = 0; p < part.length; p++)
//                    part[p] = 0;
//
//                // y's
//                for (int ix=0; ix<3; ix++ ) {
//                    for (int iy=0; iy<3; iy++) {
//
//                        if (puzzle[x+ix][y+iy].availableCount() > 1) {
//                            part[ix]++;
//                            candidates = puzzle[x+ix][y+iy].getNumbersAvailable();
//                        }
//                    }
//                }
//
//                count = 0;
//                for (int p = 0; p < part.length; p++)
//                    if (part[p] > 0)
//                        count++;
//
//                if (count==1) {
//
//                    for (int p = 0; p < part.length; p++) {
//                        if (part[p] > 0 && part[p] == candidates.length) {
//
//                            for (int iy = 0; iy < 9; iy++ ) {
//
//                                if (iy < y || iy > y+3) {
//
//                                    for (int n = 0; n < candidates.length; n++) {
//                                        changed |= puzzle[x + p][iy].remove(candidates[n]);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//
//
//
//
//        return changed;
//
//    }

}
