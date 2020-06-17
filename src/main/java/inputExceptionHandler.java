import exceptions.*;

public class inputExceptionHandler {


    /**
     *
     * @param input String
     * @return if String could be parsed as int
     */
    private boolean isInt(String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    /**
     *
     * @param input String, pref in Format Int:Int
     * @throws wrongFormatException if there's no :
     */

    public void testSplittable(String input) throws wrongFormatException {
        if(input.indexOf(':')==-1){
            throw new wrongFormatException("Format must be \"Int:Int\"");
        }
    }

    /**
     *
     * @param input String pref in Format Int:Int
     * @throws wrongFormatException if cant be parsed as int
     */
    public void testInt(String input) throws wrongFormatException{
        if(true!=isInt(input)){
            throw new wrongFormatException("Format must be \"Int:Int\"");
        }
    }

    /**
     *
     * @param difficulty to know the size of field
     * @param m first int
     * @param n second int
     * @throws notATileException if the selected Tile is not in the Minefield
     */
    public void testInRange(Difficulty difficulty, int m, int n) throws  notATileException{
        int maxM;
        int maxN;
        switch (difficulty){
            case EASY:
                maxM=9;
                maxN=9;
                break;
            case NORMAL:
                maxM=16;
                maxN=16;
                break;
            case HARD:
                maxM=16;
                maxN=30;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + difficulty);
        }
        if((maxM-1)<m||m<0){
            throw new notATileException("m Number must be between "+0+ " and "+(maxM-1)+".");
        }
        if((maxN-1)<n||n<0){
            throw new notATileException("n must be between "+0+ " and "+(maxN-1)+".");
        }
    }
}
