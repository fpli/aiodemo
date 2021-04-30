package dac.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTreeDemo {

    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");

        root.setLeft(node2);
        root.setRight(node3);

        node2.setLeft(node5);
        node3.setRight(node4);

        BinaryTree binaryTree = new BinaryTree();
        binaryTree.setRoot(root);
        System.out.println("level order recursive");
        binaryTree.levelOrder();

        System.out.println("前序遍历");
        binaryTree.preOrder();

        System.out.println("前序遍历非递归");
        binaryTree.preOrderFast();

        //System.out.println("中序遍历");
        //binaryTree.infixOrder();

        //System.out.println("后序遍历");
        //binaryTree.postOrder();

        //System.out.println("search a node ...");

        //System.out.println(binaryTree.preOrderSearch(5));

        binaryTree.delNode(5);

        System.out.println("前序遍历");
        binaryTree.preOrder();
    }

}

class BinaryTree {
    // 树的根结点， 是整颗树的起点
    private HeroNode root;

    public void preOrder() {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("tree is empty");
        }
    }

    public void preOrderFast(){
        if (root != null){
            root.preOrderFast();
        } else {
            System.out.println("tree is empty");
        }
    }

    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("tree is empty");
        }
    }

    public void infixOrderFast() {
        if (root != null) {
            root.infixOrderFast();
        } else {
            System.out.println("tree is empty");
        }
    }

    public void postOrder() {
        if (root != null) {
            root.postOrder();
        } else {
            System.out.println("tree is empty");
        }
    }

    public void postOrderFast() {
        if (root != null) {
            root.postOrderFast();
        } else {
            System.out.println("tree is empty");
        }
    }

    public void levelOrder(){
        if (root != null){
            root.levelOrder();
        } else {
            System.out.println("tree is empty");
        }
    }

    public void levelOrderFast(){
        if (root != null){
            root.levelOrderFast();
        } else {
            System.out.println("tree is empty");
        }
    }

    public void delNode(int no) {
        if (root != null) {
            if (root.getNo() == no) {
                root = null;
            } else {
                root.delNode(no);
            }
        } else {
            System.out.println("tree is empty");
        }
    }

    public HeroNode preOrderSearch(int no) {
        return root.preOrderSearch(no);
    }

    public HeroNode infixOrderSearch(int no) {
        return root.infixOrderSearch(no);
    }

    public HeroNode postOrderSearch(int no) {
        return root.preOrderSearch(no);
    }

    public void setRoot(HeroNode root) {
        this.root = root;
    }
}

/**
 * 结点
 */
class HeroNode {
    // 数据域
    private int no;
    private String name;

