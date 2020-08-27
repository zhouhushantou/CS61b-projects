public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        ArrayDeque<Character> A=new ArrayDeque<>();
        for (int i=0;i<word.length();i++) {
            A.addLast(word.charAt(i));
        }
        return A;
    }

    public boolean isPalindrome(String word){
        if (word.length()<2){
            return true;
        }
        Deque<Character> A=wordToDeque(word);
        for (int i=0;i<word.length();i++) {
            if (A.removeLast()!=word.charAt(i)){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> A=wordToDeque(word);
        for(int i=0;i<word.length()/2;i++){
            if (cc.equalChars(A.removeLast(),word.charAt(i))==false){
                return false;
            }
        }
        return true;
    }
}
