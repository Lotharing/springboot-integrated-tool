#### SpringBoot2.2.4+ShardingJdbc

---
之前Sharding-JDBC是当当网自研的关系型数据库的水平扩展框架，现在已经捐献给Apache，
具体可以查看Github，地址是：https://shardingsphere.apache.org/document/current/cn/overview/
shardingsphere文档地址是：https://shardingsphere.apache.org/document/current/cn/overview/。


---
1.分库

根据数据库表中字段goods_id的大小进行判断，如果goods_id大于20则使用database0，否则使用database1

2.分表

根据数据库表中字段goods_type的数值的奇偶进行判断，奇数使用goods_1表，偶数使用goods_0表

