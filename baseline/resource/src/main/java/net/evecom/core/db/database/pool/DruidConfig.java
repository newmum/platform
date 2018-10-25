package net.evecom.core.db.database.pool;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import net.evecom.utils.file.PropertiesUtils;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DruidConfig {
    private PropertiesUtils global = new PropertiesUtils(
            DruidConfig.class.getClassLoader().getResourceAsStream("db.properties"));

    @Bean(destroyMethod = "close", initMethod = "init", name = "dataSource")
    public DataSource druidDataSource() {
        System.out.println("druidDataSource init");
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setName("dataSource");
        druidDataSource.setDriverClassName(global.getKey("datasource.driver-class-name"));
        druidDataSource.setUrl(global.getKey("datasource.url"));
        druidDataSource.setUsername(global.getKey("datasource.username"));
        druidDataSource.setPassword(global.getKey("datasource.password"));
        druidDataSource.setInitialSize(Integer.valueOf(global.getKey("datasource.initialSize")));
        druidDataSource.setMaxActive(Integer.valueOf(global.getKey("datasource.maxActive")));
        druidDataSource.setMaxWait(Long.valueOf(global.getKey("datasource.maxWait")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(
                Long.valueOf(global.getKey("datasource.timeBetweenEvictionRunsMillis")));
        druidDataSource
                .setMinEvictableIdleTimeMillis(Long.valueOf(global.getKey("datasource.minEvictableIdleTimeMillis")));
        druidDataSource.setValidationQuery(global.getKey("datasource.validationQuery"));
        druidDataSource.setTestWhileIdle(Boolean.valueOf(global.getKey("datasource.testWhileIdle")));
        druidDataSource.setTestOnBorrow(Boolean.valueOf(global.getKey("datasource.testOnBorrow")));
        druidDataSource.setTestOnReturn(Boolean.valueOf(global.getKey("datasource.testOnReturn")));
        druidDataSource.setPoolPreparedStatements(Boolean.valueOf(global.getKey("datasource.poolPreparedStatements")));
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(
                Integer.valueOf(global.getKey("datasource.maxPoolPreparedStatementPerConnectionSize")));
        druidDataSource.setConnectionProperties(global.getKey("datasource.connectionProperties"));

//        List<Filter> filters = new ArrayList<Filter>();
//        StatFilter statFilter = new StatFilter();
//        statFilter.setSlowSqlMillis(Long.valueOf(global.getKey("datasource.stat-filter.slowSqlMillis")));
//        statFilter.setLogSlowSql(Boolean.valueOf(global.getKey("datasource.stat-filter.logSlowSql")));
//        filters.add(statFilter);
//        Log4jFilter log4jFilter = new Log4jFilter();
//        log4jFilter.setDataSourceLogEnabled(Boolean.valueOf(global.getKey("datasource.log-filter.dataSourceLogEnabled")));
//        log4jFilter.setStatementExecutableSqlLogEnable(Boolean.valueOf(global.getKey("datasource.log-filter.statementExecutableSqlLogEnable")));
//        filters.add(log4jFilter);
//
//        druidDataSource.setProxyFilters(filters);
        com.alibaba.druid.filter.logging.Slf4jLogFilter slf4jLogFilter = new com.alibaba.druid.filter.logging.Slf4jLogFilter();
        slf4jLogFilter.setStatementExecutableSqlLogEnable(false);
        List<Filter> list = new ArrayList<Filter>();
        list.add(slf4jLogFilter);
        druidDataSource.setProxyFilters(list);
        druidDataSource.setRemoveAbandoned(Boolean.valueOf(global.getKey("datasource.removeAbandoned")));
        druidDataSource.setRemoveAbandonedTimeout(Integer.valueOf(global.getKey("datasource.removeAbandonedTimeout")));
        druidDataSource.setLogAbandoned(Boolean.valueOf(global.getKey("datasource.logAbandoned")));
        try {
            druidDataSource.setFilters(global.getKey("datasource.filters"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }

    @Bean(name = "druid-stat-interceptor")
    public DruidStatInterceptor getDruidStatInterceptor() {
        DruidStatInterceptor druidStatInterceptor=new DruidStatInterceptor();
        return druidStatInterceptor;
    }
    @Bean(name = "druid-stat-pointcut")
    public JdkRegexpMethodPointcut getJdkRegexpMethodPointcut() {
        JdkRegexpMethodPointcut jdkRegexpMethodPointcut=new JdkRegexpMethodPointcut();
        jdkRegexpMethodPointcut.setPatterns("com.ssm.maven.core.service.*","com.ssm.maven.core.dao.*");
        return jdkRegexpMethodPointcut;
    }
}
