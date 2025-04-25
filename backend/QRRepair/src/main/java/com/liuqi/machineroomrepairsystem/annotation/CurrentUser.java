package com.liuqi.machineroomrepairsystem.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * 通过让 @AuthenticationPrincipal 成为我们自己注解的元注解来进一步消除对 Spring Security 的依赖
 */
@Target({ElementType.PARAMETER, ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}
