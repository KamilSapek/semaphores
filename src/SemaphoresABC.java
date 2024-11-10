import java.util.concurrent.Semaphore;

public class SemaphoresABC {
    private static final int COUNT = 10;
    private static final int DELAY = 5;

    private static final Semaphore a = new Semaphore(1, true);
    private static final Semaphore b = new Semaphore(0, true);
    private static final Semaphore c = new Semaphore(0, true);

    public static void main(String[] args) {
        new A().start(); //runs a thread defined below
        new B().start();
        new C().start();
    }

    private static final class A extends Thread { //thread definition
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            try {
                for (int i = 0; i < COUNT; i++) {
                    a.acquire();
                    System.out.println("A ");
                    System.out.println("A ");
                    b.release();
                    Thread.sleep(DELAY);
                }
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
                for (int i = 0; i < COUNT ; i++) {
                    b.acquire();
                    System.out.println("B ");
                    c.release();
                    Thread.sleep(DELAY);
                }
            } catch (InterruptedException ex) {
                System.out.println("Cos poszlo nie tak");
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
            System.out.println("\nThread B: I'm done...");
        }
    }

    private static final class C extends Thread { //thread definition
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            try {
                for (int i = 0; i < COUNT; i++) {
                    c.acquire();
                    System.out.println("C ");
                    a.release();
                    Thread.sleep(DELAY);
                }
            } catch (InterruptedException ex) {
                System.out.println("Cos poszlo nie tak");
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
            System.out.println("\nThread C: I'm done...");
        }
    }
}