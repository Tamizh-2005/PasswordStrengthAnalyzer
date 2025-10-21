import java.util.*;
import java.util.regex.Pattern;

public class PasswordStrengthAnalyzerr {
    
    // Common passwords database
    private static final Set<String> COMMON_PASSWORDS = new HashSet<>(Arrays.asList(
        "password", "123456", "123456789", "12345678", "12345", "1234567", "password1",
        "123123", "1234567890", "000000", "abc123", "password123", "qwerty", "qwerty123",
        "111111", "123321", "1234", "dragon", "1q2w3e4r", "sunshine", "654321", "master",
        "hello", "freedom", "whatever", "qazwsx", "trustno1", "jordan23", "harley",
        "password!", "letmein", "welcome", "monkey", "1qaz2wsx", "admin", "iloveyou",
        "mobile", "login", "passw0rd", "starwars", "batman", "football", "soccer"
    ));
    
    // Analysis result class
    static class AnalysisResult {
        String password;
        int score;
        String verdict;
        double entropy;
        int length;
        boolean hasUppercase;
        boolean hasLowercase;
        boolean hasDigits;
        boolean hasSpecialChars;
        boolean hasSequentialChars;
        boolean hasRepeatedChars;
        boolean isCommonPassword;
        int uppercaseCount;
        int lowercaseCount;
        int digitCount;
        int specialCharCount;
        List<String> suggestions;
        
        public AnalysisResult() {
            this.suggestions = new ArrayList<>();
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueAnalyzing = true;
        
        printWelcomeMessage();
        
        while (continueAnalyzing) {
            System.out.print("\nEnter password to analyze (or 'exit' to quit): ");
            String password = scanner.nextLine();
            
            if (password.equalsIgnoreCase("exit")) {
                continueAnalyzing = false;
                System.out.println("\n✓ Thank you for using Password Strength Analyzer!");
                continue;
            }
            
            if (password.isEmpty()) {
                System.out.println("⚠ Please enter a password.");
                continue;
            }
            
            // Analyze password
            AnalysisResult result = analyzePassword(password);
            
            // Display results
            displayResults(result);
            
            System.out.print("\nAnalyze another password? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (!choice.equals("y") && !choice.equals("yes")) {
                continueAnalyzing = false;
                System.out.println("\n✓ Thank you for using Password Strength Analyzer!");
            }
        }
        
        scanner.close();
    }
    
    private static void printWelcomeMessage() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("           PASSWORD STRENGTH ANALYZER");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Analyze your password security with comprehensive metrics");
        System.out.println("═══════════════════════════════════════════════════════");
    }
    
    private static AnalysisResult analyzePassword(String password) {
        AnalysisResult result = new AnalysisResult();
        result.password = password;
        result.length = password.length();
        
        // Calculate entropy
        result.entropy = calculateEntropy(password);
        
        // Analyze character diversity
        analyzeCharacterDiversity(password, result);
        
        // Pattern detection
        result.hasSequentialChars = hasSequentialChars(password);
        result.hasRepeatedChars = hasRepeatedChars(password);
        
        // Common password check
        result.isCommonPassword = isCommonPassword(password);
        
        // Calculate score
        result.score = calculateScore(result);
        
        // Determine verdict
        if (result.score >= 80) {
            result.verdict = "STRONG";
        } else if (result.score >= 50) {
            result.verdict = "MEDIUM";
        } else {
            result.verdict = "WEAK";
        }
        
        // Generate suggestions
        generateSuggestions(result);
        
        return result;
    }
    
