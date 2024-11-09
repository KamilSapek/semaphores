import java.util.concurrent.Semaphore;

public class SemaphoreAdding {
    private static final int DELAY = 5;

    private static final Semaphore a = new Semaphore(1, true);
    private static final Semaphore b = new Semaphore(0, true);

    private static int l1;
    private static int l2;

    public static void main(String[] args) {
        new A().start(); //runs a thread defined below
        new B().start();
    }

    private static final class A extends Thread { //thread definition
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            try {
                a.acquire();
                l1 = 26;
                b.release();
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                System.out.println("Cos poszlo nie tak");
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
            System.out.println("\nThread A: I'm done...");
        }
    }

    private static final class B extends Thread { //thread definition
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            try {
                b.acquire();
                l2 = 40;
                System.out.println(l1 + l2);
                a.release();
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                System.out.println("Cos poszlo nie tak");
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
            System.out.println("\nThread B: I'm done...");
        }
    }
}