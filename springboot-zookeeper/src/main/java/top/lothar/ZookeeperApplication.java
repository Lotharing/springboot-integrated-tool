package top.lothar;

import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.lothar.config.ZKConnect;

/**
 * @author : LuTong.Zhao
 * @date : 16:34 2020/6/27
 */
@SpringBootApplication
public class ZookeeperApplication {
    public static void main(String[] args) {

        final Logger log = LoggerFactory.getLogger(ZKConnect.class);

        SpringApplication.run(ZookeeperApplication.class, args);
        //zk
        ZooKeeper zk = ZKConnect.getZookeeper();
        long sessionId = zk.getSessionId();
        byte[] sessionPassword = zk.getSessionPasswd();
        log.info(sessionId+"");
        log.info(sessionPassword.toString());

        //重连机制一般时，把sessionid 和 sessionpassword 记录在redis / 使用会话重连

        //创建节点


    }
}
