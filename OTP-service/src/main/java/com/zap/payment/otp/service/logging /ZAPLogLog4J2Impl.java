package com.zap.payment.otp.service.logging;

import org.apache.logging.log4j.*;

public class ZAPLogLog4J2Impl implements ZAPLog {

    private final Logger log;
    private final LalogEnhancer enhancer;

    private static final Marker ZAP_LOGGING = new MarkerManager.Log4jMarker("ZAP_LOGGING");

    public ZAPLogLog4J2Impl(Class<?> clazz, LalogEnhancer enhancer) {
        this.log = LogManager.getLogger(clazz);
        this.enhancer = enhancer;
    }

    public ZAPLogLog4J2Impl(String loggerName, LalogEnhancer enhancer) {
        this.log = LogManager.getLogger(loggerName);
        this.enhancer = enhancer;
    }

    @Override
    public void debug(String message) {
        this.log(Level.DEBUG, message);
    }

    @Override
    public void debug(Throwable theThrowable) {
        this.log(Level.DEBUG, "", theThrowable);
    }

    @Override
    public void debug(String message, Throwable theThrowable) {
        this.log(Level.DEBUG, message, theThrowable);
    }

    @Override
    public void debugf(String format, Object... args) {
        logFormatted(Level.DEBUG, format, args);
    }

    @Override
    public void debugf(String format, Throwable th, Object... args) {
        logFormatted(Level.DEBUG, format, th, args);
    }


    @Override
    public void info(String message) {
        this.log(Level.INFO, message);
    }

    @Override
    public void info(Throwable theThrowable) {
        this.log(Level.INFO, "", theThrowable);
    }

    @Override
    public void info(String message, Throwable theThrowable) {
        this.log(Level.INFO, message, theThrowable);
    }

    @Override
    public void infof(String format, Object... args) {
        logFormatted(Level.INFO, format, args);
    }

    @Override
    public void infof(String format, Throwable th, Object... args) {
        logFormatted(Level.INFO, format, th, args);

    }

    @Override
    public void warn(String message) {
        this.log(Level.WARN, message);
    }

    @Override
    public void warn(Throwable theThrowable) {
        this.log(Level.WARN, "", theThrowable);
    }

    @Override
    public void warn(String message, Throwable theThrowable) {
        this.log(Level.WARN, message, theThrowable);
    }

    @Override
    public void warnf(String format, Object... args) {
        logFormatted(Level.WARN, format, args);

    }

    @Override
    public void warnf(String format, Throwable th, Object... args) {
        logFormatted(Level.WARN, format, th, args);

    }

    @Override
    public void error(String message) {
        this.log(Level.ERROR, message);
    }

    @Override
    public void error(Throwable theThrowable) {
        this.log(Level.ERROR, "", theThrowable);
    }

    @Override
    public void error(String message, Throwable theThrowable) {
        this.log(Level.ERROR, message, theThrowable);
    }

    @Override
    public void errorf(String format, Object... args) {
        logFormatted(Level.ERROR, format, args);
    }

    @Override
    public void errorf(String format, Throwable th, Object... args) {
        logFormatted(Level.ERROR, format, th, args);
    }

    @Override
    public void fatal(String message) {
        this.log(Level.FATAL, message);
    }

    @Override
    public void fatal(Throwable theThrowable) {
        this.log(Level.FATAL, "", theThrowable);
    }

    @Override
    public void fatal(String message, Throwable theThrowable) {
        this.log(Level.FATAL, message, theThrowable);
    }

    @Override
    public void fatalf(String format, Object... args) {
        logFormatted(Level.FATAL, format, args);
    }

    @Override
    public void fatalf(String format, Throwable th, Object... args) {
        logFormatted(Level.FATAL, format, th, args);
    }


    @Override
    public void trace(String message) {
        this.log(Level.TRACE, message);
    }

    @Override
    public void trace(Throwable theThrowable) {
        this.log(Level.TRACE, "", theThrowable);
    }

    @Override
    public void trace(String message, Throwable theThrowable) {
        this.log(Level.TRACE, message, theThrowable);
    }

    @Override
    public void tracef(String format, Object... args) {
        logFormatted(Level.TRACE, format, args);
    }

    @Override
    public void tracef(String format, Throwable th, Object... args) {
        logFormatted(Level.TRACE, format, th, args);
    }

    @Override
    public boolean isEnabled(Level level) {
        switch (level) {
            case DEBUG:
                return this.log.isDebugEnabled();
            case INFO:
                return this.log.isInfoEnabled();
            case WARN:
                return this.log.isWarnEnabled();
            case ERROR:
                return this.log.isErrorEnabled();
            case FATAL:
                return this.log.isFatalEnabled();
            case TRACE:
                return this.log.isTraceEnabled();
        }
        return false;
    }


    /* Nice feature determine a method and line number for every log line, very helpful */

    private String callerLocation() {
        StackTraceElement caller = callerStackTraceElement();
        if (null != caller) {
            return "." + caller.getMethodName() + "():" + caller.getLineNumber();
        }
        return "";
    }

    private StackTraceElement callerStackTraceElement() {
        for (StackTraceElement e : new Throwable().getStackTrace()) {
            if (e.getClassName().startsWith("app.lalo.logging.")) {
                continue;
            }
            return e;
        }
        return null;
    }

    /* Core passthrough methods to Log4j */

    private void logFormatted(Level level, String format, Object... args) {
        logFormatted(level, format, null, args);
    }

    private void logFormatted(Level level, String format, Throwable th, Object... args) {
        if (!isEnabled(level)) {
            return;
        }
        try {
            if (format != null) {
                if (th != null) {
                    this.log(level, String.format(format, args), th);
                } else {
                    this.log(level, String.format(format, args));
                }
            }
        } catch (Throwable t) {
            this.log(level, "There is possibly an issue with the format string '" + format + "', not logging.", t);
        }
    }

    private Object enhance(String messagePrefix, Object o) {
        if (null == this.enhancer) {
            return o;
        }
        return this.enhancer.enhance(messagePrefix, o);
    }


    private void log(Level level, Object msg) {
        Object o = enhance("", msg);
        try (var ignored = CloseableThreadContext.put("callerLoc", callerLocation())) {
            switch (level) {
                case TRACE -> {
                    this.log.trace(ZAP_LOGGING, o);
                }
                case DEBUG -> {
                    this.log.debug(ZAP_LOGGING, o);
                }
                case INFO -> {
                    this.log.info(ZAP_LOGGING, o);
                }
                case WARN -> {
                    this.log.warn(ZAP_LOGGING, o);
                }
                case ERROR -> {
                    this.log.error(ZAP_LOGGING, o);
                }
                case FATAL -> this.log.fatal(ZAP_LOGGING, o);
            }
        }
    }


    private void log(Level level, String msg, Throwable th) {
        Object o = enhance(msg, th);
        try (var ignored = CloseableThreadContext.put("callerLoc", callerLocation())) {
            switch (level) {
                case TRACE -> {
                    this.log.trace(ZAP_LOGGING, o, th);
                }
                case DEBUG -> {
                    this.log.debug(ZAP_LOGGING, o, th);
                }
                case INFO -> {
                    this.log.info(ZAP_LOGGING, o, th);
                }
                case WARN -> {
                    this.log.warn(ZAP_LOGGING, o, th);
                }
                case ERROR -> {
                    this.log.error(ZAP_LOGGING, o, th);
                }
                case FATAL -> this.log.fatal(ZAP_LOGGING, o, th);
            }
        }
    }
}
