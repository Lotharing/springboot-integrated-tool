package top.lothar.config;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author : LuTong.Zhao
 * @date : 17:28 2020/6/27
 */
public class ZKNodeOperation implements Watcher {

    final static Logger log = LoggerFactory.getLogger(ZKNodeOperation.class);

    public static void main(String[] args) {
        ZKConnect.getZookeeper();
        ZKConnect.createZKNode("/testNode","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("watcher事件{}",watchedEvent);
    }


}
