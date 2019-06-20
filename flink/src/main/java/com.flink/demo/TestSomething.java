package com.flink.demo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Phaser;
import java.util.concurrent.RecursiveAction;

/**
 * @author laisy
 * @date 2019/6/19
 * @description
 */
public class TestSomething {

    public static void main(String[] args) {
        //返回可用处理器的虚拟机的最大数量,通常来说是硬件线程数, 适当地调整自己的资源使用情况
        System.out.println(Runtime.getRuntime().availableProcessors());
        excuteForkJoinPool(262);
    }

    /**
     * Java 8里，我们有一个通用的Fork/Join池
     */
    public static void excuteForkJoinPool(int parties) {
        ForkJoinPool common = ForkJoinPool.commonPool();
        Phaser phaser = new Phaser(parties);
        common.invoke(new PhaserWaiter(phaser));
    }

    private static class PhaserWaiter extends RecursiveAction {
        private final Phaser phaser;

        private PhaserWaiter(Phaser phaser) {
            this.phaser = phaser;
            System.out.println(ForkJoinPool.commonPool().getPoolSize());
        }

        @Override
        protected void compute() {
            if (phaser.getPhase() > 0) return; // we've passed first phase
            PhaserWaiter p1 = new PhaserWaiter(phaser);
            p1.fork();
            phaser.arriveAndAwaitAdvance();
            p1.join();
        }
    }
}