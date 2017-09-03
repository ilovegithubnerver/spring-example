/**
 * This class is generated by jOOQ
 */
package com.example.dao.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.annotation.Generated;


/**
 * 示例
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.8"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Example implements Serializable {

    private static final long serialVersionUID = -563256096;

    private final Long       id;
    private final String     name;
    private final String     code;
    private final String     state;
    private final Boolean    grade;
    private final Integer    score;
    private final BigDecimal price;
    private final Date       birth;
    private final Date       sleep;
    private final Date       createddate;
    private final Long       createdby;
    private final Date       modifieddate;
    private final Long       modifiedby;

    public Example(Example value) {
        this.id = value.id;
        this.name = value.name;
        this.code = value.code;
        this.state = value.state;
        this.grade = value.grade;
        this.score = value.score;
        this.price = value.price;
        this.birth = value.birth;
        this.sleep = value.sleep;
        this.createddate = value.createddate;
        this.createdby = value.createdby;
        this.modifieddate = value.modifieddate;
        this.modifiedby = value.modifiedby;
    }

    public Example(
        Long       id,
        String     name,
        String     code,
        String     state,
        Boolean    grade,
        Integer    score,
        BigDecimal price,
        Date       birth,
        Date       sleep,
        Date       createddate,
        Long       createdby,
        Date       modifieddate,
        Long       modifiedby
    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.state = state;
        this.grade = grade;
        this.score = score;
        this.price = price;
        this.birth = birth;
        this.sleep = sleep;
        this.createddate = createddate;
        this.createdby = createdby;
        this.modifieddate = modifieddate;
        this.modifiedby = modifiedby;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public String getState() {
        return this.state;
    }

    public Boolean getGrade() {
        return this.grade;
    }

    public Integer getScore() {
        return this.score;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Date getBirth() {
        return this.birth;
    }

    public Date getSleep() {
        return this.sleep;
    }

    public Date getCreateddate() {
        return this.createddate;
    }

    public Long getCreatedby() {
        return this.createdby;
    }

    public Date getModifieddate() {
        return this.modifieddate;
    }

    public Long getModifiedby() {
        return this.modifiedby;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Example (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(code);
        sb.append(", ").append(state);
        sb.append(", ").append(grade);
        sb.append(", ").append(score);
        sb.append(", ").append(price);
        sb.append(", ").append(birth);
        sb.append(", ").append(sleep);
        sb.append(", ").append(createddate);
        sb.append(", ").append(createdby);
        sb.append(", ").append(modifieddate);
        sb.append(", ").append(modifiedby);

        sb.append(")");
        return sb.toString();
    }
}
