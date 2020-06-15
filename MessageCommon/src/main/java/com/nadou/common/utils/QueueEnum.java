package com.nadou.common.utils;

import lombok.Getter;

@Getter
public enum QueueEnum {

    QUEUE_LOG("log_exchange_direct", "log_queue_direct", "log.routekey"),//日志队列
    QUEUE_TOPIC_DEMO("demo_exchange_topic", "demo_queue_topic", "");//topic demo 队列

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
