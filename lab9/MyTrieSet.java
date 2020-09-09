import java.util.*;

public class MyTrieSet implements TrieSet61B{

    public class Node {
        boolean isKey;
        char value;
        HashMap<Character, Node> link;

        //Node constructor
        public Node(boolean isKey, char value) {
            this.isKey = isKey;
            this.value = value;
            link = new HashMap<>(5);
        }

        public void colHelp(String s, List<String> result) {
            if (this.isKey) {
                result.add(s + this.value);
            }
            Set<Character> keys = link.keySet();
            Iterator<Character> itr = keys.iterator();
            while (itr.hasNext()) {
                Node str = link.get(itr.next());
                str.colHelp(s + this.value, result);
            }

        }
    }
    Node root;
    public MyTrieSet(){
        root=new Node(false,'a');
    }
    /** Clears all items out of Trie */
    public void clear(){
        root.link.clear();
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key){
        Node nd = root;
        int i;
        for (i = 0; i < key.length(); i++) {
            if (nd.link.containsKey(key.charAt(i)))
                nd = nd.link.get(key.charAt(i));
            else
                return false;
        }
        return true;
    }

    /** Inserts string KEY into Trie */
    public void add(String key) {
        Node nd = root;
        int i;
        for (i = 0; i < key.length(); i++) {
            if (nd.link.containsKey(key.charAt(i)))
                nd = nd.link.get(key.charAt(i));
            else
                break;
        }
        //it already contains the key
        if (i == key.length() ) {
            nd.isKey = true;
            return;
        }

        for (int j = i; j < key.length(); j++) {
            nd.link.put(key.charAt(j), new Node(j == (key.length() - 1), key.charAt(j)));
            nd = nd.link.get(key.charAt(j));
        }
    }

    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix){
        List<String> result=new ArrayList<>();
        Node nd = root;
        int i;
        for (i = 0; i < prefix.length(); i++) {
            if (nd.link.containsKey(prefix.charAt(i)))
                nd = nd.link.get(prefix.charAt(i));
            else
                return result;
        }
        nd.colHelp(prefix.substring(0,prefix.length()-1),result);
        return result;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key){
        return ("hello");
    }

    public static void main(String[] args) {
       MyTrieSet A=new MyTrieSet();
       A.add("hello");
       A.add("hell");
    }
}
