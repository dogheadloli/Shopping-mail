＃ 淘淘商城

## 简介
类似京东商城、用户可以在商城浏览商品、下订单。

使用dubbo+mybatis+spring+springmvc

采用分布式系统架构，子系统之间通过调用服务来实现系统之间的通信，降低了系统之间的耦合度，提高了系统的扩展性。

搜索功能使用solrCloud做搜索引擎。

使用消息队列ActiveMQ对项目中的一些模块进行了解耦。

为了提高系统的性能使用redis集群做系统缓存，并使用redis实现session共享进而实现单点登录。

实现了秒杀抢购的功能。
### How to play
 git clone https://github.com/changrui520/Shopping-mail.git
 
 open IDEA --> File --> New --> Open
 
 choose TaoTao_parent's pom.xml，open it
 
 update the jdbc.properties files about your mysql's username and password
 
 deploy the tomcat,and start up
 
 enter in the browser: http://localhost:8080/
 
 enjoy it
 

