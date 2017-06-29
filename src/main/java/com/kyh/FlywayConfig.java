package com.kyh;

import com.kyh.config.ds.PrimaryDataSourceConfig;
import com.kyh.config.ds.SecondaryDataSourceConfig;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
/**
 * Created by kongyunhui on 2017/6/28.
 *
 * Flyway：多数据库迁移
 *
 * spring boot 默认使用FlywayAutoConfiguration进行自动化配置，仅迁移Primary数据源。因此，多个数据源迁移，需要覆盖默认配置。
 *
 * 代码分析：
 * 查看FlywayAutoConfiguration源码（主要是flyway、flywayInitializer这两个方法），可知只需重新创建FlywayBean，并保证flywayInitializer的执行，即执行migrate方法。
 *
 * 但是，用户自定义FlywayBean后，flywayInitializer不会执行①，因此我们选择初始化bean时执行migrate方法。
 *
 * ①Spring Boot的自动配置机制依靠@ConditionalOnMissingBean注解判断是否执行初始化代码，即如果用户已经创建了bean，则相关的初始化代码不再执行。
 *
 */
@Configuration
@AutoConfigureAfter({PrimaryDataSourceConfig.class, SecondaryDataSourceConfig.class}) // 在其后加载，保证DataSource实例的存在
public class FlywayConfig{
    // db1
    @Bean(initMethod = "migrate")
    public Flyway flyway1(@Qualifier("primaryDataSource") DataSource dataSource) {
        Flyway clinic = new Flyway();
        clinic.setDataSource(dataSource);
        clinic.setLocations("classpath:db/migration/primary");
        return clinic;
    }

    // db2
    @Bean(initMethod = "migrate")
    public Flyway flyway2(@Qualifier("secondaryDataSource") DataSource dataSource) {
        Flyway clinic = new Flyway();
        clinic.setDataSource(dataSource);
        clinic.setLocations("classpath:db/migration/secondary");
        return clinic;
    }
}
