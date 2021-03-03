package top.lothar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.stereotype.Service;
import top.lothar.entity.click.ClickDO;
import top.lothar.entity.click.EsClickDO;
import top.lothar.esenum.EsTypeEnum;
import top.lothar.mapper.ClickElementMapper;
import top.lothar.model.ClickElement;
import top.lothar.repository.ClickRepository;
import top.lothar.service.EsClickService;
import top.lothar.util.DateUtil;

import java.util.Date;

/**
 * <h1>通用Es收集点击事件接口实现</h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/1/23 17:42
 */
@Service
public class EsClickServiceImpl implements EsClickService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ClickRepository clickRepository;

    @Autowired
    private ClickElementMapper clickElementMapper;

    @Override
    public void saveCommonClick(ClickDO clickDO) {
        // 信息校验
        if(clickDO == null) {
            return;
        }
        EsClickDO esClickDO = new EsClickDO(clickDO);
        if(clickDO.getTime() == null){
            clickDO.setTime(new Date());
        }
        // 获取指定label的位置等信息
        ClickElement clickElement = clickElementMapper.getClickElementByLabel(clickDO.getLabel());
        if (clickElement != null) {
            esClickDO.setPosition(clickElement.getPosition());
            esClickDO.setPage(clickElement.getPage());
        }

        AliasQuery aliasQuery = new AliasQuery();
        aliasQuery.setIndexName(EsTypeEnum.CLICK.index());
        aliasQuery.setAliasName(EsTypeEnum.CLICK.index() + "_" + DateUtil.getCurrentFormatStr());
        // 索引不存在则创建
        if(!elasticsearchRestTemplate.indexExists(EsTypeEnum.CLICK.index())){
            elasticsearchRestTemplate.createIndex(EsClickDO.class);
        }
        elasticsearchRestTemplate.addAlias(aliasQuery);
        clickRepository.save(esClickDO);
    }

}
