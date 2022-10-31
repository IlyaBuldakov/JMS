package model.hardware.impl;

import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import model.hardware.SystemInfoHolder;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiskHardwareAnalyserImpl implements HardwareAnalyser {

    private static final FileSystem FILE_SYSTEM = new SystemInfo()
            .getOperatingSystem().getFileSystem();

    @Override
    public Map<Metrics, Object> analyse() {
        HashMap<Metrics, Object> resultMap = new HashMap<>();
        List<OSFileStore> fileStores = FILE_SYSTEM.getFileStores();
        for (OSFileStore fileStore : fileStores) {
            resultMap.put(Metrics.DISK_MB_FREE_SPACE, fileStore.getFreeSpace());
        }
        return resultMap;
    }
}
