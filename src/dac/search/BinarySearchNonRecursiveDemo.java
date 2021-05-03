package dac.search;

/**
 * 二分查找非递归实现
 */
public class BinarySearchNonRecursiveDemo {

    public static void main(String[] args) {
        int[] arr = {1, 5, 78, 80, 100, 345, 600};
        int index = search(arr, 1);
        System.out.println("index = " + index);
    }

    public static int search(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;// >>> 无符号右移 高位补0
            if (target == arr[mid]) {
                return mid;
            } else if (target > arr[mid]) {//向右查找
                left = mid + 1;
            } else {// 向左查找
                right = mid - 1;
            }
        }
        return -1;
    }

    public static int binarySearch(int[] arr, int target, int left, int right){
        if (left > right || target < arr[0] || target > arr[arr.length -1]){
            return -1;
        }
        int mid = (left + right) / 2;
        if (target == arr[mid]){
            return mid;
        } else if(target > arr[mid]) {
            return binarySearch(arr, target, mid + 1, right);
        } else {
            return binarySearch(arr, target, left, mid - 1);
        }
    }
}
