package carManagement;


import java.util.LinkedList;

public class CarStack {
    private final int defaultSize = 10;
    private int top;
    private int size;
    private Car[] elements;

    /**
     * 初始化栈,默认大小为10
     */
    public CarStack() {
        initStack(defaultSize);
    }

    /**
     * 初始化指定大小的栈
     * @param size 指定栈大小
     */
    public CarStack(Integer size) {
        initStack(size);
    }

    /**
     * 初始化栈
     * @param size 给定的栈大小
     */
    private void initStack(Integer size) {
        this.size = size;
        top = 0;
        elements = new Car[size];
    }

    /**
     * 清空栈
     */
    
    public void clear() {
        top = 0;
    }

    /**
     * 进栈
     * @param element 进栈的元素
     */
    
    public void push(Car element) {
        sizeCheckForPush();
        elements[top++] = element;
    }

    /**
     * 弹出栈顶元素 ，并改变指针
     * @return 栈顶元素
     */
    
    public Car pop() {
        sizeCheckForPop();
        return (Car) elements[--top];
    }
    /**
     * 返回栈顶元素 ，不改变指针
     * @return 栈顶元素
     */
    
    public Car top() {
        sizeCheckForPush();
        return (Car) elements[top - 1];
    }

    /**
     * 判断是否为空栈
     * @return true为空栈
     */
    
    public Boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在进栈的时候检查
     */
    private void sizeCheckForPush() {
        if (top >= size) {
            System.out.print(size);
            throw new RuntimeException("Stack overflow");
        }
    }

    /**
     * 退栈检查
     */
    private void sizeCheckForPop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
    }

    public boolean find(int number){
        for (Car c:elements) {
            if(c.getNumber()==number){
                return true;
            }
        }
        return false;
    }

    public void showStack(){
        for (Car c:elements) {
            System.out.print(c.getNumber()+" ");
        }
    }
}
