package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.*;
import java.util.List;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;
    private static final String NAME = "bankingTest";

    public static void main(String[] args) {
        try {
            URL url1 = new URL("https://api.privatbank.ua/p24api/exchange_rates?date=01.01.2021");
            URL url2 = new URL("https://api.privatbank.ua/p24api/exchange_rates?date=01.02.2021");
            URL url3 = new URL("https://api.privatbank.ua/p24api/exchange_rates?date=01.03.2021");
            URL url4 = new URL("https://api.privatbank.ua/p24api/exchange_rates?date=01.04.2021");
            Gson gson = new GsonBuilder().create();

            Result res1 = gson.fromJson(getInfo(url1), Result.class);
            Result res2 = gson.fromJson(getInfo(url2), Result.class);
            Result res3 = gson.fromJson(getInfo(url3), Result.class);
            Result res4 = gson.fromJson(getInfo(url4), Result.class);

            emf = Persistence.createEntityManagerFactory(NAME);
            em = emf.createEntityManager();

            persistObj(res1);
            persistObj(res2);
            persistObj(res3);
            persistObj(res4);

            select("01.04.2021");

            averageCur("01.01.2021", "01.02.2021");





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
    public static void persistObj(Result result){
        List<ExchangeRate> list = result.getExchangeRate();
        for(ExchangeRate exchangeRate : list){
            exchangeRate.setResult(result);
        }
        result.setExchangeRate(list);
        em.getTransaction().begin();
        em.persist(result);
        em.getTransaction().commit();

    }

    public static void select(String dateString){
        /*
        TypedQuery<Result> query = em
                .createQuery("SELECT r FROM Result r WHERE r.date LIKE :pattern",
                        Result.class);
        query.setParameter("pattern", dateString);
        Result res =query.getSingleResult();
         long id = res.getId();
*/
        StringBuilder sb = new StringBuilder();
        sb.append("Дата: " + dateString + "\n");


        TypedQuery<ExchangeRate> query1 = em.createQuery("SELECT er FROM ExchangeRate er WHERE er.result.date LIKE :date AND er.currency LIKE :cur",
                ExchangeRate.class);
        query1.setParameter("date", dateString);
        query1.setParameter("cur", "USD");
        ExchangeRate exchangeRate = query1.getSingleResult();

        sb.append("UAH/" + exchangeRate.getCurrency()+ ":" + "\n \t Курс купівлі:" + exchangeRate.getPurchaseRate()
        +"\n \t Курс продажу:" + exchangeRate.getSaleRate());
        System.out.println(sb);
    }

    public static void averageCur(String date1, String date2){
        TypedQuery<Double> query = em.createQuery("SELECT AVG(er.purchaseRate) FROM ExchangeRate er WHERE er.currency LIKE :cur AND er.result.date BETWEEN :dt1 AND :dt2",
                Double.class);
        query.setParameter("cur", "USD");
        query.setParameter("dt1", date1);
        query.setParameter("dt2", date2);
        double result = query.getSingleResult();
        System.out.println("Средний курс покупки USD с "+date1+ " по "+ date2+ ": " + result);

    }

}
