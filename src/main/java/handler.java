public class handler implements cliListener{
    //die Klasse existiert nur zum testen und sollte für die Implementierung wieder entfernt werden.
    Cli view;
    public handler(Cli view){
        this.view=view;
        view.addListerner(this);

    }

    public void reactToInput(){
        int x = view.getStepX();
        int y = view.getStepY();
        System.out.println(x+" und "+ y);
    }
}
