package lc.platform.admin.common.utils;

import lc.platform.admin.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库工具类
 */
@Component
public class DataBaseUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SysConfigService sysConfigService;

    public static Map<String, String> dbPkMap = new HashMap<>();
    public static Map<String, String> javaPkMap = new HashMap<>();

    @PostConstruct
    public void init() {
        String schemaName = sysConfigService.getValue("schemaName");
        if (UtilValidate.isEmpty(schemaName)) return;

        StringBuffer sql = new StringBuffer();
        sql.append("    SELECT");
        sql.append("    	k.column_name pkColName,");
        sql.append("    	t.table_name tableName");
        sql.append("    FROM");
        sql.append("    	information_schema.table_constraints t");
        sql.append("    JOIN information_schema.key_column_usage k USING (");
        sql.append("    	constraint_name,");
        sql.append("    	table_schema,");
        sql.append("    	table_name");
        sql.append("    )");
        sql.append("    WHERE");
        sql.append("    	t.constraint_type = 'PRIMARY KEY'");
        sql.append("    AND t.table_schema = ?");
        List<Map<String, Object>> tablePkMapList = jdbcTemplate.queryForList(sql.toString(), new Object[]{schemaName});
        for (Map<String, Object> map : tablePkMapList) {
            dbPkMap.put((String) map.get("tableName"), (String) map.get("pkColName"));
            javaPkMap.put(ModelUtil.dbNameToClassName((String) map.get("tableName")),
                    ModelUtil.dbNameToVarName((String)map.get("pkColName")));
//            String sysUserPKName = javaPkMap.get("SysUser");
        }
    }
}
