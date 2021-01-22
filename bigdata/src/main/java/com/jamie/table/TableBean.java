package com.jamie.table;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TableBean implements Writable {

    private String id;    // 订单id
    private String pid;    // 产品id
    private int amount;    // 数量
    private String pname;    // 产品名称
    private String flag;    // 定义一个标记，标记是订单表还是产品表

    public TableBean() {
        super();
    }

    public TableBean(String id, String pid, int amount, String pname, String flag) {
        super();
        this.id = id;
        this.pid = pid;
        this.amount = amount;
        this.pname = pname;
        this.flag = flag;
    }

    /**
     * map 写出时调
     */
    @Override
    public void write(DataOutput out) throws IOException {

        // 序列化方法
        out.writeUTF(id);
        out.writeUTF(pid);
        out.writeInt(amount);
        out.writeUTF(pname);
        out.writeUTF(flag);
    }

    /**
     * reduce 读取时调
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        // 反序列化方法
        id = in.readUTF();
        pid = in.readUTF();
        amount = in.readInt();
        pname = in.readUTF();
        flag = in.readUTF();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return id + "\t" + amount + "\t" + pname;
    }
}