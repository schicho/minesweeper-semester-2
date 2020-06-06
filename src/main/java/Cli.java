import java.util.*;

public class Cli{
    private int stepX;
    private int stepY;
    char[][] intField = new char[9][9];
    private Scanner lineReader = new Scanner(System.in);
    private List<cliListener> listeners = new ArrayList<cliListener>();

    public void addListerner(cliListener newListener){
        this.listeners.add(newListener);
        System.out.println(listeners.toString());
    }


    public void startGame(){
        for(int row=0; row<intField.length; row++){
            for (int cell = 0; cell<intField[row].length; cell++ ){
                intField[row][cell]='_'; //or what ever char is chosen for a untouched tile
            }
        }
        this.stepX=-200;
        this.stepY=-200;
        printMineField(intField);
        System.out.println("Willkommen bei einer Partie Minesweeper MVP.");
        System.out.println("Wähle eine Mine mit dem Schema \"m:n\" um anzufangen");
        readInput();
        //System.out.println(getStepY()+":"+getStepX());
    }

    public void readInput(){
        String InputString= lineReader.next();
        StringBuilder InputBuilder= new StringBuilder(InputString);
        String xString= InputBuilder.substring(InputBuilder.indexOf(":")+1,InputBuilder.length() );
        String yString=InputBuilder.substring(0,InputBuilder.indexOf(":"));
        this.stepX=Integer.parseInt(xString);
        this.stepY=Integer.parseInt(yString);
        for(cliListener listener:listeners){
            listener.reactToInput();
        }
    }


    public void printMineField(char[][] minefield){
        String rowString="m\\n  0  1  2  3  4  5  6  7  8";
        StringBuilder rowBuilder= new StringBuilder(rowString);
        System.out.println(rowBuilder);
        rowBuilder.delete(0,rowBuilder.length());

        for(int row=0; row<minefield.length; row++){
            rowBuilder.append(row);
            rowBuilder.append(" : ");
            for (int cell = 0; cell<minefield[row].length; cell++ ){
                rowBuilder.append("[");
                rowBuilder.append(minefield[row][cell]); //depending on tile implementation, interpret char to append beforehand
                rowBuilder.append("]");
            }
            System.out.println(rowBuilder);
            rowBuilder.delete(0,rowBuilder.length());
        }
    }

    public int getStepX(){
        return this.stepX;
    }
    public int getStepY(){
        return this.stepY;
    }
}

