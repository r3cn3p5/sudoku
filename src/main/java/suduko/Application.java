package suduko;

public class Application {


    public static void main(String[] args) {

        Suduko s = new Suduko();


//        s.setCell(0,2,8);
//        s.setCell(1,2,4);
//        s.setCell(2,2,1);
//
//        s.setCell(3,1,9);
//        s.setCell(4,2,7);
//
//        s.setCell(6,1,8);
//        s.setCell(6,2,9);
//        s.setCell(8,1,7);
//
//        s.setCell(0,3,1);
//        s.setCell(1,5,2);
//        s.setCell(2,4,3);
//        s.setCell(2,5,5);
//
//        s.setCell(3,4,6);
//        s.setCell(3,5,3);
//        s.setCell(5,3,4);
//        s.setCell(5,4,7);
//
//        s.setCell(6,3,7);
//        s.setCell(6,4,2);
//        s.setCell(7,3,9);
//        s.setCell(8,5,6);
//
//        s.setCell(0,7,3);
//        s.setCell(2,6,2);
//        s.setCell(2,7,9);
//
//        s.setCell(4,6,5);
//        s.setCell(5,7,1);
//
//        s.setCell(6,6,4);
//        s.setCell(7,6,7);
//        s.setCell(8,6,9);

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


        s.printGrid();

        s.process();

        s.printGrid();

    }


}
