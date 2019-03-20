package cn.realai.online.common.validation.impl;

import cn.realai.online.common.validation.annotation.SimultaneousNotNull;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class SimultaneousNotNullImpl implements ConstraintValidator<SimultaneousNotNull, String> {

    private static final ReentrantLock LOCK = new ReentrantLock();

    //全局统计打标签SimultaneousNotNull 的个数，用来跟checkCount判断是否可做最后一个判断
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    //标记打标签SimultaneousNotNull是否有一个入参有值，只要一个有值置为true，即满足条件
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);


    @Override
    public void initialize(SimultaneousNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        atomicInteger.getAndIncrement();
        Map<String, Object> attributes =
                ((ConstraintValidatorContextImpl) context).getConstraintDescriptor().getAttributes();
        String type = (String) attributes.get("type");
        String message = (String) attributes.get("message");
        int checkCount = Integer.parseInt(attributes.get("checkCount").toString());

        //同步加锁
        LOCK.lock();
        try {
            if (StringUtils.isNotBlank(value)) {
                atomicBoolean.set(true);
            }
            if (atomicInteger.get() == checkCount && !atomicBoolean.get()) {
                return false;
            }
            return true;
        } finally {
            if (atomicInteger.get() == checkCount) {
                atomicBoolean.set(false);
                atomicInteger.set(0);
            }
            // 释放锁
            LOCK.unlock();
        }
    }

}
