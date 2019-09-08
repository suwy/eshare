package com.yunde.website.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * Created by Administrator on 2018/5/7.
 */
@Configuration
public class HibernateDaoTemplate {
    /*@Autowired
    private HibernateDaoSupport hibernateDaoSupport;*/

    @Primary
    @Bean(name = "hibernateDaoSupport")
    public HibernateDaoSupport hibernateDaoSupport(@Qualifier("sessionFactory") SessionFactory sessionFactory){
        HibernateDaoSupport hibernateDaoSupport = new HibernateDaoSupport() {
            @Override
            protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
                return super.createHibernateTemplate(sessionFactory);
            }
        };
        hibernateDaoSupport.setSessionFactory(sessionFactory);

        return hibernateDaoSupport;
    }
}
