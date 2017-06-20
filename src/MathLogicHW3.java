import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MathLogicHW3 {
    public static void main(String[] args) {
        Path inputFile;
        Path outputFile;
        if (args.length == 2) {
            inputFile = Paths.get(args[0]);
            outputFile = Paths.get(args[1]);
        } else {
            inputFile = Paths.get("test.in");
            outputFile = Paths.get("test.out");
        }
        long time = System.currentTimeMillis();

        try (BufferedReader reader = Files.newBufferedReader(inputFile);
             BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            String[] str = reader.readLine().split(" ");
            if (str.length < 2) {
                System.out.println("Incorrect input : expected two numbers");
                return;
            }
            int a = Integer.parseInt(str[0]);
            int b = Integer.parseInt(str[1]);
            if (a < 0 || a > 100 || b < 0 || b > 100) {
                System.out.println("Incorrect input : expected two numbers in range [0..100]");
                return;
            }

            Prover prover = new Prover();
            List<String> result;
            if (a <= b) {
                result = prover.proveALessB(a, b);
            } else {
                result = prover.proveBLessA(a, b);
            }

            for (String s : result) {
                writer.write(s+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("----TIME : " + (System.currentTimeMillis() - time) + "ms");
        }
    }
}
