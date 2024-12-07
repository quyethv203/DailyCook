package com.example.foodwed.exception;

public enum ErrorCode {
    USSER_EXITED("fail",1001, "Email đã tồn tại"),
    USSER_Email("fail",1002,"Email needs to be in the correct format"),
    USER_EMAIL_Error("fail",1003,"email not correct"),
    PASSWORD_NOT_CORECT("fail",1004,"password not correct"),
    SUGGESTION_ERROR("fail",1005,"not found suggestion"),
    PARAM_ERROR("fail", 1006,"missing parameter"),
    UNAAUTHENTICATED("fail",1010, "kiem tra lai mat khau va email"),
    CATEGORY_EXITED("fail",1007,"category exited"),
    CATEGORY_NOT_EXITED("fail",1008,"category not exited"),
    RECIPE_NOT_FOUND("fail",1009,"recipe not found"),
    USERNOTFOUND("fail",1010,"Người dùng không tồn tại"),
    INVALIDPASSWORD("fail",1011,"Mật khẩu cũ không đúng"),
    ORDER_NOT_FOUND("fail", 1012,"Không tìm thấy order")

    ;
    ErrorCode(String status,int code, String message) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private final int code;
    private final String message;
private final String status;

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
