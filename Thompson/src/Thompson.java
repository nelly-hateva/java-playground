import java.util.ArrayList;
import java.util.Iterator;


public class Thompson {
    public static boolean match(NdfaNode node, String string)
    {
    	ArrayList<NdfaNode> activeStates = new ArrayList<NdfaNode>();
    	ArrayList<NdfaNode> nextStates = new ArrayList<NdfaNode>();
    	Iterator<NdfaNode> it;
        activeStates.add(node);
        for(int i = 0; i < string.length(); ++i)
        {
        	it = activeStates.iterator();
        	while(it.hasNext())
        		nextStates.addAll(getActive(it.next(), string.charAt(i)));
        }
        return false;
    }
}
