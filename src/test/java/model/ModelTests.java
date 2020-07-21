package model;

import model.enums.Difficulty;

import model.enums.TileState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTests {

    @Test
    @DisplayName("10 mines on an easy-sized minefield:")
    void testCorrectNumberOfMinesEasy() {
        Model easyModel = new Model(Difficulty.EASY);

        int bombCounter = 0;
        Tile[][] twoDarray = easyModel.getTileArray();
        for (Tile[] oneDarray : twoDarray) {
            for (Tile t : oneDarray) {
                if (t.getState() == TileState.MINE) {
                    bombCounter++;
                }
            }

        }
        assertEquals(10, bombCounter);
    }

    @Test
    @DisplayName("40 mines on an normal-sized minefield:")
    void testCorrectNumberOfMinesNormal() {
        Model easyModel = new Model(Difficulty.NORMAL);

        int bombCounter = 0;
        Tile[][] twoDarray = easyModel.getTileArray();
        for (Tile[] oneDarray : twoDarray) {
            for (Tile t : oneDarray) {
                if (t.getState() == TileState.MINE) {
                    bombCounter++;
                }
            }

        }
        assertEquals(40, bombCounter);
    }

    @Test
    @DisplayName("99 mines on an hard-sized minefield:")
    void testCorrectNumberOfMinesHard() {
        Model easyModel = new Model(Difficulty.HARD);

        int bombCounter = 0;
        Tile[][] twoDarray = easyModel.getTileArray();
        for (Tile[] oneDarray : twoDarray) {
            for (Tile t : oneDarray) {
                if (t.getState() == TileState.MINE) {
                    bombCounter++;
                }
            }

        }
        assertEquals(99, bombCounter);
    }

    @Test
    @DisplayName("Test multiple different scenarios when setting flags")
    void testFlaggingScenarios() {
        Model easyModel = new Model(Difficulty.EASY);
        easyModel.touch();
        Tile[][] tileArray = easyModel.getTileArray();
        for (int i = 0; i < tileArray.length; i++) {
            for (int j = 0; j < tileArray[0].length; j++) {
                if (tileArray[i][j].getState() == TileState.MINE) {
                    easyModel.flagTile(i, j);
                    assertEquals(TileState.FLAGGED_MINE, tileArray[i][j].getState());
                    easyModel.flagTile(i, j);
                    assertEquals(TileState.QMARKED_MINE, tileArray[i][j].getState());
                    easyModel.sweepTile(i, j, false);
                    assertEquals(TileState.QMARKED_MINE, tileArray[i][j].getState());
                    easyModel.flagTile(i, j);
                    assertEquals(TileState.MINE, tileArray[i][j].getState());
                }
            }
        }
        for (int i = 0; i < tileArray.length; i++) {
            for (int j = 0; j < tileArray[0].length; j++) {
                if (tileArray[i][j].getState() == TileState.FREE) {
                    easyModel.flagTile(i, j);
                    assertEquals(TileState.FLAGGED_FREE, tileArray[i][j].getState());
                    easyModel.sweepTile(i, j, false);
                    assertEquals(TileState.FLAGGED_FREE, tileArray[i][j].getState());
                    easyModel.flagTile(i, j);
                    assertEquals(TileState.QMARKED_FREE, tileArray[i][j].getState());
                    easyModel.flagTile(i, j);
                    assertEquals(TileState.FREE, tileArray[i][j].getState());

                    easyModel.sweepTile(i, j, false);
                    easyModel.flagTile(i, j);
                    assertEquals(TileState.SWEEPED_FREE, tileArray[i][j].getState());
                }
            }
        }
    }

    @Test
    @DisplayName("Test the surrounding Mine function on different setups in der Minefield")
    void testSurroundingMines(){
        String seedWithoutFlags = "0000000030103000601060206030004000500030103030403050303040504030604060506030705070308060007000800060108010602070206030703080306040804060507050805";
        //with separation: "00000_0003_0103__0006_0106_0206__0300_0400_0500_0301__0303_0403_0503_0304_0504__0306_0406_0506_0307_0507_0308__0600_0700_0800_0601_0801_0602_0702__0603_0703_0803_0604_0804_0605_0705_0805"
        Field minefieldNoFlags=new Field(seedWithoutFlags);
        assertEquals(1,minefieldNoFlags.getSurroundingMines(1,1));
        assertEquals(2,minefieldNoFlags.getSurroundingMines(1,4));
        assertEquals(3,minefieldNoFlags.getSurroundingMines(1,7));
        assertEquals(4,minefieldNoFlags.getSurroundingMines(4,1));
        assertEquals(5,minefieldNoFlags.getSurroundingMines(4,4));
        assertEquals(6,minefieldNoFlags.getSurroundingMines(4,7));
        assertEquals(7,minefieldNoFlags.getSurroundingMines(7,1));
        assertEquals(8,minefieldNoFlags.getSurroundingMines(7,4));
        assertEquals(0,minefieldNoFlags.getSurroundingMines(7,7));

        String seedWithFlags = "0000016031603160617061806190020002100190119032003210319042104190620062106190721071908220023002400220124012202230222032303240322042404220523052405";
        //with separation: "00000_1603_1603__1606_1706_1806__1900_2000_2100_1901__1903_2003_2103_1904_2104__1906_2006_2106_1907_2107_1908__2200_2300_2400_2201_2401_2202_2302__2203_2303_2403_2204_2404_2205_2305_2405"
        Field minefieldFlags=new Field(seedWithoutFlags);
        assertEquals(1,minefieldFlags.getSurroundingMines(1,1));
        assertEquals(2,minefieldFlags.getSurroundingMines(1,4));
        assertEquals(3,minefieldFlags.getSurroundingMines(1,7));
        assertEquals(4,minefieldFlags.getSurroundingMines(4,1));
        assertEquals(5,minefieldFlags.getSurroundingMines(4,4));
        assertEquals(6,minefieldFlags.getSurroundingMines(4,7));
        assertEquals(7,minefieldFlags.getSurroundingMines(7,1));
        assertEquals(8,minefieldFlags.getSurroundingMines(7,4));
        assertEquals(0,minefieldFlags.getSurroundingMines(7,7));
    }


    @Test
    @DisplayName("Test if a save and fair start is assured")
    void testFairStart(){
        /**
         * The chance for the first tile sweeped(not near the edge) an the 8 surrounding tiles not being mines may be 7,3*10^(-7)(idk i'm not the best at stochastic s)
         * So therefore the chance of that happening 100 consecutive times is 7,3*10^(-700). For comparison: the likelihood of being killed by an asteroid is: around 7,5*10^(8)
         */
        for(int i = 0; i<100;i++){
            Model model= new Model(Difficulty.HARD);
            model.sweepTile(8,8,false);
            assertEquals(0,model.getSurroundingMines(8,8));
        }
    }
}
