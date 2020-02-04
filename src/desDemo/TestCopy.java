package desDemo;

public class TestCopy {
    public static void main(String[] args) {
        int a = 5;
        add(a);
        System.out.println(a);
        Worker worker = new Worker();
        worker.age = 5;
        addWorker(worker);
        System.out.println(worker.age);
    }

    public static void add(int a){
        a = a+1;
    }

    static class Worker{
        public int age;
    }

    public static void addWorker(Worker worker){
        worker.age = worker.age + 1;
    }
}
