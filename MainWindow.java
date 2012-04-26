import javax.swing.AbstractListModel;

public class MainWindow {
	/*
	 * public static void main(String[] args) { Naytto toosa = new Naytto();
	 * toosa.setVisible(true);
	 * 
	 * }
	 */
}

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
// package kirjasto;

/**
 * 
 * @author miksa
 */
class Naytto extends javax.swing.JFrame {
	public AddWriterWindow lisays;
	private Bookshelf kirjahylly;
	private ListaMalli listaMalli;

	/**
	 * Creates new form Naytto
	 */
	public Naytto(Bookshelf kirjahylly) {
		System.out.println("DEBUG: Naytto constructor(v07)");
		this.kirjahylly = kirjahylly;
		this.listaMalli=new ListaMalli();
		// this.kirjahylly = new Bookshelf("Bookshelf", "librarian",
		// "salasana");
		lisays = new AddWriterWindow(kirjahylly);
		initComponents();

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jButton1 = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jList1 = new javax.swing.JList();
		updateWritersJButton = new javax.swing.JButton();
		menuBar = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		openMenuItem = new javax.swing.JMenuItem();
		saveMenuItem = new javax.swing.JMenuItem();
		saveAsMenuItem = new javax.swing.JMenuItem();
		exitMenuItem = new javax.swing.JMenuItem();
		editMenu = new javax.swing.JMenu();
		cutMenuItem = new javax.swing.JMenuItem();
		copyMenuItem = new javax.swing.JMenuItem();
		pasteMenuItem = new javax.swing.JMenuItem();
		deleteMenuItem = new javax.swing.JMenuItem();
		helpMenu = new javax.swing.JMenu();
		contentsMenuItem = new javax.swing.JMenuItem();
		aboutMenuItem = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jButton1.setText("Add Writer");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jLabel1.setText("Writers");

		jList1.setModel(listaMalli);
		jScrollPane1.setViewportView(jList1);

		updateWritersJButton.setText("Update Writers");
		updateWritersJButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						updateWritersJButtonActionPerformed(evt);
					}
				});

		fileMenu.setMnemonic('f');
		fileMenu.setText("File");

		openMenuItem.setMnemonic('o');
		openMenuItem.setText("Open");
		fileMenu.add(openMenuItem);

		saveMenuItem.setMnemonic('s');
		saveMenuItem.setText("Save");
		fileMenu.add(saveMenuItem);

		saveAsMenuItem.setMnemonic('a');
		saveAsMenuItem.setText("Save As ...");
		saveAsMenuItem.setDisplayedMnemonicIndex(5);
		fileMenu.add(saveAsMenuItem);

		exitMenuItem.setMnemonic('x');
		exitMenuItem.setText("Exit");
		exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exitMenuItemActionPerformed(evt);
			}
		});
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);

		editMenu.setMnemonic('e');
		editMenu.setText("Edit");

		cutMenuItem.setMnemonic('t');
		cutMenuItem.setText("Cut");
		editMenu.add(cutMenuItem);

		copyMenuItem.setMnemonic('y');
		copyMenuItem.setText("Copy");
		editMenu.add(copyMenuItem);

		pasteMenuItem.setMnemonic('p');
		pasteMenuItem.setText("Paste");
		editMenu.add(pasteMenuItem);

		deleteMenuItem.setMnemonic('d');
		deleteMenuItem.setText("Delete");
		editMenu.add(deleteMenuItem);

		menuBar.add(editMenu);

		helpMenu.setMnemonic('h');
		helpMenu.setText("Help");

		contentsMenuItem.setMnemonic('c');
		contentsMenuItem.setText("Contents");
		helpMenu.add(contentsMenuItem);

		aboutMenuItem.setMnemonic('a');
		aboutMenuItem.setText("About");
		helpMenu.add(aboutMenuItem);

		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jButton1)
												.addComponent(jLabel1)
												.addComponent(
														jScrollPane1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														136,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														updateWritersJButton))
								.addContainerGap(252, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jButton1)
								.addGap(18, 18, 18)
								.addComponent(jLabel1)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										194,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(updateWritersJButton)
								.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		kirjahylly.suljeYhteys();
		System.exit(0);
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("Debug");
		lisays.setVisible(true);
	}

	private void updateWritersJButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		listaMalli.updateLista();
		jList1.updateUI();
		System.out.println("Debug: updateWritersJButton pressed");
	}// GEN-LAST:event_updateWritersJButtonActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void start() {
		/*
		 * Set the Nimbus look and feel
		 */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Naytto.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Naytto.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Naytto.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Naytto.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				// new Naytto().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JMenuItem aboutMenuItem;
	private javax.swing.JMenuItem contentsMenuItem;
	private javax.swing.JMenuItem copyMenuItem;
	private javax.swing.JMenuItem cutMenuItem;
	private javax.swing.JMenuItem deleteMenuItem;
	private javax.swing.JMenu editMenu;
	private javax.swing.JMenuItem exitMenuItem;
	private javax.swing.JMenu fileMenu;
	private javax.swing.JMenu helpMenu;
	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JList jList1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JMenuItem openMenuItem;
	private javax.swing.JMenuItem pasteMenuItem;
	private javax.swing.JMenuItem saveAsMenuItem;
	private javax.swing.JMenuItem saveMenuItem;
	private javax.swing.JButton updateWritersJButton;

	// End of variables declaration//GEN-END:variables
	private class ListaMalli extends AbstractListModel {
		// new javax.swing.AbstractListModel() {
		String[] strings;
		public ListaMalli(){
			strings = kirjahylly.getWriters();
		}

		public int getSize() {
			return strings.length;
		}

		public Object getElementAt(int i) {
			return strings[i];
		}

		public void updateLista() {
			strings = kirjahylly.getWriters();

		}
	}
}
