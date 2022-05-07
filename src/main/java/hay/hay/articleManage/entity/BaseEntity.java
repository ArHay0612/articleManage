//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hay.hay.articleManage.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public abstract class BaseEntity<T extends Serializable> extends AbstractEntity<T> implements Serializable {
    private static final long serialVersionUID = -6527000202477422566L;
    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "id", length = 36)
    protected T id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @Column(
            name = "create_time",
            updatable = false
    )
    protected Date createTime = new Date();
    @Type(
            type = "yes_no"
    )
    @Column(
            name = "is_deleted"
    )
    protected Boolean deleted;

    public BaseEntity() {
        this.deleted = Boolean.FALSE;
    }

    public T getId() {
        return this.id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
