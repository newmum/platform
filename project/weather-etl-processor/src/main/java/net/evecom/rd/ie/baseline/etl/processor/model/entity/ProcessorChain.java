package net.evecom.rd.ie.baseline.etl.processor.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: ProcessorChain
 * @Description: 链路对象
 * @author： zhengc
 * @date： 2018年10月30日
 */
@Table(name = "PROCESSOR_CHAIN")
public class ProcessorChain extends DataEntity<ProcessorChain> implements Serializable {

    @Column(name = "CHAIN_NAME")
    private String chainName;

    @Column(name = "CHAIN_DESC")
    private String chainDesc;

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public String getChainDesc() {
        return chainDesc;
    }

    public void setChainDesc(String chainDesc) {
        this.chainDesc = chainDesc;
    }
}

