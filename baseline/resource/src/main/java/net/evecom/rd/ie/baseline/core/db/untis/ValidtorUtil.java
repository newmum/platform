package net.evecom.rd.ie.baseline.core.db.untis;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidtorUtil {
	/**
	 * 验证属性是否正常
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> String validbean(T t) {
		ValidatorFactory vFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = vFactory.getValidator();
		Set<ConstraintViolation<T>> set = validator.validate(t);
		if (set.size() > 0) {
			StringBuilder validateError = new StringBuilder();
			for (ConstraintViolation<T> val : set) {
				validateError.append(val.getMessage());
				validateError.append(",");
			}
			return validateError.toString();
		}
		return null;
	}

}
