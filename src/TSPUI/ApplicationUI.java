package TSPUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Helpers.ComparatorIndividuals;
import Helpers.Logger;
import Helpers.TSPInstance;
import Helpers.TSPLIBHelper;
import TSPSolution.TSPSolution;
import localSearch.LocalSearch;
import mutation.ExchangeMutation;
import mutation.GeneticMutation;
import mutation.InversionMutation;
import parentsSelection.ParentsSelection;
import parentsSelection.RouletteWheelSelection;
import parentsSelection.TournamentSelection;
import recombination.ArcCross;
import recombination.Combination;
import recombination.PMXCross;
import recombination.Recombination;
import survivorSelection.Elitism;
import survivorSelection.SteadyState;
import survivorSelection.SurvivorSelection;

import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;

public class ApplicationUI {

	private JFrame frmAlgoritmoEvolutivo;
	private JTextField txtAlgoritmoEvolutivoPara;
	private JTextField totalPopulation;
	private JTextField crossProb;
	private JTextField mutationProb;
	private String path;
	TSPLIBHelper fh;
	private JTextField iterations;
	protected String fileName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationUI window = new ApplicationUI();
					window.frmAlgoritmoEvolutivo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApplicationUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAlgoritmoEvolutivo = new JFrame();
		frmAlgoritmoEvolutivo.setTitle("Algoritmo Evolutivo");
		frmAlgoritmoEvolutivo.getContentPane().setBackground(SystemColor.activeCaption);
		frmAlgoritmoEvolutivo.setBounds(100, 100, 835, 623);
		frmAlgoritmoEvolutivo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAlgoritmoEvolutivo.getContentPane().setLayout(null);

