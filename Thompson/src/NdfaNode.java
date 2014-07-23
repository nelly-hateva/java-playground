import java.util.List;

public interface NdfaNode {
    int payload();
    List<? extends Transition> nexts();
    List<? extends NdfaNode> epsNexts();

    static interface Transition {
        public char key();
        public NdfaNode next();
    }
}