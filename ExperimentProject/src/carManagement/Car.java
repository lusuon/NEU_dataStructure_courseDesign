package carManagement;

public class Car {
    int number;
    int arrive;
    int leave;

    public Car(int n,int a,int l){
        this.number = n;
        this.arrive = a;
        this.leave = l;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getArrive() {
        return arrive;
    }

    public void setArrive(int arrive) {
        this.arrive = arrive;
    }

    public int getLeave() {
        return leave;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    /**
     * Calculate the fee
     * @param fee
     * @return
     */
    public int calculate(int fee){
        return  fee*(leave-arrive);
    }

}
