package top.lothar.util;

import lombok.Data;

/**
 * @author cf
 */
@Data
public class BaseResultResponse<T> {
    /**
     * 默认成功
     */
    public int status = 200;
    public boolean success = true;
    public String msg = "";
    public T data;
}
