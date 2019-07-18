package cn.xiaoyaoji.plugin.export;

import cn.com.xiaoyaoji.core.common.Constants;
import cn.com.xiaoyaoji.core.plugin.doc.DocExportPlugin;
import cn.com.xiaoyaoji.core.util.AssertUtils;
import cn.com.xiaoyaoji.core.util.JsonUtils;
import cn.com.xiaoyaoji.data.bean.Doc;
import cn.com.xiaoyaoji.data.bean.Project;
import cn.com.xiaoyaoji.service.ProjectService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author zhoujingjie
 *         created on 2017/7/1
 */
public class JsonExportPlugin extends DocExportPlugin {
    private static final String EXPORT_KEY_DOCS = "docs";
    private static final String EXPORT_KEY_VER = "version";
    private static final String GLOBAL = "global";

    @Override
    public void doExport(String projectId, HttpServletResponse response) throws IOException {
        Project project = ProjectService.instance().getProject(projectId);
        AssertUtils.notNull(project,"项目不存在");
        JSONObject json = (JSONObject) JSON.toJSON(project);
        List<Doc> docs = ProjectService.instance().getProjectDocs(projectId, true);
        json.put(EXPORT_KEY_DOCS, docs);
        json.put(EXPORT_KEY_VER, getPluginInfo().getVersion());
        json.put(GLOBAL,ProjectService.instance().getProjectGlobal(projectId));
        String jsonStr = JsonUtils.toString(json);
        String encoding = Constants.UTF8.displayName();
        response.setCharacterEncoding(encoding);
        response.setContentType("application/octet-stream");
        String fileName = URLEncoder.encode( project.getName(), Charset.forName(CharEncoding.UTF_8).displayName())+".mjson";

        response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\";");

        String path = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
        File file = new File(path + "/tmp");
        if (!file.exists()) {
            file.mkdir();
        }
        // 创建临时文件
        File jsonFile = new File(path + "/tmp/" + fileName);
        response.setContentLength((int)jsonFile.length());
        FileUtils.writeStringToFile(jsonFile, jsonStr, "UTF-8");
        FileInputStream fileInputStream = new FileInputStream(jsonFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        byte[] b = new byte[bufferedInputStream.available()];
        bufferedInputStream.read(b);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(b);
        // 人走带门
        bufferedInputStream.close();
        outputStream.flush();
        outputStream.close();
    }
}
