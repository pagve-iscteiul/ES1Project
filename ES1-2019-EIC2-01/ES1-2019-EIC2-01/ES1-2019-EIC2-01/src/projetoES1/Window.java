package projetoES1;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Sene Conté
 *
 *	Window controla e exibe na sua janela os botões (tags) 
 *	e os paineis associados a estes.
 * 	Cada painel tem a sua propria extrutura.
 * 	Tem um tamanho (por defeito) de 500*500 pixeis. 
 */

public class Window {
	
	 private JFrame frame;
	 private JPanel panel;
	 private JPanel tagPanel;
	 private Map<String,JPanel> selectablePanels = new HashMap<String, JPanel>();
	 private static Window INSTANCE;
	 
	 private static int width= 500;
	 private static int lenght = 500;
	 
	private Window() {
		INSTANCE = this;
	}
	
	
	/**
	 * 
	 * @return Acesso à instância Singleton do Window
	 */
	public static  Window getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Window();
		return INSTANCE;
	}

	private void init() {
			frame = new JFrame();
			panel = new JPanel();
			tagPanel = new JPanel();
			
			
			tagPanel.setLayout(new GridLayout(1,3));
			
			for(String s :selectablePanels.keySet()) {
				JButton button = new JButton(s);
				button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
							JPanel panel = selectablePanels.get(s);
							if(!panel.equals(null)) 
								changePanel(panel);		
					}
				});
				
				tagPanel.add(button);
			}
			
			panel.setLayout( new BorderLayout());
			panel.add(tagPanel,  BorderLayout.NORTH);
			//panel.add(selectTagPanel,BorderLayout.CENTER);
			
			frame.add(panel);
			
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			
		}
	 
	 private void changePanel(JPanel panel) {
		 this.panel.removeAll();
		 this.panel.add(tagPanel,BorderLayout.NORTH);
		 this.panel.add(panel,BorderLayout.CENTER);
		 this.panel.repaint();
		 this.panel.revalidate();
	 }
	 
	 
	 /**
	  * Torna a janela visível
	  */
	 public void open() {
		 init();
		 frame.setSize(width, lenght);
		 frame.setResizable(false);
		 frame.setVisible(true);
	 }
	 
	 /**
	  * adiciona a janela mais um botão(Tag)
	  * @param name
	  * 		  nome do tag que irá ser mostrada na janela.	 
	  * @param panel
	  * 		  painel que irá ser aberto caso clicarem neste botão(Tag)
	  */
	 public void addTagPainel(String name,JPanel panel) {
		 if(panel != null) 
			 selectablePanels.put(name, panel);
	 }

}
