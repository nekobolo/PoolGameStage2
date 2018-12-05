package application;

import java.util.ArrayList;

/**
 *
 * Use for Command DP
 * @author Daihui Yu
 */
public class saveMemento implements Command{

    Caretaker caretaker;
    Originator originator;
    ArrayList<positionOfBall> list;

    public saveMemento(Caretaker c, Originator o, ArrayList<positionOfBall> a){
        caretaker = c;
        originator = o;
        list = a;
    }


    @Override
    public void execute(){
        originator.setState(list);
        caretaker.addMemento(originator.saveToMemento());
    }

}
