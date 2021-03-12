package cn.hunkier.spring.data.declarativetransactiondemo;

public interface FooService {

    void insertRecord();

    void insertThenRollback() throws RollbackException;

    void invokeInsertTheRollback() throws RollbackException;
}
