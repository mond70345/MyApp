package com.mchen2.myapp.test.nio;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class NioTest {

    @Test
    public void hashTest() {
        final Date yesterday = DateUtil.yesterday();
        Date threeMthAgo = DateUtil.endOfDay(DateUtil.offsetMonth(yesterday, -3));
        System.out.println(threeMthAgo);
        System.out.println(threeMthAgo.getTime());
    }

    private static void unpackZip(String zipFilePath, String destinationDirectory) {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                Path destinationPath = Paths.get(destinationDirectory, entry.getName());

                if (entry.isDirectory()) {
                    Files.createDirectories(destinationPath);
                } else {
                    try (InputStream inputStream = zipFile.getInputStream(entry);
                         FileOutputStream outputStream = new FileOutputStream(destinationPath.toFile())) {

                        Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
