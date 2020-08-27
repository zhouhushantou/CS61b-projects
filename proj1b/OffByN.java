public class OffByN implements CharacterComparator {

    int N=0;
    public OffByN(int Nin){
        N=Nin;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if ((x-y)==N||(x-y)==-N){
            return true;
        }
        return false;
    }
}
