/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.UniStressChecker;

/**
 *
 * @author user
 */
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

// Abstract Class (Abstraction)
abstract class StressTest {
    public abstract int calculateStressScore(int[] answers);

    public void displaySuggestions(int score) {
        System.out.println("=== Stress Analysis ===");
        if (score <= 10) {
            System.out.println("✅ Low stress: Keep up your healthy habits!");
            System.out.println("Tips: Maintain regular sleep, exercise, and social activities.");
        } else if (score <= 20) {
            System.out.println("⚠️ Medium stress: Take short breaks and manage workload.");
            System.out.println("Tips: Try mindfulness exercises, light workouts, and organize your tasks.");
        } else {
            System.out.println("🚨 High stress: Consider talking to counselors or support services.");
            System.out.println("Tips: Reduce workload, prioritize tasks, seek professional help, practice meditation.");
        }
    }
}

// Student Class (Encapsulation)
class Student {
    private String id;
    private String name;
    private int[] answers;
    private int heartRate;
    private String mood;
    private String stressFactor;
    private int sleepHours;

    public Student(String id, String name, int[] answers, int heartRate, String mood, String stressFactor, int sleepHours) {
        this.id = id;
        this.name = name;
        this.answers = answers;
        this.heartRate = heartRate;
        this.mood = mood;
        this.stressFactor = stressFactor;
        this.sleepHours = sleepHours;
    }

    public String getID() { return id; }
    public String getName() { return name; }
    public int[] getAnswers() { return answers; }
    public int getHeartRate() { return heartRate; }
    public String getMood() { return mood; }
    public String getStressFactor() { return stressFactor; }
    public int getSleepHours() { return sleepHours; }

    public void displayInfo() {
        System.out.println("🆔 Student ID: " + id + ", 👤 Name: " + name);
    }
}

// Subclass (Inheritance + Polymorphism)
class UniStudentStressTest extends StressTest {
    @Override
    public int calculateStressScore(int[] answers) {
        int total = 0;
        for (int a : answers) total += a;
        return total;
    }
}

// File Handling Class
class FileHandler {
    private static final String FILE_NAME = "student_stress_data.txt";

    public void saveToFile(Student student, int[] answers, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            writer.write(student.getID() + "," + student.getName() + "," + today);
            for (int ans : answers) writer.write("," + ans);
            writer.write("," + score + "," + student.getHeartRate() + "," + student.getMood() + "," + student.getStressFactor() + "," + student.getSleepHours());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void viewStudentResults(String studentID) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No records found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(studentID)) {
                    found = true;
                    System.out.println("📅 Date: " + parts[2]);
                    System.out.println("🆔 ID: " + parts[0] + ", 👤 Name: " + parts[1]);
                    System.out.print("📋 Answers: ");
                    for (int i = 3; i < parts.length - 5; i++) System.out.print(parts[i] + " ");
                    System.out.println("\n📊 Stress Score: " + parts[parts.length - 5]);
                    System.out.println("💓 Heart Rate: " + parts[parts.length - 4]);
                    System.out.println("🙂 Mood: " + parts[parts.length - 3]);
                    System.out.println("⚡ Stress Factor: " + parts[parts.length - 2]);
                    System.out.println("😴 Sleep Hours: " + parts[parts.length - 1]);
                    System.out.println("------------------------");
                }
            }
            if (!found) System.out.println("No records found for Student ID: " + studentID);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public List<String[]> loadRecords(String studentId) {
        List<String[]> records = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return records;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(studentId)) {
                    records.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading records: " + e.getMessage());
        }
        return records;
    }
}

// Positive Habits
class PositiveHabits {
    public void suggestHabits() {
        System.out.println("=== 🌱 Positive Habits ===");
        System.out.println("💪 Physical Health: Sleep well, exercise daily, eat balanced meals.");
        System.out.println("🧘 Mental Health: Practice mindfulness, meditation, journaling.");
        System.out.println("📅 Productivity: Use planners, break tasks, manage time effectively.");
        System.out.println("🤝 Social & Emotional: Connect with friends/family, share feelings, gratitude.");
    }
}

// Calendar View
class CalendarView {
    private FileHandler fileHandler;

