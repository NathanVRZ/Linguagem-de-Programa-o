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

    public static List<String> printProcess(OperatingSystem os, GlobalMemory memory) {
//        OSProcess myProc = os.getProcess(os.getProcessId());
        List<OSProcess> procs = os.getProcesses(5, OperatingSystem.ProcessSort.MEMORY);
        for (int i = 0; i < procs.size(); i++) {
            OSProcess p = procs.get(i);
//            oshi.add(String.format(" %5d %5.1f %4.1f %9s %9s %s", p.getProcessID(),
//                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
//                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
//                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName()));
            oshi.add(String.format(" %s %4.1f Mb", p.getName(), 100d * p.getResidentSetSize() / memory.getTotal()));

        }
        return oshi;
    }
}
