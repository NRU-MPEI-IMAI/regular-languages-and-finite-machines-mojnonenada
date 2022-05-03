import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Transition {
    public String from;
    public String to;
    public Set<String> labels;
    public Transition(){
        labels = new HashSet<>();
    }
    public Transition(String from, String to, Set<String> labels) {
        this.from = from;
        this.to = to;
        this.labels = labels;
    }

    public void makeFrom(ArrayList<State> states){
        from = "";
        for(State state: states){
            from += state.getName();
        }
    }

    public void makeTo(ArrayList<State> states){
        to = "";
        for(State state: states){
            to += state.getName();
        }
    }
}
