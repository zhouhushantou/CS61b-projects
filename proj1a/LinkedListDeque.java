public class LinkedListDeque<T> {
    private int size=0;
    Node sentinel=new Node();

    //Constructor with no input parameter
    public LinkedListDeque(){
        sentinel.next=sentinel;
    }

    public LinkedListDeque(LinkedListDeque other){
        sentinel.next=sentinel;
        if (other.size()==0){
            return;
        }
        Node L= other.sentinel.next;
        Node J=sentinel;
        Node A;
        while (L!= other.sentinel){
            A=new Node();
            A.first=L.first;
            A.prev=J;
            J.next=A;
            J=A;
            L=L.next;
        }
        J.next=sentinel;
        sentinel.prev=J;
        return;
    }

    public class Node {
        public T first;
        public Node prev;
        public Node next;
    }

    public void addFirst(T item){
        Node A=new Node();
        A.first=item;
        A.prev=sentinel;
        if (this.size()==0){
            sentinel.next=A;
            sentinel.prev=A;
            A.next=sentinel;
        }
        else {
            sentinel.next.prev=A;
            A.next=sentinel.next;
            sentinel.next=A;
        }
        size++;
    }

    public void addLast(T item){
        Node A=new Node();
        A.first=item;

        if (this.size()==0){
            A.prev=sentinel;
            sentinel.next=A;
        }
        else {
            sentinel.prev.next=A;
            A.prev=sentinel.prev;
        }
        A.next=sentinel;
        sentinel.prev=A;
        size++;
    }

    public T removeFirst(){
        if (this.size()==0){
            return null;
        }
        Node L=sentinel.next;
        sentinel.next=L.next;
        L.next.prev=sentinel;
        L.prev=null;
        L.next=null;
        size--;
        return L.first;
    }

    public T removeLast(){
        if (this.size()==0){
            return null;
        }
        Node L=sentinel.prev;
        sentinel.prev=L.prev;
        L.prev.next=sentinel;
        L.next=null;
        L.prev=null;
        size--;
        return L.first;
    }

    public T get(int index){
        if (index>this.size()||index<0){
            return null;
        }
        if (this.size()==0){
            return null;
        }
        int i=0;
        Node L=sentinel.next;
        while(i<index){
            L=L.next;
            i++;
        }
        return L.first;
    }
    //return true if the list is empty
    public boolean isEmpty(){
        if (this.size()==0){
           return true;
        }
        else {
            return false;
        }
    }

    public void printDeque(){
        Node L=sentinel.next;
        while(L!=sentinel){
            System.out.println(L.first);
            L=L.next;
        }
    }

    //get size
    public int size(){
        return size;
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> LLD = new LinkedListDeque<>();
        //LLD.printDeque();
        //System.out.println(LLD.isEmpty());
        LLD.addLast(2);
        LLD.addFirst(10);
        LLD.addLast(5);
        LinkedListDeque<Integer> LLD2 = new LinkedListDeque<>(LLD);
        LLD2.printDeque();
    }
}
