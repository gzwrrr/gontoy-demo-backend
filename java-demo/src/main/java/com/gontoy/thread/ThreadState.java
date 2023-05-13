package com.gontoy.thread;

/**
 * 线程状态
 */
public class ThreadState {

}

enum State {
    // 新创建
    NEW,

    // 可运行
    RUNNABLE,

    // 阻塞
    BLOCKED,

    // 超时等待
    TIMED_WAIT,

    // 结束
    TERMINATED
}
