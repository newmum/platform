package net.evecom.rd.ie.baseline.core.db.config;

import com.robert.vesta.service.intf.IdService;
import net.evecom.rd.ie.baseline.utils.file.PropertiesUtils;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.IDAutoGen;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.db.OracleStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class SQLManagerConfig {

	@Bean(name = "examSqlManager")
	public SqlManagerFactoryBean getSqlManagerFactoryBean(@Qualifier("dataSource") DataSource master) {
        System.out.println("beetl getSqlManagerFactoryBean init");
		PropertiesUtils global = new PropertiesUtils(SQLManagerConfig.class.getClassLoader().getResourceAsStream("db.properties"));
		SqlManagerFactoryBean factoryBean = new SqlManagerFactoryBean();
		BeetlSqlDataSource source = new BeetlSqlDataSource();
		Properties extProperties = new Properties();
		try {
			InputStream in = SQLManagerConfig.class.getClassLoader().getResourceAsStream("btsql.properties");
			extProperties.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		factoryBean.setExtProperties(extProperties);
		source.setMasterSource(master);
		// 设置打印sql语句
		factoryBean.setInterceptors(new Interceptor[] { new DebugInterceptor() });
		factoryBean.setCs(source);
		Map map = new HashMap();
		map.put("createId", new IDAutoGen(){
			@Override
			public Object nextID(String params) {
                ApplicationContext ac = new ClassPathXmlApplicationContext(
                        "vesta.xml");
                IdService idService = (IdService) ac.getBean("idService");
				return idService.genId()+"";
			}
		});
		factoryBean.setIdAutoGens(map);
		DBStyle dbStyle = new MySqlStyle();
        String key = global.getKey("datasource.driver-class-name");
        if(key.lastIndexOf("oracle")>-1){
            dbStyle=new OracleStyle();
        }else if(key.lastIndexOf("mysql")>-1){
            dbStyle=new MySqlStyle();
        }
        factoryBean.setDbStyle(dbStyle);
		// 开启驼峰
		factoryBean.setNc(new UnderlinedNameConversion());
		// sql文件路径
		// SQLManagerConfig.class.getClassLoader().getResource("/sql").getFile();
		factoryBean.setSqlLoader(new ClasspathLoader("/sql"));
		return factoryBean;
	}

	/**
	 * 配置包扫描
	 *
	 * @return
	 */
	@Bean(name = "examSqlScannerConfigurer")
	public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer() {
		System.out.println("beetl examSqlScannerConfigurer init");
		BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
		conf.setBasePackage("net.evecom.**.model.dao");
		conf.setDaoSuffix("Dao");
		conf.setSqlManagerFactoryBeanName("examSqlManager");
		return conf;
	}
}
