package lc.platform.admin.common;

import lc.platform.admin.common.utils.DateUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 泛化的map对象类
 *
 * @author Xuminglu
 * 2016-9-23 上午9:51:23
 */
public class GenericMapRowMapper implements RowMapper<Map<String, Object>> ,Serializable {

    @Override
    public Map<String, Object> mapRow(ResultSet rs, int index) throws SQLException {
        long start = System.currentTimeMillis();
        Map<String, Object> genericMap = new HashMap<>();
        if (rs == null) {
            return null;
        } else {
            ResultSetMetaData metaData = rs.getMetaData();
            if (metaData != null) {
                int total = metaData.getColumnCount();
                for (int i = 1; i <= total; i++) {
                    /*<列> 无 <别名>, columnLabel(不为null) 默认 等于 columnName [jdbc中的约定]*/
                    String columnLabel = metaData.getColumnLabel(i);
                    String columnName = metaData.getColumnName(i);
                    if (columnName != null) {
                        if (columnName.equalsIgnoreCase(columnLabel)) {
                            // <列> 无 <别名>
//                            genericMap.put(ModelUtil.dbNameToVarName(columnName), rs.getObject(columnName));
                            if (rs.getObject(columnName) instanceof byte[]) {
                                byte[] bytes = (byte[]) rs.getObject(columnName);
                                String valueString = "byte-" + new String(Base64.encodeBase64(bytes));
//                                System.out.println("valueString:" + valueString);
                                genericMap.put(columnName, valueString);
                            } else if (rs.getObject(columnName) instanceof Date) {
                                String valueString = DateUtils.format((Date)rs.getObject(columnName),DateUtils.DATE_TIME_PATTERN);
                                genericMap.put(columnName, valueString);
                            } else {
                                genericMap.put(columnName, rs.getObject(columnName));
                            }
                        }
//                        else {
//                            // <列> 有 <别名>
//                            genericMap.put(columnLabel, rs.getObject(columnLabel));
//                        }
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("每条数据处理 用时间：" + (end - start) + " 毫秒!");
        return genericMap;
    }

}