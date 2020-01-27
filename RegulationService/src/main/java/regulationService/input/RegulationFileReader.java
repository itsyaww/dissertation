package regulationService.input;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

public class RegulationFileReader {

    public static void main(String args[]) throws IOException
    {
        String filename = "/Users/paulfrimpong/Documents/COMPUTER SCIENCE/Year 4/Final Year Project/FinalYearProject/RegulationService/src/main/resources/FCA_2019_83.pdf";

        try (PDDocument document = PDDocument.load(new File(filename)))
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

            for (int p = 1; p <= document.getNumberOfPages(); ++p)
            {
                // Set the page interval to extract. If you don't, then all pages would be extracted.
                stripper.setStartPage(p);
                stripper.setEndPage(10);

                // let the magic happen
                String text = stripper.getText(document);

                // do some nice output with a header
                String pageStr = String.format("page %d:", p);
                System.out.println(pageStr);
                for (int i = 0; i < pageStr.length(); ++i)
                {
                    System.out.print("-");
                }
                System.out.println();
                System.out.println(text.trim());
                System.out.println();

                // If the extracted text is empty or gibberish, please try extracting text
                // with Adobe Reader first before asking for help. Also read the FAQ
                // on the website:
                // https://pdfbox.apache.org/2.0/faq.html#text-extraction
            }
        }
    }
}
