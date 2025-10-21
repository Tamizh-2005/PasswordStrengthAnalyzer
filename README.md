# 🔐 Password Strength Analyzer

![Java](https://img.shields.io/badge/Java-8%2B-orange?style=flat-square&logo=java)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
![Status](https://img.shields.io/badge/Status-Active-success?style=flat-square)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-blue?style=flat-square)

A comprehensive command-line password strength analyzer built with pure Java. Analyze password security with detailed metrics, entropy calculation, pattern detection, and personalized suggestions.

<div align="center">
  <img src="https://user-images.githubusercontent.com/placeholder/demo.gif" alt="Demo" width="700"/>
</div>

## ✨ Features

- 🎯 **Comprehensive Analysis** - Evaluates passwords using multiple security metrics
- 📊 **Entropy Calculation** - Shannon entropy formula for randomness measurement
- 🔍 **Pattern Detection** - Identifies sequential (abc, 123) and repeated (aaa, 111) patterns
- 🛡️ **Common Password Database** - Checks against 50+ frequently used passwords
- 📈 **Smart Scoring System** - 0-100 score with color-coded verdicts (Weak/Medium/Strong)
- 💡 **Personalized Suggestions** - Specific recommendations to improve password strength
- 🎨 **Beautiful ASCII UI** - Professional console interface with progress bars
- 🔒 **Privacy First** - No data storage, logging, or network transmission
- 🚀 **Zero Dependencies** - Pure Java with no external libraries required

## 📸 Screenshots

### Welcome Screen
```
═══════════════════════════════════════════════════════
           PASSWORD STRENGTH ANALYZER
═══════════════════════════════════════════════════════
Analyze your password security with comprehensive metrics
═══════════════════════════════════════════════════════
```

### Analysis Results
```
╔═══════════════════════════════════════════════════════╗
║            PASSWORD ANALYSIS RESULTS                  ║
╚═══════════════════════════════════════════════════════╝

Password: M*************!

┌─────────────────────────────────────────────────────┐
│  SCORE: 75/100  VERDICT: ⚠ MEDIUM (Good, but can improve)
└─────────────────────────────────────────────────────┘

[████████████████████████████████████████░░░░░░░░░░] 75%
```

## 🚀 Quick Start

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt with UTF-8 support

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/password-strength-analyzer.git
   cd password-strength-analyzer
   ```

2. **Compile the program**
   ```bash
   javac PasswordStrengthAnalyzer.java
   ```

3. **Run the analyzer**
   ```bash
   java PasswordStrengthAnalyzer
   ```

### Quick Example

```bash
# Analyze a password
Enter password to analyze (or 'exit' to quit): MyP@ssw0rd2024!

# View detailed results
# ... analysis output ...

# Test another or exit
Analyze another password? (y/n): n
```

## 📊 Scoring Algorithm

The analyzer evaluates passwords on a **0-100 scale** using multiple criteria:

### Score Breakdown

| Component | Max Points | Description |
|-----------|------------|-------------|
| **Length** | 30 | Password length (16+ chars = max points) |
| **Character Diversity** | 40 | Uppercase, lowercase, digits, special chars |
| **Entropy** | 20 | Randomness and unpredictability |
| **Variety Bonus** | 10 | Using all character types |
| **Penalties** | -70 | Common passwords, patterns, repetition |

### Verdict Categories

| Score | Verdict | Description |
|-------|---------|-------------|
| 80-100 | 🟢 **STRONG** | Excellent security - Keep using it! |
| 50-79 | 🟡 **MEDIUM** | Good but can be improved |
| 0-49 | 🔴 **WEAK** | High security risk - Change immediately |

### Entropy Formula

```
H = -Σ(p(x) × log₂(p(x))) × L

Where:
- H = Entropy in bits
- p(x) = Probability of character x appearing
- L = Total password length
```

## 📋 Analysis Metrics

The analyzer provides detailed information about:

| Metric | Description |
|--------|-------------|
| **Score** | Overall strength rating (0-100) |
| **Entropy** | Randomness measurement in bits |
| **Length** | Total character count |
| **Composition** | Count of uppercase, lowercase, digits, special chars |
| **Patterns** | Detection of sequential or repeated characters |
| **Common Check** | Comparison against known weak passwords |

## 💡 Usage Examples

### Example 1: Weak Password

**Input:** `password`

**Output:**
- Score: 0/100
- Verdict: ✗ WEAK
- Issues: Common password, no uppercase, no digits, no special chars

### Example 2: Medium Password

**Input:** `MyPassword123`

**Output:**
- Score: 55/100
- Verdict: ⚠ MEDIUM
- Strengths: Mixed case, contains numbers
- Suggestions: Add special characters, increase length

### Example 3: Strong Password

**Input:** `K9#mX$qL2@pR5&vN`

**Output:**
- Score: 95/100
- Verdict: ✓ STRONG
- Strengths: Excellent length, high entropy, all character types

## 🔧 Customization

### Modify Common Passwords

Edit the `COMMON_PASSWORDS` set:

```java
private static final Set<String> COMMON_PASSWORDS = new HashSet<>(Arrays.asList(
    "password", "123456", "your_password",
    // Add more...
));
```

### Adjust Scoring Weights

Modify point allocations in `calculateScore()`:

```java
// Increase length importance
if (result.length >= 16) score += 40;  // Default: 30
```

### Change Minimum Requirements

Update validation thresholds:

```java
if (result.length < 10) {  // Default: 8
    result.suggestions.add("• Use at least 10 characters");
}
```

## 🛠️ Technical Details

### Architecture

```
PasswordStrengthAnalyzer
├── Main Loop (User Interaction)
├── AnalysisResult Class (Data Model)
├── Analysis Engine
│   ├── Entropy Calculator
│   ├── Pattern Detector
│   ├── Common Password Checker
│   └── Score Calculator
└── Display System
    ├── Progress Bar Renderer
    ├── Results Formatter
    └── Suggestion Generator
```

### Pattern Detection

- **Sequential Characters:** abc, xyz, 123, 987
- **Repeated Characters:** aaa, 111, @@@
- **Common Passwords:** 50+ database entries

## 🧪 Test Cases

Test the analyzer with these passwords:

| Password | Expected Score | Expected Verdict |
|----------|---------------|------------------|
| `password` | 0-10 | Weak |
| `Password123` | 25-35 | Weak |
| `P@ssw0rd!` | 40-50 | Medium |
| `MySecure2024!` | 55-70 | Medium |
| `K9#mX$qL2@pR` | 75-85 | Strong |
| `aA1!bB2@cC3#dD4$` | 90-100 | Strong |

## 🐛 Troubleshooting

<details>
<summary><b>Issue: "javac: command not found"</b></summary>

**Solution:** Install JDK and add to PATH
```bash
# Check Java installation
java -version

# Install JDK from:
# - Oracle: https://www.oracle.com/java/technologies/downloads/
# - OpenJDK: https://adoptium.net/
```
</details>

<details>
<summary><b>Issue: Character Display Problems</b></summary>

**Solution:** Enable UTF-8 encoding

**Windows:**
```cmd
chcp 65001
java PasswordStrengthAnalyzer
```

**macOS/Linux:**
```bash
export LANG=en_US.UTF-8
java PasswordStrengthAnalyzer
```
</details>

<details>
<summary><b>Issue: "Could not find or load main class"</b></summary>

**Solution:** Compile before running
```bash
# Ensure you're in the correct directory
javac PasswordStrengthAnalyzer.java
java PasswordStrengthAnalyzer
```
</details>

## 🔒 Security & Privacy

- ✅ **No Data Storage** - Passwords are never saved or logged
- ✅ **Local Processing** - All analysis happens on your machine
- ✅ **No Network** - Zero internet connectivity required
- ✅ **Memory Only** - Passwords exist in RAM during analysis only
- ✅ **Open Source** - Full transparency of code and logic

**Best Practices:**
- Don't test real passwords you're currently using
- Clear terminal history after use
- Be aware of shoulder surfing
- Use for creating NEW passwords only

## 📚 Resources

### Recommended Password Practices
- Use unique passwords for each account
- Enable two-factor authentication (2FA)
- Use a password manager (Bitwarden, 1Password, LastPass)
- Minimum 12 characters, 16+ recommended
- Include all character types
- Avoid personal information

### Ideas for Contributions
- Add password generation feature
- Integrate Have I Been Pwned API
- Multi-language support
- GUI version using JavaFX
- Zxcvbn algorithm implementation
- Export results to file
- Batch password analysis

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@Tamizh-2005](https://github.com/Tamizh-2005)
- LinkedIn: [Tamilkumar P](https://www.linkedin.com/in/tamilkumar-p)
- Email: tamilkumarp2005@gmail.com

## ⭐ Show Your Support

Give a ⭐️ if this project helped you!

## 📈 Roadmap

- [ ] Password generation with custom rules
- [ ] Breach database integration
- [ ] GUI version
- [ ] Multi-language support
- [ ] Export to PDF/CSV
- [ ] Plugin system for custom validators
- [ ] REST API version


## 📊 Statistics

![GitHub stars](https://img.shields.io/github/stars/yourusername/password-strength-analyzer?style=social)
![GitHub forks](https://img.shields.io/github/forks/yourusername/password-strength-analyzer?style=social)
![GitHub issues](https://img.shields.io/github/issues/yourusername/password-strength-analyzer)
![GitHub pull requests](https://img.shields.io/github/issues-pr/yourusername/password-strength-analyzer)

---

<div align="center">
  <p>Made with ☕ and Java</p>
  <p>Building secure passwords, one analysis at a time.</p>
  
  **[⬆ Back to Top](#-password-strength-analyzer)**
</div>
