package com.nadou.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 *@ClassName LogConsole
 *@Description 自定义打印日志
 *@Author nannan.zhang
 *@Date 2020/6/12 15:32
 *@Version 1.0
 **/
@Slf4j
public class LogConsole {
  public static void info(String msg){
    log.info("["+msg+"]");
  }

  public static void info(String msg,Exception e){
    log.info("["+msg+"]",e);

  }
}
