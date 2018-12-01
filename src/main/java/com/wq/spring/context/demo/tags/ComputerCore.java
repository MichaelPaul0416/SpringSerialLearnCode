package com.wq.spring.context.demo.tags;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/30
 * @Description:
 * @Resource:
 */
public class ComputerCore {

	private String cpu;
	private String gpu;
	private String rom;
	private String disk;
	private int diskSize;

	@Override
	public String toString() {
		return "ComputerCore{" +
				"cpu='" + cpu + '\'' +
				", gpu='" + gpu + '\'' +
				", rom='" + rom + '\'' +
				", disk='" + disk + '\'' +
				", diskSize=" + diskSize +
				'}';
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getGpu() {
		return gpu;
	}

	public void setGpu(String gpu) {
		this.gpu = gpu;
	}

	public String getRom() {
		return rom;
	}

	public void setRom(String rom) {
		this.rom = rom;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public int getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(int diskSize) {
		this.diskSize = diskSize;
	}
}
