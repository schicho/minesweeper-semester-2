import java.util.*;
//Klasse Existiert nur zum Testen und soll nach Mergen entfernt werden
public class TestCli{

    //initialisiere die Variablen, die den letzten Schritt Speichern, mit Minimum für Fehlersuche.
    private int stepN=Integer.MIN_VALUE;
    private int stepM=Integer.MIN_VALUE;

    //nurFür Ausgabentesten
    char[][] testField = new char[9][9];

    //um später Commandline Input zu lesen
    private Scanner lineReader = new Scanner(System.in);
    //Liste der Objekte, die Cli Observen
    private List<cliListener> listeners = new ArrayList<cliListener>();

    //Fügt neuen Handler/Observer hinzu.
    public void addListerner(cliListener newListener){
        this.listeners.add(newListener);
    }


    public void startGame(){
        for(int row=0; row<testField.length; row++){
            for (int cell = 0; cell<testField[row].length; cell++ ){
                testField[row][cell]='■'; //or what ever char is chosen for a untouched tile
            }
        }

        drawModel(testField);
        System.out.println("Willkommen bei einer Partie Minesweeper MVP.");
        System.out.println("Wähle eine Mine mit dem Schema \"m:n\" um anzufangen");
        readInput();
        //System.out.println(getStepY()+":"+getStepX());
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


    public void drawModel(char[][] minefield){
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
                rowBuilder.append(minefield[row][cell]);
                rowBuilder.append("]");
            }
            System.out.println(rowBuilder);
            rowBuilder.delete(0,rowBuilder.length());
        }
    }

    public int getStepN(){
        return this.stepN;
    }
    public int getStepM(){
        return this.stepM;
    }
}

