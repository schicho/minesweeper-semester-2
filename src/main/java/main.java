import java.util.logging.Handler;

public class main {
    //Diese Main Klasse hat nichts mit der eigentlichen zutun und ist zur zum Testen hier.

    public static void main(String[] args) {
        System.out.println("hey");
        Cli test = new Cli();
        handler Handler = new handler(test);
        test.startGame();
    }
}
