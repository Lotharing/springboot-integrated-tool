package top.lothar.config;

import org.apache.zookeeper.AsyncCallback;

/**
 * @author : LuTong.Zhao
 * @date : 17:59 2020/6/27
 */
public class CreateCallBack implements AsyncCallback.StringCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("创建节点---------------Node------------>"+path);
        System.out.println((String)ctx);
    }
}
