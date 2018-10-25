package net.evecom.core.rbac.annotatoin;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {
	boolean save() default false;

	boolean remove() default false;
}
