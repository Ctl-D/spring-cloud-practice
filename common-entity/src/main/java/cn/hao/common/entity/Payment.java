package cn.hao.common.entity;

import java.io.Serializable;

/**
 * payment
 *
 * @author
 */
public class Payment implements Serializable {
    /**
     * ID
     */
    private Long id;

    private String serial;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Payment(Long id, String serial) {
        this.id = id;
        this.serial = serial;
    }

    public Payment() {
    }
}