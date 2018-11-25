package cn.hunkier.juc;

/**
 * 模拟 CAS 算法
 */
public class TestCompareAndSwap {


    public static void main(String[] args) {
        final  CompareAnsSwap cas = new CompareAnsSwap();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 2000));
                    System.out.println(b);
                }
            }).start();
        }
    }
}


class CompareAnsSwap{


    private int value;

    /**
     * 获取内存值
     * @return
     */
    public synchronized int get(){
        return value;
    }

    /**
     * 比较
     * @param expectedValue
     * @param newValue
     * @return
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value ;
        if (oldValue == expectedValue){
            this.value = newValue;
        }

        return oldValue;
    }

    /**
     * 设置
     * @param expectedValue
     * @param newValue
     * @return
     */
    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        return expectedValue == compareAndSwap(expectedValue,newValue);

    }
}
