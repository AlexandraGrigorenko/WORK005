
import java.util.concurrent.Semaphore;

import java.util.concurrent.CountDownLatch;

import java.util.concurrent.CyclicBarrier;

public class Main {

    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        CountDownLatch start = new CountDownLatch(CARS_COUNT);

        CyclicBarrier barrierStart = new CyclicBarrier(CARS_COUNT);

        CountDownLatch finish = new CountDownLatch(CARS_COUNT);

        Semaphore semaphore = new Semaphore(CARS_COUNT / 2);

        Race race = new Race(new Road(60), new Tunnel(semaphore), new Road(40));

        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {

            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), barrierStart, start, finish);

            new Thread(cars[i]).start();

        }

        try {

            start.await();

            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

            finish.await();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

    }

}


