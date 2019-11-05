public class VisibleFieldTester {
    public static void main(String[]  args){
        boolean [][] testArr1 = {{false, false, false, false},
                                {true, false, false, false},
                                {false, true, true, false},
                                {false, true, false, true}};

        boolean [][] testArr2 = {{false, false, false, false},
                                 {false, false, false, false},
                                 {false, false, false, false},
                                 {false, false, false, false}};

        boolean [][] testArr3 = {{false, false},
                                {false, false}};

        boolean [][] testArr4 = {{true, false},
                {false, false}};

        boolean [][] testArr5 = {{false, false, false},
                    {false, false, false},
                    {false, false, false}};

        boolean [][] testArr6 = {{false, false, false},
                {false, false, false},
                {false, false, true}};

        boolean [][] testArr7 = {{false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}};

        boolean [][] testArr = {{false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false}};

        MineField mineTest = new MineField(testArr);
        VisibleField test = new VisibleField(mineTest);
        test.printVisibleArr();
        mineTestPrint(mineTest);

        /*
        System.out.println("numMinesLeft : " + test.numMinesLeft());
        test.cycleGuess(0,0);
        test.printVisibleArr();
        System.out.println("numMinesLeft : " + test.numMinesLeft());
        System.out.println("isUncovered (0, 0) : " + test.isUncovered(0,0));
        System.out.println("-----------------------");

        System.out.println("Uncover (1, 1) :" + test.uncover(1, 1));
        test.printVisibleArr();
        System.out.println("isUncovered (1, 1) -> true : " + test.isUncovered(1,1));
        test.cycleGuess(1,1);
        System.out.println("cycleGuess (1, 1) -> -2 (MINE_GUESs)");
        test.printVisibleArr();
        System.out.println("-----------------------");

        System.out.println("Uncover (1, 0) -> true :" + test.uncover(1, 0));
        test.printVisibleArr();
        System.out.println("-----------------------");

        System.out.println("Uncover (3, 2) -> false :" + test.uncover(3, 2));
        test.printVisibleArr();
        System.out.println("-----------------------");
        */

//        System.out.println("Uncover (1, 1) :" + test.uncover(1, 1));
//        test.printVisibleArr();

        System.out.println("Uncover (1, 1) :" + test.uncover(1, 1));
        test.printVisibleArr();

        System.out.println("Uncover (0, 2) :" + test.uncover(0, 2));
        test.printVisibleArr();

    }

    private static void mineTestPrint(MineField test){

        test.printArr();
    /*    System.out.println("Rows: " + test.numRows());
        System.out.println("Cols: " + test.numCols());
        System.out.println("numMines : " + test.numMines());
*/
        for ( int i = 0; i < test.numRows(); i++)
        {
            for(int j = 0; j < test.numCols(); j++)
            {
                System.out.print(test.numAdjacentMines(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("-----------------------------------");
    }
}