package lock;

public class LockTest {

    public static void main(String[] args) {

        Clerk clerk = new Clerk();
        Producer producer = new Producer(clerk);
        Customer customer = new Customer(clerk);

        new Thread(producer, "生产者A").start();
        new Thread(customer, "消费者A").start();
        new Thread(producer, "生产者B").start();
        new Thread(customer, "消费者B").start();
    }
}

class Clerk {

    private int product = 0;

    // 进货
    public synchronized void add() {
        // 产品已满
        while (product >= 1) {
            System.out.println(Thread.currentThread().getName() + ": " + "已满！");
            try {
                this.wait();
                System.out.println("after wait....");
            } catch (InterruptedException e) {

            }
        }
        ++product;
        // 该线程从while中出来的时候，是满足条件的
        System.out.println(Thread.currentThread().getName() + ": " + "....................进货成功，剩下" + product);
        this.notifyAll();
    }

    // 卖货
    public synchronized void sale() {
        while (product <= 0) {
            System.out.println(Thread.currentThread().getName() + ": " + "没有买到货");
            try {
                this.wait();
                System.out.println("after wait...");
            } catch (InterruptedException e) {

            }
        }
        --product;
        System.out.println(Thread.currentThread().getName() + ":买到了货物，剩下 " + product);
        this.notifyAll();
    }
}

class Producer implements Runnable {

    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    // 进货
    @Override
    public void run() {
        for (int i = 0; i < 20; ++i) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            clerk.add();
        }
    }
}

class Customer implements Runnable {

    private Clerk clerk;

    public Customer(Clerk clerk) {
        this.clerk = clerk;
    }

    // 买货
    @Override
    public void run() {
        for (int i = 0; i < 20; ++i) {
            clerk.sale();
        }
    }
}
