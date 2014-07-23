import java.util.ArrayList;


public class Node implements INode {
    public ArrayList<Node> next;
    public ArrayList<Character> chars;
    public boolean endState;

    public Node(ArrayList<Node> next, ArrayList<Character> chars, boolean endState)
    {
    	this.next = next;
    	this.chars = chars;
    	this.endState = endState;
    }

    public Node() {
    	this.next = new ArrayList<Node>();
    	this.chars = new ArrayList<Character>();
    	this.endState = false;
	}

	public void buildTrie() {
    	return;
	}

	@Override
	public INode next(char c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int payload() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Node lcp(String alpha, Node root)
	{
		Node state = root; int index;
		for(int i = 0; i < alpha.length(); i++)
		{
		    index = state.chars.indexOf(alpha.charAt(i));
		    if(index != -1)
				state = state.next.get(index);
			else
				return state;
		}
		return state;
	}
}
