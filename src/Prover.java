import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prover {

    String constructNumber(int a) {
        StringBuilder result = new StringBuilder();
        result.append("0");
        for (int i = 0; i < a; ++i) {
            result.append("'");
        }
        return result.toString();
    }

    List<String> proveALessB(int a, int b) {
        List<String> result = new ArrayList<>();
        int c = b - a;
        String A = constructNumber(a);
        String B = constructNumber(b);
        String C = constructNumber(c);

        result.add("|-?p(" + A + "+p)=" + B);

        try (BufferedReader reader = Files.newBufferedReader(Paths.get("proofs/lessProof1.in"))) {
            result.addAll(reader.lines().collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.add("@a(a+0=a)->" + A + "+0=" + A);
        result.add(A + "+0=" + A);
        List<String> buffer = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("proofs/lessProof2.in"))) {
            buffer.addAll(reader.lines().map((String s) -> s.replace("A", A)).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String incs = "";
        for (int i = 0; i < c; ++i) {
            String finalIncs = incs;
            result.addAll(buffer.stream().map((String s) -> s.replace("#", finalIncs))
                    .collect(Collectors.toList()));
            incs += "'";
        }
        result.add("(" + A + "+" + C + ")=" + B + "->?p(" + A + "+p)=" + B);
        result.add("?p(" + A + "+p)=" + B);

        return result;
    }

    List<String> proveBLessA(int a, int b) {
        List<String> result = new ArrayList<>();
        int c = a - b;
        String A = constructNumber(a);
        String B = constructNumber(b);
        String C = constructNumber(c);

        result.add("|-!?p(" + A + "+p)=" + B);

        try (BufferedReader reader = Files.newBufferedReader(Paths.get("proofs/greaterProof1.in"))) {
            result.addAll(reader.lines().collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        B = B.substring(1);
        C = C.substring(1, C.length() - 1);

        try (BufferedReader reader = Files.newBufferedReader(Paths.get("proofs/greaterProof2.in"))) {
            String finalB = B;
            String finalC = C;
            result.addAll(reader.lines().map((String s) -> s.replace("B", finalB).replace("C", finalC))
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
