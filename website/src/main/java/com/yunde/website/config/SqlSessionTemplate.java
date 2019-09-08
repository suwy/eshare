package com.yunde.website.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by Administrator on 2018/5/7.
 */
@Configuration
public class SqlSessionTemplate {

    @Primary
    @Bean(name = "sessionFactory")
    @Qualifier(value = "sessionFactory")
    public SessionFactory sessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception{
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        Properties hibernateProperties = new Properties();

        /*hibernate配置信息*/
        /*oracle数据库方言*/
        /*hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");*/
        /*mysql方言*/
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "false");
        //数据库取消配置外键，改为hibernate自动更新。并为避免表级锁，外键添加索引。
       // hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");

        /*开启二级缓存 ehcache*/
        /*hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", "true");
        hibernateProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        hibernateProperties.setProperty("hibernate.cache.provider_configuration_file_resource_path", "ehcache.xml");*/

        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "100");

        localSessionFactoryBean.setHibernateProperties(hibernateProperties);

        /*扫描hibernate注解配置的entity*/
        localSessionFactoryBean.setPackagesToScan("org.fsdcic.lzda.entity");

        if(localSessionFactoryBean.getObject() == null){
            try {
                localSessionFactoryBean.afterPropertiesSet();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return localSessionFactoryBean.getObject();
    }

}
