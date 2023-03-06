package com.chenyi.yanhuohui.util.uuid;

import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//-- 创建表
//        CREATE TABLE `sequence` (
//        `seq` CHAR(64) NOT NULL COLLATE 'utf8_general_ci',
//        `id` INT(11) NOT NULL,
//        PRIMARY KEY (`seq`) USING BTREE
//        )
//-- 创建函数
//        BEGIN
//        UPDATE sequence SET id = LAST_INSERT_ID(id+1) WHERE seq=seqname;
//        RETURN LAST_INSERT_ID();
//        END
//-- 插入初始值
//        insert into sequence VALUE ('SEQUENCE_NAME', 0);

@Slf4j
public class GlobalOrderNumberSequenceGenerator implements NumberSequenceGenerator{

    private String MYSQL_SQL_TEMPLATE = "select %s('%s')";

    private String sql;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private DataSource dataSource;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    /**
     * seqName需要创建对象的时候设置
     */
    private String seqName;

    private String funcName = "nextval";

    public boolean isUseTransaction() {
        return useTransaction;
    }

    public void setUseTransaction(boolean useTransaction) {
        this.useTransaction = useTransaction;
    }

    private boolean useTransaction;

    @PostConstruct
    public void afterPropertiesSet(){
        Assert.notNull(dataSource,"数据源不能为空！");
        this.sql = String.format(MYSQL_SQL_TEMPLATE, this.funcName, this.seqName);
    }

    @Override
    public Long getSequence() {
        return this.getNumberSequence();
    }

    @Override
    public Long getNumberSequence() {
        Connection conn = null;
        long num;
        try{
            conn = DataSourceUtils.getConnection(this.dataSource);
            Long sequence = null;
            sequence = this.getNextSequence(conn);
            num = sequence;
        }catch (Throwable e){
            throw new SbcRuntimeException("系统错误",e);
        }finally {
            DataSourceUtils.releaseConnection(conn,this.dataSource);
        }
        return num;
    }

    protected Long getNextSequence(Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Long sequence = null;
        boolean autoCommit = conn.getAutoCommit();
        try{
            if(this.useTransaction && autoCommit){
                conn.setAutoCommit(false);
            }
            stmt = conn.prepareStatement(this.sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                sequence = rs.getLong(1);
            }
            if(this.useTransaction){
                conn.commit();
            }
        }catch (SQLException e){
            if(this.useTransaction){
                conn.rollback();
            }
            throw e;
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    log.error(e.getMessage(), e);
                }
            }
            if(stmt != null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    log.error(e.getMessage(), e);
                }
            }
            if(conn != null && autoCommit && this.useTransaction){
                conn.setAutoCommit(autoCommit);
            }
        }
        return sequence;
    }
}
