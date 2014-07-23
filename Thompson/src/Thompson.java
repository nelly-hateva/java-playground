import java.util.ArrayList;
import java.util.Iterator;



public class Thompson {
    public static boolean match(NdfaNode node, String string)
    {
    	ArrayList<NdfaNode> activeStates = new ArrayList<NdfaNode>();
    	ArrayList<NdfaNode> current = new ArrayList<NdfaNode>();
    	Iterator<NdfaNode> it;
        activeStates.add(node);
        for(int i = 0; i < string.length(); ++i)
        {
        	current.addAll(activeStates);
        	activeStates.removeAll(activeStates);
        	it = current.iterator();
        	while(it.hasNext())
        	{
        		activeStates.addAll(getActive(node, string.charAt(i)));
        	}
        }
        Iterator<NdfaNode> iter = activeStates.iterator();
        while(iter.hasNext())
    		if(iter.next().payload() == 1)
    			return true;
        
        return false;
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

    static ArrayList<NdfaNode> getActive(NdfaNode node, char c)
    {
    	ArrayList<NdfaNode> activeStates = new ArrayList<NdfaNode>();
    	Character character;
    	Iterator<BasicNdfaNode.Transition> it = (Iterator<BasicNdfaNode.Transition>) node.nexts().iterator();
    	BasicNdfaNode.Transition next;
        while(it.hasNext())
    	{
    		next = it.next();
    		character = next.key();
    		if(character == c)
    			activeStates.add(next.next());
    	}

        ArrayList<NdfaNode> closure = epsilonClosure(node);
        Iterator<NdfaNode> iter = closure.iterator();
        while(iter.hasNext())
        {
        	activeStates.addAll(getActive(iter.next(), c));
        }
        return activeStates;
    }
}
