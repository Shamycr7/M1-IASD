import javax.swing.*;
 
public class JListExemple extends JFrame 
{
    private JList<String> langages;
  
    public JListExemple() 
    {
        //créer le modèle et ajouter des éléments
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("Java");
        model.addElement("PHP");
        model.addElement("Python");
        model.addElement("C++");
        model.addElement("Perl");
        model.addElement("Pascal");
        model.addElement("Ruby");
 
        //créer la liste des langages
        langages = new JList<>(model);
        add(langages);
         
        this.setTitle("Exemple de JList");  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        this.setSize(200,200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
     
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                new JListExemple();
            }
        });
    }       
}
