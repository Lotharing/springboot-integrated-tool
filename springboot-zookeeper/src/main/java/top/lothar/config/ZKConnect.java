package top.lothar.config;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author : LuTong.Zhao
 * @date : 16:44 2020/6/27
 */
public class ZKConnect implements Watcher {

    final static Logger log = LoggerFactory.getLogger(ZKConnect.class);

    public static final String zkServerPath = "39.96.21.162:2181";

    public static ZooKeeper zk = null;

    //public static final String zkServerPath = "39.96.21.162:2181,39.96.21.162:2181,39.96.21.162:2181";  //集群方式

    public static final Integer timeout = 5000;
    /**
     * 获取zookeeper连接客户端
     * @return
     */
    public static ZooKeeper getZookeeper(){
        try{
            zk = new ZooKeeper(zkServerPath, timeout, new ZKConnect());
            log.info("客户端开始连接Zookeeper服务器...");
            log.info("连接状态{}",zk.getState());

            new Thread().sleep(2000);

            log.info("连接状态{}",zk.getState());

            return zk;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建节点
     * @param path
     * @param data
     * @param acls
     */
    public static void createZKNode(String path,byte[] data,List<ACL> acls){
        String result = "";
        try {
            /**
             * path 存储的路径
             *
             * data 存储的数据
             *
             * acl 控制权限政策
             *      ZooDefs.Ids.OPEN_ACL_UNSAFE -> world:anyone:cdrwa
             *      ZooDefs.Ids.OPEN_ALL_ACL --> auth:user:password:cdrwa
             *
             * createMode 节点类型
             *      PERSISTENT ： 持久节点
             *      PERSISTENT_SEQUENTIAL ：持久顺序节点
             *      EPHEMERAL： 临时节点
             *      EPHEMERAL_SEQUENTIAL: 临时顺序节点
             *
             */

            String ctx = "{'create':'success'}";  // 例如引入sql拼写 消息队列 等
            zk.create(path,data,acls,CreateMode.PERSISTENT,new CreateCallBack(),ctx);
            //log.info("创建节点"+result+"成功");
            new Thread().sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 修改节点
     * 可以封装ctx参数
     * @param path
     * @param data
     * @param version
     */
    public static void modifyZKNode(String path,byte[] data,int version){
        try {
            Stat status = zk.setData(path,data,version);
            new Thread().sleep(2000);
            //status.get()  =  Linux get /testNode 各种信息
            System.out.println(status.getVersion());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点
     * 可以封装ctx
     * @param path
     * @param version
     */
    public static void deleteZKNode(String path,int version){
        String ctx = "{'delete':'success'}";  // 例如引入sql拼写 消息队列 等
        try {
            zk.delete(path,version,new DeleteCallBack(),ctx);
            new Thread().sleep(2000);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void process(WatchedEvent watchedEvent) {
        log.debug("接收到Watcher的通知",watchedEvent);
    }
}
