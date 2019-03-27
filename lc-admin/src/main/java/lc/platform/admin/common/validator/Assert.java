package lc.platform.admin.common.validator;

import lc.platform.admin.common.exception.GeneralRuntimeException;
import org.apache.commons.lang3.StringUtils;

public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new GeneralRuntimeException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new GeneralRuntimeException(message);
        }
    }
}
