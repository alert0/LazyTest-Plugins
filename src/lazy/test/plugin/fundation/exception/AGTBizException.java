package lazy.test.plugin.fundation.exception;

/**
 * Created by wyzhouqiang on 2015/11/21.
 */
public class AGTBizException extends RuntimeException {

	private static final long serialVersionUID = -7283338887451030430L;
	
	private String code;

    public AGTBizException(String message, String code) {
        super(message);
        this.code = code;
    }

    public AGTBizException(String message, String code, Throwable e) {
        super(message, e);
        this.code = code;
    }

    public AGTBizException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
