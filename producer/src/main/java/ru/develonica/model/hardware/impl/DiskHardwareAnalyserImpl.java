package ru.develonica.model.hardware.impl;

import java.util.Map;

import ru.develonica.common.model.hardware.Metrics;
import ru.develonica.model.hardware.HardwareAnalyser;
import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

/**
 * Disk free space analyser.
 */
public class DiskHardwareAnalyserImpl implements HardwareAnalyser {

    private static final FileSystem FILE_SYSTEM = new SystemInfo()
            .getOperatingSystem().getFileSystem();

    private static final int BYTES_IN_GB = 1_073_741_824;

    @Override
    public Map.Entry<Metrics, Object> analyse() {
        OSFileStore fileStore = FILE_SYSTEM.getFileStores().get(0);
        return Map.entry(
                Metrics.DISK_GB_FREE_SPACE,
                (fileStore.getFreeSpace() / BYTES_IN_GB));
    }
}
