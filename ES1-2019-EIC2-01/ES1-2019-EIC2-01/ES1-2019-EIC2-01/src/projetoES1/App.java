package projetoES1;
import java.io.File;

public class App {
	
	public static File file;
	
	
	public App() {
		Leitura_Ficheiro Leitura_Ficheiro = new Leitura_Ficheiro();
		Avaliar_Defeitos av = new Avaliar_Defeitos();
		Criar_Regra c = new Criar_Regra();
		av.setRegras(c.getRegras_carregadas());
		c.addObserver(av.getJTableSample(0));
		c.addObserver(av.getJTableSample(1));
		Window.getInstance().addTagPainel(Leitura_Ficheiro.getName(), Leitura_Ficheiro.getPanel());
		Window.getInstance().addTagPainel(av.getName(), av.getPanel());
		Window.getInstance().addTagPainel(c.getName(), c.getPanel());
	}
	
	private void start() {
		Window.getInstance().open();
	}
	
	
	 public static void main(String[] args) {
		App app = new App();
		app.start();
	}
	 
}
