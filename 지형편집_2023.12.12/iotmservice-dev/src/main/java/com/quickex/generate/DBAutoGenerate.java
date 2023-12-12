package com.quickex.generate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.log4j.Log4j;

@Log4j
public class DBAutoGenerate {

    private static String  db_url = "jdbc:postgresql://127.0.0.1:5432/comdb";
    private static String db_user ="postgres";
    private static String db_pwd ="root";

    //code path
    //private static String projectPath = "D:\\xxx\\xx\\xxxxx\\iotmservice";
    private static String projectPath = "D:\\非凡智慧\\代码\\韩国项目最新代码1220DEV分支\\iotmservice";

    /**
     * main
     * @param args
     */
    public static void main(String[] args) {

        log.info("=== code genergate start  ===");

        //table name
        //autoGenerate("xxxxxx");
//        autoGenerate("ko_terrain_tid");
//        autoGenerate("ko_terrain_bid");
//        autoGenerate("ko_terrain_task");
//        autoGenerate("ko_terrain_polygon");
//        autoGenerate("ko_terrain_point");
//        autoGenerate("ko_terrain_model");
        log.info("=== code genergate end  ===");

        //************  be careful ******************
        //use IBaseService BaseServiceImpl
        //
        //not IService ServiceImpl
        //************  be careful ******************
    }


    /**
     * code generation
     * @param tableName
     */
    private static void autoGenerate(String tableName) {

        AutoGenerator mpg = new AutoGenerator();

        GlobalConfig gc = new GlobalConfig();

        gc.setOutputDir(projectPath + "/src/main/java");

        gc.setAuthor("author");

        gc.setOpen(false);

        //gc.setSwagger2(true);

        gc.setFileOverride(false);

        gc.setIdType(IdType.ID_WORKER_STR);

        gc.setDateType(DateType.ONLY_DATE);

        gc.setServiceName("I%sService");

        mpg.setGlobalConfig(gc);

        // Step3：
        DataSourceConfig dsc = new DataSourceConfig();
        //
        dsc.setUrl(db_url);
        // dsc.setSchemaName("testMyBatisPlus"); //

//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setDriverName("org.postgresql.Driver");
        // db user
        dsc.setUsername(db_user);
        // db pwd
        dsc.setPassword(db_pwd);
        mpg.setDataSource(dsc);

        // Step:4：
        PackageConfig pc = new PackageConfig();
        // parent
        pc.setParent("com.quickex");
        //
        pc.setModuleName("generate");
        //  entity
        pc.setEntity("entity");
        //  mapper
        pc.setMapper("dao");
        //  service
        pc.setService("service");
        //  controller
        pc.setController("controller");
        //xml
        //pc.setXml("classpath:mapper");

        mpg.setPackageInfo(pc);

        StrategyConfig strategy = new StrategyConfig();

        strategy.setInclude(tableName);

        strategy.setNaming(NamingStrategy.underline_to_camel);

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        strategy.setEntityLombokModel(true);

        strategy.setRestControllerStyle(true);

        strategy.setControllerMappingHyphenStyle(true);

        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);

        mpg.execute();
    }

}
