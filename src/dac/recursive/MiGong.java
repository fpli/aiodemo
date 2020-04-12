package dac.recursive;

public class MiGong {

    public static void main(String[] args) {
        // 地图
        int[][] map = new int[8][7];
        // 1 表示墙 第一行和最后一行，第一列和最后一列 置1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        // 设置挡板
        map[3][1] = 1;
        map[3][2] = 1;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "  ");
            }
            System.out.println();
        }
        setWay(map, 1, 1);
        System.out.println("标记2为路线");

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "  ");
            }
            System.out.println();
        }
    }

    /**
     * 说明:(1,1)起点 (6,5)终点  map[i][j] = 0 表示还没走， = 1 表示墙 = 2 表示通路  = 3 表示探测不通
     * 在探测时定一个策略 下 -> 右 -> 上 -> 左  如果该点走不通，再回溯
     *
     * @param map 地图
     * @param i   从那一行
     * @param j   从那一列
     * @return
     */
    public static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        } else {
            if (map[i][j] == 0) {
                // 按照策略探测 下 -> 右 -> 上 -> 左
                map[i][j] = 2;// 假定该点是可以走通的
                if (setWay(map, i + 1, j)) {// 向下走
                    return true;
                } else if (setWay(map, i, j + 1)) {// 向右走
                    return true;
                } else if (setWay(map, i - 1, j)) {// 向上走
                    return true;
                } else if (setWay(map, i, j - 1)) {// 向左走
                    return true;
                } else {
                    // 此点不通
                    map[i][j] = 3;
                    return false;
                }
            } else {// map[i][j] 可能是 1,2,3
                return false;
            }
        }
    }
}
