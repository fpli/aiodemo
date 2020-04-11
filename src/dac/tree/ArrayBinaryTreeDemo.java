package dac.tree;

public class ArrayBinaryTreeDemo {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(array);
        arrayBinaryTree.preOrder(0);
    }
}
// 实现顺序存储二叉树(数组可以转化成树，树转化成数组)
class ArrayBinaryTree {

    private int[] array;

    public ArrayBinaryTree(int[] array) {
        this.array = array;
    }
    //前序遍历
    public void preOrder(int index){
        if (array == null || array.length == 0){
            System.out.println("array is empty");
            return;
        }
        System.out.println(array[index]);
        // 向左子树递归
        if (2 * index +1 < array.length){
            preOrder(2 * index + 1);
        }
        // 向右子树递归
        if (2 * index + 2 < array.length){
            preOrder(2 * index + 2);
        }
    }
    // 中序遍历
    public void infixOrder(int index){
        if (array == null || array.length == 0){
            System.out.println("array is empty");
            return;
        }

        // 向左子树递归
        if (2 * index +1 < array.length){
            infixOrder(2 * index + 1);
        }

        System.out.println(array[index]);

        // 向右子树递归
        if (2 * index + 2 < array.length){
            infixOrder(2 * index + 2);
        }
    }

    public void postOrder(int index){
        if (array == null || array.length == 0){
            System.out.println("array is empty");
            return;
        }

        // 向左子树递归
        if (2 * index +1 < array.length){
            postOrder(2 * index + 1);
        }

        // 向右子树递归
        if (2 * index + 2 < array.length){
            postOrder(2 * index + 2);
        }

        System.out.println(array[index]);
    }

}