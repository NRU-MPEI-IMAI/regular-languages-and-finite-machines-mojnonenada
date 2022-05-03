import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class State {
    private final String name;
    private List<Link> links;

    public State(String name) {
        this.name = name;
        this.links = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void addLink(State child, String letter) {
        links.add(new Link(child, letter));
    }

    public static boolean isUniq(Set<ArrayList<State>> uniqStates, ArrayList<State> tmp){
        for(ArrayList<State> uniqState: uniqStates){
            if(uniqState.size() == tmp.size()){
                int matches = 0;
                for(State state : uniqState){
                    if(tmp.contains(state))
                        matches++;
                }
                if(matches == uniqState.size()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void remakeChildren(ArrayList<Link> children) {
        links.clear();
        links.addAll(children);
    }
}
