package hello.login.web.argumentresolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)  //파라미터에만 사용하겠다
@Retention(RetentionPolicy.RUNTIME)  //런타임 내내 애노테이션 정보를 남겨두겠다
public @interface Login {
}
