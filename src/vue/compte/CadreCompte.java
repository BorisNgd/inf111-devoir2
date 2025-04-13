package vue.compte;

import baseDonnees.modeles.Transaction;
import baseDonnees.modeles.Utilisateur;
import modele.Banque;
import observer.MonObserver;
import vue.GestionnaireVue;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CadreCompte extends JFrame implements MonObserver {

    private GestionnaireVue gestionnaireVue;
    private Banque banque;
    private Utilisateur utilisateurConnecte;

    private JLabel imgLabel;
    private JLabel txtNomCompte;
    private JLabel txtNumeroCompte;
    private JLabel txtSoldeCompte;
    private JPanel panelGaucheHaut;
    private JPanel panelGaucheMilieu;
    private JPanel panelMilieuHaut;
    private JPanel panelMilieuBas;

    private  Vector<Transaction> donneees;
    String[] nomsColonnes = {"Source", "Destination", "Montant" , "Status"};
    private DefaultTableModel model = new DefaultTableModel(nomsColonnes , 0);
    private JTable tableOperations = new JTable(model);

    public CadreCompte(GestionnaireVue gestionnaireVue) {
        super("Mon compte");
        this.gestionnaireVue = gestionnaireVue;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(true);

        creationPanneaux();
        creationControles();

        gestionnaireVue.getBanque().attacherObserver(this);

    }

    private void creationControles() {

        //Panneau Gauche du haut
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/logo.png"));
        Image imageRedimensionnee = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon iconRedimensionne = new ImageIcon(imageRedimensionnee);
        imgLabel = new JLabel(iconRedimensionne);
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelGaucheHaut.add(imgLabel);

        //Panneau Gauche du milieu
        JButton btnNouvelleTransaction = new JButton("Nouvelle transaction");
        btnNouvelleTransaction.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnNouvelleTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransactionDialog(CadreCompte.this  , gestionnaireVue.getBanque()); //
                System.out.println(utilisateurConnecte.getNomUtilisateur());
            }
        });

        JButton btnDeconnexion = new JButton("Se déconnecter");
        btnDeconnexion.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDeconnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionnaireVue.getBanque().deconnecterUtilisateur();
                gestionnaireVue.activerModeConnexion();
            }
        });

        panelGaucheMilieu.setLayout(new BoxLayout(panelGaucheMilieu, BoxLayout.Y_AXIS));
        panelGaucheMilieu.add(btnNouvelleTransaction);
        panelGaucheMilieu.add(btnDeconnexion);

        //Panneau Milieu du Haut
        txtNomCompte = new JLabel("");
        txtNomCompte.setFont(new Font("SansSerif", Font.BOLD, 14));
        txtNomCompte.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        txtNumeroCompte = new JLabel("Numéro de compte: ");
        txtNumeroCompte.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        txtSoldeCompte = new JLabel("Solde: " + " $");
        txtSoldeCompte.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        panelMilieuHaut.setLayout(new BoxLayout(panelMilieuHaut, BoxLayout.Y_AXIS));
        panelMilieuHaut.add(txtNomCompte);
        panelMilieuHaut.add(Box.createVerticalStrut(5));
        panelMilieuHaut.add(txtNumeroCompte);
        panelMilieuHaut.add(Box.createVerticalStrut(5));
        panelMilieuHaut.add(txtSoldeCompte);


        //Panneau Milieu du Bas
        panelMilieuBas.setLayout(new BorderLayout());
        String[] colonnes = {"Source", "Destination", "Montant" , "Statut"};
        String[][] donnees = {
                {"Jean Dupont", "jean@example.com", "Actif"},
                {"Marie Curie", "marie@example.com", "Inactif"},
                {"Alan Turing", "alan@crypto.net", "Actif"}
        };

        tableOperations.setFillsViewportHeight(true); // remplit bien le scrollpane
        //tableOperations.setRowHeight(25); // taille des lignes

        JScrollPane scrollPane = new JScrollPane(tableOperations);
        panelMilieuBas.add(scrollPane,BorderLayout.CENTER);


    }

    private void creationPanneaux() {

        JPanel panelCentral = new JPanel(new BorderLayout());

        JPanel panelGauche = new JPanel(new GridLayout(3, 1, 0, 0));
        panelGauche.setPreferredSize(new Dimension(300, 0));
        panelGauche.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));

        panelGaucheHaut = new JPanel();
        panelGaucheHaut.setPreferredSize(new Dimension(300, 300));
        panelGaucheHaut.setBackground(new Color(121, 176, 136));
        panelGauche.add(panelGaucheHaut);

        panelGaucheMilieu = new JPanel();
        panelGaucheMilieu.setPreferredSize(new Dimension(300, 300));
        panelGaucheMilieu.setBackground(new Color(121, 176, 136));
        panelGauche.add(panelGaucheMilieu);

        JPanel panelGaucheBas = new JPanel();
        panelGaucheBas.setPreferredSize(new Dimension(300, 300));
        panelGaucheBas.setBackground(new Color(121, 176, 136));
        panelGauche.add(panelGaucheBas);

        JPanel panelMilieu = new JPanel(new BorderLayout());
        panelMilieu.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        panelMilieuHaut = new JPanel();
        panelMilieuHaut.setPreferredSize(new Dimension(0, 300));
        panelMilieuHaut.setBackground(new Color(121, 176, 136));

        panelMilieuBas = new JPanel();
        panelMilieuBas.setBackground(new Color(121, 176, 136));

        panelMilieu.add(panelMilieuHaut, BorderLayout.NORTH);
        panelMilieu.add(panelMilieuBas, BorderLayout.CENTER);

        panelCentral.add(panelGauche, BorderLayout.WEST);
        panelCentral.add(panelMilieu, BorderLayout.CENTER);

        setContentPane(panelCentral);

    }

    private void rechargerTable(Vector<Transaction> transactions) {
        model.setRowCount(0); // vide la table

        for (Transaction t : transactions) {
            Object[] ligne = {
                    t.getNoCompteSource(),
                    t.getNoCompteDestination(),
                    t.getMontant(),
                    t.getStatus()
            };
            model.addRow(ligne);
        }
    }

    @Override
    public void avertir() {

        utilisateurConnecte = gestionnaireVue.getBanque().getUtilisateurActif();
        if(utilisateurConnecte != null){
            txtNomCompte.setText(utilisateurConnecte.getNomUtilisateur());
            txtNumeroCompte.setText("Numéro de compte: " + utilisateurConnecte.getNumeroDeCompte());
            txtSoldeCompte.setText("Solde: " + String.valueOf(utilisateurConnecte.getSolde())+ " $");

            donneees = gestionnaireVue.getBanque().obtenirTransactionsPourCompte();
            rechargerTable(donneees);
        }
        System.out.println(utilisateurConnecte);
    }
}
