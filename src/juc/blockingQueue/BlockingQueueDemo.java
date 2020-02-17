package juc.blockingQueue;

/**
 * ArrayBlockingQueue:是一个基于数组结构的有界阻塞队列，此队列按FIFO(先进先出)原则对元素进行排序
 * LinkedBlockingQueue:一个基于链表结构的阻塞对列，此时按FIFO排序元素，吞吐量通常要高于ArrayBlockingQueue.
 * SynchronousQueue:一个不存储元素的阻塞对列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue.
 *
 * 1 队列
 *
 * 2 阻塞队列
 *  2.1 阻塞队列有没有好的一面
 *  在多线程领域:所谓阻塞，在某些情况下会挂起线程(即阻塞)，一旦条件满足，被挂起的线程又会自动被唤醒
 *  为什么需要BlockingQueue
 *  好处是我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，因为这一切BlockingQueue都给一手包办了
 *  在concurrent包发布以前，在多线程环境下，我们每个程序员都必须去自己控制这些细节，尤其还要兼顾效率和线程安全，而这会给我们的程序带来不小的复杂度。
 *  2.2 不得不阻塞，你如何管理
 */
public class BlockingQueueDemo {

}
