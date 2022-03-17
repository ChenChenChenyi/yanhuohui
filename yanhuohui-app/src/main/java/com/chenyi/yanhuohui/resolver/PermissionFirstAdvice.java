package com.chenyi.yanhuohui.resolver;

import com.chenyi.yanhuohui.common.base.annotation.PermissionsAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 @Aspect:作用是把当前类标识为一个切面供容器读取

 @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
 @Around：环绕增强，相当于MethodInterceptor
 @AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
 @Before：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
 @AfterThrowing：异常抛出增强，相当于ThrowsAdvice
 @After: final增强，不管是抛出异常或者正常退出都会执行
 */

@Aspect
@Component
@Slf4j
public class PermissionFirstAdvice {
    // 定义一个切面，括号内写入第1步中自定义注解的路径
    @Pointcut("@annotation(com.chenyi.yanhuohui.common.base.annotation.PermissionsAnnotation)")
    private void permissionCheck() {
    }

    // Before表示logAdvice将在目标方法执行前执行
    @Before("permissionCheck()")
    public void logAdvice(){
        // 这里只是一个示例，你可以写任何处理逻辑
        System.out.println("请求的advice触发了");
    }

    @Before("permissionCheck()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("切面方法：doBefore方法进入了");

        // 获取签名
        //JointPoint 对象很有用，可以用它来获取一个签名，利用签名可以获取请求的包名、方法名，包括参数（通过 joinPoint.getArgs() 获取）等
        Signature signature = joinPoint.getSignature();
        // 获取切入的包名
        String declaringTypeName = signature.getDeclaringTypeName();
        // 获取即将执行的方法名
        String funcName = signature.getName();
        log.info("切面方法：即将执行方法为: {}，属于{}包", funcName, declaringTypeName);

        // 也可以用来记录一些信息，比如获取请求的 URL 和 IP
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求 URL
        String url = request.getRequestURL().toString();
        // 获取请求 IP
        String ip = request.getRemoteAddr();
        log.info("切面方法：用户请求的url为：{}，ip地址为：{}", url, ip);
        System.out.println("===================切面方法===================：" + System.currentTimeMillis());
    }

    @Around("permissionCheck() && @annotation(permissionsAnnotation)")
    public Object permissionCheckFirst(ProceedingJoinPoint joinPoint, PermissionsAnnotation permissionsAnnotation) throws Throwable {
        System.out.println("===================切面方法===================：" + System.currentTimeMillis());
        String value = permissionsAnnotation.value();
        Long startTime = System.currentTimeMillis();
        //获取请求参数，详见接口类
        Object[] objects = joinPoint.getArgs();
        String name = String.valueOf(objects[0]);
        name = name + value;
        try{
            return joinPoint.proceed(new Object[]{name});
        }finally {
            long time = System.currentTimeMillis() - startTime;
            System.err.println("切面方法：[PermissionsAnnotation] " + name + ": " + time + "ms");
        }
    }
}
