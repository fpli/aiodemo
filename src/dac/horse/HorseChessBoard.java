package dac.horse;

import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class HorseChessBoard {

    private static int X; //棋盘的列数
    private static int Y; //棋盘的行数
    // 创建一个数组，标记棋盘的各个位置是否被访问过
    private static boolean[] visited;
    // 使用一个属性标记棋盘的所有位置是否都被访问过
    private static boolean finished; // true表示成功，否则没有成功


    public static void main(String[] args) {
        X = 8;
        Y = 8;
        int row = 6; // 马初始位置的行，从1开始编号
        int column = 3;// 马初始位置的列，从1开始编号
        // 创建棋盘
        int[][] chessboard = new int[X][Y];
        visited = new boolean[X * Y];// 初始值都为false
        LocalTime start = LocalTime.now();
        traversalChessBoard(chessboard, row-1, column -1, 1);
        LocalTime end = LocalTime.now();
        System.out.println(Duration.between(start, end).toMillis());

        // 输出棋盘最后的情况
        for (int[] rows : chessboard){
            for (int step : rows){
                System.out.print(step + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 完成骑士周游的算法
     * @param chessboard 棋盘
     * @param row 马当前位置的行，从0开始
     * @param column 马当前位置的列，从0开始
     * @param step 是第几步
     */
    public static void traversalChessBoard(int[][] chessboard, int row, int column, int step){
        chessboard[row][column] = step;
        visited[row * X + column] = true;// 标记该位置已经被访问
        // 获取当前位置可以走的下一个位置的集合
        ArrayList<Point> points = next(new Point(column, row));
        sort(points);// 贪心算法加以改进
        while (!points.isEmpty()){
            Point p = points.remove(0);//取出下一个可以走的位置
            // 判断该点是否被访问过
            if (!visited[p.y * X + p.x]){//未访问
                traversalChessBoard(chessboard, p.y, p.x, step + 1);
            }
        }

        // 判断马是否走完棋盘，使用step和应走的步数比较 如果没有达到数量则表示没有完成任务，将整个棋盘置0
        // 说明 step < X * Y 成立的两种情况
        // 1 棋盘到目前位置，仍然没有走完
        // 2 棋盘处于一个回溯过程
        if (step < X * Y && !finished){
            chessboard[row][column] = 0;
            visited[row * X + column] = false;
        } else {
            finished = true;
        }
    }

    // 根据当前的这一步的所有下一步的选择数目进行非递减排序
    public static void sort(ArrayList<Point> ps){
        ps.sort((Point o1, Point o2) -> {
                int count1 = next(o1).size(); // 当前这一步o1的下一步的选择数目
                int count2 = next(o2).size();
                if (count1 < count2) {
                    return -1;
                } else if (count1 == count2) {
                    return 0;
                } else {
                    return 1;
                }
            }
        );
    }

    /**
     * 根据当前位置，计算马还能走哪些位置，放入一个ArrayList中，最多8个
     *
     * @param curPoint
     * @return
     */
    public static ArrayList<Point> next(Point curPoint) {
        ArrayList<Point> ps = new ArrayList<>();

        Point p1 = new Point();

        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }

        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }

        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }

        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }
        return ps;
    }
}
