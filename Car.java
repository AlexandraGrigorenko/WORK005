

import java.util.concurrent.BrokenBarrierException;

import java.util.concurrent.CountDownLatch;

import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {

    private static int CARS_COUNT;

    static {

        CARS_COUNT = 0;

    }

    private Race race;

    private int speed;

    private String name;

    private final CyclicBarrier barrier;

    private final CountDownLatch start;

    private final CountDownLatch finish;

    public String getName() {

        return name;

    }

    public int getSpeed() {

        return speed;

    }

    public Car(Race race, int speed, CyclicBarrier barrier, CountDownLatch start, CountDownLatch finish) {

        this.race = race;

        this.speed = speed;

        CARS_COUNT++;

        this.name = "Участник #" + CARS_COUNT;

        this.barrier = barrier;

        this.start = start;

        this.finish = finish;

    }

    @Override

    public void run() {

        try {

            System.out.println(this.name + " готовится");

            Thread.sleep(500 + (int)(Math.random() * 800));

            System.out.println(this.name + " готов");

            start.countDown();

            for (int i = 0; i < race.getStages().size(); i++) {

                if (i == 0) barrier.await();

                race.getStages().get(i).go(this);

                if (i == (race.getStages().size() - 1)){

                    finish.countDown();

                    if (race.getWin().tryLock()){

                        System.out.printf(" Победитель гонки %s\n", name);

                        finish.await();

                        race.getWin().unlock();

                    }

                }

            }

        } catch (InterruptedException | BrokenBarrierException e) {

            e.printStackTrace();

        }

    }

}