package top.lothar.esenum;

/**
 * Elastic索引操作类型
 *
 * 0 新增
 * 1 更新
 * 2 删除
 */
public enum EnumElasticsearchOperator {

    ELASTIC_ADD(0),
    ELASTIC_UPDATE(1),
    ELASTIC_DELETE(2);

    private int type;

    EnumElasticsearchOperator(int type) {
        this.type = type;
    }

    public int type() {
        return type;
    }

}
