package BanRegio;

import java.io.*;
import org.apache.commons.cli.*;

public class Main
{
    public static void main(String args[]) throws IOException {
        Options options = new Options();

        Option client_id = new Option("c", "client_id", true, "client id");
        client_id.setRequired(true);
        options.addOption(client_id);

        Option client_secret = new Option("s", "client_secret", true, "client secret");
        client_secret.setRequired(true);
        options.addOption(client_secret);

        Option base_uri = new Option("b", "base_uri", true, "base uri");
        base_uri.setRequired(true);
        options.addOption(base_uri);

        Option redirect_uri = new Option("r", "redirect_uri", true, "redirect uri");
        redirect_uri.setRequired(true);
        options.addOption(redirect_uri);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try
        {
            cmd = parser.parse(options, args);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
        }

        String clientId = cmd.getOptionValue("client_id");
        String clientSecret = cmd.getOptionValue("client_secret");
        String baseUri = cmd.getOptionValue("base_uri");
        String redirectUri = cmd.getOptionValue("redirect_uri");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input = "";
        while(!input.equals("3"))
        {
            System.out.println("Bienvenido al ejemplo de OAuth2 de Banregio");

            System.out.println("¿Cómo te gustaría obtener tu token?");
            System.out.println("1. Authorization Code");
            System.out.println("2. Client Credentials");
            System.out.println("3. Salir");

            System.out.print("Selección: ");
            input = reader.readLine();

            if(input.equals("1"))
            {
                System.out.println("");
                System.out.println("");

                System.out.println("----------");
                System.out.println("Primer Paso: copia el siguiente URL en tu explorador e ingresa tus credenciales.");
                System.out.println("----------");

                BanRegioTokenHelper tokenHelper = new BanRegioTokenHelper(baseUri,
                        BanRegioTokenHelper.TokenGrantType.AuthorizationCode,
                        clientId,
                        redirectUri,
                        clientSecret);

                System.out.println(tokenHelper.getAuthorizationCodeURL());

                System.out.println("");
                System.out.println("Teclea 'Enter' cuando hayas terminado.");

                reader.readLine();

                System.out.println("");
                System.out.println("");

                System.out.println("----------");
                System.out.println("Segundo Paso: en la barra de navegación de tu exploador, copia el valor seguido de code=.");
                System.out.println("----------");

                System.out.print("Code: ");
                input = reader.readLine();

                String token = tokenHelper.fetchToken(input);
                BanRegioApiHelper apiHelper = new BanRegioApiHelper(baseUri,
                        BanRegioApiHelper.ApiEnvironment.Sandbox,
                        token);

                System.out.println("\nEl token adquirido es: " + token);

                while(!input.equals("5"))
                {
                    System.out.println("");
                    System.out.println("");

                    System.out.println("----------");
                    System.out.println("Selecciona una opción del menú");
                    System.out.println("----------");

                    System.out.println("1. Ligar Cuenta");
                    System.out.println("2. Lista Cuenta");
                    System.out.println("3. Desligar Cuenta");
                    System.out.println("4. Listar Transacciones");
                    System.out.println("5. Volver al Menú Principal");

                    System.out.print("Selección: ");
                    input = reader.readLine();

                    if (input.equals("1"))
                    {
                        System.out.print("Número de cliente: ");
                        String clientNumber = reader.readLine();

                        System.out.print("Últimos 4 dígitos: ");
                        String last4Digits = reader.readLine();

                        System.out.print("PIN: ");
                        String cardPin = reader.readLine();

                        System.out.println("");
                        System.out.println("Resultado");
                        System.out.println("----------");

                        apiHelper.linkAccount(clientNumber, last4Digits, cardPin);
                    }
                    else if (input.equals("2"))
                    {
                        System.out.println("");
                        System.out.println("Resultado");
                        System.out.println("----------");

                        apiHelper.listAccounts();
                    }
                    else if (input.equals("3"))
                    {
                        System.out.print("Número de cuenta: ");
                        String accountId = reader.readLine();

                        System.out.println("");
                        System.out.println("Resultado");
                        System.out.println("----------");

                        apiHelper.unlinkAccount(accountId);
                    }
                    else if (input.equals("4"))
                    {
                        System.out.print("Número de cuenta: ");
                        String accountId = reader.readLine();

                        System.out.println("");
                        System.out.println("Resultado");
                        System.out.println("----------");

                        apiHelper.listTransactions(accountId);
                    }
                    else if (input.equals("5"))
                    {
                        break;
                    }
                }

            }
            else if(input.equals("2"))
            {
                BanRegioTokenHelper tokenHelper = new BanRegioTokenHelper(baseUri,
                        BanRegioTokenHelper.TokenGrantType.ClientCredentials,
                        clientId,
                        clientSecret);

                String token = tokenHelper.fetchToken();

                BanRegioApiHelper apiHelper = new BanRegioApiHelper(baseUri,
                        BanRegioApiHelper.ApiEnvironment.Sandbox,
                        token);

                while(!input.equals("5"))
                {
                    System.out.println("");
                    System.out.println("");

                    System.out.println("----------");
                    System.out.println("Selecciona una opción del menú");
                    System.out.println("----------");

                    System.out.println("1. Ligar Cuenta");
                    System.out.println("2. Lista Cuenta");
                    System.out.println("3. Desligar Cuenta");
                    System.out.println("4. Listar Transacciones");
                    System.out.println("5. Volver al Menú Principal");

                    System.out.print("Selección: ");
                    input = reader.readLine();

                    if(input.equals("1"))
                    {
                        System.out.print("Número de cliente: ");
                        String clientNumber = reader.readLine();

                        System.out.print("Últimos 4 dígitos: ");
                        String last4Digits = reader.readLine();

                        System.out.print("PIN: ");
                        String cardPin = reader.readLine();

                        System.out.println("");
                        System.out.println("Resultado");
                        System.out.println("----------");

                         apiHelper.linkAccount(clientNumber, last4Digits, cardPin);
                    }
                    else if(input.equals("2"))
                    {
                        System.out.println("");
                        System.out.println("Resultado");
                        System.out.println("----------");

                         apiHelper.listAccounts();
                    }
                    else if(input.equals("3"))
                    {
                        System.out.print("Número de cuenta: ");
                        String accountId = reader.readLine();

                        System.out.println("");
                        System.out.println("Resultado");
                        System.out.println("----------");

                         apiHelper.unlinkAccount(accountId);
                    }
                    else if(input.equals("4"))
                    {
                        System.out.print("Número de cuenta: ");
                        String accountId = reader.readLine();

                        System.out.println("");
                        System.out.println("Resultado");
                        System.out.println("----------");

                         apiHelper.listTransactions(accountId);
                    }
                    else if(input == "5")
                    {
                        break;
                    }
                }
            }
            else if(input.equals("3"))
            {
                break;
            }
        }
    }
}
