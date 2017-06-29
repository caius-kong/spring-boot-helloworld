package com.kyh.config.ds;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

/**
 * Created by kongyunhui on 2017/6/28.
 *
 * 主数据源配置
 * 1、指定dao包以及使用的sqlSessionFactory
 * 2、层层注入：DataSource --> DataSourceTransactionManager --> SqlSessionFactory
 * 3、必须制定主库
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
    @Value("${spring.datasource.primary.driverClassName}")
    private String driverClassName;

    @Bean(name="primaryDataSource")
    @Primary
    public DataSource primaryDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name="primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
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