		txtAlgoritmoEvolutivoPara = new JTextField();
		txtAlgoritmoEvolutivoPara.setEditable(false);
		txtAlgoritmoEvolutivoPara.setBackground(SystemColor.activeCaption);
		txtAlgoritmoEvolutivoPara.setForeground(SystemColor.desktop);
		txtAlgoritmoEvolutivoPara.setFont(new Font("Tahoma", Font.BOLD, 27));
		txtAlgoritmoEvolutivoPara.setBounds(0, 0, 893, 35);
		txtAlgoritmoEvolutivoPara.setText("Algoritmo evolutivo para TSP");
		frmAlgoritmoEvolutivo.getContentPane().add(txtAlgoritmoEvolutivoPara);
		txtAlgoritmoEvolutivoPara.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 82, 795, 2);
		frmAlgoritmoEvolutivo.getContentPane().add(separator);
		JButton btnSelectFile = new JButton("Seleccionar Archivo");

		btnSelectFile.setBounds(10, 48, 183, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(btnSelectFile);

		totalPopulation = new JTextField();
		totalPopulation.setText("200");
		totalPopulation.setBackground(SystemColor.text);
		totalPopulation.setBounds(200, 128, 46, 20);
		frmAlgoritmoEvolutivo.getContentPane().add(totalPopulation);
		totalPopulation.setColumns(10);

		JTextPane txtpnTamaoDeLa = new JTextPane();
		txtpnTamaoDeLa.setBackground(SystemColor.activeCaption);
		txtpnTamaoDeLa.setText("Tama\u00F1o de la poblaci\u00F3n");
		txtpnTamaoDeLa.setEditable(false);
		txtpnTamaoDeLa.setBounds(20, 128, 170, 20);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnTamaoDeLa);

		JTextPane txtpnAplicarBsquedaLocal = new JTextPane();
		txtpnAplicarBsquedaLocal.setBackground(SystemColor.activeCaption);
		txtpnAplicarBsquedaLocal.setEditable(false);
		txtpnAplicarBsquedaLocal.setText("Aplicar b\u00FAsqueda local?");
		txtpnAplicarBsquedaLocal.setBounds(20, 162, 170, 20);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnAplicarBsquedaLocal);

		JRadioButton applyLocalSearch = new JRadioButton("");
		applyLocalSearch.setBackground(SystemColor.activeCaption);
		applyLocalSearch.setSelected(true);
		applyLocalSearch.setBounds(200, 159, 28, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(applyLocalSearch);

		JTextPane txtpnProbabilidadDeCruce = new JTextPane();
		txtpnProbabilidadDeCruce.setBackground(SystemColor.activeCaption);
		txtpnProbabilidadDeCruce.setText("Probabilidad de cruce");
		txtpnProbabilidadDeCruce.setEditable(false);
		txtpnProbabilidadDeCruce.setBounds(341, 128, 143, 20);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnProbabilidadDeCruce);

		crossProb = new JTextField();
		crossProb.setText("0.9");
		crossProb.setBackground(SystemColor.text);
		crossProb.setColumns(10);
		crossProb.setBounds(509, 128, 46, 20);
		frmAlgoritmoEvolutivo.getContentPane().add(crossProb);

		JTextPane txtpnProbabilidadDeMutacin = new JTextPane();
		txtpnProbabilidadDeMutacin.setBackground(SystemColor.activeCaption);
		txtpnProbabilidadDeMutacin.setText("Probabilidad de mutaci\u00F3n");
		txtpnProbabilidadDeMutacin.setEditable(false);
		txtpnProbabilidadDeMutacin.setBounds(341, 162, 143, 34);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnProbabilidadDeMutacin);

		mutationProb = new JTextField();
		mutationProb.setText("0.1");
		mutationProb.setBackground(SystemColor.text);
		mutationProb.setColumns(10);
		mutationProb.setBounds(509, 162, 46, 20);
		frmAlgoritmoEvolutivo.getContentPane().add(mutationProb);

		JTextPane txtpnConfiguracinDeParmetros = new JTextPane();
		txtpnConfiguracinDeParmetros.setEditable(false);
		txtpnConfiguracinDeParmetros.setForeground(SystemColor.desktop);
		txtpnConfiguracinDeParmetros.setBackground(SystemColor.activeCaption);
		txtpnConfiguracinDeParmetros.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtpnConfiguracinDeParmetros.setText("Configuraci\u00F3n de par\u00E1metros");
		txtpnConfiguracinDeParmetros.setBounds(10, 82, 300, 35);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnConfiguracinDeParmetros);

		JTextPane txtpnElegirConfiguracin = new JTextPane();
		txtpnElegirConfiguracin.setEditable(false);
		txtpnElegirConfiguracin.setBackground(SystemColor.activeCaption);
		txtpnElegirConfiguracin.setForeground(SystemColor.desktop);
		txtpnElegirConfiguracin.setText("Configuraci\u00F3n del algoritmo evolutivo");
		txtpnElegirConfiguracin.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtpnElegirConfiguracin.setBounds(10, 226, 412, 35);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnElegirConfiguracin);

		JButton executeBtn = new JButton("Ejecutar");
		executeBtn.setEnabled(false);
		executeBtn.setForeground(Color.WHITE);
		executeBtn.setBackground(new Color(0, 102, 255));
		executeBtn.setBounds(613, 517, 89, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(executeBtn);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAlgoritmoEvolutivo.dispatchEvent(new WindowEvent(frmAlgoritmoEvolutivo, WindowEvent.WINDOW_CLOSING));
			}
		});
		btnCerrar.setForeground(Color.BLACK);
		btnCerrar.setBackground(Color.RED);
		btnCerrar.setBounds(712, 517, 89, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(btnCerrar);

		JTextPane txtpnSeleccinDePadres = new JTextPane();
		txtpnSeleccinDePadres.setEditable(false);
		txtpnSeleccinDePadres.setBackground(SystemColor.activeCaption);
		txtpnSeleccinDePadres.setForeground(SystemColor.desktop);
		txtpnSeleccinDePadres.setText("Selecci\u00F3n de padres");
		txtpnSeleccinDePadres.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtpnSeleccinDePadres.setBounds(10, 272, 183, 35);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnSeleccinDePadres);

		JRadioButton rouletteWheel = new JRadioButton("Rueda de la Ruleta");
		rouletteWheel.setBackground(SystemColor.activeCaption);
		rouletteWheel.setSelected(true);
		rouletteWheel.setBounds(10, 314, 155, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(rouletteWheel);

		JRadioButton tournamentSelection = new JRadioButton("Selecci\u00F3n por Torneo");
		tournamentSelection.setBackground(SystemColor.activeCaption);
		tournamentSelection.setBounds(167, 314, 199, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(tournamentSelection);
		tournamentSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rouletteWheel.setSelected(false);
			}
		});
		rouletteWheel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tournamentSelection.setSelected(false);
			}
		});

		JTextPane txtpnMtodoDeCruzamiento = new JTextPane();
		txtpnMtodoDeCruzamiento.setEditable(false);
		txtpnMtodoDeCruzamiento.setBackground(SystemColor.activeCaption);
		txtpnMtodoDeCruzamiento.setForeground(SystemColor.desktop);
		txtpnMtodoDeCruzamiento.setText("M\u00E9todo de Cruzamiento");
		txtpnMtodoDeCruzamiento.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtpnMtodoDeCruzamiento.setBounds(383, 272, 199, 35);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnMtodoDeCruzamiento);

		JRadioButton pmxCross = new JRadioButton("Cruce PMX");
		pmxCross.setBackground(SystemColor.activeCaption);
		pmxCross.setSelected(true);
		pmxCross.setBounds(389, 314, 92, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(pmxCross);

		JRadioButton arcCross = new JRadioButton("Cruce basado en arcos");
		arcCross.setBackground(SystemColor.activeCaption);
		arcCross.setBounds(506, 314, 170, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(arcCross);

		JRadioButton combination = new JRadioButton("Combinaci\u00F3n");
		combination.setBackground(SystemColor.activeCaption);
		combination.setBounds(678, 314, 123, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(combination);
		combination.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pmxCross.setSelected(false);
				arcCross.setSelected(false);
			}
		});

		pmxCross.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combination.setSelected(false);
				arcCross.setSelected(false);
			}
		});

		arcCross.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pmxCross.setSelected(false);
				combination.setSelected(false);
			}
		});

		JTextPane txtpnMtodoDeMutacin = new JTextPane();
		txtpnMtodoDeMutacin.setEditable(false);
		txtpnMtodoDeMutacin.setBackground(SystemColor.activeCaption);
		txtpnMtodoDeMutacin.setForeground(SystemColor.desktop);
		txtpnMtodoDeMutacin.setText("M\u00E9todo de mutaci\u00F3n");
		txtpnMtodoDeMutacin.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtpnMtodoDeMutacin.setBounds(10, 363, 183, 35);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnMtodoDeMutacin);

		JRadioButton inversionMutation = new JRadioButton("Inversi\u00F3n");
		inversionMutation.setBackground(SystemColor.activeCaption);
		inversionMutation.setSelected(true);
		inversionMutation.setBounds(10, 404, 129, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(inversionMutation);

		JRadioButton exchangeMutation = new JRadioButton("Intercambio");
		exchangeMutation.setBackground(SystemColor.activeCaption);
		exchangeMutation.setBounds(170, 404, 129, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(exchangeMutation);

		exchangeMutation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inversionMutation.setSelected(false);
			}
		});

		inversionMutation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exchangeMutation.setSelected(false);
			}
		});

		JTextPane txtpnSeleccinDeSobrevivientes = new JTextPane();
		txtpnSeleccinDeSobrevivientes.setEditable(false);
		txtpnSeleccinDeSobrevivientes.setBackground(SystemColor.activeCaption);
		txtpnSeleccinDeSobrevivientes.setForeground(SystemColor.desktop);
		txtpnSeleccinDeSobrevivientes.setText("Selecci\u00F3n de Sobrevivientes");
		txtpnSeleccinDeSobrevivientes.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtpnSeleccinDeSobrevivientes.setBounds(383, 363, 283, 35);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnSeleccinDeSobrevivientes);

		JRadioButton elitism = new JRadioButton("Elitismo");
		elitism.setBackground(SystemColor.activeCaption);
		elitism.setSelected(true);
		elitism.setBounds(389, 404, 92, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(elitism);

		JRadioButton steadystate = new JRadioButton("Steady-State");
		steadystate.setBackground(SystemColor.activeCaption);
		steadystate.setBounds(522, 404, 129, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(steadystate);

		JTextPane txtpnTamaoDeLa_1 = new JTextPane();
		txtpnTamaoDeLa_1.setText("Cantidad de Iteraciones");
		txtpnTamaoDeLa_1.setEditable(false);
		txtpnTamaoDeLa_1.setBackground(SystemColor.activeCaption);
		txtpnTamaoDeLa_1.setBounds(587, 128, 137, 20);
		frmAlgoritmoEvolutivo.getContentPane().add(txtpnTamaoDeLa_1);

		iterations = new JTextField();
		iterations.setText("2000");
		iterations.setColumns(10);
		iterations.setBackground(Color.WHITE);
		iterations.setBounds(725, 128, 46, 20);
		frmAlgoritmoEvolutivo.getContentPane().add(iterations);

		JButton btnVerResultadosFinales = new JButton("Ver resultados Finales");
		btnVerResultadosFinales.setEnabled(false);
		btnVerResultadosFinales.setForeground(Color.WHITE);
		btnVerResultadosFinales.setBackground(new Color(0, 102, 255));
		btnVerResultadosFinales.setBounds(455, 517, 143, 23);
		frmAlgoritmoEvolutivo.getContentPane().add(btnVerResultadosFinales);

		steadystate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elitism.setSelected(false);
			}
		});

		elitism.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				steadystate.setSelected(false);
			}
		});

		executeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnVerResultadosFinales.setEnabled(false);
				Logger logger = new Logger(fileName);
				TSPInstance tspData = fh.getTSPInstance();
				ComparatorIndividuals c = new ComparatorIndividuals(tspData);
				Integer totalPopValue = Integer.parseInt(totalPopulation.getText());
				Integer totalIterations = Integer.parseInt(iterations.getText());
				Double mutationProbability = Double.parseDouble(mutationProb.getText());
				Double recombinationProba = Double.parseDouble(crossProb.getText());
				ArrayList<ArrayList<Integer>> population = tspData.generateInitialPopulation(totalPopValue);
				LocalSearch l = null;
				ParentsSelection p;
				SurvivorSelection s;
				GeneticMutation m;
				Recombination r;
				if (applyLocalSearch.isEnabled()) {
					l = new LocalSearch(tspData);
				}
				if (tournamentSelection.isEnabled()) {
					p = new TournamentSelection(population, tspData);
				} else {
					p = new RouletteWheelSelection(population, c, tspData);
				}

				if (inversionMutation.isEnabled()) {
					m = new InversionMutation();
				} else {
					m = new ExchangeMutation();
				}

				if (pmxCross.isEnabled()) {
					r = new PMXCross(tspData.getDimension());
				} else {
					if (arcCross.isEnabled()) {
						r = new ArcCross();
					} else
						r = new Combination();
				}

				if (elitism.isEnabled()) {
					s = new Elitism(population, c, totalPopValue, p, tspData);
				} else {
					s = new SteadyState(c, totalPopValue);
				}
				TSPSolution tspSolution = new TSPSolution(tspData, l, logger, r, m, p, s, totalPopValue);
				tspSolution.setInitialPopulation(population);
				tspSolution.setIterations(totalIterations);
				tspSolution.setMutationProb(mutationProbability);
				tspSolution.setRecombinationProb(recombinationProba);
				tspSolution.runAlgorithm();
				logger.closeFile();
				btnVerResultadosFinales.setEnabled(true);
			}
		});

		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("ATSP FILES", "txt", "text", "atsp");
				file.setFileFilter(filter);
				if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					java.io.File f = file.getSelectedFile();
					path = f.getPath();
					fileName = f.getName();
					fh = new TSPLIBHelper();
					fh.readTSPInstanceFile(path);
					executeBtn.setEnabled(true);
				}
			}
		});

		btnVerResultadosFinales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String path = System.getProperty("user.dir") + "/Resultados/ ";
					Runtime.getRuntime().exec("explorer.exe /select," + path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}
}
