package com.yunde.website.config;

import org.hibernate.SessionFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

;

/**
 * Created by Administrator on 2018/5/7.
 */
//@Configuration
public class HibernateTransactTemplate {

    @Bean(name = "transactionManager")
    @Qualifier(value = "transactionManager")
    public HibernateTransactionManager transactionManager(@Qualifier("sessionFactory") SessionFactory sessionFactory){
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);

        return hibernateTransactionManager;
    }

    @Bean(name = "transactionAdvice")
    @Qualifier(value = "transactionAdvice")
    public TransactionInterceptor transactionAdvice(@Qualifier("transactionManager") HibernateTransactionManager hibernateTransactionManager){
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();

        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("delete*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("import*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("edit*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
        transactionAttributes.setProperty("newTx*", "PROPAGATION_REQUIRES_NEW");
        transactionAttributes.setProperty("neverTx*", "PROPAGATION_NEVER");

        transactionInterceptor.setTransactionAttributes(transactionAttributes);
        transactionInterceptor.setTransactionManager(hibernateTransactionManager);

        return transactionInterceptor;
    }

    /*@Bean
    public BeanNameAutoProxyCreator transactionAutoProxy (@Qualifier("transactionAdvice") TransactionInterceptor transactionInterceptor){
        BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();

        transactionAutoProxy.setInterceptorNames("transactionAdvice");
        transactionAutoProxy.setBeanNames("*Service", "*ServiceImpl");
        transactionAutoProxy.setProxyTargetClass(true);

        return transactionAutoProxy;
    }*/

    @Bean
    public Advisor transactionAdvisor(@Qualifier("transactionAdvice") TransactionInterceptor transactionInterceptor){
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* org.fsdcic.lzda.service..*.*(..))");

        advisor.setPointcut(pointcut);
        advisor.setAdvice(transactionInterceptor);
        advisor.setOrder(1);

        return advisor;
    }
}
