package test.java.ru.test.app;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.annotations.Parameter;

@RunWith(Parameterized.class)
public class TestTask {
	
	@Parameter("Operand1")
	private double operand1;
	
	@Parameter("Operand2")
	private double operand2;
	
	@Parameter("Operation")
	private char operation;
	
	@Parameter("Result")
	private double result;

	public TestTask (String op1, String op2, String op, String res){
		this.operand1 = Double.parseDouble(op1);
		this.operand2 = Double.parseDouble(op2);
		this.operation = op.toCharArray()[0];
		this.result = Double.parseDouble(res);
	}
	
	@Parameters
	public static Collection<Object[]> getTestData() throws FileNotFoundException{
		Scanner data = new Scanner(new File("testdata.txt"));
		int n = 0;
		while (data.hasNext()){
			n++;
		    data.nextLine();
		}
		data.close();
		data = new Scanner(new File("testdata.txt"));
		Object[][] res = new Object[n][4];
		for (int i=0; i<n; i++){
			String line = data.nextLine();
			Object[] singleData = line.split(";");
			res[i] = singleData;
		}
		data.close();
		return Arrays.asList(res);
	}

	@Title("Check expression")
	@Test
	public void test1() throws IOException {
		switch (operation){
		case '+':
			checkAdd(operand1, operand2, result);
			break;
		case '-': 
			checkSubtract(operand1, operand2, result);
			break;
		case '*': 
			checkMultiplying(operand1, operand2, result);
			break;
		case '/': 
			checkDivision(operand1, operand2, result);
			break;
		default:
			fail("Operation is not correct.");
		}		
	}
	
	@Title("Check add")
	@Step
	private static void checkAdd(double op1, double op2, double res) {
		assertTrue("Result is not correct: " + op1 + " + " + op2 + " not equals " + res, op1 + op2 == res);
	}
	
	@Title("Check subtract")
	@Step
	private static void checkSubtract(double op1, double op2, double res) {
		assertTrue("Result is not correct: " + op1 + " - " + op2 + " not equals " + res, op1 - op2 == res);
	}
	
	@Title("Check multiplying")
	@Step
	private static void checkMultiplying(double op1, double op2, double res) {
		assertTrue("Result is not correct: " + op1 + " * " + op2 + " not equals " + res, op1 * op2 == res);	
	}
	
	@Title("Check division")
	@Step
	private static void checkDivision(double op1, double op2, double res) {
		if (op2 == 0){
			fail("Denominator can not be null: " + op1 + " / " + op2);
		}
		else {
			assertTrue("Result is not correct: " + op1 + " / " + op2 + " not equals " + res, op1 / op2 == res);
		}
	}
}
