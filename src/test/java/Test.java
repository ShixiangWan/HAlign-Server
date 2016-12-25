import malab.utils.FormatFileUtils;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        String inputFile = "C:\\workspace_intell\\HAlignWeb\\out\\artifacts\\HAlignWeb\\data\\MSA2.0.jar";
        /*FormatFileUtils fileUtils = new FormatFileUtils();
        fileUtils.statisticFile(inputFile);*/
        System.out.println(new File(inputFile).length());
    }
}
