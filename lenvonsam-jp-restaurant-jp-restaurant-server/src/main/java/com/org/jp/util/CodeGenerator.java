package com.org.jp.util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器
 *
 * @author juny
 */
public class CodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("samy");
        gc.setOpen(false);
        gc.setFileOverride(true);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/jp_restaurant?serverTimezone=Asia/Shanghai&allowMultiQueries=true&characterEncoding=UTF-8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("jprestaurant");
        dsc.setPassword("jprestaurant");
        mpg.setDataSource(dsc);


        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("model");
        pc.setEntity("model");
        pc.setParent("com.org.jp");
        mpg.setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        strategy.setSuperEntityColumns("id");
//        strategy.setInclude(scBaseControlleranner("表名，多个英文逗号分割").split(","));
//        strategy.setExclude("t_sys_dict");
        strategy.setInclude("t_sys_product");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");
        List<TableFill> tableFillList = new ArrayList<>();
        TableFill tfUpdateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        TableFill tfCreateTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill tfPlatformCode = new TableFill("platform_code", FieldFill.INSERT);
        TableFill tfCreateUserCode = new TableFill("create_user_code", FieldFill.INSERT);
        TableFill tfCreateUserName = new TableFill("create_user_name", FieldFill.INSERT);
        TableFill tfUpdateUserName = new TableFill("update_user_name", FieldFill.UPDATE);
        TableFill tfUpdateUserCode = new TableFill("update_user_code", FieldFill.UPDATE);
        tableFillList.add(tfUpdateTime);
        tableFillList.add(tfCreateTime);
        tableFillList.add(tfPlatformCode);
        tableFillList.add(tfCreateUserCode);
        tableFillList.add(tfCreateUserName);
        tableFillList.add(tfUpdateUserName);
        tableFillList.add(tfUpdateUserCode);
        strategy.setTableFillList(tableFillList);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setVersionFieldName("version");
        strategy.setLogicDeleteFieldName("del_flag");
        mpg.setStrategy(strategy);
        mpg.execute();
    }

}
