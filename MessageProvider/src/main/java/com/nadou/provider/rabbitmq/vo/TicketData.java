package com.nadou.provider.rabbitmq.vo;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lombok.Data;

/**
 *@ClassName TicketData
 *@Description TODO
 *@Author
 *@Date 2020/6/13 08:23
 *@Version 1.0
 **/
@Data
public class TicketData implements Serializable {
  private static final long serialVersionUID = 5016527849940485253L;

  private volatile ConcurrentMap<String,String> cerTicketVoConcurrentMap = new ConcurrentHashMap<String,String>();

  private static class SingletonHolder {
    static final TicketData INSTANCE = new TicketData();
  }
  public static TicketData getInstance(){
    return SingletonHolder.INSTANCE;
  }
}
