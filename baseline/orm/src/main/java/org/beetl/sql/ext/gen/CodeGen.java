package org.beetl.sql.ext.gen;

import org.beetl.sql.core.db.TableDesc;

public interface CodeGen {
	public void genCode(String entityPkg, String entityClass, TableDesc tableDesc, GenConfig config, boolean isDisplay);
}
