package common.exception;

import common.entity.StatusBean;
import common.entity.StatusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author JAVASM
 * @Classname GlobalExceptionHandler
 * @Description:
 * @Date 2020/8/3 18:28
 * @since JDK 1.8
 */

@Component //注入容器
@ControllerAdvice //controller增强注解，做异常的统一处理
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity doException(Exception e) {
        return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity doException(MyException e) {
        // 从异常对象中获取 enum 对象，生成 状态bean对象。因为失败时不返回data
        return ResponseEntity.ok(new StatusBean(e.getStatusEnum()));
    }
}
