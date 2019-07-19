package cn.com.xiaoyaoji.doc.event;

import cn.com.xiaoyaoji.data.bean.Doc;
import cn.com.xiaoyaoji.data.bean.User;

public class DocUpdatedEvent {
    private Doc source;
    private Doc now;
    private String remark;
    private User user;

    public DocUpdatedEvent(Doc source, Doc now, User user,String remark) {
        this.source = source;
        this.now = now;
        this.remark = remark;
        this.user = user;
    }

    public Doc getSource() {
        return source;
    }

    public Doc getNow() {
        return now;
    }

    public String getRemark() {
        return remark;
    }

    public User getUser() {
        return user;
    }
}
