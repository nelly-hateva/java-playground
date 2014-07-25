import java.util.List;

public interface NdfaNode {
    int payload();
    List<? extends Transition> nexts();
    List<? extends NdfaNode> epsNexts();
    public int marker();
   

    static interface Transition {
        public char key();
        public NdfaNode next();
    }
}