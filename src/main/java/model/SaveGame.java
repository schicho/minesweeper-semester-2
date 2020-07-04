package model;
import  java.util.Stack;

public class SaveGame {
    private Stack SweepCoordinats = new Stack();


    public void addSweepCoords(int m, int n){
        SweepCoordinats.push((Integer) n);
        SweepCoordinats.push((Integer) m );
    }


    public String genSeed(Field mineField){
        int rows = mineField.getRows();
        int cols= mineField.getCols();
        int toAddi;
        int toAddj;
        boolean add=false;
        StringBuilder seed = new StringBuilder("");
        if(cols==9){
            seed.append("0");
        }
        else if(cols==16){
            seed.append("1");
        }
        else{seed.append("2");}
        for(int i=0; i<rows; i++){
            for(int j=0; j < cols;j++){
                toAddi=i;
                toAddj=j;
                if(mineField.isFlagged(i,j)){
                    toAddi+=32;
                    add=true;
                }
                if(mineField.isQmarked(i,j)){
                    toAddj+=60;
                    add=true;
                }
                if(mineField.isMine(i,j)){
                    if(toAddi>=32){
                        toAddi-=16;
                    }
                    if(toAddj>=60){
                        toAddj-=30;
                    }
                    if(Math.ceil(Math.log10(toAddi+1))<=1){
                        seed.append("0");seed.append(toAddi);
                    }
                    else {
                        seed.append(toAddi);
                    }
                    if(Math.ceil(Math.log10(toAddj+1))<=1){
                        seed.append("0"); seed.append(toAddj);
                    }
                    else {seed.append(toAddj); }
                    add=false;
                }

                if(add){
                    seed.append(toAddi);
                    seed.append(toAddj);
                }
                add=false;

            }
        }

        seed.append("9999");
        while (true!=SweepCoordinats.empty()){
            int nextCoor=(int) SweepCoordinats.pop();
            if(Math.ceil(Math.log10(nextCoor+1))<=1){
                seed.append("0");
            }
            seed.append(nextCoor);
        }
        return seed.toString();

    }

}
