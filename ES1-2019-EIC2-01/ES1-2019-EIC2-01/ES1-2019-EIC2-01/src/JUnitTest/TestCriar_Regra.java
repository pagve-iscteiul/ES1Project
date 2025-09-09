package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projetoES1.Criar_Regra;
import projetoES1.Regra;

class TestCriar_Regra {
	
	Criar_Regra cr;

	@BeforeEach
	void setUp() throws Exception {
		cr = new Criar_Regra();
	}

	@Test
	void testFilter() {
		String filter = cr.filter("pedro");
		assertEquals("LAA", filter);
	}

	@Test
	void testGetName() {
		assertEquals("Criar Regra", cr.getName());
	}

	@Test
	void testGetPanel() {
		JPanel panel = cr.getPanel();
		assertEquals(panel, cr.getPanel());
	}

	@Test
	void testGetRuleName() {
		String rule_name = cr.getRuleName();
		assertEquals(rule_name, cr.getRuleName());
	}

	@Test
	void testGetInputValue() {
		String input = cr.getInputValue();
		assertEquals(input, cr.getInputValue());
	}

	@Test
	void testGetPanel1() {
		JPanel input_panel = cr.getPanel1();
		assertEquals(input_panel, cr.getPanel1());
	}

	@Test
	void testGetRegras_carregadas() {
		LinkedList<Regra> regras_carregadas = cr.getRegras_carregadas();
		assertEquals(regras_carregadas, cr.getRegras_carregadas());
	}

}
