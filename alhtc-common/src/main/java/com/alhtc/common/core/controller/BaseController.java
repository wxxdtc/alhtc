package com.alhtc.common.core.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.alhtc.common.constant.HttpStatus;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.core.page.PageDomain;
import com.alhtc.common.core.page.TableDataInfo;
import com.alhtc.common.core.page.TableSupport;
import com.alhtc.common.utils.DateUtils;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.common.utils.sql.SqlUtil;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import static com.alhtc.common.utils.SecurityUtils.getLoginUser;

/**
 * web层通用数据处理
 *
 * @author wangxiaoxu
 */
public class BaseController {

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});

	}

	/**
	 * 设置请求分页数据
	 */
	protected void startPage() {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			Boolean reasonable = pageDomain.getReasonable();
			PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
		}
	}

	/**
	 * 设置请求排序数据
	 */
	protected void startOrderBy() {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			PageHelper.orderBy(orderBy);
		}
	}

	/**
	 * 响应请求分页数据
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	protected TableDataInfo getDataTable(List<?> list) {
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(HttpStatus.SUCCESS);
		rspData.setMsg("查询成功");
		rspData.setRows(list);
		rspData.setTotal(new PageInfo(list).getTotal());
		return rspData;
	}

	/**
	 * 返回成功
	 */
	public R success() {
		return R.success();
	}

	/**
	 * 返回失败消息
	 */
	public R error() {
		return R.error();
	}

	/**
	 * 返回成功消息
	 */
	public R success(String message) {
		return R.success(message);
	}

	/**
	 * 返回失败消息
	 */
	public R error(String message) {
		return R.error(message);
	}

	/**
	 * 响应返回结果
	 *
	 * @param rows 影响行数
	 * @return 操作结果
	 */
	protected R toR(int rows) {
		return rows > 0 ? R.success() : R.error();
	}

	/**
	 * 响应返回结果
	 *
	 * @param result 结果
	 * @return 操作结果
	 */
	protected R toR(boolean result) {
		return result ? success() : error();
	}

	/**
	 * 获取登录用户名
	 */
	public String getUsername() {
		return getLoginUser().getUsername();
	}

}
