package vue.compte;

import baseDonnees.modeles.Transaction;
import baseDonnees.modeles.Utilisateur;
import modele.Banque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionDialog extends JDialog {

    private Utilisateur utilisateurConnectee;
    private static final int LARGEUR_CHAMP = 10;

    public TransactionDialog(JFrame parent, Banque banque) {

        super(parent, "Nouvelle transaction", true);
        utilisateurConnectee = banque.getUtilisateurActif();
        setSize(400, 200);
        setResizable(false);
        setLocationRelativeTo(parent);
        //setLayout(new GridLayout(4, 2, 10, 10));

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Champs de saisie
        JTextField inputNumeroCompte = new JTextField(LARGEUR_CHAMP);
        inputNumeroCompte.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField inputMontant = new JTextField(LARGEUR_CHAMP);
        inputMontant.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Label
        JLabel txtNumeroCompte = new JLabel("Compte de destination");
        txtNumeroCompte.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel txtMontant = new JLabel("Montant");
        txtMontant.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel txtErreurMessage = new JLabel();
        txtErreurMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Bouton
        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btnConfirmer = new JButton("Confirmer");
        btnConfirmer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajout composants au panel
        panel.add(txtNumeroCompte);
        panel.add(inputNumeroCompte);
        panel.add(txtMontant);
        panel.add(inputMontant);
        panel.add(btnAnnuler);
        panel.add(btnConfirmer);
        panel.add(txtErreurMessage);

        add(panel);

        // Action du bouton
        btnConfirmer.addActionListener(e -> {
            try {
                String numeroCompteDes = inputNumeroCompte.getText();
                double montant = Double.parseDouble(inputMontant.getText());

                Transaction transaction = new Transaction(utilisateurConnectee.getNumeroDeCompte() , numeroCompteDes , montant);
                banque.soumettreTransaction(transaction);
                dispose(); // ferme le dialogue
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Montant invalide !");
            }
        });

        btnAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
}
