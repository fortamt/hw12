package test1;

import java.lang.annotation.*;
import java.util.concurrent.*;

public class RepeatTask {
    public static void main(String[] strings) {
        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(10);
        customThreadPoolExecutor.execute(new MyRunnable());
    }
}

@Repeat(3)
class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("Hello!");
    }
}

class CustomThreadPoolExecutor extends ThreadPoolExecutor {
    public CustomThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize, corePoolSize,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Override
    public void execute(Runnable command) {
        int counter = 0;
        int counter1 = 0;
        try {
            Class repeatClass = Class.forName("test1.MyRunnable");
            Annotation annotation = repeatClass.getAnnotation(Repeat.class);
            Repeat repeat = (Repeat) annotation;
            counter = repeat.value();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while(counter1 < counter){
            command.run();
            counter1++;
        }


    }
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Repeat{
    int value();
}