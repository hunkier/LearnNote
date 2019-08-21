package cn.hunkier.spring.data.transactionpropagationdemo;

public interface FooService {

//    void insertRecord();

    void insertThenRollback() throws RollbackException;

    void invokeInsertTheRollback() throws RollbackException;
}
