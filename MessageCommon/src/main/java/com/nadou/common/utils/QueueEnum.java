package com.nadou.common.utils;

import lombok.Getter;

@Getter
public enum QueueEnum {

    QUEUE_LOG("log.direct", "log.queue", "log.routekey"),//日志队列
    QUEUE_ORDINARY("ordinary.direct", "ordinary.queue", "ordinary.routekey"),//普通队列
    QUEUE_DEAD_LETTER("deadletter.direct", "deadletter.queue", "deadletter.routekey");//死信队列

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String qname;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String qname, String routeKey) {
        this.exchange = exchange;
        this.qname = qname;
        this.routeKey = routeKey;
    }
}
