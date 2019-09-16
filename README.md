# 开发常用代码平台

- coder
  
  代码生成器，管理平台
  
- eureka
  
    服务注册中心
  
- flink
  
  flink的学习平台

- frame 

  实现公共功能，底层模块
  
  1、日志输出，对slf4j进行封装，其他服务模块类不需要再定义LoggerFactory.getLogger，还需要调整，比如每个模块输出独立的log日志，还要输出到ELK
  
  2、数据库模块，连接池，资源回收，还要引入数据库监控
  
  3、通用工具类：对常用文件写入/读取，压缩包加解密，
  
  
- spider

  爬虫平台，实现各种方式数据采集，常见http，https，webservice方式
  
- website

  集成数据库在内的，全家桶的简易展示数据平台
  
  config 配置信息
  
  controller层 责具体的业务模块流程的控制，对页面api接口声明，入参的检验；
  
  entity 实体层
  
  service层（服务层）负责业务模块的应用逻辑应用设计，一个或多个DAO进行的再次封装，事务控制在此处，包括数据格式转换、单位换算，service的异常要抛出到controller的try catch，service的方法是基本类，map，list，boolean
  
  DAO层（全称为data access data）做数据持久层的工作，负责与数据库进行联络的一些任务都封装在此，基于某一张表的原子操作，增删改查；
  
