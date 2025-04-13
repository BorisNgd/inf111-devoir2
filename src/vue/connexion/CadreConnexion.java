package vue.connexion;

import baseDonnees.bases.Colonne;
import modele.Banque;
import vue.GestionnaireVue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadreConnexion extends JFrame  {

    private JButton btnConnexion;
    private JLabel txtNomUtilisateur;
    private JLabel txtMotdePasse;
    private JTextField inputNomUtilisateur;
    private JPasswordField inputMotdePasse;
    private JLabel txtMessageErreur;


    private static final int LARGEUR = 400;
    private static final int LONGUEUR = 200;

    private GestionnaireVue gestionnaireVue;
    private Banque banque;

    public CadreConnexion(GestionnaireVue gestionnaireVue){
        super("Connexion");
        this.gestionnaireVue = gestionnaireVue;
        banque = gestionnaireVue.getBanque();
        setSize(LARGEUR , LONGUEUR);;
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        creationChampsSaisie();
        creationBouton();
        creationPanel();

    }

    private void creationChampsSaisie() {
        final int LARGEUR_CHAMP = 10;
        txtNomUtilisateur = new JLabel("Nom d'utilisateur");
        txtNomUtilisateur.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtNomUtilisateur.setForeground(Color.WHITE);

        inputNomUtilisateur = new JTextField(LARGEUR_CHAMP);
        inputNomUtilisateur.setAlignmentX(Component.CENTER_ALIGNMENT);


        txtMotdePasse = new JLabel("Mot de passe");
        txtMotdePasse.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtMotdePasse.setForeground(Color.WHITE);

        inputMotdePasse = new JPasswordField(LARGEUR_CHAMP);
        inputMotdePasse.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtMessageErreur = new JLabel("Accès refusé");
        txtMessageErreur.setForeground(Color.RED);
        txtMessageErreur.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtMessageErreur.setVisible(false);
    }

    private void creationBouton() {
        btnConnexion = new JButton("Connexion");
        btnConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomUtilisateur = inputNomUtilisateur.getText();
                char[] motdePasse = inputMotdePasse.getPassword();

                if (nomUtilisateur.isEmpty()){
                    txtMessageErreur.setText("Le champs nom utilisateur est obligatoire");
                    txtMessageErreur.setVisible(true);
                    return;
                }

                if(motdePasse.length == 0){
                    txtMessageErreur.setText("Le champs mot de passe est obligatoire");
                    txtMessageErreur.setVisible(true);
                    return;
                }

                System.out.println(new String(motdePasse));
                if(banque.verifier(nomUtilisateur , new String (motdePasse))){
                    banque.setUtilisateurActif(nomUtilisateur);
                    txtMessageErreur.setVisible(false);
                    reinitialiserLesChamps();
                    gestionnaireVue.activerModeCompte();

                }else {
                    txtMessageErreur.setText("Accès refusé");
                    txtMessageErreur.setVisible(true);
                }

            }
        });
    }

    private void reinitialiserLesChamps() {
        inputMotdePasse.setText("");
        inputNomUtilisateur.setText("");
    }

    private void creationPanel(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));
        panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));
        panel.setBackground(Color.DARK_GRAY);

        panel.add(txtNomUtilisateur);
        panel.add(Box.createVerticalStrut(5));
        panel.add(inputNomUtilisateur);
        panel.add(Box.createVerticalStrut(5));
        panel.add(txtMotdePasse);
        panel.add(Box.createVerticalStrut(5));
        panel.add(inputMotdePasse);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnConnexion);
        panel.add(Box.createVerticalStrut(5));
        panel.add(txtMessageErreur);
        add(panel);
    }
}
