package cn.hunk.learn.web.contact.exception;

/**
 * 姓名重复自定义异常类
 * Created by hunk on 2015/8/5.
 */
public class NameRepeatException  extends Exception {
    public NameRepeatException(String message) {
        super(message);
    }
}
