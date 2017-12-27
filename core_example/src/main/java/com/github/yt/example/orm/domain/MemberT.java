package com.github.yt.example.orm.domain;

import com.github.yt.core.domain.BaseEntity;

@javax.persistence.Table(name = "member")
public class MemberT extends BaseEntity {

    @javax.persistence.Id
    private String memberId;

    private String name;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public MemberT setName(String name) {
        this.name = name;
        return this;
    }
}
