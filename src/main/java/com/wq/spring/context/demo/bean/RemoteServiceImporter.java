package com.wq.spring.context.demo.bean;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/28
 * @Description:
 * @Resource:
 */
public class RemoteServiceImporter {

	private String serviceName;

	private String remoteAddress;


	public RemoteServiceImporter(String serviceName, String remoteAddress) {
		this.serviceName = serviceName;
		this.remoteAddress = remoteAddress;
	}

	public RemoteServiceImporter(){}

	@Override
	public String toString() {
		return "RemoteServiceImporter{" +
				"serviceName='" + serviceName + '\'' +
				", remoteAddress='" + remoteAddress + '\'' +
				'}';
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
}
