package com.yunde.website.allpay.common;

import com.jfinal.ext.model.RedisModel;
import com.jfinal.ext.model.RedisUnionAttrModel;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

public class GeneratorConfig {
    public static DataSource getDataSource() {
        Prop p = PropKit.use("config.txt");
        DruidPlugin druid = new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
        druid.start();
        return druid.getDataSource();
    }

    public static void main(String[] args) {
        // base model 所使用的包名
        String baseModelPackageName = "com.allpay.common.model.base";
        // base model 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/com/allpay/common/model/base";

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "com.allpay.common.model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        // 创建生成器
        Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        // 设置数据库方言
        gernerator.setDialect(new MysqlDialect());
        gernerator.addExcludedTableType("table", "view");
        // 添加需要生成的表
        gernerator.addIncludedTable("sys_menu", "SysMenu", RedisModel.class);
        gernerator.addIncludedTable("sys_resources", "SysResource", RedisModel.class);
        gernerator.addIncludedTable("sys_role", "SysRole", RedisModel.class);
        gernerator.addIncludedTable("sys_role_menu");
        gernerator.addIncludedTable("sys_role_resource");
        gernerator.addIncludedTable("sys_user", "SysUser", RedisModel.class);
        gernerator.addIncludedTable("sys_user_menu");
        gernerator.addIncludedTable("sys_user_resource");
        gernerator.addIncludedTable("sys_user_role");
        gernerator.addIncludedTable("operators", "Operator", RedisUnionAttrModel.class);

        //gernerator.setBeanGenerator(baseModelPackageName.replace("base", "bean"), baseModelOutputDir.replace("base", "bean"));
        // 设置是否在 Model 中生成 dao 对象
        gernerator.setGenerateDaoInModel(true);
        // 设置是否生成Model
        gernerator.setIsBuildBaseModelColunms(true);
        gernerator.setGenerateModel(true);
        // 设置是否生成MappingKit
        gernerator.setGenerateMappingKit(true);
        // 设置是否生成字典文件
        gernerator.setGenerateDataDictionary(true);
        // 生成
        gernerator.generate();
    }

}
