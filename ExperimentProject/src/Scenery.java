import java.util.LinkedList;

/**
 * Maintains information about a city.
 * This class can be define as a vertex.
 *
 */

public class Scenery implements Comparable{
    private String name;
    private LinkedList<Road> adj;//adj list
    private boolean known;//the status of the shortest path
    private Scenery path ;//the former of shortest path
    private int dist;

    public Scenery(String n){
        this.name = n;
        this.adj = new LinkedList<Road>();
    }

    
    public int getFee(String cityName){
        for (Road s:adj) {
            if(s.getGoal().equals(name)) return s.getFee();
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Road> getAdj() {
        return adj;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdj(LinkedList<Road> adj) {
        this.adj = adj;
    }

    public boolean isKnown() {
        return known;
    }
    public void setKnown(boolean known) {
        this.known = known;
    }
    public Scenery getPath() {
        return path;
    }
    public void setPath(Scenery path) {
        this.path = path;
    }
    public int getDist() {
        return dist;
    }
    public void setDist(int dist) {
        this.dist = dist;
    }

    @Override
    public int compareTo(Object o) {
        Scenery otherScenery = (Scenery) o;
        /*
        if (this.getDist()-otherScenery.getDist()<0) return -1;
        else if(this.getDist()-otherScenery.getDist()>0) return 1;
        else return 0;
        */
        return this.getDist()- otherScenery.getDist();
    }

}
