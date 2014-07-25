import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DfaNodeWrapper implements DfaNode {
    int payload;
    DfaNodeWrapper next;
    BasicNdfaNode ndfaNode;

    private DfaNodeWrapper(BasicNdfaNode ndfaNode) {
        this.payload = ndfaNode.payload;
        this.ndfaNode = ndfaNode;
    }

	@Override
	public int payload() {
		return payload;
	}

	@Override
	public DfaNode next() {
		ArrayList<NdfaNode> activeStates = new ArrayList<NdfaNode>();
        for(BasicNdfaNode.Transition t: ndfaNode.nexts())
        {
        	if(c == t.key())
        		activeStates.add(t.next());
        }
        
        ArrayList<NdfaNode> closure = epsilonClosure(node);
        Iterator<NdfaNode> iter = closure.iterator();
        while(iter.hasNext())
        {
        	activeStates.addAll(getActive(iter.next(), c));
        }
        return activeStates;
    }

	static ArrayList<NdfaNode> epsilonClosure(NdfaNode node)
    {
    	Iterator<BasicNdfaNode> iter;
    	ArrayList<NdfaNode> closure = new ArrayList<NdfaNode>();
    	iter = (Iterator<BasicNdfaNode>) node.epsNexts().iterator();
    	while(iter.hasNext())
    		closure.addAll(epsilonClosure(iter.next()));
    	closure.add(node);
    	return closure;
    }

}
