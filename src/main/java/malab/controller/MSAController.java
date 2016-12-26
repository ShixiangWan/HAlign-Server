package malab.controller;

import malab.utils.FormatFileUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Controller
public class MSAController implements ServletContextAware {
    private ServletContext servletContext;
    @Autowired
    public void setServletContext(ServletContext context) {
        this.servletContext  = context;
    }

    @RequestMapping(value="predict", method = RequestMethod.POST)
    public ModelAndView handleUploadData(@RequestParam("type") String sequenceType, @RequestParam("alg") String algorithm,
                                         @RequestParam("mode") String mode, @RequestParam("file") CommonsMultipartFile file,
                                         @RequestParam("paste")String paste, Model map){
        String root = this.servletContext.getRealPath("/");
        String time = Long.toString(new Date().getTime());
        String jarFile = root+"data/HAlign2.1.jar";
        String inputFile = root+"data/input.fasta";
        String outputFile = root+"data/"+time+".fasta";
        try {
            if (!paste.isEmpty()){
                /*write paste content*/
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(inputFile));
                bufferedWriter.write(paste);
                bufferedWriter.flush();
                bufferedWriter.close();
            } else if (!file.isEmpty()) {
                /*write upload content*/
                file.getFileItem().write(new File(inputFile));
            } else {
                map.addAttribute("error", "Please paste or upload a fasta DNA/RNA/Protein file.");
                return new ModelAndView("index");
            }
            /*algorithm*/
            if (algorithm.equals("2") && sequenceType.equals("DNA")) {
                algorithm = "2"; //KBand for DNA/RNA
            } else if (algorithm.equals("2") && sequenceType.equals("Protein")) {
                algorithm = "1"; //KBand for Protein
            }
            map.addAttribute("alg", algorithm);
            /*Hadoop or Standalone*/
            String command = "";
            if (mode.equals("hadoop")) {
                command = "java -jar "+jarFile+" -localMSA "+inputFile+" "+outputFile+" "+algorithm;
                map.addAttribute("mode", "Hadoop cluster mode");
            } else if (mode.equals("standalone")){
                command = "java -jar "+jarFile+" -localMSA "+inputFile+" "+outputFile+" "+algorithm;
                map.addAttribute("mode", "Stand-alone mode");
            }
            /*Run MSA*/
            long startRunTime = System.currentTimeMillis();
            System.out.println(command);
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            map.addAttribute("time", time); //for downloading
            map.addAttribute("runtime", (System.currentTimeMillis()-startRunTime)+"ms");

            /*statistic file info*/
            FormatFileUtils fileUtils = new FormatFileUtils();
            fileUtils.statisticFile(inputFile);

            map.addAttribute("size", fileUtils.getFileSize());
            map.addAttribute("number", fileUtils.getAllNumber());
            map.addAttribute("max", fileUtils.getMaxLength());
            map.addAttribute("min", fileUtils.getMinLength());
            map.addAttribute("avg", fileUtils.getAvgLength());

            return new ModelAndView("index");
        } catch (Exception e) {
            map.addAttribute("error", "Failed, please contact author.");
        }
        return new ModelAndView("index");
    }

    /*
    * @function: view MSA result
    * */
    @RequestMapping(value="view")
    public ModelAndView ViewResult (String time, Model map){
        map.addAttribute("time", time);
        return new ModelAndView("view");
    }

    /*
    * @function: view tree result
    * */
    @RequestMapping(value="tree")
    public ModelAndView ViewTree (String time, String type, Model map) throws InterruptedException, IOException {
        String root = this.servletContext.getRealPath("/");

        /*Tree*/
        Process process;
        String command;
        String inputPath = root+"data/";
        String inputFile = time+".fasta";
        command = "java -jar "+inputPath+"HAlign2.1.jar -tree "+inputPath+inputFile+" "+inputPath+"tree/tree.tre";
        System.out.println(command);
        process = Runtime.getRuntime().exec(command);
        process.waitFor();

        /*Convert*/
//        String jarFile = inputPath + "tree/TreeGraph.jar";
//        String treFile = inputPath + "tree/tree1.tre";
//        String xtgFile = inputPath + "tree/tree.xtg";
//        String svgFile = inputPath + "tree/tree.svg";
//        command = "java -jar "+jarFile+" -convert "+treFile+" -xtg "+xtgFile;
//        process = Runtime.getRuntime().exec(command);
//        process.waitFor();
//        command = "java -jar "+jarFile+" -image "+xtgFile+" "+svgFile;
//        process = Runtime.getRuntime().exec(command);
//        process.waitFor();
        if (type.equals("1"))  map.addAttribute("name", "tree2");
        else  map.addAttribute("name", "tree1");

        return new ModelAndView("tree");
    }

    /*
    * @function: download DNA/RNA/Protein example file
    * */
    @RequestMapping(value="download")
    public ResponseEntity<byte[]> DownloadExample(String time){
        try {
            String fileName = time+".fasta";
            /*bytes file*/
            String pathName = this.servletContext.getRealPath("/")+"data/"+fileName;
            byte[] bytes = FileUtils.readFileToByteArray(new File(pathName));
            /*http wrap*/
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment",
                    new String(fileName.getBytes("gb2312"),"iso-8859-1"));
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception ignored) {
            return null;
        }
    }
}
