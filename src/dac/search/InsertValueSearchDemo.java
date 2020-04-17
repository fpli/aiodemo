package dac.search;

public class InsertValueSearchDemo {

    public static void main(String[] args) {
        int[] arr = {1, 5, 78, 80, 100, 345, 600};
        System.out.println(insertValue(arr, 0, arr.length - 1, 200));
    }

    public static int insertValue(int[] array, int left, int right, int target){
        //注意：findVal < arr[0]  和  findVal > arr[arr.length - 1] 必须需要
        //否则我们得到的 mid 可能越界
        if (left > right || target < array[left] || target > array[right]) {
            return -1;
        }

        // 求出mid, 自适应
        int mid = left + (right - left) * (target - array[left]) / (array[right] - array[left]);
        int midVal = array[mid];
        if (target > midVal) { // 说明应该向右边递归
            return insertValue(array, mid + 1, right, target);
        } else if (target < midVal) { // 说明向左递归查找
            return insertValue(array, left, mid - 1, target);
        } else {
            return mid;
        }
    }
}
