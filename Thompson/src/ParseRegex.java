import java.util.ArrayList;
import java.util.List;

public class ParseRegex {


    private static NdfaNode build(String regex, int pos, NdfaNode continuation) {
        if (pos < 0) return continuation;
        char c = regex.charAt(pos);
        switch(c) {
        case '?': {
            NdfaNode sub = build(regex, pos - 1, continuation);
            return BasicNdfaNode.epsilonTo(sub, continuation);
        }
        case '*': {
            NdfaNode[] nexts = new NdfaNode[2];
            nexts[0] = continuation;
            NdfaNode loop = BasicNdfaNode.epsilonTo(nexts);
            NdfaNode sub = build(regex, pos - 1, loop);
            nexts[1] = sub;
            return loop;
        }
        case ')':
            int index = matchingOpenBracket(regex, pos);
            String subregex = regex.substring(index + 1, pos);
            return build(regex, index - 1, buildAlt(subregex, continuation));
        default:
            return build(regex, pos - 1, BasicNdfaNode.transitionTo(c, continuation));
        }
    }
    
    private static int matchingOpenBracket(String regex, int index) {
        for (int i=index - 1; i>=0; --i) {
            char c = regex.charAt(i);
            switch (c) {
            case '(':
                return i;
            case ')':
                i = matchingOpenBracket(regex, i);
                break;
            }
        }
        throw new RuntimeException("Invalid regex.");
    }
    
    private static int lastSplit(String regex, int index) {
        for (int i=index - 1; i>=0; --i) {
            char c = regex.charAt(i);
            switch (c) {
            case '|':
                return i;
            case ')':
                i = matchingOpenBracket(regex, i);
                break;
            }
        }
        return -1;
    }
    
    private static NdfaNode buildAlt(String regex, NdfaNode continuation) {
        int start;
        int end = regex.length();
        List<NdfaNode> children = new ArrayList<>();
        do {
            start = lastSplit(regex, end);
            String subregex = regex.substring(start + 1, end);
            children.add(build(subregex, subregex.length() - 1, continuation));
            end = start;
        } while (end >= 0);
        return children.size() == 1 ? children.get(0) : BasicNdfaNode.epsilonTo(children);
    }
    
    public static NdfaNode build(String regex) {
        return buildAlt(regex, BasicNdfaNode.payload(1));
    }
    
    
    public static void main(String[] args) {
        
        NdfaNode t = build("a*bc");
        System.out.println(t);
        
        System.out.println(Thompson.match(t, "aaaaabc"));
        System.out.println(Thompson.match(t, "abaabc"));
        System.out.println(Thompson.match(t, "abbac"));
        System.out.println(Thompson.match(t, "bc"));
        System.out.println(Thompson.match(t, "aaaaabac"));
        
        t = build("a|bc");
        System.out.println(Thompson.match(t, "aaaaabc"));
        System.out.println(Thompson.match(t, "a"));
        System.out.println(Thompson.match(t, "abbac"));
        System.out.println(Thompson.match(t, "bc"));
        System.out.println(Thompson.match(t, "aaaaabac"));

        t = build("(bc|a)*(e?)");
        System.out.println();
        System.out.println(Thompson.match(t, "aaaaabc"));
        System.out.println(Thompson.match(t, "a"));
        System.out.println(Thompson.match(t, "abbac"));
        System.out.println(Thompson.match(t, "bc"));
        System.out.println(Thompson.match(t, "aaaaabac"));
        System.out.println(Thompson.match(t, "abce"));
        System.out.println(Thompson.match(t, "aaaaabace"));
        System.out.println(t);
        
    }
}
