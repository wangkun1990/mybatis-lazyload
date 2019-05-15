package com.mybatis.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 分配算法
 */
public class AllocationAlgorithm {


    /**
     * 随机平均分配算法
     * @param hosts
     * @param tasks
     * @return
     */
    public static Map<String, List<String>> randomAllocationAverage(List<String> hosts, List<String> tasks) {


        int hostSize = CollectionUtils.size(hosts);
        int taskSize = CollectionUtils.size(tasks);
        Map<String, List<String>> result = new HashMap<>();
        if (hostSize > 0 && taskSize > 0) {
            List<String> tasksCopy = Lists.newArrayList(tasks);
            if (hostSize == 1) {
                result.put(hosts.get(0), tasks);
            } else if (hostSize > taskSize) {
                /**
                 * 主机数比任务数多,每个主机分配到一个任务,有主机没有分配到任务,实现方式可以有多种
                 * 1.使用同一个随机数,获取相同下标数据
                 * 2.使用两个不同的随机数，分别获取主机和任务
                 */
                // 使用数值较小的
                int num = Math.min(hostSize, taskSize);
                for (int i = 0; i < taskSize; i++) {
                    int randomIndex = new Random().nextInt(num--);
                    result.put(hosts.get(i), Lists.newArrayList(tasksCopy.remove(randomIndex)));
                    // 或者这种
                    //result.put(hosts.get(randomIndex), Lists.newArrayList(tasks.get(randomIndex)));
                }
            } else if (hostSize < taskSize){
                /**
                 * 主机数比任务数少,有的主机分配到的任务多,有的主机分配到的任务少,但是最多和最少相差不超过1
                 */
                int num = taskSize;
                for (int i = 0; i < hostSize; i++) {
                    if (num == 0) {
                        break;
                    }
                    int randomIndex = new Random().nextInt(num--);
                    String key = hosts.get(i);
                    List<String> tmpList = result.get(key);
                    if (tmpList == null) {
                        tmpList = new ArrayList<>();
                    }
                    tmpList.add(tasksCopy.remove(randomIndex));
                    result.put(key, tmpList);
                    if (i == hostSize - 1) {
                        // 进入下一次循环
                        i = -1;
                    }
                }

            } else {
                // 主机数和任务数相同,每个主机分配到一个任务
                int num = hostSize;
                for (int i = 0; i < hostSize; i++) {
                    // [0,hostSize)
                    int random = new Random().nextInt(num--);
                    result.put(hosts.get(i), Lists.newArrayList(tasksCopy.remove(random)));
                }
            }
        }
        return result;
    }


    /**
     * 随机分配算法
     * @param hosts
     * @param tasks
     * @return
     */
    public static Map<String, List<String>> allocationRandom(List<String> hosts, List<String> tasks) {
        int hostSize = CollectionUtils.size(hosts);
        int taskSize = CollectionUtils.size(tasks);
        Map<String, List<String>> result = new HashMap<>();
        if (hostSize > 0 && taskSize > 0) {
            for (int i = 0; i < taskSize; i++) {
                int index = new Random().nextInt(hostSize);
                String key = hosts.get(index);
                List<String> tmpList = result.get(key);
                if (tmpList == null) {
                    tmpList = new ArrayList<>();
                }
                tmpList.add(tasks.get(i));
                result.put(key, tmpList);
            }
        }
        return result;
    }

    public static Map<String, List<String>> allocationAverage(List<String> hosts, List<String> tasks) {
        int hostSize = CollectionUtils.size(hosts);
        int taskSize = CollectionUtils.size(tasks);
        Map<String, List<String>> result = new HashMap<>();
        if (hostSize > 0 && taskSize > 0) {
            for (int i = 0; i < taskSize; i++) {
                int j = i % hostSize;
                String key = hosts.get(j);
                List<String> tmpList = result.get(key);
                if (tmpList == null) {
                    tmpList = new ArrayList<>();
                }
                tmpList.add(tasks.get(i));
                result.put(key, tmpList);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> hosts = Lists.newArrayList("A", "B", "C");
        List<String> tasks = Lists.newArrayList("1", "2", "3", "4", "5", "6");


        System.out.println(randomAllocationAverage(hosts, tasks));


        System.out.println(allocationAverage(hosts, tasks));
        System.out.println(allocationRandom(hosts, tasks));
    }

}
