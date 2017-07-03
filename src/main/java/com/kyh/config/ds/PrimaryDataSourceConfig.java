package com.kyh.config.ds;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by kongyunhui on 2017/6/28.
 *
 * 主数据源配置
 * 1、指定dao包以及使用的sqlSessionFactory
 * 2、层层注入：DataSource --> DataSourceTransactionManager --> SqlSessionFactory
 * 3、必须制定主库
 *
 * 后补：考虑多数据源回滚问题，采用atomikos进行分布式事务管理，因此去掉TransactionManager。
 */
@Configuration
@MapperScan(basePackages = PrimaryDataSourceConfig.PACKAGE, sqlSessionFactoryRef="primarySqlSessionFactory")
public class PrimaryDataSourceConfig {
    static final String PACKAGE = "com.kyh.dao.primary";
    static final String MAPPER_LOCATION = "classpath:mybatis/mapper/primary/*.xml";
    static final String TYPE_ALIASES_PACKAGE = "com.kyh.model.primary";

    @Value("${spring.datasource.primary.url}")
    private String url;
    @Value("${spring.datasource.primary.username}")
    private String username;
    @Value("${spring.datasource.primary.password}")
    private String password;

    @Bean(name="primaryDataSource")
    @Primary
    public DataSource primaryDataSource(){
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName("primaryDataSource");
        atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"); // XA协议的数据源
        Properties properties = new Properties();
        properties.put("URL", url);
        properties.put("user", username);
        properties.put("password", password);
        atomikosDataSourceBean.setXaProperties(properties);

        atomikosDataSourceBean.setMinPoolSize(10);
        atomikosDataSourceBean.setMaxPoolSize(100);
        atomikosDataSourceBean.setBorrowConnectionTimeout(30);
        atomikosDataSourceBean.setTestQuery("select 1");
        atomikosDataSourceBean.setMaintenanceInterval(60);
        return atomikosDataSourceBean;
    }

    @Bean(name="primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(PrimaryDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setTypeAliasesPackage(PrimaryDataSourceConfig.TYPE_ALIASES_PACKAGE);
        return sessionFactory.getObject();
    }
}
