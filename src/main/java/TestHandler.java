//Klasse existiert nur zum Testen und als Beispiel. Soll nach dem Mergen entfernt werden!
public class TestHandler implements cliListener{

    //Referenz zur der zu Observenden Klasse, in dem Fall view
    TestCli view;

    public TestHandler(TestCli view){
        this.view=view;
        //Speichert innerhalb von View, dass Testhandler.reactToInput aufgerufen werden soll, im Fall von InputRead
        view.addListerner(this);

    }

    //Gibt die neuen Werte vom Cli wieder, falls kein Interger.MIN_VALUE zurückkommt.
    public void reactToInput(){
        int n = view.getStepN();
        int m = view.getStepM();
        System.out.println("Handler sagt: "+m+" und "+ n);
    }
}
