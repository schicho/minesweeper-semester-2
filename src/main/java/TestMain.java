import java.util.logging.Handler;
//Diese Main Klasse hat nichts mit der eigentlichen zutun und ist zur zum Testen hier. Nach Merge löschen!
public class TestMain {

    public static void main(String[] args) {
        System.out.println("hey");
        TestCli view = new TestCli();
        //Initialisiert TestHandler mit referenz auf view
        TestHandler Handler = new TestHandler(view);
        view.startGame();
    }
}
