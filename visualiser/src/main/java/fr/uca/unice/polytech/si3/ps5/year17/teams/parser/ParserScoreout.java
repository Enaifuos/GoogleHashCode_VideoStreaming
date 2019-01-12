
        package fr.uca.unice.polytech.si3.ps5.year17.teams.parser;

        import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.IOException;

public class ParserScoreout extends Parser{

    /*    Attributs    */
    private int strategy = 0;
    private int score;
    private String filename;

    /**
     * Constructeur
     */
    public ParserScoreout(String filename, int strategy) {
        this.filename = filename;
        this.strategy = strategy;
    }


    @Override
    public void execute() throws IOException {
        // TODO Auto-generated method stub
        FileReader reader = new FileReader(this.filename);
        BufferedReader buffer = new BufferedReader(reader);

        String[] data = nextLine(buffer);
        this.score = stringToInt(data[0]);
    }

    public int stringToInt(String str) {
        int res = 0;
        for (char c : str.toCharArray()) {
            res *= 10;
            res += ((int) c) - 48;
        }
        return res;
    }

    public int getScore() {
        return this.score;
    }


}