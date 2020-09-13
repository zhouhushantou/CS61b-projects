import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class SeparableEnemySolver {

    Graph g;
    boolean seperatable;
    Map<String,assist> nodes;
    /**
     * Creates a SeparableEnemySolver for a file with name filename. Enemy
     * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
     */
    SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
        this.g = graphFromFile(filename);
    }

    /** Alterntive constructor that requires a Graph object. */
    SeparableEnemySolver(Graph g) {
        this.g = g;
    }

    /**
     * Returns true if input is separable, false otherwise.
     */
    public boolean isSeparable() {
        seperatable=true;
        Set<String> nd= g.labels();
       nodes =new HashMap<>();
        for(String s:nd){
            nodes.put(s,new assist(false,0));
        }

        for(String s:nd) {
            if (nodes.get(s).group == 0) {
                nodes.put(s, new assist(true, 1));
            }
            findNeighbor(s, 0);
            if (!seperatable)
                return seperatable;
        }
        return seperatable;
    }

    private void findNeighbor(String s,int group){
        Set<String> nb;
        if (!nodes.get(s).mark) {
            if (group == 1)
                nodes.put(s, new assist(true, 1));
            else
                nodes.put(s, new assist(true, 2));
        }
        else {
            if ((group == 1) && (nodes.get(s).group == 2)) {
                seperatable = false;
                return;
            }
            if ((group == 2) && (nodes.get(s).group == 1)) {
                seperatable = false;
                return;
            }
        }
        nb = g.neighbors(s);
        for (String sn : nb) {
            if(nodes.get(sn).mark){
                if (nodes.get(sn).group==nodes.get(s).group) {
                    seperatable = false;
                }
                continue;
            }
            if (nodes.get(s).group == 1) {
                findNeighbor(sn, 2);
            }
            else
                findNeighbor(sn, 1);
        }
    }

    public class assist{
        boolean mark=false;
        int group=0;
        public assist(boolean mark, int group){
            this.mark=mark;
            this.group=group;
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        g.connect("A", "B");
        g.connect("C", "D");
        g.connect("E", "D");
        g.connect("E", "C");
        SeparableEnemySolver A=new SeparableEnemySolver(g);
        A.isSeparable();
    }


    /* HELPERS FOR READING IN CSV FILES. */

    /**
     * Creates graph from filename. File should be comma-separated. The first line
     * contains comma-separated names of all people. Subsequent lines each have two
     * comma-separated names of enemy pairs.
     */
    private Graph graphFromFile(String filename) throws FileNotFoundException {
        List<List<String>> lines = readCSV(filename);
        Graph input = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                for (String name : lines.get(i)) {
                    input.addNode(name);
                }
                continue;
            }
            assert(lines.get(i).size() == 2);
            input.connect(lines.get(i).get(0), lines.get(i).get(1));
        }
        return input;
    }

    /**
     * Reads an entire CSV and returns a List of Lists. Each inner
     * List represents a line of the CSV with each comma-seperated
     * value as an entry. Assumes CSV file does not contain commas
     * except as separators.
     * Returns null if invalid filename.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        return records;
    }

    /**
     * Reads one line of a CSV.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next().trim());
        }
        return values;
    }

    /* END HELPERS  FOR READING IN CSV FILES. */

}
