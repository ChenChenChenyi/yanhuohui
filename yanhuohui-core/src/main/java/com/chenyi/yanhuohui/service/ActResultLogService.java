package com.chenyi.yanhuohui.service;

import com.chenyi.yanhuohui.utils.JDBCDruidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActResultLogService {

    public Map<String, Object> import2DBFromExcel10w(List<Map<Integer, String>> dataList) {
        HashMap<String, Object> result = new HashMap<>();
        //结果集中数据为0时,结束方法.进行下一次调用
        if (dataList.size() == 0) {
            result.put("empty", "0000");
            return result;
        }
        //JDBC分批插入+事务操作完成对10w数据的插入
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long startTime = System.currentTimeMillis();
            System.out.println(dataList.size() + "条,开始导入到数据库时间:" + startTime + "ms");
            conn = JDBCDruidUtils.getConnection();
            //控制事务:默认不提交
            conn.setAutoCommit(false);
            String sql = "insert into manager (name,role,create_time) values";
            sql += "(?,?,?)";
            ps = conn.prepareStatement(sql);
            //循环结果集:这里循环不支持"烂布袋"表达式
            for (int i = 0; i < dataList.size(); i++) {
                Map<Integer, String> item = dataList.get(i);
                ps.setString(1, item.get(0));
                ps.setString(2, item.get(1));
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

                //将一组参数添加到此 PreparedStatement 对象的批处理命令中。
                ps.addBatch();
            }
            //执行批处理
            ps.executeBatch();
            //手动提交事务
            conn.commit();
            long endTime = System.currentTimeMillis();
            System.out.println(dataList.size() + "条,结束导入到数据库时间:" + endTime + "ms");
            System.out.println(dataList.size() + "条,导入用时:" + (endTime - startTime) + "ms");
            result.put("success", "1111");
        } catch (Exception e) {
            result.put("exception", "0000");
            e.printStackTrace();
        } finally {
            //关连接
            JDBCDruidUtils.close(conn, ps);
        }
        return result;
    }
}
