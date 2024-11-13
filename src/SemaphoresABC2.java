import java.util.concurrent.Semaphore;

public class SemaphoresABC2 {
    private static final int COUNT = 10;
    private static final int DELAY = 5;

    private static final Semaphore a = new Semaphore(1, true);
    private static final Semaphore b = new Semaphore(0, true);
    private static final Semaphore c = new Semaphore(0, true);

    public static void main(String[] args) {
        new A().start(); //runs a thread defined below
        new C().start();
        new B().start();
    }

    private static final class A extends Thread {
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            try {
                for (int i = 0; i < COUNT * 2; i++) {
                    a.acquire();
                    System.out.print("A ");
                    c.release();
                }
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                System.out.println("Cos poszlo nie tak");
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
            System.out.println("\nThread A: I'm done...");
        }
    }

    private static final class B extends Thread {
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            try {
                for (int i = 0; i < COUNT; i++) {
                    b.acquire();
                    System.out.print("B ");
                    a.release();
                }
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                System.out.println("Cos poszlo nie tak");
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
            System.out.println("\nThread B: I'm done...");
        }
    }

    private static final class C extends Thread {
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            try {
                for (int i = 0; i < COUNT * 2; i++) {
                    c.acquire();
                    System.out.print("C ");
                    if (i % 2 != 0) {
                        a.release();
                    } else {
                        b.release();
                    }
                }
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                System.out.println("Cos poszlo nie tak");
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
            System.out.println("\nThread C: I'm done...");
        }
    }
}