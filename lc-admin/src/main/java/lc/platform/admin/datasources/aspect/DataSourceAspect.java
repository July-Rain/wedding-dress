/*
package lc.platform.admin.datasources.aspect;

import lc.platform.admin.datasources.DataSourceNames;
import lc.platform.admin.datasources.DynamicDataSource;
import lc.platform.admin.datasources.annotation.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
public class DataSourceAspect implements Ordered {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(lc.platform.admin.datasources.annotation.DataSource)")
//    @Pointcut("(execution(* com.baomidou.mybatisplus.service.impl.ServiceImpl.insert*(..)))) " +
//            "or (execution(* com.baomidou.mybatisplus.service.impl.ServiceImpl.update*(..))) " +
//            "or (execution(* com.baomidou.mybatisplus.service.impl.ServiceImpl.delete*(..))) " )
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        System.out.println(method.getName() + method.getClass());
        DataSource ds = method.getAnnotation(DataSource.class);
        if(ds == null){
            DynamicDataSource.setDataSource(DataSourceNames.CITY);
            logger.debug("set datasource is " + DataSourceNames.CITY);
        }else {
            DynamicDataSource.setDataSource(ds.name());
            logger.debug("set datasource is " + ds.name());
        }

        try {
            return point.proceed();//
        } finally {
            DynamicDataSource.clearDataSource();
            logger.debug("clean datasource");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
*/
