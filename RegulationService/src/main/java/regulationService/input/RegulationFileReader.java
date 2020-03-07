package regulationService.input;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RegulationFileReader {

    private final int START_PAGE = 1;
    private final int MAX_NUM_PAGES = 3; //maximum number of pages to parse/analyse

    public static void main(String[] args) throws IOException {
        //For testing purposes

        String filename = "/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2019_83.pdf";
        //String filename = "/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2018_6.pdf";
        //String filename = "/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2019_10.pdf";
        //String filename = "/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2018_55.pdf";
        //String filename = "/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2018_55.pdf";
        //String filename = "/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2020_2.pdf";

        String contents = new RegulationFileReader().parseRegulationPDF(filename);

        System.exit(0);
    }

    public String parseRegulationPDF(String fileName) throws IOException
    {
        if(fileName.contains(".DS_Store")){
            return null; //Don't process hidden files
        }

        String fileContents = "";

        File file = new File(fileName);

        try (PDDocument document = PDDocument.load(new FileInputStream(file)))
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

            // Set the page interval to extract. If you don't, then all pages would be extracted.
            stripper.setStartPage(START_PAGE);
            stripper.setEndPage(pages);

            // let the magic happen
            fileContents = stripper.getText(document);

            System.out.println("TEXT FILE: " + fileContents);

            return fileContents;
        }
    }
}
