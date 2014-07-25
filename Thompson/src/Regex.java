public class Regex {
    public static boolean match(NdfaNode node, String string)
    {
    	int[] indices = new int[100];
    	return match(string, node, indices, 0);
    }

    private static boolean match(String string, NdfaNode node, int[] indices, int pos)
    {
    	int old = 0;
    	if (node.marker() != -1)
    	{
    	    old = indices[node.marker()];
    	    indices[node.marker()] = pos;
    	}
    	if(pos < string.length())
    	{
    		for(NdfaNode.Transition t: node.nexts())
    		{
    			if(t.key() == string.charAt(pos))
    			{
    				if (match(string, t.next(), indices, pos + 1))
    					return true;
    			}
    		}
    		for(NdfaNode n: node.epsNexts())
    		{
    		//	for(NdfaNode.Transition t: n.nexts())
        	//	{
        			//if(t.key() == string.charAt(pos))
        			//{
        				if (match(string, n, indices, pos))
        					return true;
        	//		}
        	//	}
    		}
    		if(node.marker() != -1)
    		    indices[node.marker()] = old;
    		return false;
    	}
    	for(NdfaNode n: node.epsNexts())
    		if (match(string, n, indices, pos))
    		    return true;
    	return node.payload() != 0;
    }

    static String[] find(String regex, String text)
    {
    	t = ParseRegex.build("(.*)?" + regex);
        Regex.match(t, "aaaaabc");
    	return match
    }

    static void replaceAll()
    {
    	
    }
}
