package lc.platform.admin.common.utils;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * page转换工具
 *
 * @author LiCheng
 * @data 2018.3.10
 */
@SuppressWarnings("all")
public class UtilPage {

    private static final Logger logger = LoggerFactory.getLogger(UtilPage.class);

    private UtilPage() {
    }

    /**
     * mybatisplus的page对象转换页面显示的page对象
     *
     * @param page
     * @param service
     * @param ew
     * @return
     */
    public static PageUtils getPageUtils(Page page, IService service, Wrapper ew) {
        //进行查询
        Page<?> dataPage = service.selectPage(page, ew);
        //获取参数
        List<?> records = dataPage.getRecords();
        int total = dataPage.getTotal();
        int size = dataPage.getSize();
        int current = dataPage.getCurrent();
        //转换返回对象
        return new PageUtils(records, total, size, current);

    }

    /**
     * 分页：可根据部门过滤数据
     * @param page
     * @param service
     * @param ew
     * @param filtByDeptFlag
     * @return
     */
    public static PageUtils getPageUtils(Page page, IService service, Wrapper ew, boolean filtByDeptFlag) {
        //filte by dept
        if (filtByDeptFlag) {
            ew.addFilterIfNeed(filtByDeptFlag, getFiltByDeptSql(null));
        }
        //进行查询
        Page<?> dataPage = service.selectPage(page, ew);
        //获取参数
        List<?> records = dataPage.getRecords();
        int total = dataPage.getTotal();
        int size = dataPage.getSize();
        int current = dataPage.getCurrent();
        //转换返回对象
        return new PageUtils(records, total, size, current);

    }

    /**
     * 根据部门过滤数据，封装返回EntityWrapper
     * @param ew
     * @return
     */
    public static Wrapper filtByDept4EW(Wrapper<?> ew) {
        if (ew != null) {
            ew.addFilterIfNeed(true, getFiltByDeptSql(null));
        }
        return ew;
    }

    /**
     * 获取部门筛选的条件字符串
     * @param tableAlias 表别名
     * @return
     */
    private static String getFiltByDeptSql(String tableAlias) {
        //获取表的别名
        if(UtilValidate.isNotEmpty(tableAlias)){
            tableAlias +=  ".";
        }else{
            tableAlias = "";
        }
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        StringBuffer sql = new StringBuffer();
        sql.append(tableAlias).append("dept_id in (").append(StringUtils.join(user.getDeptIdList(), ",")).append(")");
        return sql.toString();
    }

    /**
     * 转换实体参数为map,支持添加部门过滤参数
     * @param entityObj
     * @param filtByDeptFlag 是否需要根据部门过滤数据
     * @return
     */
    public static Map<String,Object> wrapperEntitpParam2Map(Object entityObj,String tableAlias, boolean filtByDeptFlag) {
        Map<String, Object> params = null;
        try {
            params = BeanUtil.transBean2Map(entityObj);
            if (filtByDeptFlag) {
                params.put(Constant.SQL_FILTER, getFiltByDeptSql(tableAlias));
            }
        } catch (Exception e) {
            logger.error("实体转换异常！",e);
        }
        return params;
    }
}
