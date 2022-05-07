package hay.hay.articleManage.Dto;

import java.io.Serializable;

public class ResponseBean implements Serializable {
    public static final int AUTH_FAILED = 40001;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_AVAILABLE = 403;
    public static final int NOT_FOUNDED = 404;
    public static final int METHOD_NOT_ALLOW = 405;
    public static final int SERVER_ERROR = 500;
    public static final int OK = 200;
    public static final int FAILED = 0;
    private int code = 0;
    private boolean success = false;
    private Object data;

    public ResponseBean code(int code) {
        this.code = code;
        return this;
    }

    public ResponseBean success(boolean success) {
        this.success = success;
        return this;
    }

    public ResponseBean data(Object data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return this.code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Object getData() {
        return this.data;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public ResponseBean(final int code, final boolean success, final Object data) {
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public ResponseBean() {
    }
}

