package cn.com.xiaoyaoji.test;

import cn.com.xiaoyaoji.data.DataFactory;
import cn.com.xiaoyaoji.service.ServiceFactory;
import org.junit.Test;

public class DeleteDoc {

    @Test
    public void test(){
        DataFactory.instance().deleteDocHistory();
    }
}
