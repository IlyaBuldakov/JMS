package model.hardware.impl;

import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import model.hardware.SystemInfoHolder;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.util.List;
import java.util.Map;

public class DiskHardwareAnalyserImpl implements HardwareAnalyser {

    private static final FileSystem FILE_SYSTEM = SystemInfoHolder.SYSTEM_INFO
            .getOperatingSystem().getFileSystem();

    private static final int BYTES_IN_GB = 1_073_741_824;

    @Override
    public Map<Metrics, Object> analyse() {
        OSFileStore fileStore = FILE_SYSTEM.getFileStores().get(0);
        return Map.of(Metrics.DISK_MB_FREE_SPACE,
                fileStore.getFreeSpace() / BYTES_IN_GB);
    }
}
