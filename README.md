# 采集数据系统（初稿）

- frame 

  实现公共功能，底层模块
  
  1、日志输出，对slf4j进行封装，其他服务模块类不需要再定义LoggerFactory.getLogger，还需要调整，比如每个模块输出独立的log日志，还要输出到ELK
  2、数据库模块，连接池，资源回收，还要引入数据库监控
  3、对常用文件写入/读取
  
  
- spider

  服务生产者,实现各种方式数据采集，常见http，https，webservice方式
  
- website

  服务消费者,页面展示
  
- eureka

   服务注册中心