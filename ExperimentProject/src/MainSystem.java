import java.io.*;
import java.util.*;

/**
 * models the rail system using an adjacency list representation. 
 */

public class MainSystem {
    private HashMap<String, Scenery> cityList = new HashMap<>();   //图
    private ArrayList<Scenery> cities = new ArrayList<>(); //
    public HashMap<String, Scenery> getCityList() {
        return cityList;
    }
    public HashMap<String, Scenery> load_services() throws IOException {
        String pathname = "services_change.txt";
        File filename = new File(pathname);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        int id = 0;
        ArrayList<String> added = new ArrayList<>();

        line = br.readLine();


        while (line != null) {
            String[] infos = line.split(" ");
            String start = infos[0];
            String end = infos[1];
            int fare = Integer.parseInt(infos[2]);
            int distance = Integer.parseInt(infos[3]);
            Scenery toBeAdd = null;


            //如果无城市，则添加
            if (!added.contains(start)||!added.contains(end)) {
                if (!added.contains(start)) {
                    added.add(start);
                    toBeAdd = new Scenery(start);
                    cityList.put(start, toBeAdd);
                    cities.add(toBeAdd);
                }
                if (!added.contains(end)) {
                    added.add(end);
                    toBeAdd = new Scenery(end);
                    cityList.put(end, toBeAdd);
                    cities.add(toBeAdd);
                }
            }
            Road s1 = new Road(cityList.get(end),fare,distance);
            Road s2 = new Road(cityList.get(start),fare,distance);
            cityList.get(start).getAdj().add(s1);
            cityList.get(end).getAdj().add(s2);
            line = br.readLine();
        }
            return cityList;
    }

    public void reset(HashMap<String, Scenery> cityList){
        for (Map.Entry<String, Scenery> entry:cityList.entrySet()){
            entry.getValue().setDist(Integer.MAX_VALUE);
            entry.getValue().setKnown(false);
            entry.getValue().setPath(null);
        }
    }

    public void OutputGraph(HashMap<String, Scenery> cityList){
        for (Map.Entry<String, Scenery> entry:cityList.entrySet()){
            System.out.println(entry.getKey()+":");
            for (Road s:entry.getValue().getAdj()) {
                System.out.println("\t"+String.format("to:%s,fee:%d,distance:%d",s.getGoal().getName(),s.getFee(),s.getDistance()));
            }
        }
    }

    public int[][] OutputMatrix(HashMap<String, Scenery> cityList){
        //遍历邻接表，无法get到则输出32767
        //如何处理下表与地名的对应，考虑建立ArrayList，下标映射
        System.out.println("initalizing the matrix");
        // 初始化数组，全32767
        int[][] matrix = new int[cityList.size()][cityList.size()];
        for (int[] row:matrix) {
            for(int i =0;i<cityList.size();i++)
                row[i]=32767;
        }


        //第一行：列名
        System.out.print("\t");
        for (Scenery scenery :cities) {
            System.out.print(scenery.getName()+"\t");
        }
        System.out.println("");


        for (int i=0;i<cities.size();i++){
            Scenery current = cities.get(i);
            //到自己的距离为0
            matrix[i][cities.indexOf(current)] = 0;
            for (Road s:current.getAdj()) {
                matrix[i][cities.indexOf(s.getGoal())] = s.getDistance();
            }
            System.out.print(current.getName()+"\t");
            for (int dist:matrix[i]) {
                System.out.print(dist+"\t");
            }
            System.out.println("");
        }
        return matrix;
    }


    public String recover_route(HashMap<String, Scenery> cityList, String start, String end){
        int distance = 0;
        Scenery startScenery = cityList.get(start);
        Scenery endScenery = cityList.get(end);
        int fee = endScenery.getDist();
        StringBuilder wholePath = new StringBuilder();
        if(endScenery.getPath()==null) return "Can not reach.";

        Stack<String> pathStack = new Stack<>();
        while(endScenery.getPath()!=null){
            Scenery current = endScenery;
            endScenery = endScenery.getPath();
            for(Road s: endScenery.getAdj()){
                if(s.getGoal()==current){
                    distance+=s.getDistance();
                    pathStack.push(s.getGoal().getName());
                }
            }
        }
        pathStack.push(start);
        while(pathStack.size()!=0) {
            wholePath.append(pathStack.pop());
            if (pathStack.size()==0) {
                continue;
            } else {
                wholePath.append(" to ");
            }
        }
        return String.format("The smallest cost : %d \n length: %d \n path: %s",fee,distance,wholePath.toString());
    }
    public void calc_route(HashMap<String, Scenery> cityList, String start){
        Scenery startScenery = cityList.get(start);
        if(start==null) throw new NoSuchElementException();
        PriorityQueue<Scenery> notKnown = new PriorityQueue<>();
        startScenery.setDist(0);
        //向堆加入所有节点
        for (Map.Entry<String, Scenery> entry:cityList.entrySet()){
            notKnown.add(entry.getValue());
        }
        while(notKnown.size()!=0){
            Scenery smallestDist = notKnown.poll();
            smallestDist.setKnown(true);
            for (Road road :smallestDist.getAdj()) {
                Scenery adj = road.getGoal();
                if(!adj.isKnown()){
                    int newDist = smallestDist.getDist()+ road.getFee();
                    if(newDist < adj.getDist()){
                        //更新adj的路径
                        adj.setDist(newDist);

                        //刷新adj在优先队列内的位置
                        notKnown.remove(adj);
                        notKnown.add(adj);

                        adj.setPath(smallestDist);
                    }
                }
            }
        }
    }

    public static void main(String args[]) throws IOException {
        MainSystem rs = new MainSystem();
        HashMap<String, Scenery> cityList = rs.load_services();
        rs.reset(cityList);

        rs.OutputMatrix(cityList);


        /*
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String start = null;
        String end = null;
        System.out.println("\nEnter the start:");
        start = br.readLine();
        System.out.println("Enter the goal:");
        end =  br.readLine();
        rs.calc_route(cityList,start);
        System.out.println(rs.recover_route(cityList,start,end));
        */
    }
}
    
