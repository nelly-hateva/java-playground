import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasicNdfaNode implements NdfaNode {
    
    int payload;
    List<Transition> nexts;
    List<NdfaNode> epsNexts;
    
    
    private BasicNdfaNode(int payload, List<Transition> nexts,
            List<NdfaNode> epsNexts) {
        this.payload = payload;
        this.nexts = nexts;
        this.epsNexts = epsNexts;
    }

    public static BasicNdfaNode payload(int payload) {
        return new BasicNdfaNode(payload,
                Collections.<Transition>emptyList(), Collections.<NdfaNode>emptyList());
    }
    
    public static BasicNdfaNode epsilonTo(NdfaNode... next) {
        return new BasicNdfaNode(0, Collections.<Transition>emptyList(),
                Arrays.asList(next));
    }
    
    public static NdfaNode epsilonTo(List<NdfaNode> next) {
        return new BasicNdfaNode(0, Collections.<Transition>emptyList(),
                next);
    }
    
    public static BasicNdfaNode transitionTo(char c, NdfaNode next) {
        return new BasicNdfaNode(0, Collections.singletonList(new Transition(c, next)),
                Collections.<NdfaNode>emptyList());
        
    }

    @Override
    public int payload() {
        return payload;
    }

    @Override
    public List<Transition> nexts() {
        return nexts;
    }

    @Override
    public List<NdfaNode> epsNexts() {
        return epsNexts;
    }

    @Override
    public String toString() {
        return "BasicNdfaNode [payload=" + payload + ", nexts=" + nexts
                + ", epsNexts=" + epsNexts + "]";
    }

    static class Transition implements NdfaNode.Transition {
        final private char c;
        final private NdfaNode next;
        
        public Transition(char c, NdfaNode next) {
            super();
            this.c = c;
            this.next = next;
        }

        public char key() {
            return c;
        }
        
        public NdfaNode next() {
            return next;
        }

        @Override
        public String toString() {
            return c + "->.";
        }
    }
}
