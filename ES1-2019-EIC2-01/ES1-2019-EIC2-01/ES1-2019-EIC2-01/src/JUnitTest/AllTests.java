package JUnitTest;

import org.junit.runner.RunWith;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.runner.JUnitPlatform;

@RunWith(JUnitPlatform.class)
@SelectClasses({TestRegra.class,TestJTableSample.class,Test_Leitura_Ficheiro.class, TestAvaliar_Defeitos.class, TestCriar_Regra.class})
public class AllTests {
	
}
