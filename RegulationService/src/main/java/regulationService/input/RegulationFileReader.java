package regulationService.input;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegulationFileReader {

    private final int MAX_NUM_PAGES = 3; //maximum number of pages to parse/analyse


    public static void main(String[] args) throws IOException {
        //For testing purposes
        String filename = "/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2019_83.pdf";
        String fileContents = new RegulationFileReader().parseRegulationPDF(filename);
        System.exit(0);
    }

    public String parseRegulationPDF(String fileName) throws IOException
    {
        try (PDDocument document = PDDocument.load(new File(fileName)))
        {
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent())
            {
                throw new IOException("You do not have permission to extract text");
            }

            PDFTextStripper stripper = new PDFTextStripper();

            // This example uses sorting, but in some cases it is more useful to switch it off,
            // e.g. in some files with columns where the PDF content stream respects the
            // column order.
            stripper.setSortByPosition(true);

            int pages = Math.min(MAX_NUM_PAGES, document.getNumberOfPages());

            for (int pageNo = 1; pageNo <= pages; ++pageNo)
            {
                // Set the page interval to extract. If you don't, then all pages would be extracted.
                stripper.setStartPage(pageNo);
                stripper.setEndPage(pages);

                // let the magic happen
                String text = stripper.getText(document);

                
                if(pageNo == 1)
                {
                    System.out.println("REGULATION TITLE: " + extractTitle(text));
                    System.out.println("REGULATION CODE: " + extractRegCode(text));
                    //extractGoLiveDate(text);
                    //extractRegulatoryIssueDate(text);
                }
                String[] lines = text.split(System.getProperty("line.separator"));
                for(String line: lines)
                {
                    System.out.println(line.trim()/* + "***\n"*/);
                }
                //System.out.println("---------------------------------------------------------------------------------");

                // If the extracted text is empty or gibberish, please try extracting text
                // with Adobe Reader first before asking for help. Also read the FAQ
                // on the website:
                // https://pdfbox.apache.org/2.0/faq.html#text-extraction
            }
        }

        return ""; //TODO ADD FILLER HERE
    }

    private String extractRegulatoryIssueDate(String pageContents) {
        return "";
    }

    private String extractGoLiveDate(int pageNo, String[] lines) {
        Pattern pattern = Pattern.compile(".s");
        Matcher matcher;
        for(String line: lines)
        {
            matcher = pattern.matcher(line);
            //matcher.
        }
        return "";
    }

    private String extractRegCode(String pageContents) {
        Pattern pattern = Pattern.compile("(FCA \\d{4}/\\d*)\\n");
        Matcher matcher;

            matcher = pattern.matcher(pageContents);
            if(matcher.find())
            {
                return matcher.group(1);
            }
            return "NO MATCH";//pageContents.split(System.getProperty("line.separator"))[0];

        //return lines[0]; //First line on any regulation is the regulation code if regex doesn't work
    }

    private String extractTitle(String pageContents) {
        Pattern pattern = Pattern.compile("(FCA \\d{4}/\\d*)\\n*(.*)\\n(.*|\\n)?");
        Matcher matcher;
        matcher = pattern.matcher(pageContents);
        if(matcher.find())
        {
            return matcher.group(2) + matcher.group(3);
        }
        return "NO MATCH";//pageContents.split(System.getProperty("line.separator"))[1]; //Second line on any regulation file is the regulation title if regex doesn't work
    }
}
