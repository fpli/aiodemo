package dac.linked;

/**
 * 约瑟夫问题:
 *  josehpu问题为:设编号为1,2,..n的n个人围坐一圈，约定编号为k(1<=k<=n)的人
 *  从1开始报数，数到m的那个人出列，他的下一位又从1开始报数，数到m的那个人又出列
 *  依次类推，直到所有人出列为止，由此产生一个出队编号的序列。
 *
 *  提示：
 *  用一个不带头节点的循环链表来处理josephu问题，先构成一个由n个节点的单循环链表，
 *  然后由k结点起从1开始计数，计到m时，对应结点从链表中删除，然后再从被删除结点的下一个结点又从1开始计数，
 *  直到最后一个结点从链表中删除算法结束。
 *
 *  思路:采用单向环形链表
 *  根据用户的输入，生成一个小孩出圈的顺序
 *  n = 5,即有5个人
 *  k = 1,从第1个人开始报数
 *  m = 2, 数2下
 *
 *  1 需求创建一个辅助指针helper,事先应该指向环形链表的最后一个节点
 *  补充:当k != 1时， 小孩报数前，先让first和helper移动k-1次
 *  2 当小孩开始报数时，让first和helper指针同时的移动m-1次
 *  3 这时就可以将first指向的小孩节点出圈
 *  first = first.next;
 *  helper.next = first;
 *  原来first指向的节点就没有任何引用，就会被回收
 */
public class Josephu {

    public static void main(String[] args) {
        // 测试一把构建环型链表和遍历是否OK
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(125);//加入5个小孩结点
        circleSingleLinkedList.showBoy();

        // 测试一把小孩出圈是否正确
        circleSingleLinkedList.countBoy(10, 20, 125);
    }
}

// 创建一个环形的单向链表
class CircleSingleLinkedList{
    // 创建一个first节点，当前没有编号
    private Boy first = null;

    // 添加小孩结点，构成一个环形的链表
    public void addBoy(int nums){
        // nums 做数据校验
        if (nums < 1){
            System.out.printf("nums的值错误", nums);
            return;
        }
        Boy curBoy = null;// 辅助指针，帮助构建环形链表
        // 使用for循环来创建我们的环形链表
        for (int i = 1; i <= nums ; i++) {
            // 根据编号创建小孩结点
            Boy boy = new Boy(i);
            // 如果是第一个小孩
            if (i == 1){
                first = boy;
                first.setNext(first);// 构成环
                curBoy = first;
            } else {
                curBoy.setNext(boy); // curBor.next = boy 指向变更
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    // 遍历单向环形链表
    public void showBoy(){
        // 判断链表是否为空
        if (first == null){
            System.out.println("没有任何小孩");
            return;
        }
        // 因为first不能动，因此我们仍然使用一个辅助指针完成遍历
        Boy curBoy = first;
        while (true){
            System.out.printf("小孩的编号 %d \n", curBoy.getNo());
            if (curBoy.getNext() == first){//说明已经遍历完毕
                break;
            }
            curBoy = curBoy.getNext();// curBoy后移
        }
    }

    //根据用户的输入，计算出小孩出圈的顺序

    /**
     *  1 需求创建一个辅助指针helper,事先应该指向环形链表的最后一个节点
     *  补充:当k != 1时， 小孩报数前，先让first和helper移动k-1次
     *  2 当小孩开始报数时，让first和helper指针同时的移动m-1次
     *  3 这时就可以将first指向的小孩节点出圈
     *  first = first.next;
     *  helper.next = first;
     *  原来first指向的节点就没有任何引用，就会被回收
     * @param startNo 表示从第几个小孩开始数数
     * @param countNum  表示数几下
     * @param nums    表示最初有多少小孩在圈中
     */
    public void countBoy(int startNo, int countNum, int nums){
        // 先对数据进行校验
        if (first == null || startNo <1 || startNo > nums){
            System.out.println("参数输入有误,请重新输入");
            return;
        }

        // 创建一个辅助指针helper，事先应该指向环形链表的最后这个结点
        Boy helper = first;
        while (true){
            if (helper.getNext() == first){// 说明helper指向最后小孩结点
                break;
            }
            helper = helper.getNext();
        }

        // 小孩报数前，先让first和helper移动k-1次
        for (int j = 0; j < startNo-1; j++){
            first = first.getNext();
            helper = helper.getNext();
        }

        // 当小孩报数时，让first和helper指针同时移动m-1次，然后出圈 是一个循环操作，直到圈中只有一个结点
        while (true){
            if (helper == first){//说明圈中只有一个人
                break;
            }
            // 让first和helper指针同时移动countNum-1次,然后出圈
            for (int i = 0; i < countNum -1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            // 这时first指向的结点，就是要出圈的小孩结点
            System.out.printf("小孩%d出圈\n", first.getNo());
            // 这时将first指向的结点出圈
            first = first.getNext();
            helper.setNext(first);
        }

        System.out.printf("最后留在圈的小孩编号%d\n", helper.getNo());
    }
}

// 创建一个Boy类，表示一个节点
class Boy {

    private int no;//编号
    private Boy next;//指向下一个节点，默认null

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
