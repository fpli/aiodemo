package dac.tree;

public class BinarySortTreeDemo {

    public static void main(String[] args) {
        int[] array = {7, 3, 10, 12, 5, 1, 9, 0};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int i = 0; i < array.length; i++) {
            binarySortTree.addNode(new Node(array[i]));
        }
        System.out.println("infix order");
        binarySortTree.infixOrder();
        System.out.println("------delete node-----");
        binarySortTree.deleteNode(10);
        binarySortTree.deleteNode(2);
        binarySortTree.deleteNode(1);
        binarySortTree.deleteNode(0);
        binarySortTree.deleteNode(3);
        binarySortTree.deleteNode(12);
        binarySortTree.deleteNode(5);
        binarySortTree.deleteNode(9);
        binarySortTree.deleteNode(7);
        binarySortTree.infixOrder();
    }
}

class BinarySortTree {

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

    public int deleteLeftTreeMini(Node node) {
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
            System.out.println("this tree is emprty.");
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
}