package net.evecom.etl.processor.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.evecom.core.db.model.entity.DataEntity;
import net.evecom.core.rbac.model.entity.*;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ProcessorChain
 * @Description: 链路对象
 * @author： zhengc
 * @date： 2018年10月30日
 */
@Table(name = "processor_chain")
public class ProcessorChain extends DataEntity<ProcessorChain> implements Serializable {

	@Column(name = "CHAIN_NAME")
	@ApiModelProperty(value = "链路名称")
	private String chainName;

	@Column(name = "TYPE")
	@ApiModelProperty(value = "链路类型")
	private int type;

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
