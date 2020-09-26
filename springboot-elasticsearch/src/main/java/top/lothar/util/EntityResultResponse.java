package top.lothar.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author cf
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EntityResultResponse<T> extends BaseResultResponse<T> implements Serializable {
    public EntityResultResponse(EnumSystem enumSystem, String msg) {
        setStatus(enumSystem.code());
        setSuccess(enumSystem.code() == 200);
        setMsg(msg);
    }

    public EntityResultResponse(EnumSystem enumSystem) {
        setStatus(enumSystem.code());
        setSuccess(enumSystem.code() == 200);
        setMsg(enumSystem.desc());
    }

    public EntityResultResponse(T entity) {
        super();
        setData(entity);
    }

    public EntityResultResponse(T entity, String msg) {
        super();
        setData(entity);
        setMsg(msg);
    }

    public EntityResultResponse() {
        super();
    }


}
