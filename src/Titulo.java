public record Titulo(String Title, String Year, String Runtime, String Rated, String Released, String Genre) {
    @Override
    public String toString() {
        return "Filme: " + Title + " (" + Year + ") - Duração: " + Runtime + " - Classificação: " + Rated + " - Lançamento: " + Released + " - Genero: " + Genre;

    }
}



