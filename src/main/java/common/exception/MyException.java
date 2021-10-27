package common.exception;

import common.entity.StatusEnum;

/**
 * @author JAVASM
 * @Classname MyException
 * @Description:
 * @Date 2020/8/3 18:47
 * @since JDK 1.8
 */
public class MyException extends RuntimeException {
    public StatusEnum statusEnum;

    //  传入一个包含错误码和错误信息的Enum，生成一个异常对象
    public MyException(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }
}
