package suduko;

public class SudokoNumber {


    private boolean[] numberState = new boolean[9];


    public SudokoNumber() {

        for ( int i=0; i<numberState.length; i++)
            numberState[i] = true;


    }

    public boolean remove(int number) {


        if (numberState[number-1] == false)
            return false;

        numberState[number-1] = false;

        return true;
    }

    public void removeAllExcept(int number) {

        for ( int i=0; i<numberState.length; i++)
            numberState[i] = false;

        numberState[number-1] = true;
    }

    public boolean isAvailable(int number) {

        return numberState[number-1];
    }

    public int[] getNumbersAvailable() {

        int[] av_array = new int[availableCount()];
        int av_index = 0;

        for ( int i=0; i<numberState.length; i++) {

            if ( numberState[i] == true) {
                av_array[av_index] = i+1;
                av_index++;

            }
        }

        return av_array;
    }

    public int availableCount() {

        int count = 0;

        for ( int i=0; i<numberState.length; i++) {

            if ( numberState[i] == true)
                count++;

        }

        return count;
    }


    public int getSingle() {

        if (availableCount() == 1)
            return getNumbersAvailable()[0];


        return 0;

    }

    public boolean isSingle() {
        return availableCount()==1;
    }

    public String printSingle() {

        if (availableCount() == 1)
            return String.valueOf(getNumbersAvailable()[0]);


        return " ";

    }

}
