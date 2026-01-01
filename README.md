# UniStressChecker 🧠💓

## 🚀 Overview
UniStressChecker is a Java console application designed to help university students track and analyze their stress levels.  
It uses questionnaires, heart rate input, mood tracking, and lifestyle factors to calculate a stress score, provide suggestions, and visualize trends.

---

## 🛠 Features
- **Stress Analysis**: Calculates stress scores based on questionnaire answers.
- **Personalized Suggestions**: Offers tips depending on stress level (low, medium, high).
- **File Handling**: Saves student stress data to `student_stress_data.txt`.
- **View Results**: Retrieve past records by Student ID.
- **Positive Habits**: Suggests healthy lifestyle practices.
- **Calendar View**: Displays monthly stress logs with emoji indicators.
- **Stress Trends**: Shows weekly averages and peak stress days.

---

## 📂 Project Structure
- `StressTest` (abstract class) → defines stress calculation and suggestions.
- `Student` (class) → encapsulates student data.
- `UniStudentStressTest` (class) → implements stress score calculation.
- `FileHandler` (class) → handles saving and loading records.
- `PositiveHabits` (class) → suggests healthy habits.
- `CalendarView` (class) → monthly stress visualization.
- `StressTrends` (class) → weekly stress trends.
- `UniStressChecker` (main class) → runs the app.

---

## ⚙️ Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/unistresschecker.git
   cd unistresschecker
