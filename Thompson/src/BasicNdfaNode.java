import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasicNdfaNode implements NdfaNode {
    
    int payload;
    int marker;
    List<Transition> nexts;
    List<NdfaNode> epsNexts;
    
    
    private BasicNdfaNode(int payload, List<Transition> nexts,
            List<NdfaNode> epsNexts, int marker) {
        this.payload = payload;
        this.nexts = nexts;
        this.epsNexts = epsNexts;
        this.marker = marker;
    }

    public static BasicNdfaNode payload(int payload) {
        return new BasicNdfaNode(payload,
                Collections.<Transition>emptyList(), Collections.<NdfaNode>emptyList(), -1);
    }
    
    public static BasicNdfaNode epsilonTo(int marker, NdfaNode... next) {
        return new BasicNdfaNode(0, Collections.<Transition>emptyList(),
                Arrays.asList(next), marker);
    }
    
    public static NdfaNode epsilonTo(int marker, List<NdfaNode> next) {
        return new BasicNdfaNode(0, Collections.<Transition>emptyList(),
                next, marker);
    }
    
    public static BasicNdfaNode transitionTo(char c, NdfaNode next) {
        return new BasicNdfaNode(0, Collections.singletonList(new Transition(c, next)),
                Collections.<NdfaNode>emptyList(), -1);
        
    }

    public static BasicNdfaNode dotTo(NdfaNode next) {
        List<Transition> trans = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; ++c) {
            trans.add(new Transition(c, next));
        }
        return new BasicNdfaNode(0, trans, Collections.<NdfaNode>emptyList(), -1);
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
        return "BasicNdfaNode [marker =" + marker + ", payload=" + payload + ", nexts=" + nexts
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

	@Override
	public int marker() {
		return marker;
	}
}
