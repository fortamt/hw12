import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Water {
    static Semaphore semaphoreH = new Semaphore(2);
    static Semaphore semaphoreO = new Semaphore(1);
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    public static void main(String[] args) {
            Thread threadH = new Thread(Water::madeH);
            threadH.start();
            Thread threadO = new Thread(Water::madeO);
            threadO.start();
    }

    public static void madeH(){
        int counter = 0;
        while (true) {
            try {
                semaphoreH.acquire();
                System.out.println("H");
                counter++;
                if(counter == 2){
                    cyclicBarrier.await();
                    counter = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            semaphoreH.release();
        }
    }

    public static void madeO(){
        while(true) {
            try {
                semaphoreO.acquire();
                System.out.println("O");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            semaphoreO.release();
        }
    }
}

