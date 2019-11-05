public class MineFieldTester{
    public static void main(String[] args) {
        //MineFieldTester mine = new MineFieldTester();

        boolean [][] testArr =
                {{false, false, false, false},
                        {true, false, false, false},
                        {false, true, true, false},
                        {false, true, false, true}};

        MineField test1 = new MineField(testArr);
        testSequence(test1);

        boolean[][] smallMineField =
                {{false, false, false, false},
                        {true, false, false, false},
                        {false, true, true, false},
                        {false, true, false, true}};

        MineField test2 = new MineField(smallMineField);
        testSequence(test2);

        boolean[][] emptyMineField =
                {{false, false, false, false},
                        {false, false, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};

        MineField test3 = new MineField(emptyMineField);
        testSequence(test3);

        boolean[][] almostEmptyMineField =
                {{false, false, false, false},
                        {false, false, false, false},
                        {false, false, false, false},
                        {false, true, false, false}};

        MineField test4 = new MineField(almostEmptyMineField);
        testSequence(test4);

        MineField test5 = new MineField(4, 4, 5);
        testSequence(test5);
        test5.populateMineField(0, 0);
        testSequence(test5);

        MineField test6 = new MineField(4, 4, 5);
        testSequence(test6);
        test6.populateMineField(2, 2);
        testSequence(test6);
    }

    private static void testSequence(MineField test){
        test.printArr();
        System.out.println("Rows: " + test.numRows());
        System.out.println("Cols: " + test.numCols());
        System.out.println("numMines : " + test.numMines());

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
