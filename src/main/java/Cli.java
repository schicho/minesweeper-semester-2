//Eigentliche Cli Klasse. Funktioniert nicht ohne Klasse Field! Entklammern, wenn es um die richtige Implementation geht.

/*
import java.lang.reflect.Field;
import java.util.*;

public class Cli{

    //initialisiere die Variablen, die den letzten Schritt Speichern, mit Minimum für Fehlersuche.
    private int stepN=Integer.MIN_VALUE;
    private int stepM=Integer.MIN_VALUE;

    //nurFür Ausgabentesten


    //um später Commandline Input zu lesen
    private Scanner lineReader = new Scanner(System.in);
    //Liste der Objekte, die Cli Observen
    private List<cliListener> listeners = new ArrayList<cliListener>();

    //Fügt neuen Handler/Observer hinzu.
    public void addListerner(cliListener newListener){
        this.listeners.add(newListener);
    }

    //Übergabe des Initialisierten Fields. Zeichnen
    public void startGame(Field intField){
        drawModel(intField);
        System.out.println("Willkommen bei einer Partie Minesweeper MVP.");
        System.out.println("Wähle eine Mine mit dem Schema \"m:n\" um anzufangen");
        readInput();
    }


    public void readInput(){
        //Nimmt Commandline Input
        String InputString= lineReader.next();
        StringBuilder InputBuilder= new StringBuilder(InputString);
        //Parsed den Input und speichert ihn ab
        String xString= InputBuilder.substring(InputBuilder.indexOf(":")+1,InputBuilder.length() );
        String yString=InputBuilder.substring(0,InputBuilder.indexOf(":"));
        this.stepN=Integer.parseInt(xString);
        this.stepM=Integer.parseInt(yString);
        //Singnale, dass der Input genommen wurde, wird an Handler verschickt
        for(cliListener listener:listeners){
            listener.reactToInput();
        }
    }


    public void drawModel(Field minefield){
        //erzeugt Nummerrierung für n
        String rowString="m\\n  0  1  2  3  4  5  6  7  8";
        //StringBuilder wird verwendet um Speicherplatz durch neuinitialisieren von Strings zu spaaren
        StringBuilder rowBuilder= new StringBuilder(rowString);
        System.out.println(rowBuilder);
        rowBuilder.delete(0,rowBuilder.length());

        //Für jede Zeile von minefield wird ein Wert in [] an einen String appended, der gedruckt und gelöscht wird
        for(int row=0; row<minefield.length; row++){
            rowBuilder.append(row);
            rowBuilder.append(" : ");
            for (int cell = 0; cell<minefield[row].length; cell++ ){
                rowBuilder.append("[");
                if(minefield[row][cell]==true){
                    rowBuilder.append(minefield[row][cell].getSurroundingMines);
                }
                else rowBuilder.append('■');
                rowBuilder.append("]");
            }
            System.out.println(rowBuilder);
            rowBuilder.delete(0,rowBuilder.length());
        }
    }

    public void displayWin(){
        System.out.println("You won! Much cool, very skill! ");
    }

    public void displayFailure(){
        System.out.println("Disgausting! You are not a clown. You are the entire Circus!");
    }

    public int getStepN(){
        return this.stepN;
    }
    public int getStepM(){
        return this.stepM;
    }
}*/