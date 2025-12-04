package com.example.labassessment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText inputPrice;
    private TextInputEditText inputDownPayment;
    private TextInputEditText inputLoanPeriod;
    private TextInputEditText inputInterestRate;
    private TextView outputLoanAmount;
    private TextView outputTotalInterest;
    private TextView outputTotalPayment;
    private TextView outputMonthlyPayment;
    private final DecimalFormat df = new DecimalFormat("0.00"); // Formatter for currency

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        inputPrice = findViewById(R.id.input_price);
        inputDownPayment = findViewById(R.id.input_down_payment);
        inputLoanPeriod = findViewById(R.id.input_loan_period);
        inputInterestRate = findViewById(R.id.input_interest_rate);
        outputLoanAmount = findViewById(R.id.output_loan_amount);
        outputTotalInterest = findViewById(R.id.output_total_interest);
        outputTotalPayment = findViewById(R.id.output_total_payment);
        outputMonthlyPayment = findViewById(R.id.output_monthly_payment);
        Button calculateButton = findViewById(R.id.button_calculate);
        Button resetButton = findViewById(R.id.button_reset);

        // Set Click Listeners
        calculateButton.setOnClickListener(v -> calculateLoan());
        resetButton.setOnClickListener(v -> resetFields());
    }

    private void calculateLoan() {
        String priceStr = inputPrice.getText().toString();
        String downPaymentStr = inputDownPayment.getText().toString();
        String periodStr = inputLoanPeriod.getText().toString();
        String interestRateStr = inputInterestRate.getText().toString();

        // Input Validation
        if (priceStr.isEmpty() || downPaymentStr.isEmpty() || periodStr.isEmpty() || interestRateStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double vehiclePrice = Double.parseDouble(priceStr);
            double downPayment = Double.parseDouble(downPaymentStr);
            double loanPeriodYears = Double.parseDouble(periodStr);
            double interestRate = Double.parseDouble(interestRateStr);

            // Step 1: Calculate Loan Amount
            double loanAmount = vehiclePrice - downPayment;

            // Step 2: Calculate Total Interest
            double totalInterest = loanAmount * (interestRate / 100.0) * loanPeriodYears;

            // Step 3: Calculate Total Payment
            double totalPayment = loanAmount + totalInterest;

            // Step 4: Calculate Monthly Payment
            double monthlyPayment = totalPayment / (loanPeriodYears * 12.0);

            // Display Results using string resources
            outputLoanAmount.setText(getString(R.string.loan_amount_rm, df.format(loanAmount)));
            outputTotalInterest.setText(getString(R.string.total_interest_rm, df.format(totalInterest)));
            outputTotalPayment.setText(getString(R.string.total_payment_rm, df.format(totalPayment)));
            outputMonthlyPayment.setText(getString(R.string.monthly_payment_rm, df.format(monthlyPayment)));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format. Check your inputs.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "An unexpected error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void resetFields() {
        inputPrice.setText("");
        inputDownPayment.setText("");
        inputLoanPeriod.setText("");
        inputInterestRate.setText("");
        outputLoanAmount.setText("Loan Amount: RM 0.00");
        outputTotalInterest.setText("Total Interest: RM 0.00");
        outputTotalPayment.setText("Total Payment: RM 0.00");
        outputMonthlyPayment.setText("Monthly Payment: RM 0.00");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}