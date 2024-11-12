import java.util.concurrent.Semaphore;

public class SemaphoreAdding {
    private static final int DELAY = 5;

    private static final Semaphore a = new Semaphore(1, true);
    private static final Semaphore b = new Semaphore(0, true);
    private static final Semaphore c = new Semaphore(0, true);
    private static final Semaphore d = new Semaphore(0, true);

    private static int A = 0;
    private static int B = 0;
    private static int C = 3;

    public static void main(String[] args) {
        new A().start(); //runs a thread defined below
        new B().start();
        new C().start();
        new D().start();
    }

    private static final class A extends Thread { //thread definition
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            try {
                a.acquire();
                A = 10;
                b.release();
                a.acquire();
                B = B + 5;
                C = C + A;
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
                B = B + C;
                c.release();
                b.acquire();
                A = A + B;
                Thread.sleep(DELAY);
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
                c.acquire();
                C = B + 10;
                d.release();
                c.acquire();
                A = 2 * A;
                B = B + A;
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                System.out.println("Cos poszlo nie tak");
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
            System.out.println("\nThread C: I'm done...");
        }
    }

    private static final class D extends Thread { //thread definition
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            try {
                d.acquire();
                System.out.println(A + B + C);
                a.release();
                b.release();
                c.release();
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                System.out.println("Cos poszlo nie tak");
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
            System.out.println("\nThread D: I'm done...");
        }
    }
}
