import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("_____________Test#1_______________");
        test1();
        System.out.println("_____________Test#2_______________");
        test2();
        System.out.println("_____________Test#3_______________");
        test3();

    }

    static void test1() {
        Set<String> stateNames = new HashSet<>();
        stateNames.add("q1");stateNames.add("q2");stateNames.add("q3");stateNames.add("q4");stateNames.add("q5");
        Set<String> rootStates = new HashSet<>();
        rootStates.add("q1");
        Set<String> terminalStates = new HashSet<>();
        terminalStates.add("q2");
        Set<Transition> transitions = new HashSet<>();
        Set<String> labels = new HashSet<>();
        labels.add("a");
        transitions.add(new Transition("q1","q2",labels));
        transitions.add(new Transition("q1","q3",labels));
        transitions.add(new Transition("q1","q4",labels));
        transitions.add(new Transition("q5","q1",labels));
        labels = new HashSet<>();
        labels.add("b");
        transitions.add(new Transition("q3","q1",labels));
        transitions.add(new Transition("q4","q5",labels));

        NKA_TREE nka1 = new NKA_TREE(stateNames, rootStates, terminalStates, transitions);
        DKA dka1 = nka1.NkaToDka();
        dka1.makeFile();
        System.out.println("Sigma = " + dka1.getSigma());
        System.out.println("\nQ = " + dka1.getBeautyQ());
        System.out.println("\nS = [" + dka1.getS().iterator().next().getName()+"]");
        System.out.println("\nT = " + dka1.getBeautyT());
        System.out.println("\nTransitions:\n"+dka1.getBeautyTransitions());
    }

    static void test2() {
        Set<String> stateNames = new HashSet<>();
        stateNames.add("q1");stateNames.add("q2");stateNames.add("q3");
        Set<String> rootStates = new HashSet<>();
        rootStates.add("q1");
        Set<String> terminalStates = new HashSet<>();
        terminalStates.add("q1");
        Set<Transition> transitions = new HashSet<>();
        Set<String> labels = new HashSet<>();
        labels.add("a");
        transitions.add(new Transition("q1","q1",labels));
        transitions.add(new Transition("q1","q2",labels));
        transitions.add(new Transition("q2","q3",labels));
        labels = new HashSet<>();
        labels.add("b");
        transitions.add(new Transition("q1","q2",labels));
        transitions.add(new Transition("q2","q3",labels));
        transitions.add(new Transition("q3","q1",labels));

        NKA_TREE nka1 = new NKA_TREE(stateNames, rootStates, terminalStates, transitions);
        DKA dka1 = nka1.NkaToDka();
        dka1.makeFile();
        System.out.println("Sigma = " + dka1.getSigma());
        System.out.println("\nQ = " + dka1.getBeautyQ());
        System.out.println("\nS = [" + dka1.getS().iterator().next().getName()+"]");
        System.out.println("\nT = " + dka1.getBeautyT());
        System.out.println("\nTransitions:\n"+dka1.getBeautyTransitions());
    }

    static void test3() {
        Set<String> stateNames = new HashSet<>();
        stateNames.add("q1");stateNames.add("q2");stateNames.add("q3");stateNames.add("q4");stateNames.add("q5");stateNames.add("q6");stateNames.add("q7");stateNames.add("q8");
        Set<String> rootStates = new HashSet<>();
        rootStates.add("q1");
        Set<String> terminalStates = new HashSet<>();
        terminalStates.add("q8");
        Set<Transition> transitions = new HashSet<>();
        Set<String> labels = new HashSet<>();
        labels.add("a");
        transitions.add(new Transition("q1","q2",labels));
        transitions.add(new Transition("q2","q3",labels));
        transitions.add(new Transition("q3","q4",labels));
        transitions.add(new Transition("q6","q7",labels));
        labels = new HashSet<>();
        labels.add("b");
        transitions.add(new Transition("q4","q5",labels));
        transitions.add(new Transition("q5","q6",labels));
        transitions.add(new Transition("q7","q8",labels));
        labels = new HashSet<>();
        labels.add("lmb");
        transitions.add(new Transition("q3","q5",labels));
        transitions.add(new Transition("q5","q3",labels));
        transitions.add(new Transition("q2","q6",labels));
        transitions.add(new Transition("q6","q2",labels));
        transitions.add(new Transition("q6","q8",labels));
        transitions.add(new Transition("q8","q6",labels));

        NKA_TREE nka1 = new NKA_TREE(stateNames, rootStates, terminalStates, transitions);
        nka1.deleteLambda();
        DKA dka1 = nka1.NkaToDka();
        dka1.makeFile();
        System.out.println("Sigma = " + dka1.getSigma());
        System.out.println("\nQ = " + dka1.getBeautyQ());
        System.out.println("\nS = [" + dka1.getS().iterator().next().getName()+"]");
        System.out.println("\nT = " + dka1.getBeautyT());
        System.out.println("\nTransitions:\n"+dka1.getBeautyTransitions());
    }
}
