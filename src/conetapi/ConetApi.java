/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conetapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.ws.http.HTTPException;
import org.json.simple.JSONObject;

/**
 *
 * @author VBarrera
 */
public class ConetApi extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        try {

            URL url = new URL("http://crud.local/api/custom");
            OutputStreamWriter printout;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-Authorization-Token", "17ae938e232fd594e8bd0362f91916d3");
           connection.setRequestProperty("X-Authorization-Time", "2017-03-27");
            connection.setRequestProperty("Content-Type", "application/json");
            // System.out.println(connection.getResponseCode());
            
            
           // connection.setDoInput(true);
           // connection.setUseCaches (false);
            connection.connect();
            JSONObject jsonParam = new JSONObject();

            jsonParam.put("name", "109");
            jsonParam.put("address", "1019");
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
			System.out.println(output);
		}

            // System.out.println(url.getRef());
            System.out.println("" + connection.getResponseCode());
            System.out.println("" + connection.getResponseMessage());
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
