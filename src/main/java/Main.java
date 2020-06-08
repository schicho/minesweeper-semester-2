public class Main {

    private static Model model;
    private static Controller controller;
    private static Cli cli;

    public static void main(String[] args) {


        model = new Model(Difficulty.EASY);
        controller = new Controller();
        cli.initializeView(model);


        do {
            update();

            draw();
            if(model.checkCurrentGameState() == GameState.WON) {
                cli.displayWin();
                System.exit(0);
            }
            else if(model.checkCurrentGameState() == GameState.LOST) {
                cli.displayFailure();
                System.exit(0);
            }
        } while(model.checkCurrentGameState() == GameState.RUNNING);


    }

    public static void update() {
        controller.updateModel(model);
    }

    public static void draw() {
        cli.drawModel(model);
    }
}
