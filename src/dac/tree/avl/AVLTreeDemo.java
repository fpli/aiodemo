package dac.tree.avl;

public class AVLTreeDemo {

    public static void main(String[] args) {
        //int[] array = {4, 3, 6, 5, 7, 8};
        int[] array = {50, 34, 53, 23, 69, 100, 32, -100, -30, 400, 350, 500, 38};
        //int[] array = {10, 11, 7, 6, 8, 9};

        AVLTree avlTree = new AVLTree();

        for (int i = 0; i < array.length; i++) {
            avlTree.addNode(new Node(array[i]));
        }

        System.out.println("infix Order:");
        avlTree.infixOrder();

        System.out.println("the height of the tree:" + avlTree.getRoot().height());
        System.out.println("the height of the tree left:" + avlTree.getRoot().leftHeight());
        System.out.println("the height of the tree right:" + avlTree.getRoot().rightHeight());
        System.out.println("root:" + avlTree.getRoot());
        System.out.println("left of root:" + avlTree.getRoot().left);
        System.out.println("right of root:" + avlTree.getRoot().right);
        //avlTree.deleteNode(32);
        avlTree.preOrder();

        avlTree.deleteNode(38);
        avlTree.deleteNode(34);
        avlTree.deleteNode(23);
        avlTree.deleteNode(-100);
        avlTree.deleteNode(-30);
        avlTree.deleteNode(32);

        System.out.println("the height of the tree:" + avlTree.getRoot().height());
        System.out.println("the height of the tree left:" + avlTree.getRoot().leftHeight());
        System.out.println("the height of the tree right:" + avlTree.getRoot().rightHeight());
        System.out.println("root:" + avlTree.getRoot());
        System.out.println("left of root:" + avlTree.getRoot().left);
        System.out.println("right of root:" + avlTree.getRoot().right);
        System.out.println("-------------add 85 to tree ---------");
        avlTree.addNode(new Node(85));

        System.out.println("the height of the tree:" + avlTree.getRoot().height());
        System.out.println("the height of the tree left:" + avlTree.getRoot().leftHeight());
        System.out.println("the height of the tree right:" + avlTree.getRoot().rightHeight());
        System.out.println("root:" + avlTree.getRoot());
        System.out.println("left of root:" + avlTree.getRoot().left);
        System.out.println("right of root:" + avlTree.getRoot().right);
        avlTree.preOrder();
    }
}

class AVLTree {

    private Node root;

    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    public int deleteRightTreeMini(Node node) {
        Node target = node;
        while (target.left != null) {
            target = target.left;
        }
        deleteNode(target.value);
        return target.value;
    }

    public int deleteLeftTreeMax(Node node) {
        Node target = node;
        while (target.right != null) {
            target = target.right;
        }
        deleteNode(target.value);
        return target.value;
    }

    public void deleteNode(int value) {
        if (root == null) {
            return;
        } else {
            Node targetNode = search(value);
            if (targetNode == null) {
                return;
            }

            if (root.left == null && root.right == null) {
                root = null;
                return;
            }

            Node parent = searchParent(value);

            if (targetNode.left == null && targetNode.right == null) {
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) {
                    parent.right = null;
                }
            } else if (targetNode.left != null && targetNode.right != null) {
                int miniValue = deleteRightTreeMini(targetNode.right);
                targetNode.value = miniValue;
            } else {
                if (targetNode.left != null) {
                    if (parent != null) {
                        if (parent.left.value == value) {
                            parent.left = targetNode.left;
                        } else {
                            parent.right = targetNode.left;
                        }
                    } else {
                        root = targetNode.left;
                    }
                } else {
                    if (parent != null) {
                        if (parent.left.value == value) {
                            parent.left = targetNode.right;
                        } else {
                            parent.right = targetNode.right;
                        }
                    } else {
                        root = targetNode.right;
                    }
                }
            }
        }

    }

    public void addNode(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.addNode(node);
        }
    }

    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("this tree is empty.");
        }
    }

    public void preOrder(){
        if (root != null){
            root.preOrder();
        } else {
            System.out.println("this tree is empty.");
        }
    }

    public Node getRoot() {
        return root;
    }
}

class Node {

    int value;

    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    private void leftRotate() {
        // create a new node with the current value of node
        Node newNode  = new Node(value);
        newNode.left  = left;
        newNode.right = right.left;
        value         = right.value;
        right         = right.right;
        left          = newNode;
    }

    private void rightRotate() {
        Node newNode  = new Node(value);
        newNode.right = right;
        newNode.left  = left.right;
        value         = left.value;
        left          = left.left;
        right         = newNode;
    }

    public Node search(int value) {
        if (value == this.value) {
            return this;
        } else if (value < this.value) {
            if (this.left == null) {
                return null;
            } else {
                return this.left.search(value);
            }
        } else {
            if (this.right == null) {
                return null;
            } else {
                return this.right.search(value);
            }
        }
    }

    public Node searchParent(int value) {
        if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
            return this;
        } else {
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value);
            } else if (value >= this.value && this.right != null) {
                return this.right.searchParent(value);
            } else {
                return null;
            }
        }
    }

    public void addNode(Node node) {
        if (node.value < this.value) {
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.addNode(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.addNode(node);
            }
        }

        //
        if (rightHeight() - leftHeight() > 1) {
            if (right != null && right.leftHeight() > right.rightHeight()){
                right.rightRotate();
            }
            leftRotate();
            return;// very important!oot
        }

        //
        if (leftHeight() - rightHeight() > 1){
            if (left != null && left.rightHeight() > left.leftHeight()){
                left.leftRotate();
            }
            rightRotate();
        }
    }

    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    public void preOrder() {
        System.out.println(this);

        if (this.left != null) {
            this.left.preOrder();
        }

        if (this.right != null) {
            this.right.preOrder();
        }
    }
}