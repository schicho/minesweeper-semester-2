
import java.util.*;

public class Cli{
    //Existieren nur um in drawModel() durch die einzelnen Elemente des Arrays zu laufen.
    int minefieldWidth;
    int minefieldHeight;


    //Übergabe des Initialisierten Fields. Zeichnen
    public void startGame(Model minefield){
        Difficulty difficulty= minefield.getDifficulty();
        switch (difficulty) {
            case EASY:
                this.minefieldWidth = 9;
                this.minefieldHeight = 9;
                break;
            case NORMAL:
                this.minefieldWidth = 16;
                this.minefieldHeight = 16;
                break;
            case HARD:
                this.minefieldWidth = 16;
                this.minefieldHeight = 30;
                break;
        }
        drawModel(minefield);
        System.out.println("Willkommen bei einer Partie Minesweeper MVP.");
        System.out.println("Wähle ein Feld in dem Schema \"m:n\" um anzufangen");
    }



    public void drawModel(Model minefield){
        //erzeugt Nummerrierung für n
        String rowString="m\\n";
        //StringBuilder wird verwendet um Speicherplatz durch neuinitialisieren von Strings zu spaaren
        StringBuilder rowBuilder= new StringBuilder(rowString);
        for(int coll=0;coll<this.minefieldWidth;coll++){
            rowBuilder.append("  "+coll);
        }
        System.out.println(rowBuilder);
        rowBuilder.delete(0,rowBuilder.length());

        //Für jede Zeile von minefield wird ein Wert in [] an einen String appended, der gedruckt und gelöscht wird
        for(int row=0; row<this.minefieldHeight; row++){
            rowBuilder.append(row);
            rowBuilder.append(" : ");
            for (int coll = 0; coll<minefieldWidth; coll++ ){
                rowBuilder.append("[");
                if(minefield.isSweeped(row,coll)){
                    if(minefield.isMine(row,coll)){
                        rowBuilder.append('B');
                    }
                    else rowBuilder.append(minefield.getSurroundingMines(row,coll));
                }
                else rowBuilder.append('■');
                rowBuilder.append("]");
            }
            System.out.println(rowBuilder);
            rowBuilder.delete(0,rowBuilder.length());
        }
    }
    
    public void askForNextTile(){ System.out.println("Bitte wähle das nächste Feld.");}

    public void displayWin(){
        System.out.println("You won! Much cool, very skill! ");
    }

    public void displayFailure(){
        System.out.println("Disgausting! You are not a clown. You are the entire Circus!");
    }

}