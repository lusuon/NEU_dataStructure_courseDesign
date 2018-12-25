package carManagement;

import java.util.LinkedList;
import java.util.Queue;

public class Parking_lot {
    private CarStack park;
    private CarStack temp;
    private Queue<Car> waiting;
    private static int INIT_PARK_SPACE = 10;
    private int time;

    private static Parking_lot parking_lot = new Parking_lot();
    public static Parking_lot getParking_lot(){
        return parking_lot;
    }

    private Parking_lot(){
        park = new CarStack(INIT_PARK_SPACE);
        temp = new CarStack(INIT_PARK_SPACE);
        waiting = new LinkedList<Car>();
    }


    public Parking_lot(int space){
        park = new CarStack(space);
        temp = new CarStack(space);
        waiting = new LinkedList<Car>();
    }

    public void in(int number,int arrive,int leave){
        in(new Car(number,arrive,leave));
    }

    public void in(Car c){
        try {
            park.push(c);//已满，加入失败，进入等待
        }catch (Exception e){
            waiting.add(c);
        }
    }

    public void out(int number){
        if(!park.find(number)){
            System.out.println("The car is not in the parking lot.");
        }else{
            Car popped = null;
            while(true){
                popped= park.pop();
                if(popped.getNumber()==number){
                    break;
                }
                temp.push(popped);
            }
            while(temp.top()!=null){
                park.push(temp.pop());
            }
            //未计算费用

            System.out.println("The popped car's number:"+popped.getNumber());
        }

        if(waiting.peek()!=null){
            park.push(waiting.poll());
        }
    }

    public void showParking(){
        park.showStack();
    }

    public void showWaiting(){
        for (Car c:waiting) {
            System.out.print(c.number+" and ");
        }
    }

    public static void main(String args[]){
        String command = null;
        int number = 1000;
        int arrive = 1;
        int leave = 2;
        for(int i = 0;i<20;i++){
            parking_lot.in(number,arrive,leave);
            number++;
            arrive++;
            leave++;
        }
        //parking_lot.showParking();
        parking_lot.showWaiting();
        System.out.println("end");
    }

}
