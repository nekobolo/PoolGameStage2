package application;
import java.util.ArrayList;

/**
 *
 * Use for Memento DP
 * @author Daihui Yu
 */
public class Caretaker {

    ArrayList<Memento> savedStates = new ArrayList<>();

    public void addMemento(Memento memento){
        savedStates.add(memento);
    }

    public Memento getMemento(int index){ return savedStates.get(index); }

    public int getSavedStatesSize(){ return savedStates.size();}

    public void removeMemento(Memento memento){ savedStates.remove(memento); }


}
