package com.example.practice_design_pattern.OOP.Read;


import org.springframework.util.SimpleIdGenerator;

import java.math.BigDecimal;
import java.rmi.AccessException;

/**
 * @author akk
 * @className Wallet
 * @description:
 * @date 2021/7/13 15:20
 */
// 封装
public class Wallet {
    // 钱包的唯一编号
    private String id;
    // 钱包创建的时间
    private long createTime;
    // 钱包中的余额
    private BigDecimal balance;
    // 上次钱包余额变更的时间
    private long balanceLastModifiedTime;
    // ...省略其他属性...

    public Wallet() {
        this.id = new SimpleIdGenerator().generateId().toString();
        this.createTime = System.currentTimeMillis();
        this.balance = BigDecimal.ZERO;
        this.balanceLastModifiedTime = System.currentTimeMillis();
    }

    // 注意：下面对get方法做了代码折叠，是为了减少代码所占文章的篇幅
    // 代码中并没有对 id 和 CreateTime 进行赋值，这是因为这两个属性实在创建对象的时候就是固定了，不应该在被改动的,所以没有暴露出设值方法
    public String getId() { return this.id; }
    public long getCreateTime() { return this.createTime; }
    public BigDecimal getBalance() { return this.balance; }
    public long getBalanceLastModifiedTime() { return this.balanceLastModifiedTime;  }

    // 对于钱包的特性值增加金额或者减少金额，不存在设置金额所以 balance 也不暴露出来，而是暴露出 increaseBalance 和 decreaseBalance 来进行修改
    // balanceLastModifiedTime 属性是和 balance 的修改绑定在一起的，以保证数据一致性
    public void increaseBalance(BigDecimal increasedAmount) throws AccessException {
        if (increasedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccessException("...");
        }
        this.balance.add(increasedAmount);
        this.balanceLastModifiedTime = System.currentTimeMillis();
    }

    public void decreaseBalance(BigDecimal decreasedAmount) throws AccessException {
        if (decreasedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccessException("...");
        }
        if (decreasedAmount.compareTo(this.balance) > 0) {
            throw new AccessException("...");
        }
        this.balance.subtract(decreasedAmount);
        this.balanceLastModifiedTime = System.currentTimeMillis();
    }
}
