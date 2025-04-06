package com.zap.payment.otp.service.logging;

/**
 * This is an opinionated wrapper around log4j2 interfaces.
 * If you feel like you want access to more log4j logger api they can be added here;
 * for now we start with basic logging and helpful formatting methods.
 */
public interface ZAPLog {

    void debug(String message);

    void debug(Throwable theThrowable);

    void debug(String message, Throwable theThrowable);

    void debugf(String format, Object... args);

    void debugf(String format, Throwable th, Object... args);

    void info(String message);

    void info(Throwable theThrowable);

    void info(String message, Throwable theThrowable);

    void infof(String format, Object... args);

    void infof(String format, Throwable th, Object... args);

    void warn(String message);

    void warn(Throwable theThrowable);

    void warn(String message, Throwable theThrowable);

    void warnf(String format, Object... args);

    void warnf(String format, Throwable th, Object... args);

    void error(String message);

    void error(Throwable theThrowable);

    void error(String message, Throwable theThrowable);

    void errorf(String format, Object... args);

    void errorf(String format, Throwable th, Object... args);

    void fatal(String message);

    void fatal(Throwable theThrowable);

    void fatal(String message, Throwable theThrowable);

    void fatalf(String format, Object... args);

    void fatalf(String format, Throwable th, Object... args);

    void trace(String message);

    void trace(Throwable theThrowable);

    void trace(String message, Throwable theThrowable);

    void tracef(String format, Object... args);

    void tracef(String format, Throwable th, Object... args);

    enum Level {
        DEBUG, INFO, WARN, ERROR, FATAL, TRACE
    }

    boolean isEnabled(Level level);

    @FunctionalInterface
    public interface LalogEnhancer {
        public Object enhance(String messagePrefix, Object o);
    }

}
