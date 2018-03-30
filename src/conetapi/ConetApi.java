/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conetapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author VBarrera
 */
public class ConetApi extends Application {

    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

    public void obtenerjson() {
        // System.out.println("Debug:  " + formatoFecha.format(new Date()));
        /*JSONParser parser = new JSONParser();

        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = addr.getHostName();
 
            File file = new File("C://Windows/score.json");
            Object obj = parser.parse(new FileReader(file.getCanonicalPath()));
            JSONObject jsonObject = (JSONObject) obj;

            String blog = (String) jsonObject.get("Blog");
            System.out.println(blog);

            String temas = (String) jsonObject.get("Temas");
            System.out.println(temas);

            long inicio = (Long) jsonObject.get("Inicio");
            System.out.println(inicio);

            JSONObject innerObject = (JSONObject) jsonObject.get("Posts");
            System.out.println(innerObject.toJSONString());

            // loop array
            JSONArray tags = (JSONArray) jsonObject.get("Tags");
            Iterator<String> iterator = tags.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        } catch (FileNotFoundException e) {
            System.out.println("1" + e.getMessage());
        } catch (IOException e) {
            System.out.println("2" + e.getMessage());
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(ConetApi.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        //obtenerjson();
        try {

            URL url = new URL("http://crud.local/api/custom");
            OutputStreamWriter printout;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-Authorization-Token", "17ae938e232fd594e8bd0362f91916d3");
            connection.setRequestProperty("X-Authorization-Time", formatoFecha.format(new Date()));
            connection.setRequestProperty("Content-Type", "application/json");
            // System.out.println(connection.getResponseCode());

            // connection.setDoInput(true);
            // connection.setUseCaches (false);
            connection.connect();
            JSONObject jsonParam = new JSONObject();

            jsonParam.put("name", "1sii");
            jsonParam.put("address", "1cartage");
            // jsonParam.put("title", "Pruebas desde cartagena");
            // jsonParam.put("body", "hola pruebas");
            printout = new OutputStreamWriter(connection.getOutputStream());
            printout.write(jsonParam.toString());
            printout.flush();
            printout.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(output);
                JSONObject jsonObject = (JSONObject) obj;

                String blog = (String) jsonObject.get("api_message");
                System.err.println(blog);
                System.out.println(output);
            }

            // System.out.println(url.getRef());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
