package com.example.guessnumber;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	TextView myTextView;
	TextView myCounterView;
	EditText myEditText;
	Button resetButton;
	Button guessButton;
	ArrayList<Integer> myNumber;
	int numGuesses;
	boolean hasWon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myTextView = (TextView) findViewById(R.id.myTextView);
		myCounterView = (TextView) findViewById(R.id.myCounterView);
		myEditText = (EditText) findViewById(R.id.myEditText);
		resetButton = (Button) findViewById(R.id.resetButton);
		guessButton = (Button) findViewById(R.id.guessButton);
		myNumber = new ArrayList<Integer>(); 
		this.init();
		
		resetButton.setOnClickListener(this);
		guessButton.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void init() {
		numGuesses = 0;
		hasWon = false;
		myNumber.clear();
		Random rand = new Random();
		for (int i = 0; i < 4; i++) {
			while (true) {
				boolean equal = false;
				int randNum = rand.nextInt(10);
				for (int j = 0; j < i; j++) {
					if (myNumber.get(j) == randNum) {
						equal = true;
						break;
					}
				}
				if (!equal) {
					myNumber.add(randNum);
					break;
				}
			}
		}
		myCounterView.setText(Integer.toString(numGuesses));
		myTextView.setText("Welcome! Please input a four-digit number with different digits to take a guess.");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.resetButton:
			this.init();
			break;
		case R.id.guessButton:
			if (!hasWon) {
				takeOneGuess();
			}
			break;
		}
		
	}
	private void takeOneGuess() {
		numGuesses++;
		myCounterView.setText(Integer.toString(numGuesses));
		String guessString = myEditText.getText().toString();
		int guessNum = Integer.parseInt(guessString);
		int numCorrectPosition = 0;
		int numCorrectNumber = 0;
		if (!(guessNum >= 0 && guessNum <= 9999 && guessString.length() == 4)) {
			myTextView.setText("Invalid input. The number should be a four-digits Number");
			return;
		}
		ArrayList<Integer> guessArray = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			guessArray.add((guessNum / ((int) Math.pow(10, 3 - i))) % 10);
			for (int j = 0; j < i; j++) {
				if (guessArray.get(j) == guessArray.get(i)) {
					myTextView.setText("Invalid input. The four digits may not be the same");
					return;
				}
			}
		}
		//myTextView.setText("You just punched in" + (int) Math.pow(10, 3) + "\n" + guessArray.get(0) + " " + guessArray.get(1) + " " + guessArray.get(2) + " " + guessArray.get(3));
		for (int i = 0; i < 4; i ++) {
			if (myNumber.get(i) == guessArray.get(i)) {
				numCorrectPosition++;
				continue;
			}
			
			for (int j = 0; j < 4; j++) {
				if (i == j) {
					continue;
				}
				if (myNumber.get(j) == guessArray.get(i)) {
					numCorrectNumber++;
					break;
				}
			}
		}
		myEditText.setText("");
		if (numCorrectPosition == 4) {
			hasWon = true;
			myTextView.setText("Congratulations!\nYou won the game with " 
					+ numGuesses 
					+ " guesses!\n"
					+ "Touch \"Restart\" to play a new round");
		} else {
			myTextView.setText(guessString
					+ ": "
					+ numCorrectPosition
					+ "A"
					+ numCorrectNumber
					+ "B\n"
					+ "You got "
					+ numCorrectPosition
					+ " digits in correct position and "
					+ numCorrectNumber
					+ " with wrong position but correct number.\n");
		}
	}
}
