import java.util.*;

public class NKA_TREE {
    private Set<State> states;
    private Set<State> rootStates;
    private Set<State> terminalStates;
    private Set<String> sigma;
    private final static String LAMBDA = "lmb";
    private Set<State> parsedStates;

    Set<State> lmbStates;

    public NKA_TREE(Set<String> stateNames, Set<String> rootStates, Set<String> terminalStates, Set<Transition> transitions) {
        states = new HashSet<>();
        lmbStates = new HashSet<>();
        this.rootStates = new HashSet<>();
        this.terminalStates = new HashSet<>();
        for (String stateName: stateNames) {
            states.add(new State(stateName));
        }
        for (String rootState: rootStates) {
            for (State state: states) {
                if(state.getName().equals(rootState)) {
                    this.rootStates.add(state);
                    break;
                }
            }
        }
        for (String terminalState: terminalStates) {
            for (State state: states) {
                if(state.getName().equals(terminalState)) {
                    this.terminalStates.add(state);
                    break;
                }
            }
        }
        sigma = new HashSet<>();
        for (Transition transition : transitions) {
            states.forEach((state) -> {
                if(state.getName().equals(transition.from)) {
                    for (String letter: transition.labels) {
                        if(!letter.equals(LAMBDA))
                            sigma.add(letter);
                        else
                            lmbStates.add(state);
                        state.addLink(getState(transition.to), letter);
                    }
                }
            });
        }
    }

    private State getState(String name) {
        for (State state: states) {
            if (state.getName().equals(name))
                return state;
        }
        return null;
    }

    public void deleteLambda() {
        ArrayList<Link> tmp = new ArrayList<>();
        for (State state: lmbStates) {
            if (states.contains(state)) {
                parsedStates = new HashSet<>();
                if(isTerminalAfterLmb(state)) {
                    terminalStates.add(state);
                }
                parsedStates = new HashSet<>();
                state.remakeChildren(getNonLmbChildren(state, state));
            }
        }
        System.out.println("NFA with lambda transitions -> NFA without lambda transitions");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private ArrayList<Link> getNonLmbChildren(State state, State rootState) {
        parsedStates.add(state);
        ArrayList<Link> nonLmbChildren = new ArrayList<>();
        for (Link link: state.getLinks()) {
            if(!parsedStates.contains(link.state)){
                if(link.letter.equals(LAMBDA)) {
                    nonLmbChildren.addAll(getNonLmbChildren(link.state, rootState));
                    for (State state1 : states) {
                        for (Link link1: state1.getLinks())
                            if(link1.state == link.state && !link1.letter.equals(LAMBDA))
                                link1.state = rootState;
                    }
                    states.remove(link.state);
                    terminalStates.remove(link.state);
                } else {
                    nonLmbChildren.add(link);
                }
            }
        }
        return nonLmbChildren;
    }

    private boolean isTerminalAfterLmb(State state) {
        parsedStates.add(state);
        if (terminalStates.contains(state)) {
            return true;
        }
        for (Link link: state.getLinks()) {
            if(link.letter.equals(LAMBDA) && !parsedStates.contains(link.state)) {
                return isTerminalAfterLmb(link.state);
            }
        }
        return false;
    }

    public DKA NkaToDka() {
        System.out.println("                  DFA");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        DKA dka = new DKA(sigma, rootStates);
        Transition transition;
        Set<ArrayList<State>> uniqStates = new HashSet<>();
        Deque<ArrayList<State>> queue = new ArrayDeque<>();
        ArrayList<State> tmp = new ArrayList<>(), tmp1;
        tmp.add(rootStates.iterator().next());
        queue.addLast(tmp);
        uniqStates.add(tmp);
        while (!queue.isEmpty()) {
            tmp = queue.pop();
            dka.addState(tmp);
            for (String letter : sigma) {
                tmp1 = new ArrayList<>();
                transition = new Transition();
                for(State state : tmp){
                    for (Link link : state.getLinks()){
                        if(letter.equals(link.letter)){
                            if(!tmp1.contains(link.state))
                                tmp1.add(link.state);
                            transition.labels.add(link.letter);
                        }
                    }
                }
                if(!tmp1.isEmpty()){
                    if(State.isUniq(uniqStates, tmp1)){
                        for(State state : tmp1){
                            for (State state1 : terminalStates) {
                                if (state1.getName().equals(state.getName())) {
                                    dka.addTerminalState(tmp1);
                                }
                            }
                        }
                        queue.addLast(tmp1);
                        uniqStates.add(tmp1);
                    }
                    transition.makeFrom(tmp);
                    transition.makeTo(tmp1);
                    dka.addTransition(transition);
                }
            }
        }
        return dka;
    }
}
