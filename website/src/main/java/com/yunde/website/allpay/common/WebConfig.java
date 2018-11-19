package com.yunde.website.allpay.common;

import com.jfinal.base.UserSession;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.handler.WritableParameterHandler;
import com.jfinal.ext.handler.XssHandler;
import com.jfinal.ext.plugin.global.GlobalConfigPlugin;
import com.jfinal.ext.plugin.validate.ValidatePlugin;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.route.AutoBindRoutes;
import com.jfinal.template.Engine;
import com.yunde.website.allpay.api._BasicModuleMappingKit;
import com.yunde.website.allpay.api._ModuleMappingKit;
import com.yunde.website.allpay.api.bean._PluginMappingKit;
import com.yunde.website.allpay.base.plugin.allpay.AllpayModulePlugin;
import com.yunde.website.allpay.common.model._MappingKit;
import com.yunde.website.allpay.common.util.JfinalDictTag;
import com.yunde.website.allpay.common.util.JfinalTag;
import com.yunde.website.allpay.common.util.JsonUtil;
import org.assertj.core.util.DateUtil;

/**
 * @author: suwy
 * @date: 2018/11/18
 * @decription:
 */
public class WebConfig extends JFinalConfig {

    /**
     * 配置常量
     */
    @Override
    public void configConstant(Constants me) {
        //系统配置文件
        PropKit.use("config.txt");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setViewType(ViewType.JFINAL_TEMPLATE);
    }

    @Override
    public void configEngine(Engine me) {
        me.addSharedFunction("/WEB-INF/templete/_index_view.html");
        me.addSharedFunction("/WEB-INF/templete/_list_view.html");
        me.addSharedFunction("/WEB-INF/templete/_page_view.html");
        me.addSharedFunction("/WEB-INF/templete/_form_view.html");
        me.addSharedFunction("/WEB-INF/templete/_list_tree_view.html");
        me.addSharedFunction("/WEB-INF/templete/_button.html");
        me.addSharedFunction("/WEB-INF/templete/_button_group.html");
        me.addSharedFunction("/WEB-INF/templete/_input.html");
        me.addSharedFunction("/WEB-INF/templete/_dict.html");
        me.addSharedFunction("/WEB-INF/templete/_search_bar.html");

        me.addSharedObject("loginUser", UserSession.get());

        me.addSharedObject("tag", new JfinalTag());
        me.addSharedObject("dictTag", new JfinalDictTag());

        me.addSharedObject("sk", new com.jfinal.kit.StrKit());
        me.addSharedObject("ju", new JsonUtil());
        me.addSharedObject("du", new DateUtil());
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        AutoBindRoutes route = new AutoBindRoutes();
        route.setBaseViewPath("/modules/web");
        route.autoScan(false);
        me.add(route);
    }

    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins me) {
        LogKit.info("配置druid数据库连接池插件");
        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
        me.add(dp);

        LogKit.info("关联model和表");
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        if (!GlobalConfig.isProduction()) {
            arp.setShowSql(true);
        }
        _MappingKit.mapping(arp);
        me.add(arp);

        if (GlobalConfig.isProduction()) {
            LogKit.info("配置druid（只读）数据库连接池插件");
            DruidPlugin read_dp = new DruidPlugin(PropKit.get("read.jdbcUrl"), PropKit.get("user"),
                    PropKit.get("password"));
            me.add(read_dp);
            me.add(new ActiveRecordPlugin("read", read_dp));
        }

        if (!PropKit.getBoolean("devMode", true)) {
            LogKit.info("初始化graylog插件");
//            me.add(new GraylogPlugin());
        }

        LogKit.info("初始化globalconfig插件");
        me.add(new GlobalConfigPlugin());

        LogKit.info("初始化validate插件");
        me.add(new ValidatePlugin());

        LogKit.info("初始化AllpayModule插件");
        AllpayModulePlugin allpayModulePlugin = new AllpayModulePlugin(PropKit.get("allpay_api_url"));
        _BasicModuleMappingKit.mapping(allpayModulePlugin);
        _ModuleMappingKit.mapping(allpayModulePlugin);
        _PluginMappingKit.mapping(allpayModulePlugin);

        //基础的关联关系
//        allpayModulePlugin.addSelectBean("$proprietor", ProprietorModule.me.proprietor, "proprietorId", null, null);
//        allpayModulePlugin.addSelectBean("$device", DeviceModule.me.device, "deviceId", null, null);
//        allpayModulePlugin.addSelectBean("$producer", ProducerModule.me.producer, "producerId", null, null);
//        allpayModulePlugin.addSelectBean("$propAddress", ProprietorModule.me.proprietor, "addressId", null, null);
//        allpayModulePlugin.addSelectBean("$factory", FactoryModule.me.factory, "factoryId", null, null);

        me.add(allpayModulePlugin);

//        if (PropKit.getBoolean("redis", false)) {
//            LogKit.info("初始化redis连接池");
//            RedisPlugin rp = new RedisPlugin(PropKit.get("redis.cacheName"),
//                    PropKit.get("redis.host"),
//                    PropKit.getInt("redis.port"),
//                    Protocol.DEFAULT_TIMEOUT,
//                    PropKit.get("redis.password"),
//                    PropKit.getInt("redis.database", 0)
//            );
//            rp.getJedisPoolConfig().setMaxIdle(50);
//            rp.getJedisPoolConfig().setMaxTotal(50);
//            rp.getJedisPoolConfig().setMinIdle(30);
//            me.add(rp);
//
//            RedisDb.setPrefix(PropKit.get("resis.prefix"));
//        }
    }

    /**
     * 系统启动完成之后初始化数据
     */
    @Override
    public void afterJFinalStart() {
    }

    /**
     * 系统关闭之前
     */
    @Override
    public void beforeJFinalStop() {

    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {
    }

    /**
     * 配置处理器
     */

    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("contextPath"));
        me.add(new XssHandler());
        me.add(new WritableParameterHandler());
    }

    /**
     * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     */
    public static void main(String[] args) {
//        JFinal.start("WebRoot", 80, "/", 5);
        JFinal.start("WebRoot", 8081, "/");
    }
}
