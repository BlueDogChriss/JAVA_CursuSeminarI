import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Program03_JSON {
    public static void main(String[] args) throws Exception {
        // 1. Citire lista de profesori din fișier JSON
        List<Profesor> profesori = new ArrayList<>();
        try (var fisier = new FileReader("date\\profesori.json")) {
            var jsonProfesori = new JSONArray(new JSONTokener(fisier));

            for (int index = 0; index < jsonProfesori.length(); index++) {
                var jsonProfesor = jsonProfesori.getJSONObject(index);
                var profesor = new Profesor(
                        jsonProfesor.getInt("idProfesor"),
                        jsonProfesor.getString("prenume"),
                        jsonProfesor.getString("nume"),
                        jsonProfesor.getString("departament"));
                profesori.add(profesor);
            }

            // Varianta 2: folosind stream
            /*
            profesori = StreamSupport.stream(jsonProfesori.spliterator(), false)
                    .map(item -> (JSONObject)item)
                    .map(item -> new Profesor(
                            item.getInt("idProfesor"),
                            item.getString("prenume"),
                            item.getString("nume"),
                            item.getString("departament")))
                    .collect(Collectors.toList());
             */
        }

        // 2. Statistici departamente
        Map<String, List<Profesor>> temp = profesori.stream()
                .collect(Collectors.groupingBy(Profesor::getDepartament));

        List<Departament> departamente = temp.entrySet().stream()
                .map(pereche -> new Departament(
                        pereche.getKey(),
                        pereche.getValue().size()))
                .sorted((d1, d2) -> -Integer.compare(
                        d1.getNumarProfesori(), d2.getNumarProfesori()))
                .collect(Collectors.toList());

        // 3. Salvare statistici în fișier JSON
        JSONArray jsonDepartamente = new JSONArray();
        for (var departament : departamente) {
            JSONObject jsonDepartament = new JSONObject();
            jsonDepartament.put("denumire", departament.getDenumire());
            jsonDepartament.put("numarProfesori", departament.getNumarProfesori());
            jsonDepartamente.put(jsonDepartament);
        }
        try (var fisier = new FileWriter("date\\departamente.json")){
            jsonDepartamente.write(fisier, 3, 0);
        }
    }
}

class Profesor {
    private final int idProfesor;
    private final String prenume;
    private final String nume;
    private final String departament;

    public Profesor(int idProfesor, String prenume, String nume, String departament) {
        this.idProfesor = idProfesor;
        this.prenume = prenume;
        this.nume = nume;
        if (departament.length() == 0) {
            this.departament = "[NECUNOSCUT]";
        } else {
            this.departament = departament;
        }
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNume() {
        return nume;
    }

    public String getDepartament() {
        return departament;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Profesor{");
        sb.append("idProfesor=").append(idProfesor);
        sb.append(", prenume='").append(prenume).append('\'');
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", departament='").append(departament).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

class Departament {
    String denumire;
    int numarProfesori;

    public Departament(String denumire, int numarProfesori) {
        this.denumire = denumire;
        this.numarProfesori = numarProfesori;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNumarProfesori() {
        return numarProfesori;
    }

    public void setNumarProfesori(int numarProfesori) {
        this.numarProfesori = numarProfesori;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Departament{");
        sb.append("denumire='").append(denumire).append('\'');
        sb.append(", numarProfesori=").append(numarProfesori);
        sb.append('}');
        return sb.toString();
    }
}