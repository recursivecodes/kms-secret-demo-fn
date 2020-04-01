package codes.recursive;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.secrets.SecretsClient;
import com.oracle.bmc.secrets.model.Base64SecretBundleContentDetails;
import com.oracle.bmc.secrets.requests.GetSecretBundleRequest;
import com.oracle.bmc.secrets.responses.GetSecretBundleResponse;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

public class SecretDemo {

    private final String secretId = System.getenv("SECRET_ID");
    private SecretsClient secretsClient;

    public SecretDemo() {
        String version = System.getenv("OCI_RESOURCE_PRINCIPAL_VERSION");
        BasicAuthenticationDetailsProvider provider = null;
        if( version != null ) {
            provider = ResourcePrincipalAuthenticationDetailsProvider.builder().build();
        }
        else {
            try {
                provider = new ConfigFileAuthenticationDetailsProvider("~/.oci/config", "DEFAULT");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        secretsClient = new SecretsClient(provider);
        secretsClient.setRegion(Region.US_PHOENIX_1);
    }

    public String  handleRequest() throws IOException {
        return getSecretValue(secretId);
    }

    private String getSecretValue(String secretOcid) throws IOException {

        // create get secret bundle request
        GetSecretBundleRequest getSecretBundleRequest = GetSecretBundleRequest
                .builder()
                .secretId(secretOcid)
                .stage(GetSecretBundleRequest.Stage.Current)
                .build();

        // get the secret
        GetSecretBundleResponse getSecretBundleResponse = secretsClient.
                getSecretBundle(getSecretBundleRequest);

        // get the bundle content details
        Base64SecretBundleContentDetails base64SecretBundleContentDetails =
                (Base64SecretBundleContentDetails) getSecretBundleResponse.
                        getSecretBundle().getSecretBundleContent();

        // decode the encoded secret
        byte[] secretValueDecoded = Base64.decodeBase64(base64SecretBundleContentDetails.getContent());

        return new String(secretValueDecoded);
    }

}