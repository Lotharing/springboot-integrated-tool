package top.lothar.config;

import org.apache.zookeeper.AsyncCallback;

/**
 * @author : LuTong.Zhao
 * @date : 18:36 2020/6/27
 */
public class DeleteCallBack implements AsyncCallback.VoidCallback  {

    @Override
    public void processResult(int rc, String path, Object ctx) {
        System.out.println("删除节点---------------Node------------>"+path);
        System.out.println((String)ctx);

    }
}
