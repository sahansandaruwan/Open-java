# Grid Sensor Anomaly Detector & Local Minima Analysis

## Problem Overview
You are tasked with designing a spatial data processing system in Java that reads sensor measurements arranged in a 2D grid, identifies local anomaly points, and calculates a system status level. The solution must be structured using modular methods, control flow, and basic 1D/2D arrays.

---

## System Requirements & Task Breakdown

### 1. Input Reading and Validation Method: `readAndValidateGrid`
* **Task:** Prompt the user for grid dimensions—rows ($R$) and columns ($C$).
* **Validation:** 
  * Ensure $R \ge 3$ and $C \ge 3$.
  * If dimensions are invalid, print an error message and repeatedly prompt until valid inputs are provided.
* **Storage:** Read $R \times C$ integer values (representing sensor readings between $-100$ and $100$) from standard input and store them in a 2D integer array. Return the populated 2D array.

### 2. Local Minima Extraction Method: `findLocalMinima`
* **Definition:** A cell at index $(i, j)$ is a **Local Minimum** if its value is *strictly less than* all of its existing orthogonal neighbors (Up: $(i-1, j)$, Down: $(i+1, j)$, Left: $(i, j-1)$, Right: $(i, j+1)$).
  * *Boundary Rule:* Corner cells only have 2 neighbors; edge cells have 3 neighbors. You must handle boundary conditions dynamically without accessing out-of-bounds array indices.
* **Task:** Scan the 2D array, identify all local minima values, and return them as a single 1D integer array.
  * *Hint:* Count how many local minima exist first to size your 1D array, or track them carefully.

### 3. System Status Classifier Method: `evaluateGridStatus`
* **Task:** Accept the original 2D array and the 1D array of detected local minima, then return a status string (`CRITICAL`, `WARNING`, or `STABLE`) based on these evaluation rules:
  * Calculate the total average value of all elements across the entire 2D grid.
  * **CRITICAL:** If total local minima count $> 3$ **AND** the grid average value $< 0$.
  * **WARNING:** If total local minima count $> 0$ **OR** the grid average value $< 20$ (and it does not meet the `CRITICAL` criteria).
  * **STABLE:** In all other cases.

### 4. Program Execution: `main` Method
* Coordinate execution step-by-step:
  1. Call `readAndValidateGrid` to initialize sensor data.
  2. Pass the grid to `findLocalMinima` to retrieve the detected minima.
  3. Pass the grid and the minima array to `evaluateGridStatus` to obtain the system status.
  4. Print out the formatted grid, the array of local minima, and the final status report.

---

## Constraints & Scope
* **Allowed Language Features:** Primitive types (`int`, `double`, `boolean`), standard math operators, relational/logical operators, `Scanner` input, `if/else`, `switch`, `for`/`while` loops, static methods, and standard 1D/2D arrays.
* **Forbidden Features:** Do **not** use Java Collections (`ArrayList`, `HashMap`), external library functions (e.g., `Arrays.sort`), or custom Class/Object models beyond static methods inside your main class.

---

## Sample Test Case for Verification

### Example Input Grid (3 x 3):
```
 10   20   15
  5   30   25
 12    8   40
```

### Verification Analysis:
* **Cell $(1, 0)$ [Value = 5]:** Neighbors are Up `10`, Right `30`, Down `12`. Since $5 < 10$, $5 < 30$, and $5 < 12$, it is a **Local Minimum**.
* **Cell $(2, 1)$ [Value = 8]:** Neighbors are Left `12`, Up `30`, Right `40`. Since $8 < 12$, $8 < 30$, and $8 < 40$, it is a **Local Minimum**.

### Expected Outputs:
* **Local Minima Array:** `[5, 8]`
* **Grid Total Sum:** $10 + 20 + 15 + 5 + 30 + 25 + 12 + 8 + 40 = 165$
* **Grid Average:** $165 / 9 = 18.33$
* **Evaluated Status:** `WARNING` (Minima count $> 0$ and Average $< 20$