package testing;

import static org.junit.jupiter.api.Assertions.*;
import calculator.Operations;

import org.junit.jupiter.api.Test;

class OperationsTest {

	@Test
	void testAll() {
		String op = Operations.ADD.toString();
		assertEquals( "+", op);
		
		op = Operations.SUBTRACT.toString();
		assertEquals( "-", op);
		
		op = Operations.MULTIPLY.toString();
		assertEquals( "x", op);
		
		op = Operations.DIVIDE.toString();
		assertEquals( "\u00f7", op);
		
		op = Operations.MEDIANT.toString();
		assertEquals( "Med.", op);
		
		op = Operations.POWER.toString();
		assertEquals( "^", op);
	}

}
