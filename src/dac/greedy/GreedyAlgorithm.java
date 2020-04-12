package dac.greedy;

import java.util.*;

public class GreedyAlgorithm {

    public static void main(String[] args) {
        Map<String, HashSet<String>> broadcasts = new HashMap<>();
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");
        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");
        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");
        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");
        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        broadcasts.put("K1", hashSet1);
        broadcasts.put("K2", hashSet2);
        broadcasts.put("K3", hashSet3);
        broadcasts.put("K4", hashSet4);
        broadcasts.put("K5", hashSet5);
        // 存放所有地区
        Set<String> allareas = new HashSet<>();
        allareas.add("北京");
        allareas.add("上海");
        allareas.add("天津");
        allareas.add("广州");
        allareas.add("深圳");
        allareas.add("成都");
        allareas.add("杭州");
        allareas.add("大连");

        // 创建ArrayList 存放选择的电台集合
        List<String> selects = new ArrayList<>();


        HashSet<String> tempSet = new HashSet<>();
        String maxKey = null;
        while (allareas.size() != 0){
            maxKey = null;
            for (String key : broadcasts.keySet()){
                tempSet.clear();
                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                // 求tempSet和allareas 集合的交集，并把交集赋给tempSet
                tempSet.retainAll(allareas);
                // tempSet.size() > broadcasts.get(maxKey).size() 这里体现贪心算法,每次都选择最优的
                if (tempSet.size()> 0 && (maxKey == null || tempSet.size() > broadcasts.get(maxKey).size())){
                    maxKey = key;
                }
            }
            if (maxKey != null){
                selects.add(maxKey);
                // 将maxKey指向的广播电台从allareas 清除掉
                allareas.removeAll(broadcasts.get(maxKey));
            }
        }

        System.out.println("得到选择的结果:" + selects);
    }
}