    private static void analyzeCharacterDiversity(String password, AnalysisResult result) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.uppercaseCount++;
            } else if (Character.isLowerCase(c)) {
                result.lowercaseCount++;
            } else if (Character.isDigit(c)) {
                result.digitCount++;
            } else {
                result.specialCharCount++;
            }
        }
        
        result.hasUppercase = result.uppercaseCount > 0;
        result.hasLowercase = result.lowercaseCount > 0;
        result.hasDigits = result.digitCount > 0;
        result.hasSpecialChars = result.specialCharCount > 0;
    }
    
    private static double calculateEntropy(String password) {
        if (password.isEmpty()) return 0.0;
        
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c : password.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        
        double entropy = 0.0;
        int length = password.length();
        
        for (int count : frequency.values()) {
            double probability = (double) count / length;
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }
        
        return Math.round(entropy * length * 100.0) / 100.0;
    }
    
    private static boolean hasSequentialChars(String password) {
        String lower = password.toLowerCase();
        
        // Check for sequential letters (abc, xyz)
        for (int i = 0; i < lower.length() - 2; i++) {
            char c1 = lower.charAt(i);
            char c2 = lower.charAt(i + 1);
            char c3 = lower.charAt(i + 2);
            
            if (c2 == c1 + 1 && c3 == c2 + 1) {
                return true;
            }
            if (c2 == c1 - 1 && c3 == c2 - 1) {
                return true;
            }
        }
        
        // Check for sequential numbers (123, 456)
        for (int i = 0; i < password.length() - 2; i++) {
            if (Character.isDigit(password.charAt(i)) &&
                Character.isDigit(password.charAt(i + 1)) &&
                Character.isDigit(password.charAt(i + 2))) {
                
                int d1 = Character.getNumericValue(password.charAt(i));
                int d2 = Character.getNumericValue(password.charAt(i + 1));
                int d3 = Character.getNumericValue(password.charAt(i + 2));
                
                if (d2 == d1 + 1 && d3 == d2 + 1) {
                    return true;
                }
                if (d2 == d1 - 1 && d3 == d2 - 1) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private static boolean hasRepeatedChars(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            if (password.charAt(i) == password.charAt(i + 1) &&
                password.charAt(i) == password.charAt(i + 2)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean isCommonPassword(String password) {
        return COMMON_PASSWORDS.contains(password.toLowerCase());
    }
    
    private static int calculateScore(AnalysisResult result) {
        int score = 0;
        
        // Length scoring (max 30 points)
        if (result.length >= 16) score += 30;
        else if (result.length >= 12) score += 25;
        else if (result.length >= 8) score += 15;
        else if (result.length >= 6) score += 5;
        
        // Character diversity (max 40 points)
        if (result.hasLowercase) score += 10;
        if (result.hasUppercase) score += 10;
        if (result.hasDigits) score += 10;
        if (result.hasSpecialChars) score += 10;
        
        // Entropy bonus (max 20 points)
        if (result.entropy > 60) score += 20;
        else if (result.entropy > 40) score += 15;
        else if (result.entropy > 20) score += 10;
        else if (result.entropy > 10) score += 5;
        
        // Penalties
        if (result.isCommonPassword) score -= 50;
        if (result.hasSequentialChars) score -= 10;
        if (result.hasRepeatedChars) score -= 10;
        
        // Variety bonus (max 10 points)
        int varietyCount = 0;
        if (result.hasLowercase) varietyCount++;
        if (result.hasUppercase) varietyCount++;
        if (result.hasDigits) varietyCount++;
        if (result.hasSpecialChars) varietyCount++;
        
        if (varietyCount == 4) score += 10;
        else if (varietyCount == 3) score += 5;
        
        return Math.max(0, Math.min(100, score));
    }
    
    private static void generateSuggestions(AnalysisResult result) {
        if (result.score >= 80) {
            result.suggestions.add("✓ Excellent! Your password is strong.");
            return;
        }
        
        if (result.isCommonPassword) {
            result.suggestions.add("⚠ This is a commonly used password. Choose something unique.");
        }
        
        if (result.length < 8) {
            result.suggestions.add("• Use at least 8 characters (12+ recommended)");
        } else if (result.length < 12) {
            result.suggestions.add("• Consider using 12 or more characters for better security");
        }
        
        if (!result.hasUppercase) {
            result.suggestions.add("• Add uppercase letters (A-Z)");
        }
        
        if (!result.hasLowercase) {
            result.suggestions.add("• Add lowercase letters (a-z)");
        }
        
        if (!result.hasDigits) {
            result.suggestions.add("• Include numbers (0-9)");
        }
        
        if (!result.hasSpecialChars) {
            result.suggestions.add("• Add special characters (!@#$%^&*)");
        }
        
        if (result.hasSequentialChars) {
            result.suggestions.add("• Avoid sequential characters (abc, 123)");
        }
        
        if (result.hasRepeatedChars) {
            result.suggestions.add("• Avoid repeated characters (aaa, 111)");
        }
        
        if (result.entropy < 30) {
            result.suggestions.add("• Increase password randomness and variety");
        }
    }
    
    private static void displayResults(AnalysisResult result) {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║            PASSWORD ANALYSIS RESULTS                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        
        // Masked password
        String masked = maskPassword(result.password);
        System.out.println("\nPassword: " + masked);
        
        // Score and verdict
        String verdictColor = getVerdictDisplay(result.verdict);
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│  SCORE: " + result.score + "/100  " + verdictColor);
        System.out.println("└─────────────────────────────────────────────────────┘");
        
        // Progress bar
        displayProgressBar(result.score);
        
        // Detailed metrics
        System.out.println("\n┌─────────────────── METRICS ──────────────────────────┐");
        System.out.printf("│  Length:         %-3d characters                      │%n", result.length);
        System.out.printf("│  Entropy:        %.2f bits                           │%n", result.entropy);
        System.out.printf("│  Uppercase:      %-3d                                 │%n", result.uppercaseCount);
        System.out.printf("│  Lowercase:      %-3d                                 │%n", result.lowercaseCount);
        System.out.printf("│  Numbers:        %-3d                                 │%n", result.digitCount);
        System.out.printf("│  Special Chars:  %-3d                                 │%n", result.specialCharCount);
        System.out.println("└─────────────────────────────────────────────────────┘");
        
        // Security checks
        System.out.println("\n┌─────────────── SECURITY CHECKS ──────────────────────┐");
        displayCheck("Contains uppercase letters", result.hasUppercase);
        displayCheck("Contains lowercase letters", result.hasLowercase);
        displayCheck("Contains numbers", result.hasDigits);
        displayCheck("Contains special characters", result.hasSpecialChars);
        displayCheck("No sequential characters", !result.hasSequentialChars);
        displayCheck("No repeated characters", !result.hasRepeatedChars);
        displayCheck("Not a common password", !result.isCommonPassword);
        System.out.println("└─────────────────────────────────────────────────────┘");
        
        // Suggestions
        if (!result.suggestions.isEmpty()) {
            System.out.println("\n┌────────────── SUGGESTIONS ───────────────────────────┐");
            for (String suggestion : result.suggestions) {
                System.out.println("│  " + suggestion);
            }
            System.out.println("└─────────────────────────────────────────────────────┘");
        }
    }
    
    private static String maskPassword(String password) {
        if (password == null || password.length() <= 3) {
            return "*".repeat(password != null ? password.length() : 0);
        }
        return password.charAt(0) + "*".repeat(password.length() - 2) + password.charAt(password.length() - 1);
    }
    
    private static String getVerdictDisplay(String verdict) {
        switch (verdict) {
            case "STRONG":
                return "VERDICT: ✓ STRONG (Excellent security!)";
            case "MEDIUM":
                return "VERDICT: ⚠ MEDIUM (Good, but can improve)";
            case "WEAK":
                return "VERDICT: ✗ WEAK (Needs improvement!)";
            default:
                return "VERDICT: " + verdict;
        }
    }
    
    private static void displayProgressBar(int score) {
        int barLength = 50;
        int filled = (int) ((score / 100.0) * barLength);
        
        System.out.print("\n[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                System.out.print("█");
            } else {
                System.out.print("░");
            }
        }
        System.out.println("] " + score + "%");
    }
    
    private static void displayCheck(String label, boolean passed) {
        String icon = passed ? "✓" : "✗";
        String status = passed ? "PASS" : "FAIL";
        System.out.printf("│  %s %-38s [%s] │%n", icon, label, status);
    }
}