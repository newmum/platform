package net.evecom.rd.ie.baseline.core.db.database.pool;

import com.alibaba.druid.support.http.StatViewServlet;
import net.evecom.rd.ie.baseline.utils.file.PropertiesUtils;

public class DruidServlet extends StatViewServlet {
	private static final long serialVersionUID = 1L;
	private PropertiesUtils global = new PropertiesUtils(
			DruidFilter.class.getClassLoader().getResourceAsStream("db.properties"));

	@Override
	public String getInitParameter(String name) {
		name = "druid." + name;
		String value = global.getKey(name);
		System.out.println(name + ":" + value);
		return value;
	}
}
