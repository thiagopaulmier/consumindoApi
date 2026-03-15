import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MeuTesteApi {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner scanner = new Scanner(System.in);
        String busca = "";
        // 1. Criamos a lista que guardará os objetos do tipo Titulo
        List<Titulo> listaDeFilmes = new ArrayList<>();

        // Configurando o Gson com Pretty Printing
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        while (!busca.equalsIgnoreCase("sair")) {
            System.out.println("Digite o nome do filme (ou 'sair' para encerrar):");
            busca = scanner.nextLine();

            if (busca.equalsIgnoreCase("sair")) {
                break; // Sai do laço imediatamente
            }

            String chave = "b195bb55";
            String endereco = "http://www.omdbapi.com/?t=" +
                    URLEncoder.encode(busca, StandardCharsets.UTF_8) + "&apikey=" + chave;

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Transformando o JSON da resposta em objeto Java
                Titulo meuTitulo = gson.fromJson(response.body(), Titulo.class);

                // 2. Adicionando o filme encontrado à nossa lista
                listaDeFilmes.add(meuTitulo);
                System.out.println("Filme adicionado à lista!");

            } catch (Exception e) {
                System.out.println("Erro ao buscar filme: " + e.getMessage());
            }
        }

        // 3. Ao sair do loop, exibimos a lista completa no console
        System.out.println("\n--- LISTA FINAL DE FILMES ---");
        System.out.println(listaDeFilmes);

        // 4. Salvando a lista inteira como um JSON no arquivo
        try (FileWriter arquivo = new FileWriter("meus_filmes.json")) {
            arquivo.write(gson.toJson(listaDeFilmes));
            System.out.println("\nArquivo JSON gerado com sucesso!");
        }

        System.out.println("Programa encerrado.");
    }
}