    // next域
    private HeroNode left; // 左子树
    private HeroNode right;// 右子树

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }
    // 前序遍历
    public void preOrder() {
        System.out.println(this);
        if (left != null) {
            left.preOrder();
        }
        if (right != null) {
            right.preOrder();
        }
    }

    /**
     * current Node, left node, right node
     */
    public void preOrderFast(){
        Stack<HeroNode> stack = new Stack<>();
        stack.push(this);
        // 栈先进后出，所以先加入右侧节点，这样输出的时候，先输出左侧节点
        HeroNode currentNode;
        while (!stack.isEmpty()){
            currentNode = stack.pop();
            System.out.println(currentNode);
            // stack FILO, so right node first, then left node
            if (currentNode.right != null){
                stack.push(currentNode.right);
            }

            if (currentNode.left != null){
                stack.push(currentNode.left);
            }
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (left != null) {
            left.infixOrder();
        }
        System.out.println(this);
        if (right != null) {
            right.infixOrder();
        }
    }

    public void infixOrderFast(){
        Stack<HeroNode> stack = new Stack<>();

        HeroNode currentNode = this;

        while (!stack.isEmpty() ||  currentNode != null){
            // 入栈所有左节点并输出左节点
            while (currentNode != null){
                stack.push(currentNode);
                currentNode = currentNode.left;
            }
            // 弹出左节点
            currentNode = stack.pop();

            System.out.println(currentNode);
            // 弹出后，指向当前节点的右节点
            currentNode = currentNode.right;
        }
    }

    // 后序遍历
    public void postOrder() {
        if (left != null) {
            left.postOrder();
        }
        if (right != null) {
            right.postOrder();
        }
        System.out.println(this);
    }

    /**
     * 后序遍历非递归方式
     */
    public void postOrderFast(){
        Stack<HeroNode> stack1 = new Stack<>();
        Stack<HeroNode> stack2 = new Stack<>();

        stack1.push(this);
        HeroNode currentNode;
        while (!stack1.isEmpty()){
            currentNode = stack1.pop();
            // 中、右、左顺序压入栈中
            stack2.push(currentNode);
            // 压入stack1为先左后右，保证中、右、左顺序压入stack2中
            if (currentNode.left != null) {
                stack1.push(currentNode.left);
            }
            if (currentNode.right != null) {
                stack1.push(currentNode.right);
            }

        }

        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop());
        }
    }

    /**
     * 后序遍历递归定义：先左子树，后右子树，再根节点。
     * 	后序遍历的难点在于：需要判断上次访问的节点是位于左子树，还是右子树。
     * 		若是位于左子树，则需跳过根节点，先进入右子树，再回头访问根节点；
     * 		若是位于右子树，则直接访问根节点。
     */
    public void postOrderFast2(){
        Stack<HeroNode> stack1 = new Stack<>();
        //当前访问的结点               上次访问的结点
        HeroNode currentNode = this, lastVisitNode = this;

        //把currentNode移到左子树的最下边
        while (currentNode != null){
            stack1.push(currentNode);
            currentNode = currentNode.left;
        }

        while (!stack1.isEmpty()){
            // 弹出栈顶元素
            currentNode = stack1.pop();
            //一个根节点被访问的前提是：无右子树或右子树已被访问过
            if (currentNode.right != null && currentNode.right != lastVisitNode){
                //根节点再次入栈
                stack1.push(currentNode);

                //进入右子树，且可肯定右子树一定不为空
                currentNode = currentNode.right;
                while (currentNode != null){
                    //再走到右子树的最左边
                    stack1.push(currentNode);
                    currentNode = currentNode.left;
                }
            } else {
                // 打印结点
                System.out.println(currentNode);
                //修改最近被访问的节点
                lastVisitNode = currentNode;
            }
        }
    }

    /**
     * 二叉树横向遍历， 递归的方式实现
     */
    public void levelOrder(){
        int height = height();
        for (int i = 1; i <= height; i++){
            levelOrder(i);
        }
    }

    public void levelOrder(int height){
        if (height == 1){
            System.out.println(this);
            return;
        }
        if (left != null){
            left.levelOrder(height -1);
        }
        if (right != null){
            right.levelOrder(height -1);
        }
    }

    public int height(){
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    /**
     * 二叉树横向遍历(又称层序遍历)， 非递归的方式实现
     */
    public void levelOrderFast(){
        Queue<HeroNode> queue = new LinkedList<>();
        queue.offer(this);
        HeroNode currentNode;
        while (!queue.isEmpty()){
            currentNode = queue.poll();
            System.out.println(currentNode);

            if (currentNode.left != null){
                queue.offer(currentNode.left);
            }

            if (currentNode.right != null){
                queue.offer(currentNode.right);
            }
        }
    }

    // 前序查找
    public HeroNode preOrderSearch(int no) {
        HeroNode result = null;
        if (this.no == no) {
            result = this;
        }

        if (this.left != null) {
            result = this.left.preOrderSearch(no);
        }

        if (result != null) {
            return result;
        }

        if (this.right != null) {
            result = this.right.preOrderSearch(no);
        }
        return result;
    }
    // 中序查找
    public HeroNode infixOrderSearch(int no) {
        HeroNode result = null;
        if (left != null) {
            result = left.preOrderSearch(no);
        }

        if (result != null) {
            return result;
        }

        if (this.no == no) {
            return this;
        }

        if (right != null) {
            result = right.preOrderSearch(no);
        }
        return result;
    }
    // 后序查找
    public HeroNode postOrderSearch(int no) {
        HeroNode result = null;
        if (left != null) {
            result = left.preOrderSearch(no);
        }

        if (result != null) {
            return result;
        }

        if (right != null) {
            result = right.preOrderSearch(no);
        }

        if (result != null) {
            return result;
        }

        if (this.no == no) {
            result = this;
        }

        return result;
    }
    // 删除结点
    public boolean delNode(int no) {
        boolean flag = false;

        if (this.left != null) {
            if (this.left.no == no){
                this.left = null;
                return true;
            } else {
                flag = this.left.delNode(no);
            }
        }

        if (!flag){
            if (this.right != null) {
                if (this.right.no == no){
                    this.right = null;
                    return true;
                } else {
                    return this.right.delNode(no);
                }
            }
        }

        return false;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}