public class Main {

    public static void main(String[] args) {

        private Model model;
        private Controller controller;
        private boolean running;

        model = new Model(EASY);
        controller = new Controller();
        CLI.startGame(model);
        running = true;


        do {
            update();
            draw();

        } while(running);


        public void update() {
            controller.updateModel();
        }

        public void draw() {
            CLI.drawModel();
        }

    }

}
