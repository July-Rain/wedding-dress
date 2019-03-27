package lc.platform.admin.modules.sys.controller;

import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.common.utils.Result;
import lc.platform.admin.modules.sys.entity.SysRecordOperEntity;
import lc.platform.admin.modules.sys.service.SysRecordOperService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;


/**
 * 系统增删改日志
 *
 * @author XuMinglu
 * @email 542686693@qq.com
 * @date 2018-03-21 17:51:17
 */
@RestController
@RequestMapping("mgt/sysrecordoper")
public class SysRecordOperController extends AbstractController{

    @Autowired
    private SysRecordOperService sysRecordOperService;
    @Resource
    private JdbcTemplate jdbcTemplate;


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("mgt:sysrecordoper:list")
    public Result list(@RequestParam Map<String, Object> params){

        PageUtils page = sysRecordOperService.queryPage(params);

        return Result.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("mgt:sysrecordoper:info")
    public Result info(@PathVariable("id") Long id){

        SysRecordOperEntity sysRecordOper = sysRecordOperService.selectById(id);

        return Result.ok().put("sysRecordOper", sysRecordOper);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("mgt:sysrecordoper:save")
    public Result save(@RequestBody SysRecordOperEntity sysRecordOper){

        //you should set the SysRecordOperEntity's 'id' value
		sysRecordOperService.insert(sysRecordOper);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("mgt:sysrecordoper:update")
    public Result update(@RequestBody SysRecordOperEntity sysRecordOper){

        sysRecordOperService.updateById(sysRecordOper);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("mgt:sysrecordoper:delete")
    public Result delete(@RequestBody Long[] ids){

		sysRecordOperService.deleteBatchIds(Arrays.asList(ids));

        return Result.ok();
    }

    //系统接口
   // @RequestMapping(value = "/synDate", method = RequestMethod.POST)
    @RequestMapping("/synData")
    @ResponseBody
    public String synDate(@RequestBody JSONObject args, HttpServletRequest request)
    {

        JSONObject result = new JSONObject();
        //获取部门id
        String deptId = args.get("deptId").toString();
        String ensql=args.get("ensql").toString();

       //base64编码sql
        Base64.Decoder decoder = Base64.getDecoder();
        //执行接口传过来的SQL同步相关数据
        try {
            final String deSql = new String(decoder.decode(ensql), "UTF-8");
            jdbcTemplate.execute(new ConnectionCallback<Object>() {
                @Override
                public Object doInConnection(Connection con) throws SQLException, DataAccessException {
                    con.setAutoCommit(false);
                    PreparedStatement preparedStatement = con.prepareStatement(deSql);
                    boolean execute = preparedStatement.execute();
                    con.commit();
                    return null;
                }
            });
            result.put("desql",deSql);
            result.put("code","0");
        } catch (UnsupportedEncodingException e) {
            result.put("code","001");
            e.printStackTrace();
        }

        return result.toString();
    }

}
