package com.jamie.concurrency;

import com.jamie.ThreadUtil;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AS {

    /**
     * 多线程处理任务
     */
    @Test
    public void update() throws InterruptedException {
        String[] arr = new String[]{"5f27d333f8838642d9fc764b", "5f27d333f8838642d9fc764c", "5f27d333f8838642d9fc764d", "5f27d333f8838642d9fc764e", "5f27d333f8838642d9fc764f", "5f27d334f8838642d9fc7650", "5f27d334f8838642d9fc7651", "5f27d334f8838642d9fc7652", "5f27d334f8838642d9fc7653", "5f27d334f8838642d9fc7654", "5f27d334f8838642d9fc7655", "5f27d334f8838642d9fc7656", "5f27d334f8838642d9fc7657", "5f27d334f8838642d9fc7658", "5f27d334f8838642d9fc7659", "5f27d334f8838642d9fc765a", "5f27d334f8838642d9fc765b", "5f27d334f8838642d9fc765c", "5f27d334f8838642d9fc765d", "5f27d334f8838642d9fc765e", "5f27d334f8838642d9fc765f", "5f27d334f8838642d9fc7660", "5f27d334f8838642d9fc7661", "5f27d334f8838642d9fc7662", "5f27d334f8838642d9fc7663", "5f27d334f8838642d9fc7664", "5f27d334f8838642d9fc7665", "5f27d335f8838642d9fc7666", "5f27d335f8838642d9fc7667", "5f27d335f8838642d9fc7668", "5f27d335f8838642d9fc7669", "5f27d335f8838642d9fc766a", "5f27d335f8838642d9fc766b", "5f27d335f8838642d9fc766d", "5f27d335f8838642d9fc766c", "5f27d336f8838642d9fc766e", "5f27d336f8838642d9fc766f", "5f27d336f8838642d9fc7670", "5f27d336f8838642d9fc7671", "5f27d336f8838642d9fc7672", "5f27d336f8838642d9fc7673", "5f27d336f8838642d9fc7674"};
        List<String> ids = Arrays.asList(arr);

        int total = ids.size();

        //每个线程处理 5 条
        int threadCount = 5;
        //分成 segment 份，需要 segment 个线程处理
        int segment = (int) Math.ceil((double) total / threadCount);

        System.out.println("数据一共有 " + total + " 条, 每个线程处理 " + threadCount + " 条数据, 需要 " + segment + " 个线程处理");

        List<MKTask> taskList = new ArrayList<>();
        for (int i = 0; i < segment; i++) {
            int start = i * threadCount;
            int end = (i + 1) * threadCount;
            if (i == segment - 1) {
                end = total;
            }
            List<String> subList = ids.subList(start, end);

            MKTask mkTask = new MKTask(subList);
            taskList.add(mkTask);
            ThreadUtil.execute(mkTask);
        }

        while (true) {
            TimeUnit.SECONDS.sleep(2);
            //每秒打印一次各个线程的处理进度
            int sum = 0;
            for (MKTask mkTask : taskList) {
                int cur = mkTask.getCur();
                sum = sum + cur;
            }
            DecimalFormat dF = new DecimalFormat("0.00");
            float f = (float) sum / total;

            System.out.println("任务处理进度 " + dF.format(f * 100) + "%");
        }
    }
}

