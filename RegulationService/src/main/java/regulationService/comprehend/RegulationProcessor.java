package regulationService.comprehend;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.*;

public class RegulationProcessor {

    private static final Regions REGION = Regions.EU_WEST_2;
    private static final String LANGUAGE_CODE = "en";
    private static String text = "It is raining today in Seattle";

    public static void runClient(){

        AmazonComprehend comprehendClient = setUpComprehendClient();
        detectLanguage(comprehendClient);
        detectEntities(comprehendClient);
        detectKeyPhrases(comprehendClient);
    }

    private static AmazonComprehend setUpComprehendClient(){
        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();

        return AmazonComprehendClientBuilder.standard()
                        .withCredentials(awsCreds)
                        .withRegion(REGION)
                        .build();
    }

    private static void detectLanguage(AmazonComprehend client)
    {
        // Call detectDominantLanguage API
        System.out.println("Calling DetectDominantLanguage");
        DetectDominantLanguageRequest detectDominantLanguageRequest = new DetectDominantLanguageRequest().withText(text);
        DetectDominantLanguageResult detectDominantLanguageResult = client.detectDominantLanguage(detectDominantLanguageRequest);
        detectDominantLanguageResult.getLanguages().forEach(System.out::println);
        System.out.println("Calling DetectDominantLanguage\n");
        System.out.println("Done");
    }

    private static void detectEntities(AmazonComprehend client)
    {
        // Call detectEntities API
        System.out.println("Calling DetectEntities");
        DetectEntitiesRequest detectEntitiesRequest = new DetectEntitiesRequest().withText(text)
                .withLanguageCode(LANGUAGE_CODE);
        DetectEntitiesResult detectEntitiesResult  = client.detectEntities(detectEntitiesRequest);
        detectEntitiesResult.getEntities().forEach(System.out::println);
        System.out.println("End of DetectEntities\n");
    }

    private static void detectKeyPhrases(AmazonComprehend client)
    {
        // Call detectKeyPhrases API
        System.out.println("Calling DetectKeyPhrases");
        DetectKeyPhrasesRequest detectKeyPhrasesRequest = new DetectKeyPhrasesRequest().withText(text)
                .withLanguageCode(LANGUAGE_CODE);
        DetectKeyPhrasesResult detectKeyPhrasesResult = client.detectKeyPhrases(detectKeyPhrasesRequest);
        detectKeyPhrasesResult.getKeyPhrases().forEach(System.out::println);
        System.out.println("End of DetectKeyPhrases\n");
    }
}
