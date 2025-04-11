package com.memory.commutator;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestClass {
    public static void main(String[] args) {
        List<String> safeList = new CopyOnWriteArrayList<>();

        safeList.add("79930125779");
        safeList.add("79045404032");
        safeList.add("79021898067");
        safeList.add("79018917453");
        safeList.add("79580402301");
        safeList.add("79812345678");
        safeList.add("79123456789");
        safeList.add("79231234567");
        safeList.add("79451234567");
        safeList.add("79761234567");
        System.out.printf("List: %s\n", safeList);

        for (int i = 0; i < 5; i++) {
            new Thread(new ListWorker(safeList), "Worker-" + (i+1)).start();
        }
    }
    static class ListWorker implements Runnable {
        private final List<String> list;
        private final Random random = new Random();

        ListWorker(List<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    if (!list.isEmpty()) {
                        int index = random.nextInt(list.size());
                        String removed = list.remove(index);
                        System.out.println(Thread.currentThread().getName() + " удалил: " + removed);

                        Thread.sleep(100 + random.nextInt(200));

                        list.add(removed);
                        System.out.println(Thread.currentThread().getName() + " вернул: " + removed);

                        Thread.sleep(100);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