    public CalendarView(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void showCalendar(String studentId, int year, int month) {
        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate firstDay = yearMonth.atDay(1);
            int lengthOfMonth = yearMonth.lengthOfMonth();

            List<String[]> records = fileHandler.loadRecords(studentId);
            Map<LocalDate, Integer> stressMap = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (String[] parts : records) {
                LocalDate date = LocalDate.parse(parts[2], formatter);
                if (date.getYear() == year && date.getMonthValue() == month) {
                    int score = Integer.parseInt(parts[parts.length - 5]);
                    stressMap.put(date, score);
                }
            }

            System.out.println("\n📅 Stress Calendar for " + yearMonth + " (Student: " + studentId + ")");
            System.out.println("Mo Tu We Th Fr Sa Su");

            int dayOfWeek = firstDay.getDayOfWeek().getValue();
            for (int i = 1; i < dayOfWeek; i++) System.out.print("   ");

            for (int day = 1; day <= lengthOfMonth; day++) {
                LocalDate current = yearMonth.atDay(day);
                if (stressMap.containsKey(current)) {
                    int score = stressMap.get(current);
                    if (score <= 10) System.out.print("🔵 ");
                    else if (score <= 20) System.out.print("🟡 ");
                    else System.out.print("🔴 ");
                } else {
                    System.out.printf("%2d ", day);
                }

                if (current.getDayOfWeek().getValue() == 7) System.out.println();
            }
            System.out.println();

            // Legend
            System.out.println("\nLegend:");
            System.out.println("🔵 = Low stress day (score ≤ 10)");
            System.out.println("🟡 = Medium stress day (11–20)");
            System.out.println("🔴 = High stress day (score > 20)");

        } catch (Exception e) {
            System.out.println("Error generating calendar: " + e.getMessage());
        }
    }
}

// UniStressChecker Trends
class StressTrends {
    private FileHandler fileHandler;

    public StressTrends(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void showTrends(String studentId) {
        try {
            List<String[]> records = fileHandler.loadRecords(studentId);
            if (records.isEmpty()) {
                System.out.println("No data for stress trends.");
                return;
            }

            Map<LocalDate, Integer> stressMap = new TreeMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (String[] parts : records) {
                LocalDate date = LocalDate.parse(parts[2], formatter);
                int score = Integer.parseInt(parts[parts.length - 5]);
                stressMap.put(date, score);
            }

            Map<Integer, List<Integer>> weeklyScores = new HashMap<>();
            for (Map.Entry<LocalDate, Integer> entry : stressMap.entrySet()) {
                int week = entry.getKey().get(WeekFields.ISO.weekOfYear());
                weeklyScores.computeIfAbsent(week, k -> new ArrayList<>()).add(entry.getValue());
            }

            System.out.println("\n📊 Weekly Average Stress Scores:");
            for (Map.Entry<Integer, List<Integer>> entry : weeklyScores.entrySet()) {
                double avg = entry.getValue().stream().mapToInt(i -> i).average().orElse(0);
                System.out.printf("Week %d → Average Stress: %.2f%n", entry.getKey(), avg);
            }

            LocalDate peakDay = Collections.max(stressMap.entrySet(), Map.Entry.comparingByValue()).getKey();
            int peakScore = stressMap.get(peakDay);
            System.out.println("\n🚨 Peak Stress Day: " + peakDay + " → Stress Score: " + peakScore);

        } catch (Exception e) {
            System.out.println("Error analyzing stress trends: " + e.getMessage());
        }
    }
}

// Main App
public class UniStressChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileHandler fileHandler = new FileHandler();
        StressTest stressTest = new UniStudentStressTest();
        PositiveHabits positiveHabits = new PositiveHabits();
        CalendarView calendarView = new CalendarView(fileHandler);
        StressTrends stressTrends = new StressTrends(fileHandler);

        String[] questions = {
            "📘 How stressed are you about your academic workload?",
            "😟 How often do you feel anxious or nervous?",
            "🛏️ How bad do you sleep at night?",
            "💭 How often do you feel overwhelmed by personal issues?",
            "🙂 How dissatisfied are you with your daily routine?"
        };

