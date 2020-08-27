public class ArrayDeque <T> implements Deque<T>{
    private int size=0;
    private int nextFirst=0;
    private int nextLast=1;
    private int arraySize=8;
    T[] A = (T[]) new Object[arraySize];
    public void ArrayDeque() {
    }

    @Override
    public void printDeque(){
        if(size()==0){
            return;
        }
       int i=next(nextFirst);
        while (i!=nextLast){
            System.out.println(A[i]);
            i=next(i);
        }
    }

    private void checkSize(){
        if (size==(arraySize-1)){
            resize(2*arraySize);
        }
        if (size<(arraySize*0.3)){
            resize(arraySize/2);
        }
    }

    @Override
    public void addFirst(T item){
        A[nextFirst]=item;
        nextFirst=prev(nextFirst);
        size++;
        checkSize();
    }

    @Override
    public void addLast(T item){
        A[nextLast]=item;
        nextLast=next(nextLast);
        size++;
        checkSize();
    }

    @Override
    public T removeFirst(){
        nextFirst=next(nextFirst);
        size--;
        T temp=A[nextFirst];
        checkSize();
        return temp;
    }

    @Override
    public T removeLast(){
        nextLast=prev(nextLast);
        size--;
        T temp=A[nextLast];
        checkSize();
        return temp;
    }

    //the circular array traverse next
    private int next(int i){
        if ((i+1)==arraySize){
            return 0;
        }
        return i+1;
    }

    //the circular array traverse prev
    private int prev(int i){
        if (i==0){
            return arraySize-1;
        }
        return i-1;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public T get(int i){
        int j=next(nextFirst);
        int k=0;
        while (k<i){
            j=next(j);
            k++;
        }
        return A[j];
    }

    private void resize(int newSize){
        if (newSize<8){
            return;
        }
        T[] B = (T[]) new Object[newSize];
        int j=0;
        int i=next(nextFirst);
        while (i!=nextLast){
            B[j]=A[i];
            j++;
            i=next(i);
        }
        nextFirst=newSize-1;
        nextLast=j;
        A=B;
        arraySize=newSize;
    }

     public static void main(String[] args) {
        ArrayDeque<Integer> AD=new ArrayDeque<>();
        AD.addFirst(10);
        AD.addLast(5);
        AD.addFirst(3);
         AD.addFirst(4);
         AD.addFirst(10);
         AD.addLast(5);
         AD.addFirst(3);
         AD.addFirst(4);
         int B=AD.get(5);
        AD.printDeque();
    }
}
