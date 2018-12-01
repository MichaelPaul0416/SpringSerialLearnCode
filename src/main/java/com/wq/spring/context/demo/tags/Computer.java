package com.wq.spring.context.demo.tags;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/30
 * @Description:
 * @Resource:
 */
public class Computer {

	private ComputerCore computerCore;

	private ExpandService expandService;

	private String owner;

	private double price;

	@Override
	public String toString() {
		return "Computer{" +
				"computerCore=" + computerCore +
				", expandService=" + expandService +
				", owner='" + owner + '\'' +
				", price=" + price +
				'}';
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ComputerCore getComputerCore() {
		return computerCore;
	}

	public void setComputerCore(ComputerCore computerCore) {
		this.computerCore = computerCore;
	}

	public ExpandService getExpandService() {
		return expandService;
	}

	public void setExpandService(ExpandService expandService) {
		this.expandService = expandService;
	}
}
