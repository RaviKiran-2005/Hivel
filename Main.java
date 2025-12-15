import java.util.*;
import java.util.regex.*;

public class Main {

    // Convert value from given base to decimal
    static long convertToDecimal(String value, int base) {
        return Long.parseLong(value, base);
    }

    // Lagrange interpolation to compute f(0)
    static long findConstant(List<Integer> x, List<Long> y, int k) {
        double result = 0.0;

        for (int i = 0; i < k; i++) {
            double term = y.get(i);
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    term *= (-x.get(j)) / (double)(x.get(i) - x.get(j));
                }
            }
            result += term;
        }
        return Math.round(result);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        // Read entire input
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.trim().isEmpty()) break;
            sb.append(line);
        }

        String input = sb.toString();

        // -------- Extract k --------
        Pattern kPattern = Pattern.compile("\"k\"\\s*:\\s*(\\d+)");
        Matcher kMatcher = kPattern.matcher(input);

        if (!kMatcher.find()) {
            System.out.println("Invalid input");
            return;
        }
        int k = Integer.parseInt(kMatcher.group(1));

        List<Integer> x = new ArrayList<>();
        List<Long> y = new ArrayList<>();

        // -------- Extract points --------
        Pattern pointPattern = Pattern.compile(
            "\"(\\d+)\"\\s*:\\s*\\{[^}]*\"base\"\\s*:\\s*\"?(\\d+)\"?[^}]*\"value\"\\s*:\\s*\"([^\"]+)\""
        );

        Matcher m = pointPattern.matcher(input);

        while (m.find()) {
            int xi = Integer.parseInt(m.group(1));
            int base = Integer.parseInt(m.group(2));
            String value = m.group(3);

            x.add(xi);
            y.add(convertToDecimal(value, base));
        }

        if (x.size() < k) {
            System.out.println("Invalid input");
            return;
        }

        long constant = findConstant(x, y, k);
        System.out.println(constant);
    }
}
