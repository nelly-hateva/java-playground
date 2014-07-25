import java.util.ArrayList;
import java.util.List;

public class ParseRegex {

    static int marker = 0;

    private static NdfaNode build(String regex, int pos, NdfaNode continuation) {
        if (pos < 0) return continuation;
        char c = regex.charAt(pos);
        switch(c) {
        case '?': {
            int start = argStart(regex, pos - 1);
            String subregex = regex.substring(start, pos);
            NdfaNode sub = build(subregex, subregex.length() - 1, continuation);
            return build(regex, start - 1, BasicNdfaNode.epsilonTo(-1, sub, continuation));
        }
        case '*': {
            NdfaNode[] nexts = new NdfaNode[2];
            nexts[1] = continuation;
            NdfaNode loop = BasicNdfaNode.epsilonTo(-1, nexts);
            int start = argStart(regex, pos - 1);
            String subregex = regex.substring(start, pos);
            NdfaNode sub = build(subregex, subregex.length() - 1, loop);
            nexts[0] = sub;
            return build(regex, start - 1, loop);
        }
        case ')':
            int index = matchingOpenBracket(regex, pos);
            String subregex = regex.substring(index + 1, pos);
            marker += 2;
            NdfaNode closeBracket = BasicNdfaNode.epsilonTo(marker-1, continuation);
            NdfaNode openBracket = BasicNdfaNode.epsilonTo(marker-2, buildAlt(subregex, closeBracket));
            return build(regex, index - 1, openBracket);
        case '.':
            return build(regex, pos - 1, BasicNdfaNode.dotTo(continuation));
        default:
            return build(regex, pos - 1, BasicNdfaNode.transitionTo(c, continuation));
        }
    }
    
    private static int argStart(String regex, int index) {
        if (regex.charAt(index) == ')')
            return matchingOpenBracket(regex, index);
        else return index;
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
       // System.out.println("children" + children.size());
        return children.size() == 1 ? children.get(0) : BasicNdfaNode.epsilonTo(-1, children);
    }
    
    public static NdfaNode build(String regex) {
        return buildAlt(regex, BasicNdfaNode.payload(1));
    }   
   
    public static void main(String[] args) {
        
        NdfaNode t = build("");
        System.out.println(Regex.match(t, ""));
        System.out.println(Regex.match(t, "a"));
        System.out.println(t);

        t = build("abc");
        System.out.println(Regex.match(t, "ab"));
        System.out.println(Regex.match(t, "bc"));
        System.out.println(Regex.match(t, "abc"));
        System.out.println(t);

        t = build("a*bc");
        System.out.println(Regex.match(t, "aaaaabc"));
        System.out.println(Regex.match(t, "abaabc"));
        System.out.println(Regex.match(t, "abbac"));
        System.out.println(Regex.match(t, "bc"));
        System.out.println(Regex.match(t, "aaaaabac"));
        System.out.println(t);
        
        t = build("a|bc");
        System.out.println(Regex.match(t, "aaaaabc"));
        System.out.println(Regex.match(t, "a"));
        System.out.println(Regex.match(t, "abbac"));
        System.out.println(Regex.match(t, "bc"));
        System.out.println(Regex.match(t, "aaaaabac"));
        System.out.println(t);

        t = build("e?");
        System.out.println(Regex.match(t, ""));
        System.out.println(Regex.match(t, "e"));
        System.out.println(Regex.match(t, "ee"));
        System.out.println(Regex.match(t, "a"));
        System.out.println(t);


        t = build("(a)");
        System.out.println(Regex.match(t, "a"));
        System.out.println(Regex.match(t, "e"));
        System.out.println(Regex.match(t, ""));
        System.out.println(t);

        t = build("(e?)");
        System.out.println(Regex.match(t, ""));
        System.out.println(Regex.match(t, "e"));
        System.out.println(Regex.match(t, "ee"));
        System.out.println(Regex.match(t, "a"));
        System.out.println(t);

        t = build("bc|a");
        System.out.println(Regex.match(t, ""));
        System.out.println(Regex.match(t, "a"));
        System.out.println(Regex.match(t, "bc"));
        System.out.println(Regex.match(t, "bca"));
        System.out.println(t);

        t = build("(a)");
        System.out.println(Regex.match(t, "a"));
        System.out.println(Regex.match(t, "e"));
        System.out.println(Regex.match(t, ""));
        System.out.println(t);

        

        t = build("(bc|a)*");
        System.out.println(Regex.match(t, ""));
        System.out.println(Regex.match(t, "aa"));
        System.out.println(Regex.match(t, "bca"));
        System.out.println(Regex.match(t, "bcbcabc"));
        System.out.println(Regex.match(t, "cabc"));
        System.out.println(t);

        t = build("(bc|a)*(e?)");
        System.out.println(Regex.match(t, "aaaaabc"));
        System.out.println(Regex.match(t, "a"));
        System.out.println(Regex.match(t, "abbac"));
        System.out.println(Regex.match(t, "bc"));
        System.out.println(Regex.match(t, "aaaaabac"));
        System.out.println(Regex.match(t, "abce"));
        System.out.println(Regex.match(t, "aaaaabace"));
        System.out.println(Regex.match(t, "aaaaaae"));
        System.out.println(Regex.match(t, "bcbce"));
        System.out.println(Regex.match(t, "bcbceee"));
        System.out.println(Regex.match(t, "eee"));
        System.out.println(Regex.match(t, ""));
        System.out.println(Regex.match(t, "e"));
        System.out.println(t);

        System.out.println(Regex.find("((c) (a) (t))", "abccat"));
    }
}
