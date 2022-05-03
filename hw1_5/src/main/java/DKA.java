import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DKA {
    private Set<String> sigma;
    private Set<ArrayList<State>> Q;
    private Set<State> S;
    private Set<ArrayList<State>> T;
    private Set<Transition> transitions;

    public DKA(Set<String> sigma, Set<State> s) {
        this.sigma = sigma;
        S = s;
        Q = new HashSet<>();
        T = new HashSet<>();
        transitions = new HashSet<>();
    }

    public void addState(ArrayList<State> states) {
        Q.add(states);
    }

    public void addTerminalState(ArrayList<State> terminalStates) {
        T.add(terminalStates);
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }


    public Set<String> getSigma() {
        return sigma;
    }

    public Set<State> getS() {
        return S;
    }

    public String getBeautyTransitions() {
        StringBuilder beautyTransitions = new StringBuilder();
        for(Transition transition: transitions) {
            beautyTransitions.append(transition.from)
                    .append(" -> ")
                    .append(transition.to)
                    .append(" [label = \"")
                    .append(transition.labels.iterator().next())
                    .append("\"];\n");
        }
        return beautyTransitions.toString();
    }

    public String getBeautyQ(){
        StringBuilder beautyQ = new StringBuilder("[");
        for(ArrayList<State> dkaState: Q) {
            if(!beautyQ.toString().equals("["))
                beautyQ.append(", ");
            for(State state : dkaState) {
                beautyQ.append(state.getName());
            }
        }
        return beautyQ + "]";
    }

    public String getBeautyT() {
        StringBuilder beautyT = new StringBuilder("[");
        for(ArrayList<State> dkaState: T){
            if(!beautyT.toString().equals("["))
                beautyT.append(", ");
            for(State state : dkaState){
                beautyT.append(state.getName());
            }
        }
        return beautyT + "]";
    }

    private String make_T_In_File() {
        StringBuilder beautyT = new StringBuilder();
        for(ArrayList<State> dkaState: T){
            if(!beautyT.toString().equals(" "))
                beautyT.append("; ");
            for(State state : dkaState){
                beautyT.append(state.getName());
            }
        }
        return beautyT + ";";
    }

    private String make_S_In_File() {
        StringBuilder beautyS = new StringBuilder();
        beautyS.append(S.iterator().next().getName());
        return beautyS + ";";
    }

    private String getBeautyTransitionsInFile() {
        StringBuilder beautyTransitions = new StringBuilder();
        for(Transition transition: transitions){
            beautyTransitions.append("\t").append(transition.from).append(" -> ").append(transition.to).append(" [label = \"").append(transition.labels.iterator().next()).append("\"];\n");
        }
        return beautyTransitions.toString();
    }

    public void makeFile() {
        File file = new File("src/main/resources/dka.gv");
        try {
            PrintWriter pw = new PrintWriter(file);
            StringBuilder sb = new StringBuilder();
            pw.println("digraph dka {");
            pw.println("\trankdir=LR;");

            pw.println("\tnode [shape = doublecircle]" + make_T_In_File());
            pw.println("\tnode [shape = circle];");
            pw.println("\t\"\" [shape = none];");

            pw.println("\t\"\" -> " + make_S_In_File());
            pw.print(getBeautyTransitionsInFile());
            pw.println("}");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
