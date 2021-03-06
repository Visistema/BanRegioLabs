package BanRegio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class BanRegioApiHelper
{
    public enum ApiEnvironment {Sandbox, Production}

    private String accessToken;
    private String baseUri;

    private BanRegioApiHelper(){}

    public BanRegioApiHelper(String baseUri,
                             ApiEnvironment apiEnvironment,
                             String accessToken)
    {
        this.accessToken = accessToken;
        this.baseUri = baseUri;
    }

    private HttpURLConnection buildHttpConnection(String specificUrl)
    {
        String resourceUrl = baseUri + specificUrl;

        HttpURLConnection conn = null;
        try
        {
            conn = (HttpURLConnection)(new URL(resourceUrl).openConnection());
            conn.addRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("User-Agent", "my-user-agent-name");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return conn;
    }

    private void outputResponse(HttpURLConnection conn) throws IOException
    {
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            br.close();

            System.out.println("" + sb.toString());
        }
        else
        {
            System.out.println(conn.getResponseMessage());
        }
    }

    public void linkAccount(String clientNumber, String last4Digits, String cardPin)
    {
        JSONObject client = new JSONObject();
        client.put("number", clientNumber);

        JSONObject card = new JSONObject();
        card.put("last_4_digits", last4Digits);
        card.put("ping", cardPin);

        JSONObject payload = new JSONObject();
        payload.put("client", client);
        payload.put("card", card);

        try
        {
            HttpURLConnection conn = buildHttpConnection("/v1/accounts");
            conn.setRequestMethod("POST");

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(payload.toString());
            wr.flush();

            outputResponse(conn);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void listAccounts()
    {
        try
        {
            HttpURLConnection conn = buildHttpConnection("/v1/accounts");
            conn.setRequestMethod("GET");

            outputResponse(conn);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void unlinkAccount(String accountId)
    {
        try
        {
            HttpURLConnection conn = buildHttpConnection("/v1/accounts/" + accountId);
            conn.setRequestMethod("DELETE");

            outputResponse(conn);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void listTransactions(String accountId)
    {
        try
        {
            HttpURLConnection conn = buildHttpConnection("/v1/accounts/" + accountId + "/transactions");
            conn.setRequestMethod("GET");

            outputResponse(conn);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
