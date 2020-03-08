package regulationService.comprehend;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.*;
import regulationService.model.Message;
import regulationService.model.Regulation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegulationProcessor {

    private static final Regions REGION = Regions.EU_WEST_2;
    private final String FCA_REGULATION_REGEX = "(FCA \\d{4}\\/\\d*)\\s*([ \\w\\/.,’()“”:;-]*)\\s*([ \\w\\/.,’()“”:;-]*)[\\s\\w\\/.,’()“”:;-]*Commencement[\\s\\w\\/.,’()“”:;-]+?(\\d{1,2}.*\\d{4})[\\s\\w\\/.,’()“”:;-]*By order of the Board\\s+(\\d{1,2}.*\\d{4})";
    
    private static final String LANGUAGE_CODE = "en";
    private static final String ENDPOINT_NAME = "categorise-regulation-module";
    private static final String CLASSIFIER_ARN = "arn:aws:comprehend:eu-west-2:172506724237:document-classifier/Regulation-Shortened";

    private static String endpointArn = "arn:aws:comprehend:eu-west-2:172506724237:document-classifier-endpoint/categorise-regulation-module";
    private static String exampleText = getExampleText();

    private AmazonComprehend comprehendClient;

    public RegulationProcessor()
    {
        this.comprehendClient = setUpComprehendClient();
    }
    private static String getExampleText() {
        return "TRADE REPOSITORIES (GUIDANCE) INSTRUMENT 2019\n" +
                "Powers exercised\n" +
                "A. The Financial Conduct Authority (“the FCA”) makes this instrument in the exercise\n" +
                "of:\n" +
                "(1) section 139A (Power of the FCA to give guidance) in the Financial Services\n" +
                "and Markets Act 2000 (“the Act”);\n" +
                "(2) the powers of direction, guidance and related provisions in or under the\n" +
                "following provisions of the Over the Counter Derivatives, Central\n" +
                "Counterparties and Trade Repositories (Amendments etc. and Transitional\n" +
                "Provision) (EU Exit) Regulations 2019 (SI 2019/335):\n" +
                "(a) regulation 69 (Statement of policy);\n" +
                "(b) regulation 71 (Powers to issue guidance);\n" +
                "(c) regulation 75 (Application of Part 11 of the Act (information gathering\n" +
                "and investigations)); and\n" +
                "(d) regulation 76 (Application of Part 26 of the Act (notices)).\n" +
                "Commencement\n" +
                "B. This instrument comes into force on exit day as defined in the European Union\n" +
                "(Withdrawal) Act 2018, immediately after the changes made by the Credit Rating\n" +
                "Agencies (Guidance) Instrument 2019 come into force.\n" +
                "Amendments to the Handbook\n" +
                "C. The Decision Procedure and Penalties manual (DEPP) is amended in accordance with\n" +
                "Annex A to this instrument.\n" +
                "Amendments to material outside the Handbook\n" +
                "D. The Enforcement Guide (EG) is amended in accordance with Annex B to this\n" +
                "instrument.\n" +
                "Citation\n" +
                "E. This instrument may be cited as the Trade Repositories (Guidance) Instrument 2019.\n" +
                "By order of the Board\n" +
                "28 March 2019 ";
    }

    public static void main(String[] param){

        RegulationProcessor processor = new RegulationProcessor();

        //detectLanguage(comprehendClient);
        //detectEntities(comprehendClient);
        //detectKeyPhrases(comprehendClient);

        processor.classifyRegulation(exampleText);
    }

    private AmazonComprehend setUpComprehendClient(){
        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();

        return AmazonComprehendClientBuilder.standard()
                        .withCredentials(awsCreds)
                        .withRegion(REGION)
                        .build();
    }

    private void detectLanguage()
    {
        // Call detectDominantLanguage API
        System.out.println("Calling DetectDominantLanguage");
        DetectDominantLanguageRequest detectDominantLanguageRequest = new DetectDominantLanguageRequest().withText(exampleText);
        DetectDominantLanguageResult detectDominantLanguageResult = this.comprehendClient.detectDominantLanguage(detectDominantLanguageRequest);
        detectDominantLanguageResult.getLanguages().forEach(System.out::println);
        System.out.println("Calling DetectDominantLanguage\n");
        System.out.println("Done");
    }

    private void detectEntities(AmazonComprehend client)
    {
        // Call detectEntities API
        System.out.println("Calling DetectEntities");
        DetectEntitiesRequest detectEntitiesRequest = new DetectEntitiesRequest().withText(exampleText)
                .withLanguageCode(LANGUAGE_CODE);
        DetectEntitiesResult detectEntitiesResult  = this.comprehendClient.detectEntities(detectEntitiesRequest);
        detectEntitiesResult.getEntities().forEach(System.out::println);
        System.out.println("End of DetectEntities\n");
    }

    private void detectKeyPhrases(AmazonComprehend client)
    {
        // Call detectKeyPhrases API
        System.out.println("Calling DetectKeyPhrases");
        DetectKeyPhrasesRequest detectKeyPhrasesRequest = new DetectKeyPhrasesRequest().withText(exampleText)
                .withLanguageCode(LANGUAGE_CODE);
        DetectKeyPhrasesResult detectKeyPhrasesResult = this.comprehendClient.detectKeyPhrases(detectKeyPhrasesRequest);
        detectKeyPhrasesResult.getKeyPhrases().forEach(System.out::println);
        System.out.println("End of DetectKeyPhrases\n");
    }

    private DocumentClass classifyRegulation(String text) {
        //endpointArn = getOrCreateEndpoint();
        //endpointArn = createEndpoint();

        // Call classifyDocument API
        System.out.println("Classifying Regulation");
        ClassifyDocumentRequest classifyDocumentRequest = new ClassifyDocumentRequest()
                .withEndpointArn(endpointArn)
                .withText(text);
        ClassifyDocumentResult classifyDocumentResult = this.comprehendClient.classifyDocument(classifyDocumentRequest);
        classifyDocumentResult.getClasses().forEach(System.out::println);

        //deleteEndpoint(client, endpointArn);

        return classifyDocumentResult.getClasses().get(0);
    }

    private String getOrCreateEndpoint() throws RuntimeException
    {
        DescribeEndpointRequest describeEndpointRequest = new DescribeEndpointRequest().withEndpointArn(endpointArn);
        DescribeEndpointResult describeEndpointResult = this.comprehendClient.describeEndpoint(describeEndpointRequest);

        if(describeEndpointResult.getSdkHttpMetadata().getHttpStatusCode() == 200)
        {
            return describeEndpointRequest.getEndpointArn();
        }
        else{
            return createEndpoint();
        }
    }

    private String createEndpoint()
    {
        System.out.println("Creating endpoint: " + ENDPOINT_NAME);

        CreateEndpointRequest createEndpointRequest = new CreateEndpointRequest()
                .withEndpointName(ENDPOINT_NAME)
                .withModelArn(CLASSIFIER_ARN)
                .withDesiredInferenceUnits(1);

        CreateEndpointResult createEndpointResult = this.comprehendClient.createEndpoint(createEndpointRequest);

        if (createEndpointResult.getSdkHttpMetadata().getHttpStatusCode() != 200)
        {
            throw new RuntimeException("Endpoint creation failed. Cannot proceed");
        }
        return createEndpointResult.getEndpointArn();
    }

    private void deleteEndpoint(AmazonComprehend client, String endpointArn)
    {
        DeleteEndpointRequest deleteEndpointRequest = new DeleteEndpointRequest()
                    .withEndpointArn(endpointArn);
            DeleteEndpointResult deleteEndpointResult = this.comprehendClient.deleteEndpoint(deleteEndpointRequest);

            if (deleteEndpointResult.getSdkHttpMetadata().getHttpStatusCode()!=200)
            {
                System.out.println("Endpoint deletion failed ");
            }
    }

    public Regulation enrichRegulation(String fileContents) {

        DocumentClass result = classifyRegulation(fileContents);
        Regulation regulation = new Regulation();

        String title = extractTitle(fileContents);
        String code = extractRegCode(fileContents);
        String goLive = extractGoLiveDate(fileContents);
        String issueDate = extractRegulatoryIssueDate(fileContents);

        regulation.setDateIssued(issueDate);
        regulation.setGoLive(goLive);
        regulation.setRegulationTitle(title);
        regulation.setRegulationCode(code);

        System.out.println("REGULATION TITLE: " + title);
        System.out.println("REGULATION CODE: " + code);
        System.out.println("REGULATION GO LIVE: " + goLive);
        System.out.println("REGULATION ISSUE DATE: " + issueDate);
        System.out.println("REGULATION MODULE CODE: " + result.getName() + " CONFIDENCE: " + result.getScore());

        return regulation;
    }

    public Message createMessage(String fileContents) {

        DocumentClass result = classifyRegulation(fileContents);
        Regulation regulation = new Regulation();
        Message message = new Message();

        String title = extractTitle(fileContents);
        String code = extractRegCode(fileContents);
        String goLive = extractGoLiveDate(fileContents);
        String issueDate = extractRegulatoryIssueDate(fileContents);

        regulation.setDateIssued(issueDate);
        regulation.setGoLive(goLive);
        regulation.setRegulationTitle(title);
        regulation.setRegulationCode(code);
        regulation.setAtRisk(false);

        message.setModuleCode(result.getName());
        message.setRegulation(regulation);

        System.out.println("REGULATION TITLE: " + title);
        System.out.println("REGULATION CODE: " + code);
        System.out.println("REGULATION GO LIVE: " + goLive);
        System.out.println("REGULATION ISSUE DATE: " + issueDate);
        System.out.println("REGULATION MODULE CODE: " + result.getName() + " CONFIDENCE: " + result.getScore());

        return message;
    }

    private String extractRegulatoryIssueDate(String fileContents) {
        Pattern pattern = Pattern.compile(FCA_REGULATION_REGEX);
        Matcher matcher;
        matcher = pattern.matcher(fileContents);
        if(matcher.find())
        {
            return matcher.group(4);
        }
        return "NO MATCH";
    }

    private String extractGoLiveDate(String fileContents) {
        Pattern pattern = Pattern.compile(FCA_REGULATION_REGEX);
        Matcher matcher;
        matcher = pattern.matcher(fileContents);
        if(matcher.find())
        {
            return matcher.group(5);
        }
        return "NO MATCH";
    }

    private String extractRegCode(String fileContents) {
        Pattern pattern = Pattern.compile(FCA_REGULATION_REGEX);
        Matcher matcher;

        matcher = pattern.matcher(fileContents);
        if(matcher.find())
        {
            return matcher.group(1);
        }
        return "NO MATCH";//fileContents.split(System.getProperty("line.separator"))[0];

        //return lines[0]; //First line on any regulation is the regulation code if regex doesn't work
    }

    private String extractTitle(String fileContents) {
        Pattern pattern = Pattern.compile(FCA_REGULATION_REGEX);
        Matcher matcher;
        matcher = pattern.matcher(fileContents);
        if(matcher.find())
        {
            String titleStart = matcher.group(2);
            String titleEnd = matcher.group(3); //Check for titles which span multiple lines
            if (titleEnd.toLowerCase().contains("powers"))
            {
                return titleStart;
            }
            return titleStart + titleEnd;
        }
        return "NO MATCH";//fileContents.split(System.getProperty("line.separator"))[1]; //Second line on any regulation file is the regulation title if regex doesn't work
    }
}
