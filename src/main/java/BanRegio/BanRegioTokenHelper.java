package BanRegio;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class BanRegioTokenHelper
{
    public enum TokenGrantType {AuthorizationCode, ClientCredentials}

    private TokenGrantType tokenGrantType;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String baseUri;

    private BanRegioTokenHelper(){}

    public BanRegioTokenHelper(String baseUri,
                               TokenGrantType tokenGrantType,
                               String clientId,
                               String redirectUri,
                               String clientSecret)
    {
        this.baseUri = baseUri;
        this.tokenGrantType = tokenGrantType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
    }

    public BanRegioTokenHelper(String baseUri,
                               TokenGrantType tokenGrantType,
                               String clientId,
                               String clientSecret)
    {
        this.baseUri = baseUri;
        this.tokenGrantType = tokenGrantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getAuthorizationCodeURL()
    {
        try
        {
            OAuthClientRequest request = OAuthClientRequest
                    .authorizationLocation(baseUri + "/oauth/authorize")
                    .setClientId(this.clientId)
                    .setResponseType("code")
                    .setRedirectURI(this.redirectUri)
                    .buildQueryMessage();

            return request.getLocationUri();
        }
        catch (OAuthSystemException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String fetchToken()
    {
        try
        {
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(baseUri + "/oauth/token")
                    .setGrantType(GrantType.CLIENT_CREDENTIALS)
                    .setClientId(this.clientId)
                    .setClientSecret(this.clientSecret)
                    .buildBodyMessage();

            request.setHeader("User-Agent", "my-user-agent-name");
            request.setLocationUri(request.getLocationUri() + "/");

            String accessToken = oAuthClient
                    .accessToken(request, OAuthJSONAccessTokenResponse.class)
                    .getAccessToken();

            return accessToken;

        } catch (OAuthSystemException e)
        {
            e.printStackTrace();
        } catch (OAuthProblemException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String fetchToken(String authorizationCode)
    {
        try
        {
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(baseUri + "/oauth/token")
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(this.clientId)
                    .setClientSecret(this.clientSecret)
                    .setRedirectURI(this.redirectUri)
                    .setCode(authorizationCode)
                    .buildBodyMessage();

            request.setHeader("User-Agent", "my-user-agent-name");
            request.setLocationUri(request.getLocationUri() + "/");

            String accessToken = oAuthClient
                    .accessToken(request, OAuthJSONAccessTokenResponse.class)
                    .getAccessToken();

            return accessToken;

        } catch (OAuthSystemException e)
        {
            e.printStackTrace();
        } catch (OAuthProblemException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
