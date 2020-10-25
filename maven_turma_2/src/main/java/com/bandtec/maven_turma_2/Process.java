package com.bandtec.maven_turma_2;

import java.util.ArrayList;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import java.util.List;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;

public class Process {

    static List<String> oshi = new ArrayList<>();

    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();
        printProcess(os, hal.getMemory());
        
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < oshi.size(); i++) {
            output.append(oshi.get(i));
            if (oshi.get(i) != null && !oshi.get(i).endsWith("\n")) {
                output.append('\n');
            }
        }
        
        System.out.println(output);
    }

    private static void printMemory(GlobalMemory memory) {
        oshi.add("Physical Memory: \n " + memory.toString());
        VirtualMemory vm = memory.getVirtualMemory();
        oshi.add("Virtual Memory: \n " + vm.toString());
        List<PhysicalMemory> pmList = memory.getPhysicalMemory();
        if (!pmList.isEmpty()) {
            oshi.add("Physical Memory: ");
            for (PhysicalMemory pm : pmList) {
                oshi.add(" " + pm.toString());
            }
        }
    }

    private static void printProcess(OperatingSystem os, GlobalMemory memory) {
//        OSProcess myProc = os.getProcess(os.getProcessId());
        List<OSProcess> procs = os.getProcesses(5, OperatingSystem.ProcessSort.CPU);
//        oshi.add(" PID %CPU %MEM  VSZ RSS   NAME ");
        for (int i = 0; i < procs.size(); i++) {
            OSProcess p = procs.get(i);
            oshi.add(String.format(" %5d %5.1f %4.1f %9s %9s %s", p.getProcessID(),
                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName()));
            
        }
    }
}
