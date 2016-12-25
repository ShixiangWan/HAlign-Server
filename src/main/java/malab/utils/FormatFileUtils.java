package malab.utils;

import java.io.*;

public class FormatFileUtils {
    long fileSize;
    int maxLength;
    int minLength;
    int avgLength;
    int allNumber;

    public int getAllNumber() {
        return allNumber;
    }

    public long getFileSize() {
        return fileSize;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getAvgLength() {
        return avgLength;
    }

    /*delete all files or path*/
    public boolean clearPath(File path) {
        if (path.isFile() && path.exists()) {
            path.delete();
            return true;
        }
        if (path.isDirectory()) {
            String[] children = path.list();
            for (String child : children) {
                boolean success = clearPath(new File(path, child));
                if (!success) {
                    return false;
                }
            }
        }
        return path.delete();
    }

    /*statistic file info*/
    public void statisticFile (String inputFile) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            int number = 0;
            int length;
            int min = 0;
            int max = 0;
            int all = 0;
            String line;
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                if (line.equals("")) {
                    continue;
                }
                if (line.startsWith(">")) {
                    number++;
                } else {
                    length = line.trim().length();
                    if (max == 0 || max < length) max = length;
                    if (min == 0 || min > length) min = length;
                    all += length;
                }
            }
            avgLength = all /number;
            minLength = min;
            maxLength = max;
            fileSize = new File(inputFile).length();
            allNumber = number;
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
