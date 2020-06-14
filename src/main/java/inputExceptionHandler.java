import exceptions.*;

public class inputExceptionHandler {
    public void testSplittable(String input) throws wrongFormatException {
        if(input.indexOf(':')==-1){
            throw new wrongFormatException("Format must be \"Int:Int\"");
        }
    }
}