        while (true) {
            try {
                System.out.println("\n📌 Stress Checker App");
                System.out.println("1. Check Stress Level");
                System.out.println("2. View Previous Results");
                System.out.println("3. Positive Habits");
                System.out.println("4. View Stress Trends");
                System.out.println("5. View Calendar Monthly Stress Log");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number (1–6).");
                    scanner.nextLine();
                    continue;
                }
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        String id = scanner.nextLine().trim();
                        System.out.print("Enter Student Name: ");
                        String name = scanner.nextLine().trim();

                        System.out.println("\n📊 Answer the following questions on a scale of 1 to 5:");
                        System.out.println(" 1 = Low, 5 = High\n");

                        int[] answers = new int[questions.length];
                        for (int i = 0; i < questions.length; i++) {
                            while (true) {
                                System.out.println("Q" + (i + 1) + ": " + questions[i]);
                                if (scanner.hasNextInt()) {
                                    int response = scanner.nextInt();
                                    if (response >= 1 && response <= 5) {
                                        answers[i] = response;
                                        break;
                                    } else {
                                        System.out.println("Please enter a number between 1 and 5.");
                                    }
                                } else {
                                    System.out.println("Invalid input. Enter a number between 1 and 5.");
                                    scanner.next();
                                }
                            }
                        }
                        scanner.nextLine();

                        int heartRate = 0;
                        while (true) {
                            System.out.print("💓 Enter your heart rate: ");
                            if (scanner.hasNextInt()) {
                                heartRate = scanner.nextInt();
                                scanner.nextLine();
                                if (heartRate > 0) break;
                                else System.out.println("Heart rate must be positive.");
                            } else {
                                System.out.println("Invalid input. Please enter a valid number.");
                                scanner.next();
                            }
                        }

                        System.out.println("🙂 How are you feeling today? 😍 Great | 🙂 Good | 😐 Normal | 😟 Not great | 😢 Bad");
                        String mood = scanner.nextLine().trim();

                        System.out.println("⚡ What makes you feel this way?");
                        System.out.println("💖 Relationship | 🎓 School | 🏡 Family | 🤝 Friends | ☀️ Weather | ⚽ Sports | 💰 Finances | 💼 Work | 🏥 Health | 😴 Sleep");
                        String stressFactor = scanner.nextLine().trim();

                        int sleepHours = 0;
                        while (true) {
                            System.out.print("😴 How many hours did you sleep last night? ");
                            if (scanner.hasNextInt()) {
                                sleepHours = scanner.nextInt();
                                scanner.nextLine();
                                if (sleepHours >= 0 && sleepHours <= 24) break;
                                else System.out.println("Enter a valid number of hours (0–24).");
                            } else {
                                System.out.println("Invalid input. Please enter a number.");
                                scanner.next();
                            }
                        }

                        Student student = new Student(id, name, answers, heartRate, mood, stressFactor, sleepHours);
                        int score = stressTest.calculateStressScore(student.getAnswers());

                        student.displayInfo();
                        System.out.println("📊 Current Stress Score: " + score);
                        stressTest.displaySuggestions(score);
                        fileHandler.saveToFile(student, answers, score);

                        if (heartRate > 100 && score > 20)
                            System.out.println("⚠️ High heart rate with high stress detected!");
                        break;

                    case 2:
                        System.out.print("Enter your Student ID: ");
                        String studentId = scanner.nextLine().trim();
                        fileHandler.viewStudentResults(studentId);
                        break;

                    case 3:
                        positiveHabits.suggestHabits();
                        break;

                    case 4:
                        System.out.print("Enter Student ID: ");
                        String trendId = scanner.nextLine().trim();
                        stressTrends.showTrends(trendId);
                        break;

                    case 5:
                        System.out.print("Enter Student ID: ");
                        String calendarId = scanner.nextLine().trim();
                        int year = 0, month = 0;
                        while (true) {
                            System.out.print("Enter year (e.g: 2025): ");
                            if (scanner.hasNextInt()) {
                                year = scanner.nextInt();
                                if (year >= 1900 && year <= 2100) break;
                                else System.out.println("Please enter a valid year (1900–2100).");
                            } else {
                                System.out.println("Invalid input. Please enter a year.");
                                scanner.next();
                            }
                        }
                        while (true) {
                            System.out.print("Enter month (1–12): ");
                            if (scanner.hasNextInt()) {
                                month = scanner.nextInt();
                                if (month >= 1 && month <= 12) break;
                                else System.out.println("Please enter a valid month (1–12).");
                            } else {
                                System.out.println("Invalid input. Please enter a number.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine();
                        calendarView.showCalendar(calendarId, year, month);
                        break;

                    case 6:
                        System.out.println("👋 Bye... Stay stress free!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice! Enter a number from 1 to 6.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}