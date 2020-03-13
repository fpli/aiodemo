package dac.linked;

public class SingleLinkedListDemo {

    public static void main(String[] args) {
        HeroNode heroNode1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode heroNode2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode heroNode3 = new HeroNode(3, "吴用", "智多星");
        HeroNode heroNode4 = new HeroNode(4, "林冲", "豹子头");

        SingleLinkedList singleLinkedList = new SingleLinkedList();

//        singleLinkedList.addNode(heroNode1);
//        singleLinkedList.addNode(heroNode2);
//        singleLinkedList.addNode(heroNode3);
//        singleLinkedList.addNode(heroNode4);

        singleLinkedList.addNodeByOrder(heroNode1);
        singleLinkedList.addNodeByOrder(heroNode4);
        singleLinkedList.addNodeByOrder(heroNode3);
        singleLinkedList.addNodeByOrder(heroNode2);
        singleLinkedList.list();
        System.out.println("修改后的情况");
        HeroNode heroNode = new HeroNode(2, "卢俊义", "玉麒麟~~~");
        singleLinkedList.update(heroNode);
        singleLinkedList.list();

        System.out.println("删除后链表的情况");
        // 删除一个结点
        singleLinkedList.del(heroNode1);
        singleLinkedList.del(heroNode3);
        singleLinkedList.del(heroNode4);
        singleLinkedList.del(heroNode2);
        singleLinkedList.list();
    }

}

// 定义单向链表, 管理结点
class SingleLinkedList{
    // 先初始化链表的头结点， 头结点不能动
    private final HeroNode head = new HeroNode(0, "","");

    //添加结点到单向链表, 直接添加结点到链表最后，不考虑链表中间插入结点
    // 思路: 1 找到当前链表的最后一个结点 2 将最后一个结点的next指向新的结点
    public void addNode(HeroNode node){
        // 因为head结点不能动，需要一个辅助指针
        HeroNode helper = head;
        // 遍历链表
        while (true){
            if (helper.next == null){
                break;
            }
            helper = helper.next;
        }
        //此时 helper指向链表的最后一个结点
        helper.next = node;
    }

    // 指定位置插入结点 链表中间插入结点
    public void addNodeByOrder(HeroNode node){
        // 因为头结点不能动，需要借助辅助指针
        HeroNode helper = head;
        boolean flag = false;       //添加的编号是否存在
        while (true){
            if (helper.next == null){//到达链表的最后一个结点
                break;
            }
            if (helper.next.no > node.no){// 位置找到，就在helper 结点的后面插入
                break;
            } else if (helper.next.no == node.no){
                // heroNode的编号已经存在，不能再添加
                flag = true;
                break;
            }
            helper = helper.next;// helper指针后移
        }

        if (flag){
            System.out.printf("准备插入的结点已经存在 %d \n", node.no);
        } else {
            node.next = helper.next;
            helper.next = node;
        }
    }

    // 修改结点信息， 根据no编号来修改其他数据
    public void update(HeroNode node){
        // 判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        // helper 辅助指针
        HeroNode helper = head.next;
        // 找到需要修改的结点，根据no编号
        boolean flag = false;
        while (true){
            if (helper == null){
                break;// 到达链表的最后
            }

            if (helper.no == node.no){
                // 找到要修改的结点
                flag = true;
                break;
            }

            helper = helper.next;
        }
        if (flag){
            helper.name = node.name;
            helper.nickName = node.nickName;
        } else {
            System.out.printf("没有找到要修改的结点 %d \n", node.no);
        }
    }

    // 删除链表的某个结点
    public void del(HeroNode node){
        // 辅助指针指向需要删除结点的前一个结点
        HeroNode helper = head;
        boolean flag = false;    // 标志是否找到待删除结点
        while (true){
            if (helper.next == null){
                break;
            }
            if (helper.next.no == node.no){
                // 找到了待删除结点的前一个结点
                flag = true;
                break;
            }
            helper = helper.next;
        }

        if (flag){
            helper.next = helper.next.next;
        } else {
            System.out.println("没有找到要删除的结点");
        }
    }

    // 显示链表【遍历】
    public void list(){
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        // 因为头结点不能动，因此我们需要一个辅助变量
        HeroNode helper = head.next;
        while (true){
            // 判断是否到链表最后一个结点
            if (helper == null){
                break;
            }
            System.out.println(helper);
            helper = helper.next;
        }
    }
}

class HeroNode{

    public int no;
    public String name;
    public String nickName;

    public HeroNode  next; // 指向下一个结点

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
