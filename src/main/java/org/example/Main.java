package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;
    private static final String NAME = "bankingTest";
    //static int id = 0;
    public static void main(String[] args) {
        try {
            URL url = new URL("https://api.privatbank.ua/p24api/exchange_rates?date=01.12.2021");

            Gson gson = new GsonBuilder().create();

            Result res = gson.fromJson(getInfo(url), Result.class);

            emf = Persistence.createEntityManagerFactory(NAME);
            em = emf.createEntityManager();

            em.getTransaction().begin();
            em.persist(res);
            long id = res.getId();
            em.getTransaction().commit();
            


            em.close();
            emf.close();





        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static String getInfo(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

}
