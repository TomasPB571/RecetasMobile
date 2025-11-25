package com.example.libroderecetaspro;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



//Conectamos con la api
public class ApiService {

    public static List<Receta> obtenerRecetasDesdeAPI() {
        List<Receta> lista = new ArrayList<>();

        try {
            URL url = new URL("https://www.themealdb.com/api/json/v1/1/search.php?s="); //URL del JSON
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder resultado = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                resultado.append(linea);
            }

            JSONObject json = new JSONObject(resultado.toString());
            JSONArray comidas = json.getJSONArray("meals");

            for (int i = 0; i < comidas.length() && i < 10; i++) {
                JSONObject comida = comidas.getJSONObject(i);
                String nombre = comida.getString("strMeal"); // Recorre las recetas del JSON hasta un máximo de 10  y extrae la data
                String categoriaAPI = comida.getString("strCategory");
                String instrucciones = comida.getString("strInstructions");

                // 🧠 Asignar tipo según nombre o categoría
                String tipo = determinarTipo(nombre, categoriaAPI);

                Receta receta = new Receta(nombre, tipo, instrucciones);
                lista.add(receta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 📌 Método auxiliar para decidir el tipo de comida
    private static String determinarTipo(String nombre, String categoria) {
        nombre = nombre.toLowerCase();
        categoria = categoria.toLowerCase();

        // 🔹 DESAYUNOS
        if (nombre.contains("egg") || nombre.contains("toast") || nombre.contains("omelette")
                || nombre.contains("migas") || nombre.contains("pancake")
                || nombre.contains("cereal") || nombre.contains("oat") || categoria.contains("breakfast")) {
            return "Desayuno";

            // 🔹 POSTRES
        } else if (nombre.contains("cake") || nombre.contains("dessert") || nombre.contains("pudding")
                || nombre.contains("pie") || nombre.contains("brownie") || nombre.contains("cookie")
                || nombre.contains("ice") || nombre.contains("sweet") || categoria.contains("dessert")) {
            return "Postre";

            // 🔹 ALMUERZOS / COMIDAS PRINCIPALES
        } else if (nombre.contains("burger") || nombre.contains("chicken") || nombre.contains("beef")
                || nombre.contains("pasta") || nombre.contains("curry") || nombre.contains("sushi")
                || nombre.contains("rice") || nombre.contains("fish") || nombre.contains("meat")
                || nombre.contains("salmon") || categoria.contains("main course") || categoria.contains("seafood")) {
            return "Almuerzo";

            // 🔹 Si no encaja en nada
        } else {
            return "Almuerzo";
        }
    }

}
