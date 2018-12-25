/**
 * models a rail service from the rail system. 
 * This class contains public data members for a destination city, a fee, and a distance. 
 */

public class Road {
    private Scenery goal;
    private int fee;
    private int distance;

    public Road(Scenery g, int f, int d){
        this.distance = d;
        this.fee = f;
        this.goal = g;
    }



    public Scenery getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = new Scenery(goal);
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
