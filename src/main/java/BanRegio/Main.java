package BanRegio;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.io.IOException;

public class Main
{
    public static void main(String args[]) throws OAuthSystemException, IOException
    {
        try
        {
            String client_id = "client_id";
            String client_secret = "client_secret";

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation("https://sandbox.banregio.com/oauth/token")
                    .setGrantType(GrantType.CLIENT_CREDENTIALS)
                    .setClientId(client_id)
                    .setClientSecret(client_secret)
                    .buildBodyMessage();

            String access_token = oAuthClient.accessToken(request, OAuthJSONAccessTokenResponse.class).getAccessToken();

            BanRegioApiHelper banregio = new BanRegioApiHelper(BanRegioApiHelper.ApiEnvironment.Sandbox,
                    client_id,
                    client_secret,
                    access_token);

            banregio.listAccounts();
        }
        catch (OAuthProblemException e)
        {
            System.out.println("OAuth error: " + e.getError());
            System.out.println("OAuth error description: " + e.getDescription());
        }
    }
}
