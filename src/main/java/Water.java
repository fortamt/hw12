import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Water {

    public static void main(String[] args) {
        Thread H1 = new Thread(Atoms::releaseHydrogen);
        H1.start();
        Thread H2 = new Thread(Atoms::releaseHydrogen);
        H2.start();
        Thread O1 = new Thread(Atoms::releaseOxygen);
        O1.start();


    }
}

class Atoms{
    public static final CyclicBarrier BARRIER = new CyclicBarrier(3, new MadeWater());
    public static void releaseHydrogen(){
        while(true) {
            try {
                BARRIER.await();
                System.out.println("H");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void releaseOxygen(){
        while(true) {
            try {
                BARRIER.await();
                System.out.println("O");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }

    private static class MadeWater implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Молекула воды готова");

        }
    }
}

