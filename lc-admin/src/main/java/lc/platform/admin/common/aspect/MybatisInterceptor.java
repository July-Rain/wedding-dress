package lc.platform.admin.common.aspect;

/**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: mybatis拦截器
 * @date 2018/3/21 16:15
 */
//@Intercepts({
//        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
//})
//@Component
//public class MybatisInterceptor extends AbstractController implements Interceptor  {
//
//   @Value("${SynURL}")
//   private String url;
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        //获取当前请求
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        //加载jdbc
//        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean("jdbcTemplate");
//
//        try{
//            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];  // 获取xml中的一个select/update/insert/delete节点，主要描述的是一条SQL语句
//            Object parameter = null;
//            // 获取参数，if语句成立，表示sql语句有参数，参数格式是map形式
//            if (invocation.getArgs().length > 1) {
//                parameter = invocation.getArgs()[1];
//                System.out.println("parameter = " + parameter);
//            }
//            String sqlId = mappedStatement.getId(); // 获取到节点的id,即sql语句的id
//            System.out.println("sqlId = " + sqlId);
//            BoundSql boundSql = mappedStatement.getBoundSql(parameter);  // BoundSql就是封装myBatis最终产生的sql类
//            Configuration configuration = mappedStatement.getConfiguration();  // 获取节点的配置
//            String sql = showSql(configuration, boundSql); // 获取到最终的sql语句
//            System.out.println("sql = " + sql);
//            //过滤系统操作记录表
//            if (!sql.contains("sys_record_oper")) {
//
//                Base64.Encoder encoder = Base64.getEncoder();
//                byte[] textByte = sql.getBytes("UTF-8");
//                //编码
//                String enSql = encoder.encodeToString(textByte);
//                System.out.println("enSql = " + enSql);
//                //获取当前登录人信息
//                SysUserEntity user = getUser();
//
//                //获取ip
//                String ip=IpUtil.getIpAddr(request);
//
//                //线程池调用相关的接口
//                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
//                //多线程池执行线程任务
//                fixedThreadPool.execute(new MyThread(url, enSql, user.getDeptId().toString()));
//                //拼接相关的保存系统操作记录的SQL
//                String insertsql="insert into sys_record_oper (id,method,oper_sql,ip,dept_id,last_upd_time,last_upd_person_id,last_upd_person_name) VALUES('"+IdWorker.getId()+"','"+sqlId+"','"+enSql+"','"+ip+"','"+user.getDeptId().toString()+"',NOW(),'"+user.getUserId().toString()+"','"+user.getNickName()+"');";
//                //执行保存到系统记录表中的SQL
//                jdbcTemplate.execute(new ConnectionCallback<Object>() {
//                    @Override
//                    public Object doInConnection(Connection con) throws SQLException, DataAccessException {
//                        con.setAutoCommit(false);
//                        PreparedStatement preparedStatement = con.prepareStatement(insertsql);
//                        boolean execute = preparedStatement.execute();
//                        con.commit();
//                        return null;
//                    }
//                });
//
//            }
//
//
//        }catch(Exception e){
//            // log.error(e.getMessage(), e);
//        }
//        return invocation.proceed();// 执行完上面的任务后，不改变原有的sql执行过程
//    }
//    // 封装了一下sql语句，使得结果返回完整xml路径下的sql语句节点id + sql语句
//    public noLock String getSql(Configuration configuration, BoundSql boundSql,String sqlId) {
//        String sql = showSql(configuration, boundSql);
//        StringBuilder str = new StringBuilder(100);
//        str.append(sqlId);
//        str.append(":");
//        str.append(sql);
//        return str.toString();
//    }
//    /*如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理<br>　　*/
//    private noLock String getParameterValue(Object obj) {
//        String value = null;
//        if (obj instanceof String) {
//            value = "'" + obj.toString() + "'";
//        } else if (obj instanceof Date) {
//            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
//            value = "'" + formatter.format(new Date()) + "'";
//        } else {
//            if (obj != null) {
//                value = obj.toString();
//            } else {
//                value = "";
//            }
//
//        }
//        return value;
//    }
//    // 进行？的替换
//    public noLock String showSql(Configuration configuration, BoundSql boundSql) {
//        Object parameterObject = boundSql.getParameterObject();  // 获取参数
//        List<ParameterMapping> parameterMappings = boundSql
//                .getParameterMappings();
//        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");  // sql语句中多个空格都用一个空格代替
//        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
//            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry(); // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换<br>　　　　　　　// 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
//            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
//                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
//
//            } else {
//                MetaObject metaObject = configuration.newMetaObject(parameterObject);// MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
//                for (ParameterMapping parameterMapping : parameterMappings) {
//                    String propertyName = parameterMapping.getProperty();
//                    if (metaObject.hasGetter(propertyName)) {
//                        Object obj = metaObject.getValue(propertyName);
//                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
//                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
//                        Object obj = boundSql.getAdditionalParameter(propertyName);  // 该分支是动态sql
//                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
//                    }else{sql=sql.replaceFirst("\\?","缺失");}//打印出缺失，提醒该参数缺失并防止错位
//                }
//            }
//        }
//        return sql;
//    }
//
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//}
//class MyThread implements Runnable {
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getEnsql() {
//        return ensql;
//    }
//
//    public void setEnsql(String ensql) {
//        this.ensql = ensql;
//    }
//
//    public String getDeptId() {
//        return deptId;
//    }
//
//    public void setDeptId(String deptId) {
//        this.deptId = deptId;
//    }
//
//    private String url;
//    private String ensql;
//    private String deptId;
//
//    public MyThread(String url, String ensql, String deptId) {
//        this.url = url;
//        this.ensql = ensql;
//        this.deptId = deptId;
//    }
//
//
//
//    @Override
//     public void run() {
//
//                   try {
//                       doRequestMenthod(url,ensql,deptId);
//                   }catch (Exception e){
//
//                   }
//             }
//
//    public void doRequestMenthod(String url,String ensql,String deptId) throws Exception{
//        JSONObject postJson = new JSONObject();
//        //封装请求体
//        postJson.put("ensql", ensql);
//        postJson.put("deptId", deptId);
//        //调用相关接口
//        String httpResult = SysHttpClient.doPostRequest(url, postJson.toString());
//
//        System.out.println("postJson = " + postJson.toString());
//        if(!UtilValidate.isEmpty(httpResult)){
//            JSONObject postJson1=JSONObject.fromObject(httpResult);
//
//            if(!"0".equals(postJson1.get("code"))){
//                throw new Exception("调用相应的接口异常");
//            }
//        }else{
//            throw new Exception("调用相应的接口异常");
//        }
//    }
// }