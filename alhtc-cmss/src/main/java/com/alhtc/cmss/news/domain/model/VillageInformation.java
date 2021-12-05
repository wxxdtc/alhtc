package com.alhtc.cmss.news.domain.model;

import com.alhtc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 信息发布表 village_information
 *
 * @author lei.si
 */
public class VillageInformation extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 信息ID
	 */
	private Long informationId;

	/**
	 * 信息标题
	 */
	private String informationTitle;

	/**
	 * 信息类型（1-通知 2-公告）
	 */
	private String informationType;

	/**
	 * 信息内容
	 */
	private String informationContent;

	/**
	 * 浏览数
	 */
	private long visitor_volume;

	/**
	 * 信息状态（0-正常 1-关闭）
	 */
	private String status;

	public Long getInformationId() {
		return informationId;
	}

	public void setInformationId(Long informationId) {
		this.informationId = informationId;
	}

	public void setInformationTitle(String informationTitle) {
		this.informationTitle = informationTitle;
	}

	@NotBlank(message = "信息标题不能为空")
	@Size(min = 0, max = 50, message = "信息标题不能超过50个字符")
	public String getInformationTitle() {
		return informationTitle;
	}

	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}

	public String getInformationType() {
		return informationType;
	}

	public void setInformationContent(String informationContent) {
		this.informationContent = informationContent;
	}

	public String getInformationContent() {
		return informationContent;
	}

	public void setVisitorVolume(Long visitor_volume) {
		this.visitor_volume = visitor_volume;
	}

	public Long getVisitorVolume() {
		return visitor_volume;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("informationId", getInformationId())
				.append("informationTitle", getInformationTitle())
				.append("informationType", getInformationType())
				.append("informationContent", getInformationContent())
				.append("visitor_volume", getVisitorVolume())
				.append("status", getStatus())
				.append("createBy", getCreateBy())
				.append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy())
				.append("updateTime", getUpdateTime())
				.append("remark", getRemark())
				.toString();
	}
}
