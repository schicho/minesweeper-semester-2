package model;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * The main funktion of the class is to create a seed, representing the status of th game
 */
public class SaveGame {
    /**
     * Saves the Coordinates of Tiles, that were clicked/chosen to sweep, but not recursivly chosen
     * in order n, m.
     */
    private List<Integer> sweepedCoordinates = new LinkedList<>();


    /**
     * add sweepcoordinates to stack
     *
     * @param m m coordinate
     * @param n n coordinate of Tile to sweep
     */

    public void addSweepCoords(int m, int n) {
        sweepedCoordinates.add(m);
        sweepedCoordinates.add(n);
    }

    /**
     * generates saveseed
     *
     * @param mineField
     * @return seed, representing the Minefield encoded in base64
     */
    public String genSeed(Field mineField) {
        int rows = mineField.getRows();
        int cols = mineField.getCols();
        int toAddi;
        int toAddj;
        boolean add = false;
        StringBuilder seed = new StringBuilder("");

        //encode difficulty
        if (cols == 9) {
            seed.append("0");
        }
        else if (cols == 16) {
            seed.append("1");
        }
        else {
            seed.append("2");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                /*
                 * toAddi,toAddij are the coordates which will be appended to the seed. The encode mine, Qmarked, flag and combinations
                 *                  toAddi<16,toAddij<30 -> mine
                 *                  toAddi>=32-> Flagged Free
                 *                  toAddj>= 60 -> Qmarked Free
                 *                  32>toAddi>=16 -> Flagged Mine
                 *                  60>toAddj>=30 -> Qmarked Mine
                 *                  toAddi>=32, toAddj>=60 is still available (encode sweep like this only makes this harder)
                 */
                toAddi = i;
                toAddj = j;
                if (mineField.isFlagged(i, j)) {
                    toAddi += 32;
                    add = true;
                }
                if (mineField.isQmarked(i, j)) {
                    toAddj += 60;
                    add = true;
                }
                if (mineField.isMine(i, j)) {
                    if (toAddi >= 32) {
                        toAddi -= 16;
                    }
                    if (toAddj >= 60) {
                        toAddj -= 30;
                    }
                    if (Math.ceil(Math.log10(toAddi + 1)) <= 1) {
                        seed.append("0");
                        seed.append(toAddi);
                    }
                    else {
                        seed.append(toAddi);
                    }
                    if (Math.ceil(Math.log10(toAddj + 1)) <= 1) {
                        seed.append("0");
                        seed.append(toAddj);
                    }
                    else {
                        seed.append(toAddj);
                    }
                    add = false;
                }

                if (add) {
                    if (Math.ceil(Math.log10(toAddi + 1)) <= 1) {
                        seed.append("0");
                        seed.append(toAddi);
                    }
                    else {
                        seed.append(toAddi);
                    }
                    if (Math.ceil(Math.log10(toAddj + 1)) <= 1) {
                        seed.append("0");
                        seed.append(toAddj);
                    }
                    else {
                        seed.append(toAddj);
                    }
                }
                add = false;

            }
        }

        seed.append("9999"); // identifierer
        sweepedCoordinates.forEach(elements -> {
            int nextCoor = elements.intValue();
            if (Math.ceil(Math.log10(nextCoor + 1)) <= 1) {
                seed.append("0");
            }
            seed.append(nextCoor);
        });
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(seed.toString().getBytes());

    }

}